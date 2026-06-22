
package classes;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe principal da Interface Gráfica.
 * Gerencia a janela, inputs do usuário, exibição de resultados e o painel de simulação.
 */
public class Interface extends JFrame implements ActionListener {

	private int validation;
	private double ballS, time , heightBuilding;
	private JTextField BuildingHeightField;
	private JLabel BuildingHeightLabel, ballSpeedLabel, totalTimeLabel;
	private JButton calc, reset, simulateButton;
	private Simulation sim;
	private Calculator calculator;

	/**
	 * Construtor: Inicializa a janela, componentes visuais, layout e configurações iniciais.
	 */
	public Interface() {

		setSize(400, 720);
		setTitle("Calculadora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		// Inicialização de componentes de entrada
		BuildingHeightLabel = new JLabel("Altura do prédio(m):");
		BuildingHeightLabel.setBounds(65, 65, 130, 30);
		BuildingHeightField = new JTextField();
		BuildingHeightField.setBounds(225, 65, 100, 30);

		// Inicialização de botões
		reset = new JButton("Resetar");
		calc = new JButton("Calcular");
		simulateButton = new JButton("Ver Simulação");

		calc.setBounds(65, 105, 130, 30);
		reset.setBounds(225, 105, 100, 30);
		simulateButton.setBounds(140, 250, 120, 30);

		calc.setForeground(new Color(0, 0, 0));
		reset.setForeground(new Color(165, 12, 12));

		// Painel de exibição para Velocidade
		JPanel output1 = new JPanel();
		output1.setBounds(65, 145, 260, 45);
		output1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Velocidade",
				TitledBorder.LEFT, TitledBorder.TOP));
		output1.setLayout(new GridBagLayout());
		ballSpeedLabel = new JLabel();
		ballSpeedLabel.setText("0,00 m/s");
		ballSpeedLabel.setBounds(150, 148, 50, 40);
		ballSpeedLabel.setForeground(new Color(10, 90, 170));
		ballSpeedLabel.setFont(new Font("Arial", Font.BOLD, 16));

