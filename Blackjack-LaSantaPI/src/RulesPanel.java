

import javax.swing.*;

public class RulesPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5548530021237782633L;

	public RulesPanel() {
        JTextArea rulesText = new JTextArea(10, 30);
        rulesText.setText("1. El objetivo es acercarse lo más posible a 21 puntos sin pasarse.\n" +
                "2. Las cartas del 2 al 10 valen su valor nominal.\n" +
                "3. Las cartas J, Q, K valen 10 puntos.\n" +
                "4. El as (A) puede valer 1 o 11 puntos, dependiendo de lo que convenga.\n" +
                "5. El crupier debe plantarse si tiene 17 o más puntos.\n" +
                "6. El jugador puede pedir cartas tantas veces como quiera.\n" +
                "7. Si el jugador supera 21 puntos, pierde automáticamente.\n" +
                "8. Si el crupier se pasa de 21 puntos, gana el jugador.\n" +
                "9. Si ambos tienen el mismo puntaje, el juego termina en empate.");
        rulesText.setEditable(false);
        add(rulesText);
    }
}
