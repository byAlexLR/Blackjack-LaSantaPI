import java.awt.*;

import javax.swing.*;

public class CreditsPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 674421160023666790L;

    public CreditsPanel() {
        setLayout(new GridBagLayout());
        JTextArea creditsText = new JTextArea(5, 30);
        creditsText.setText("Equipo de Desarrollo: LaSanta PI\n" +
                "Integrantes: Aday Perdomo, Alejandro Lorenzo, Bautista Barreto, Larian Scerbet y Matías Ariel\n" +
                "Versión: 1.0\n" +
                "Fecha: 04/02/2025");
        creditsText.setEditable(false);
        creditsText.setFont(new Font("Arial", Font.BOLD, 14));
        creditsText.setBackground(new Color(240, 240, 240));
        creditsText.setWrapStyleWord(true);
        creditsText.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(creditsText);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        add(scrollPane, gbc);
    }
}
