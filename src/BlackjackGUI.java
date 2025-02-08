import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.*;

public class BlackjackGUI {

    private double saldo = 1000;
    private Mazo mazo;
    private Mano manoJugador;
    private Mano manoCrupier;
    private JPanel gamePanel;
    private JTextArea jugadorTextArea;
    private JTextArea crupierTextArea;
    private JTextField apuestaField;
    private JLabel saldoLabel_1;
    private double apuesta;
    private JButton hitButton, standButton;
    private static Clip clip;
    private static final Logger LOGGER = Logger.getLogger(BlackjackGUI.class.getName());

    public static void playBackgroundMusic(String filePath, int startSeconds) {
        try {
            File musicFile = new File(filePath);
            if (!musicFile.exists()) {
                LOGGER.log(Level.SEVERE, "El archivo de música no se encontrado: {0}", filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Convertir segundos a microsegundos y verificar rango válido
            long startMicroseconds = startSeconds * 1_000_000L;
            if (startMicroseconds < clip.getMicrosecondLength()) {
                clip.setMicrosecondPosition(startMicroseconds);
            } else {
                LOGGER.warning("El tiempo de inicio excede la duración de la pista.");
            }

            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.log(Level.SEVERE, "Error al reproducir la música", e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackjackGUI().createAndShowGUI());
        playBackgroundMusic("src/music/music.wav", 13);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("BLACKJACK");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new CardLayout());
        RulesPanel rulesPanel = new RulesPanel();
        CreditsPanel creditsPanel = new CreditsPanel();
        gamePanel = new JPanel();

        panel.add(gamePanel, "Juego");
        panel.add(rulesPanel, "Reglas");
        panel.add(creditsPanel, "Créditos");

        frame.getContentPane().add(panel);
        frame.getContentPane().add(createButtonPanel(panel), BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createButtonPanel(JPanel panel) {
        JPanel buttonPanel = new JPanel();
        JButton playButton = new JButton("JUGAR");
        JButton rulesButton = new JButton("REGLAS");
        JButton creditsButton = new JButton("CRÉDITOS");
        JButton exitButton = new JButton("SALIR");

        playButton.addActionListener(e -> {
            ((CardLayout) panel.getLayout()).show(panel, "Juego");

            // Solo iniciar un nuevo juego si no hay una partida en curso
            if (manoJugador == null || manoJugador.getCartas().isEmpty()) {
                startGame();
            }
        });
        rulesButton.addActionListener(e -> ((CardLayout) panel.getLayout()).show(panel, "Reglas"));
        creditsButton.addActionListener(e -> ((CardLayout) panel.getLayout()).show(panel, "Créditos"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(playButton);
        buttonPanel.add(rulesButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);

        return buttonPanel;
    }

    private void startGame() {
        if (manoJugador != null && !manoJugador.getCartas().isEmpty()) {
            return; // No reiniciar si la partida ya comenzó
        }

        mazo = new Mazo();
        manoJugador = new Mano();
        manoCrupier = new Mano();

        gamePanel.setLayout(new BorderLayout());

        gamePanel.add(createSaldoPanel(), BorderLayout.NORTH);
        gamePanel.add(createGamePanels(), BorderLayout.CENTER);
        gamePanel.add(createActionPanel(), BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private JPanel createSaldoPanel() {
        JPanel saldoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        saldoLabel_1 = new JLabel(String.valueOf(saldo + "€"));
        saldoLabel_1.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel saldoLabel = new JLabel("SALDO:");
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblApuesta = new JLabel("APUESTA:");
        apuestaField = new JTextField(10);
        JButton apostarButton = new JButton("APOSTAR");
        apostarButton.addActionListener(e -> realizarApuesta());

        saldoPanel.add(saldoLabel);
        saldoPanel.add(saldoLabel_1);
        saldoPanel.add(lblApuesta);
        saldoPanel.add(apuestaField);
        saldoPanel.add(apostarButton);

        return saldoPanel;
    }

    private JPanel createGamePanels() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        jugadorTextArea = new JTextArea(10, 20);
        jugadorTextArea.setEditable(false);
        crupierTextArea = new JTextArea(10, 20);
        crupierTextArea.setEditable(false);

        centerPanel.add(createPlayerPanel("JUGADOR", jugadorTextArea));
        centerPanel.add(createPlayerPanel("CRUPIER", crupierTextArea));

        return centerPanel;
    }

    private JPanel createPlayerPanel(String title, JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        hitButton = new JButton("PEDIR");
        standButton = new JButton("PLANTARSE");

        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        hitButton.addActionListener(e -> pedirCarta());
        standButton.addActionListener(e -> plantarse());

        actionPanel.add(hitButton);
        actionPanel.add(standButton);

        return actionPanel;
    }

    private void realizarApuesta() {
        try {
            if (apuestaField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(gamePanel, "Ingresa un número válido para la apuesta.");
                return;
            }
    
            apuesta = Double.parseDouble(apuestaField.getText());
    
            if (apuesta <= 0) {
                JOptionPane.showMessageDialog(gamePanel, "La apuesta debe ser mayor a 0.");
                return;
            }
    
            if (apuesta > saldo) {
                JOptionPane.showMessageDialog(gamePanel, "No tienes suficiente saldo para esta apuesta.");
                return;
            }
    
            // Restar apuesta del saldo
            actualizarSaldo(saldo - apuesta);
            repartirCartas();
            actualizarJuego();
    
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(gamePanel, "Ingresa un número válido para la apuesta.");
        }
    }

    private void pedirCarta() {
        if (manoJugador.calcularValor() <= 21) {
            manoJugador.agregarCarta(mazo.repartir());
            actualizarJuego();

            if (manoJugador.calcularValor() > 21) {
                determinarGanador();
            }
        }
    }

    private void plantarse() {
        while (manoCrupier.calcularValor() < 17) {
            manoCrupier.agregarCarta(mazo.repartir());
        }

        crupierTextArea.setText("");
        mostrarMano(manoCrupier, crupierTextArea);
        crupierTextArea.append("Valor: " + manoCrupier.calcularValor() + "\n");

        gamePanel.revalidate();
        gamePanel.repaint();

        Timer timer = new Timer(1000, e -> determinarGanador());
        timer.setRepeats(false);
        timer.start();
    }

    private void repartirCartas() {
        manoJugador.agregarCarta(mazo.repartir());
        manoJugador.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());

        actualizarJuego();
    }

    private void actualizarJuego() {
        jugadorTextArea.setText("");
        crupierTextArea.setText("");
    
        if (!manoJugador.getCartas().isEmpty()) {
            mostrarMano(manoJugador, jugadorTextArea);
            jugadorTextArea.append("Valor: " + manoJugador.calcularValor() + "\n");
        }
    
        if (!manoCrupier.getCartas().isEmpty()) {
            mostrarMano(manoCrupier, crupierTextArea);
        }
    }

    private void mostrarMano(Mano mano, JTextArea textArea) {
        for (Carta carta : mano.getCartas()) {
            textArea.append(carta.toString() + "\n");
        }
    }

    private void determinarGanador() {
        int valorJugador = manoJugador.calcularValor();
        int valorCrupier = manoCrupier.calcularValor();
        String mensaje;

        if (valorJugador > 21) {
            mensaje = "¡Te has pasado! El crupier gana.";
        } else if (valorCrupier > 21 || valorJugador > valorCrupier) {
            mensaje = "¡Has ganado!";
            actualizarSaldo(saldo + (apuesta * 2));
        } else if (valorJugador < valorCrupier) {
            mensaje = "El crupier gana.";
        } else {
            mensaje = "Es un empate.";
            actualizarSaldo(saldo + apuesta);
        }

        JOptionPane.showMessageDialog(gamePanel, mensaje);
        reiniciarJuego();
    }

    private void actualizarSaldo(double nuevoSaldo) {
        double saldoAnterior = saldo;
        saldo = nuevoSaldo;
        saldoLabel_1.setText(String.valueOf(saldo + "€"));

        // Determinar color de parpadeo
        Color colorFlash = (saldo > saldoAnterior) ? Color.GREEN : Color.RED;

        Timer timer = new Timer(200, new ActionListener() {
            private int count = 0;
            private boolean toggle = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                saldoLabel_1.setForeground(toggle ? colorFlash : Color.BLACK);
                toggle = !toggle;
                count++;

                if (count >= 6) { // Parpadea 3 veces (6 cambios)
                    ((Timer) e.getSource()).stop();
                    saldoLabel_1.setForeground(Color.BLACK); // Restablecer color original
                }
            }
        });

        timer.start();
    }

    private void reiniciarJuego() {
        apuestaField.setText("");
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        apuestaField.setEnabled(true);

        // Mantener el saldo y la interfaz, solo resetear manos
        manoJugador = new Mano();
        manoCrupier = new Mano();
        actualizarJuego();
    }
}