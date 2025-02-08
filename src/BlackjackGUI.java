import java.awt.*;
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
    private JButton hitButton;
    private JButton standButton;
    private static Clip clip;
    private static final Logger LOGGER = Logger.getLogger(BlackjackGUI.class.getName());

    public static void playBackgroundMusic(String filePath, int startSeconds) {
        try {
            File musicFile = new File(filePath);
            if (!musicFile.exists()) {
                LOGGER.log(Level.SEVERE, "El archivo de m\u00fasica no se encontr\u00f3: {0}", filePath);
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
        playBackgroundMusic("src/music.wav", 13);
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
            ((CardLayout) panel.getLayout()).show(panel, "Juego");
            startGame();
        });
        rulesButton.addActionListener(e -> ((CardLayout) panel.getLayout()).show(panel, "Reglas"));
        creditsButton.addActionListener(e -> ((CardLayout) panel.getLayout()).show(panel, "Créditos"));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(playButton);
        buttonPanel.add(rulesButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startGame() {
        mazo = new Mazo();
        manoJugador = new Mano();
        manoCrupier = new Mano();

        gamePanel.removeAll();
        gamePanel.setLayout(new BorderLayout());

        JPanel saldoPanel = new JPanel();
        JLabel saldoLabel = new JLabel("SALDO:");
        saldoLabel_1 = new JLabel(String.valueOf(saldo));
        JLabel lblApuesta = new JLabel("APUESTA:");
        apuestaField = new JTextField(10);
        JButton apostarButton = new JButton("APOSTAR");
        apostarButton.addActionListener(e -> realizarApuesta());

        saldoPanel.add(saldoLabel);
        saldoPanel.add(saldoLabel_1);
        saldoPanel.add(lblApuesta);
        saldoPanel.add(apuestaField);
        saldoPanel.add(apostarButton);
        gamePanel.add(saldoPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        // Panel del Jugador con título
        JPanel jugadorPanel = new JPanel(new BorderLayout());
        jugadorPanel.setBorder(BorderFactory.createTitledBorder("JUGADOR"));
        jugadorTextArea = new JTextArea(10, 20);
        jugadorTextArea.setEditable(false);
        jugadorPanel.add(new JScrollPane(jugadorTextArea), BorderLayout.CENTER);

        // Panel del Crupier con título
        JPanel crupierPanel = new JPanel(new BorderLayout());
        crupierPanel.setBorder(BorderFactory.createTitledBorder("CRUPIER"));
        crupierTextArea = new JTextArea(10, 20);
        crupierTextArea.setEditable(false);
        crupierPanel.add(new JScrollPane(crupierTextArea), BorderLayout.CENTER);

        centerPanel.add(jugadorPanel);
        centerPanel.add(crupierPanel);
        gamePanel.add(centerPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        hitButton = new JButton("PEDIR");
        standButton = new JButton("PLANTARSE");
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        hitButton.addActionListener(e -> pedirCarta());
        standButton.addActionListener(e -> plantarse());
        actionPanel.add(hitButton);
        actionPanel.add(standButton);
        gamePanel.add(actionPanel, BorderLayout.SOUTH);

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void realizarApuesta() {
        try {
            apuesta = Double.parseDouble(apuestaField.getText());
            if (apuesta <= 0 || apuesta > saldo) {
                JOptionPane.showMessageDialog(gamePanel, "Apuesta no válida.");
            } else {
                saldo -= apuesta;
                saldoLabel_1.setText(String.valueOf(saldo));
                repartirCartas();
                actualizarJuego();
                hitButton.setEnabled(true);
                standButton.setEnabled(true);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(gamePanel, "Ingresa un número válido.");
        }
    }

    private void repartirCartas() {
        manoJugador.agregarCarta(mazo.repartir());
        manoJugador.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());
    }

    private void actualizarJuego() {
        jugadorTextArea.setText("");
        mostrarMano(manoJugador, jugadorTextArea);
        jugadorTextArea.append("Valor: " + manoJugador.calcularValor() + "\n");

        crupierTextArea.setText("");
        crupierTextArea.append("Carta visible: \n" + manoCrupier.getCartas().get(0).toString() + "\n");
    }

    private void mostrarMano(Mano mano, JTextArea textArea) {
        for (Carta carta : mano.getCartas()) {
            textArea.append(carta.toString() + "\n");
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
        // El crupier juega hasta alcanzar 17 o más
        while (manoCrupier.calcularValor() < 17) {
            manoCrupier.agregarCarta(mazo.repartir());
        }
        
        // Mostrar todas las cartas del crupier antes de mostrar el resultado final
        crupierTextArea.setText(""); 
        mostrarMano(manoCrupier, crupierTextArea);
        crupierTextArea.append("Valor: " + manoCrupier.calcularValor() + "\n");
    
        // Forzar actualización de la UI para mostrar la mano completa del crupier
        gamePanel.revalidate();
        gamePanel.repaint();
    
        // Retrasar la ejecución para que el jugador vea el resultado
        Timer timer = new Timer(1000, e -> determinarGanador());
        timer.setRepeats(false);
        timer.start();
    }    

    private void determinarGanador() {
        int valorJugador = manoJugador.calcularValor();
        int valorCrupier = manoCrupier.calcularValor();

        String mensaje;

        if (valorJugador > 21) {
            mensaje = "¡Te has pasado! El crupier gana.";
        } else if (valorCrupier > 21 || valorJugador > valorCrupier) {
            mensaje = "¡Has ganado!";
            saldo += apuesta * 2;
        } else if (valorJugador < valorCrupier) {
            mensaje = "El crupier gana.";
        } else {
            mensaje = "Es un empate.";
            saldo += apuesta; // Se devuelve la apuesta
        }

        saldoLabel_1.setText(String.valueOf(saldo));
        JOptionPane.showMessageDialog(gamePanel, mensaje);

        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        reiniciarJuego();
    }

    private void reiniciarJuego() {
        apuestaField.setText("");
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        startGame();
    }
}