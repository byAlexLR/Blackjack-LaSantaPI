import java.awt.*;
import javax.swing.*;

public class BlackjackGUI {
    private double saldo = 1000; // Saldo inicial
    private Mazo mazo;
    private Mano manoJugador;
    private Mano manoCrupier;
    private JPanel gamePanel;
    private JTextArea gameTextArea;
    private JTextField apuestaField;
    private JLabel saldoLabel_1; // Etiqueta para mostrar el saldo actualizado
    private double apuesta;
    private JButton hitButton;
    private JButton standButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackjackGUI().createAndShowGUI());
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new CardLayout());
        RulesPanel rulesPanel = new RulesPanel();
        CreditsPanel creditsPanel = new CreditsPanel();
        gamePanel = new JPanel();

        panel.add(gamePanel, "Juego");
        panel.add(rulesPanel, "Reglas");
        panel.add(creditsPanel, "Créditos");
        creditsPanel.setLayout(new BoxLayout(creditsPanel, BoxLayout.X_AXIS));

        frame.getContentPane().add(panel);

        JPanel buttonPanel = new JPanel();
        JButton playButton = new JButton("JUGAR");
        JButton rulesButton = new JButton("REGLAS");
        JButton creditsButton = new JButton("CRÉDITOS");
        JButton exitButton = new JButton("SALIR");

        playButton.addActionListener(e -> {
            ((CardLayout) panel.getLayout()).show(panel, "JUEGO");
            startGame();
        });
        rulesButton.addActionListener(e -> ((CardLayout) panel.getLayout()).show(panel, "REGLAS"));
        creditsButton.addActionListener(e -> ((CardLayout) panel.getLayout()).show(panel, "CRÉDITOS"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(playButton);
        buttonPanel.add(rulesButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void startGame() {
        mazo = new Mazo();
        manoJugador = new Mano();
        manoCrupier = new Mano();

        SwingUtilities.invokeLater(this::actualizarJuego);

        gameTextArea = new JTextArea(10, 30);
        gameTextArea.setEditable(false);
        gamePanel.setLayout(new BorderLayout());

        JPanel saldoPanel = new JPanel();
        JLabel saldoLabel = new JLabel("SALDO:");
        saldoLabel_1 = new JLabel(String.valueOf(saldo));
        JLabel lblApuesta = new JLabel("APUESTA:");
        apuestaField = new JTextField(10);
        
        JButton apostarButton = new JButton("APOSTAR");
        apostarButton.addActionListener(e -> realizarApuesta());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(gameTextArea));

        JPanel actionPanel = new JPanel();
        hitButton = new JButton("PEDIR");
        standButton = new JButton("PLANTARSE");

        // Inicialmente deshabilitar los botones
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        hitButton.addActionListener(e -> pedirCarta());
        standButton.addActionListener(e -> plantarse());

        actionPanel.add(hitButton);
        actionPanel.add(standButton);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        saldoPanel.setLayout(new FlowLayout());
        saldoPanel.add(saldoLabel);
        saldoPanel.add(saldoLabel_1);
        saldoPanel.add(lblApuesta);
        saldoPanel.add(apuestaField);
        saldoPanel.add(apostarButton);

        gamePanel.add(saldoPanel, BorderLayout.NORTH);
        gamePanel.add(centerPanel, BorderLayout.CENTER);

        gamePanel.revalidate();
        gamePanel.repaint();

        actualizarJuego();
    }

    private void realizarApuesta() {
        try {
            apuesta = Double.parseDouble(apuestaField.getText());
            if (apuesta <= 0 || apuesta > saldo) {
                JOptionPane.showMessageDialog(gamePanel,
                        "Apuesta no válida. Debes apostar una cantidad mayor que 0 y no superior a tu saldo.");
            } else {
                saldo -= apuesta;
                saldoLabel_1.setText(String.valueOf(saldo));
                repartirCartas();
                actualizarJuego();

                // Habilitar los botones después de una apuesta válida
                hitButton.setEnabled(true);
                standButton.setEnabled(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(gamePanel, "Por favor ingresa un número válido para la apuesta.");
        }
    }

    private void repartirCartas() {
        manoJugador.agregarCarta(mazo.repartir());
        manoJugador.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());
    }

    private void actualizarJuego() {
        gameTextArea.setText("");
        gameTextArea.append("\n--- MANO DEL JUGADOR ---\n");
        mostrarMano(manoJugador);
        gameTextArea.append("Valor de la mano del jugador: " + manoJugador.calcularValor() + "\n");

        gameTextArea.append("\n--- MANO DEL CRUPIER ---\n");
        gameTextArea.append("Carta visible del crupier: \n");
        gameTextArea.append(manoCrupier.getCartas().get(0).toString() + "\n");

        if (manoJugador.calcularValor() > 21) {
            gameTextArea.append("\n¡Te has pasado! El crupier gana.\n");
            saldoLabel_1.setText(String.valueOf(saldo));
            endGame();
        }
    }

    private void mostrarMano(Mano mano) {
        for (Carta carta : mano.getCartas()) {
            gameTextArea.append(carta.toString() + "\n");
        }
    }

    private void pedirCarta() {
        if (manoJugador.calcularValor() <= 21) {
            manoJugador.agregarCarta(mazo.repartir());
            actualizarJuego();
        }
    }

    private void plantarse() {
        while (manoCrupier.calcularValor() < 17) {
            manoCrupier.agregarCarta(mazo.repartir());
        }

        gameTextArea.append("\n--- MANO FINAL DEL CRUPIER ---\n");
        mostrarMano(manoCrupier);
        gameTextArea.append("Valor de la mano del crupier: " + manoCrupier.calcularValor() + "\n");

        determinarGanador();
    }

    private void determinarGanador() {
        if (manoCrupier.calcularValor() > 21) {
            gameTextArea.append("\n¡El crupier se ha pasado! ¡Tú ganas!\n");
            saldo += apuesta * 2;
        } else if (manoJugador.calcularValor() > manoCrupier.calcularValor()) {
            gameTextArea.append("\n¡Tú ganas!\n");
            saldo += apuesta * 2;
        } else if (manoJugador.calcularValor() < manoCrupier.calcularValor()) {
            gameTextArea.append("\nEl crupier gana.\n");
        } else {
            gameTextArea.append("\nEs un empate.\n");
            saldo += apuesta;
        }
        saldoLabel_1.setText(String.valueOf(saldo));
        endGame();
    }

    private void endGame() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        int option = JOptionPane.showConfirmDialog(null, "¿Quieres jugar otra ronda?", "Fin de la partida",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            apuestaField.setText("");
            mazo = new Mazo();
            manoJugador = new Mano();
            manoCrupier = new Mano();
            actualizarJuego();
        } else {
            System.exit(0);
        }
    }
}
