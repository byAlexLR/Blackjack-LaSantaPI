import java.awt.*;
import javax.swing.*;

public class CreditsPanel extends JPanel {
    private static final long serialVersionUID = 674421160023666790L;

    public CreditsPanel() {
        setLayout(new GridBagLayout());

        // Crear un JLabel para las reglas
        String credits = "<html>" +
                "· <b>Equipo de Desarrollo:</b> LaSanta PI<br>" +
                "· <b>Integrantes:</b> Alejandro Lorenzo, Aday Perdomo, Bautista Barreto, Larian Scerbet y Matías Ariel<br>" +
                "· <b>Versión:</b> 2.0<br>" +
                "· <b>Fecha:</b> 08/02/2025<br>" +
                "</html>";

        JLabel creditsLabel = new JLabel(credits);
        creditsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        creditsLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
        creditsLabel.setOpaque(true);
        creditsLabel.setBorder(BorderFactory.createTitledBorder("Créditos del Juego"));

        // Configurar GridBagConstraints para centrar el JLabel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Cambiar a NONE para centrar
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Añadir el JLabel al panel
        add(creditsLabel, gbc);
    }
}