package classes ;

public class Calculator {
	double gravity = 9.8;
	private double buildingHeight;
	private double totalTime;
	private double ballSpeed;


	//construtor de calculadora
	public Calculator(double buildingHeight) {
		if (buildingHeight <= 0) {
			throw new IllegalArgumentException(" Valores não pode ser menores ou igual a zero");
		}
		this.buildingHeight = buildingHeight;	}

	// getters
	public double getTotalTime() {
		return totalTime;
	}
	public double getballSpeed(){
		return ballSpeed;
	}

	// metodo para calcular o tempo da queda s = s0 +v0*T + 1/2 at^2
	//s = 0 + 0*t +1/2 at^2
	//s = gt^2/2
	//2s = gt^2
	//t^2 = 2d/g => t = sqrt(2d/g)
	
	public double CalculateTotalTime() {
		totalTime = Math.sqrt(2*buildingHeight/gravity);
		return totalTime;

	}
	// metodo para calcular a velocidade da bola
	public double CalculateBallSpeed() {
		totalTime = Math.sqrt(2*buildingHeight/gravity);
		ballSpeed = totalTime*gravity;
		return ballSpeed;

	}
}
