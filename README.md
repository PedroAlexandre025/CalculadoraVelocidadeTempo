# 🏢⚽ Building Height Calculator

Uma aplicação desenvolvida em **Java utilizando Swing**, capaz de calcular o **tempo de queda** e a **velocidade final** de uma bolinha lançada do topo de um prédio, desconsiderando a resistência do ar. Além dos cálculos físicos, o programa apresenta uma **simulação gráfica da queda e dos rebotes da bola**.

---

## 📌 Sobre o Projeto

O usuário informa a altura do prédio e o sistema calcula:

* ⏱️ O tempo que a bolinha leva para atingir o solo;
* 🚀 A velocidade da bolinha no momento do impacto;
* 🎬 Uma simulação visual da queda e dos quique(s) da bola após atingir o chão.

O projeto foi desenvolvido com fins educacionais e lúdicos, aplicando conceitos básicos de Física e Programação Orientada a Objetos.

---

## ⚙️ Funcionalidades

* Entrada da altura do prédio através de interface gráfica;
* Validação dos dados inseridos;
* Cálculo do tempo total de queda;
* Cálculo da velocidade final da bolinha;
* Simulação animada da queda;
* Simulação de rebotes após o impacto;
* Botão para resetar os cálculos e iniciar uma nova simulação.

---

## 📏 Restrições

A altura do prédio deve estar entre:

* **Maior que 5 metros**
* **Menor ou igual a 828 metros**

Caso um valor inválido seja informado, o sistema exibirá uma mensagem de erro.

---

## 🧮 Fórmulas Utilizadas

A aceleração da gravidade considerada é:

g = 9,8 m/s²

### Tempo de queda

t = √(2h / g)

Onde:

* `t` = tempo de queda (s)
* `h` = altura do prédio (m)
* `g` = aceleração da gravidade (9,8 m/s²)

### Velocidade final

v = g × t

Onde:

* `v` = velocidade final (m/s)
* `g` = aceleração da gravidade (9,8 m/s²)
* `t` = tempo de queda (s)

**Observação:** Os cálculos desconsideram completamente a resistência do ar.

---

## 🖥️ Tecnologias Utilizadas

* Java
* Java Swing
* Programação Orientada a Objetos (POO)
* Timer (javax.swing.Timer)

---

## 📂 Estrutura do Projeto

classes/
├── Calculator.java          # Responsável pelos cálculos físicos
├── InputsValidation.java    # Validação da altura informada
└── Interface.java           # Interface gráfica e simulação

---

## ▶️ Como Executar

1. Clone este repositório:

git clone https://github.com/seu-usuario/seu-repositorio.git

2. Abra o projeto em uma IDE Java, como:

* Eclipse;
* IntelliJ IDEA;
* NetBeans.

3. Execute a classe responsável por iniciar a aplicação.

4. Digite a altura do prédio e clique em **"Calcular"**.

5. Após visualizar os resultados, clique em **"Ver Simulação"** para assistir à animação da queda da bolinha.

---

## 🎥 Simulação

Após os cálculos, o programa exibe uma animação contendo:

* O prédio proporcional à altura informada;
* A bola iniciando a queda do topo do prédio;
* O impacto com o solo;
* Rebotes com alturas reduzidas;
* Elementos visuais decorativos para tornar a experiência mais intuitiva.

---

## 🎯 Objetivo

Este projeto tem como objetivo demonstrar, de forma simples e visual, a aplicação de conceitos de Física em conjunto com técnicas de desenvolvimento de interfaces gráficas em Java.

---

## 👨‍💻 Autor

Desenvolvido por **Pedro Alexandre, Andrew Mickael e Anthony Sotti** como projeto acadêmico para estudo de Física, Java Swing e Programação Orientada a Objetos.