		// Painel de exibição para Tempo de queda
		JPanel output2 = new JPanel();
		output2.setBounds(65, 200, 260, 45);
		output2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Tempo de queda",
				TitledBorder.LEFT, TitledBorder.TOP));
		output2.setLayout(new GridBagLayout());
		totalTimeLabel = new JLabel();
		totalTimeLabel.setText("0,00 s" );
		totalTimeLabel.setBounds(150,203,50,40);
		totalTimeLabel.setForeground(new Color(10, 90, 170));
		totalTimeLabel.setFont(new Font("Arial", Font.BOLD, 16));

		// Registro de ouvintes de eventos para os botões
		calc.addActionListener(this);
		reset.addActionListener(this);
		simulateButton.addActionListener(this);

		// Adição dos componentes ao JFrame
		add(output1);
		add(output2);
		add(reset);
		add(calc);
		add(BuildingHeightLabel);
		add(BuildingHeightField);
		output1.add(ballSpeedLabel);
		output2.add(totalTimeLabel);

		// Botão de simulação inicia invisível
		simulateButton.setVisible(false);
		add(simulateButton);

		setVisible(true);
	}

	/**
	 * Gerencia os eventos de clique nos botões: Calcular, Resetar e Simular.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// Lógica do botão Calcular: valida entrada, realiza o cálculo e atualiza a interface
		if(e.getSource()==calc) {
			try{
				String h = BuildingHeightField.getText();
				heightBuilding = Double.parseDouble(h);

				validation = new InputsValidation().validation(heightBuilding);

				if(validation == 1) {
					printErrorMessage("Erro 1","Altura inválida do Prédio! Deve ser maior que 5 e menor que 828" );
				}
				else {
					calculator = new Calculator(heightBuilding);
					ballS= calculator.CalculateBallSpeed();
					time = calculator.CalculateTotalTime();

					String res1 = String.format("%.2f", ballS);
					String res2 = String.format("%.2f", time);
					ballSpeedLabel.setText(res1+" m/s");
					totalTimeLabel.setText(res2+" s" );
					simulateButton.setVisible(true);
				}

			}catch(NumberFormatException ex) {
				printErrorMessage("Erro de Entrada", "Insira dados válidos");
			}
		}
		// Lógica do botão Resetar: limpa campos, reseta resultados e remove a simulação atual
		else if(e.getSource() == reset) {
			BuildingHeightField.setText("");
			totalTimeLabel.setText("0,00s");
			ballSpeedLabel.setText("0,00 m/s");
			simulateButton.setVisible(false);

			if(sim != null) {
				sim.timer.stop();
				remove(sim);
			}

			repaint();
			revalidate();
		}
		// Lógica do botão Ver Simulação: instância e exibe o painel de simulação
		else if(e.getSource() == simulateButton ){

			if(sim != null){
				sim.timer.stop();
				remove(sim);
			}
			sim = new Simulation();
			sim.setBounds(0, 300, 400, 400);
			sim.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "",
					TitledBorder.LEFT, TitledBorder.TOP));
			sim.setLayout(new GridBagLayout());
			add(sim);
			sim.setVisible(true);

			repaint();
			revalidate();
		}
	}

	/**
	 * Exibe janelas de aviso de erro para o usuário.
	 */
	public void printErrorMessage(String title, String error) {
		JOptionPane.showMessageDialog(null,  error, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Classe interna que gerencia a animação física da queda e dos rebotes da bola.
	 */
	public class Simulation extends JPanel{
		private static final double SCALE = 1.2;

		private int pixelsBuildingHeight, buildingPosition;
		double fallCounter;
		private Timer timer;
		private double ballY;
		private int goingUp = 0;
		private int valid =0;

		/**
		 * Configura os parâmetros da simulação, calcula escalas e inicializa o Timer para a animação.
		 */
		public Simulation(){
			setBackground(new Color(45, 205, 218));
			pixelsBuildingHeight = (int)(heightBuilding * SCALE);

			// Ajustes dinâmicos de altura para exibição no painel
			if(pixelsBuildingHeight < 120) {
				pixelsBuildingHeight = 120;
			}
			else if(pixelsBuildingHeight < 210) {
				pixelsBuildingHeight = 210;
			}
			else if(pixelsBuildingHeight > 350) {
				pixelsBuildingHeight = 350;
			}

			buildingPosition = 380-pixelsBuildingHeight;
			ballY = buildingPosition;

			double frameSpeed =  pixelsBuildingHeight/(calculator.getTotalTime()*60);

			// Timer que executa a máquina de estados para movimentar a bolinha
			timer = new Timer(16, e ->{
				switch (goingUp){
					case 0:
						ballY += frameSpeed;
						if (ballY >= 375){
							switch (valid){
								case 0:
									goingUp = 1;
									valid++;
									break;
								case 2:
									goingUp = 2;
									break;
								case 3:
									timer.stop();

									break;
							}
						}
						repaint();
						break;

					case 1:
						ballY-= frameSpeed;
						fallCounter = buildingPosition+((380-buildingPosition)/4)*2.4;
						if (ballY<=fallCounter){
							valid++;
							goingUp = 0;
						}
						repaint();
					    break;

					case 2:
						ballY-=frameSpeed;
						if (ballY<=fallCounter+ fallCounter*0.25){
							goingUp = 0;
							valid++;

						}
						repaint();
						break;
				}
			});
			timer.start();
		}

		/**
		 * Responsável pelo desenho dos elementos gráficos (prédio, chão, bola, etc).
		 */
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Desenho do prédio
			g.setColor(Color.GRAY);
			g.fillRect(160, buildingPosition, 80, pixelsBuildingHeight);
			g.setColor(Color.BLACK);
			g.drawRect(160, buildingPosition, 80, pixelsBuildingHeight);

			// Desenho do chão e porta
			g.setColor(Color.GREEN);
			g.fillRect(1 ,380, 400, 20);
			g.setColor(new Color(81, 80, 80, 255));
			g.fillRect(190, 350, 20,30);

			// Desenho da bola (objeto em movimento)
			g.setColor(Color.red);
			g.fillOval(197, (int)ballY, 7,  7);

			// Desenho das janelas do prédio
			g.setColor(Color.YELLOW);
			for(int y = buildingPosition + 10; y < 340; y += 25){
				g.fillRect(175, y, 10, 10);
				g.fillRect(215, y, 10, 10);
			}

			// Desenho de elementos decorativos (árvores)
			g.setColor(new Color(64, 19, 19));
			g.fillRect(90, 350, 10, 30);
			g.fillRect(300, 350, 10, 30);
			g.setColor(new Color(27, 67, 19));
			g.fillOval(80, 330, 30,25);
			g.fillOval(290, 330, 30,25);
		}
	}
}