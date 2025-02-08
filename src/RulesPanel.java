import java.awt.*;
import javax.swing.*;

public class RulesPanel extends JPanel {
    private static final long serialVersionUID = 5548530021237782633L;

    public RulesPanel() {
        setLayout(new GridBagLayout());

        // Crear un JLabel para las reglas
        String rules = "<html>" +
                "<b>1.</b> El objetivo es acercarse lo más posible a 21 puntos sin pasarse.<br>" +
                "<b>2.</b> Las cartas del 2 al 10 valen su valor nominal.<br>" +
                "<b>3.</b> Las cartas J, Q, K valen 10 puntos.<br>" +
                "<b>4.</b> El as (A) puede valer 1 o 11 puntos, dependiendo de lo que convenga.<br>" +
                "<b>5.</b> El crupier debe plantarse si tiene 17 o más puntos.<br>" +
                "<b>6.</b> El jugador puede pedir cartas tantas veces como quiera.<br>" +
                "<b>7.</b> Si el jugador supera 21 puntos, pierde automáticamente.<br>" +
                "<b>8.</b> Si el crupier se pasa de 21 puntos, gana el jugador.<br>" +
                "<b>9.</b> Si ambos tienen el mismo puntaje, el juego termina en empate." +
                "</html>";

        JLabel rulesLabel = new JLabel(rules);
        rulesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Cambiar a negrita
        rulesLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        rulesLabel.setOpaque(true); // Hacer el JLabel transparente
        rulesLabel.setBorder(BorderFactory.createTitledBorder("Reglas del Juego"));

        // Configurar GridBagConstraints para centrar el JLabel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Cambiar a NONE para centrar
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Añadir el JLabel al panel
        add(rulesLabel, gbc);
    }
}