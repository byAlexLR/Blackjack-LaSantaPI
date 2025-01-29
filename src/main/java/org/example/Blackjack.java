import java.util.*;

public class Blackjack {

    // Clase para representar una carta en formato ASCII
    public static class Carta {
        private String valor;
        private String palo;

        public Carta(String valor, String palo) {
            this.valor = valor;
            this.palo = palo;
        }

        public String getValor() {
            return valor;
        }

        public String getPalo() {
            return palo;
        }

        // Representación de la carta en formato ASCII
        @Override
        public String toString() {
            String topBottom = "+-------+";
            String middle = "| " + valor + "     |"; // Ajuste de espaciado para el valor
            if (valor.equals("10")) middle = "| " + valor + "    |";  // Para el valor 10
            if (valor.length() == 1) middle = "|  " + valor + "    |"; // Para los valores 1-9

            String palo = "   ";
            if (this.palo.equals("Corazones")) palo = " ♥ ";
            else if (this.palo.equals("Diamantes")) palo = " ♦ ";
            else if (this.palo.equals("Tréboles")) palo = " ♣ ";
            else if (this.palo.equals("Picas")) palo = " ♠ ";

            return topBottom + "\n" +
                    middle + "\n" +
                    "|  " + palo + "   |" + "\n" +
                    topBottom;
        }
    }

    // Clase para el mazo de cartas
    public static class Mazo {
        private List<Carta> cartas;

        public Mazo() {
            cartas = new ArrayList<>();
            String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
            String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};
            for (String palo : palos) {
                for (String valor : valores) {
                    cartas.add(new Carta(valor, palo));
                }
            }
            Collections.shuffle(cartas);
        }

        public Carta repartir() {
            if (cartas.isEmpty()) {
                return null;
            }
            return cartas.remove(0);
        }
    }

    // Clase para la mano de cartas del jugador o el crupier
    public static class Mano {
        private List<Carta> cartas;

        public Mano() {
            cartas = new ArrayList<>();
        }

        public void agregarCarta(Carta carta) {
            cartas.add(carta);
        }

        public int calcularValor() {
            int valor = 0;
            int ases = 0;
            for (Carta carta : cartas) {
                String valorCarta = carta.getValor();
                if (valorCarta.equals("J") || valorCarta.equals("Q") || valorCarta.equals("K")) {
                    valor += 10;
                } else if (valorCarta.equals("A")) {
                    ases++;
                    valor += 11;
                } else {
                    valor += Integer.parseInt(valorCarta);
                }
            }
            while (valor > 21 && ases > 0) {
                valor -= 10;
                ases--;
            }
            return valor;
        }

        public List<Carta> getCartas() {
            return cartas;
        }

        // Método para mostrar las cartas de la mano
        public void mostrarCartas() {
            for (Carta carta : cartas) {
                System.out.println(carta);
            }
        }
    }

    // Método para mostrar el menú de inicio
    public static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Jugar");
        System.out.println("2. Ver reglas");
        System.out.println("3. Salir");
        System.out.print("Selecciona una opción: ");
    }

    // Método para mostrar las reglas
    public static void mostrarReglas() {
        System.out.println("\n--- REGLAS DEL JUEGO ---");
        System.out.println("1. El objetivo es acercarse lo más posible a 21 puntos sin pasarse.");
        System.out.println("2. Las cartas del 2 al 10 valen su valor nominal.");
        System.out.println("3. Las cartas J, Q, K valen 10 puntos.");
        System.out.println("4. El as (A) puede valer 1 o 11 puntos, dependiendo de lo que convenga.");
        System.out.println("5. El crupier debe plantarse si tiene 17 o más puntos.");
        System.out.println("6. El jugador puede pedir cartas tantas veces como quiera.");
        System.out.println("7. Si el jugador supera 21 puntos, pierde automáticamente.");
        System.out.println("8. Si el crupier se pasa de 21 puntos, gana el jugador.");
        System.out.println("9. Si ambos tienen el mismo puntaje, el juego termina en empate.");
    }

    // Método para jugar una ronda de Blackjack
    public static double jugar(double saldo) {
        Scanner scanner = new Scanner(System.in);
        Mazo mazo = new Mazo();
        Mano manoJugador = new Mano();
        Mano manoCrupier = new Mano();

        // Pedir la apuesta
        double apuesta;
        do {
            System.out.print("\n¿Cuánto dinero deseas apostar? (Saldo disponible: " + saldo + "): ");
            apuesta = scanner.nextDouble();
            if (apuesta <= 0 || apuesta > saldo) {
                System.out.println("Apuesta no válida. Debes apostar una cantidad mayor que 0 y no superior a tu saldo.");
            }
        } while (apuesta <= 0 || apuesta > saldo);

        saldo -= apuesta; // Restar la apuesta al saldo inicial

        // Repartimos dos cartas al jugador y al crupier
        manoJugador.agregarCarta(mazo.repartir());
        manoJugador.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());
        manoCrupier.agregarCarta(mazo.repartir());

        // Empezamos el juego
        boolean jugando = true;
        while (jugando) {
            System.out.println("\n--- BLACKJACK ---");
            System.out.println("Mano del jugador:");
            manoJugador.mostrarCartas();
            System.out.println("Valor de la mano del jugador: " + manoJugador.calcularValor());
            System.out.println("Carta visible del crupier:");
            System.out.println(manoCrupier.getCartas().get(0)); // Mostrar solo la carta visible

            if (manoJugador.calcularValor() > 21) {
                System.out.println("¡Te has pasado! El crupier gana.");
                saldo = 0; // El jugador pierde todo el saldo
                break;
            }

            // Preguntamos al jugador qué quiere hacer
            System.out.print("\n¿Qué deseas hacer? (1 para Pedir / 2 para Plantarse): ");
            int accion = scanner.nextInt();

            if (accion == 1) {
                manoJugador.agregarCarta(mazo.repartir());
            } else if (accion == 2) {
                // El jugador se planta, el crupier juega
                while (manoCrupier.calcularValor() < 17) {
                    manoCrupier.agregarCarta(mazo.repartir());
                }

                // Mostramos las manos finales
                System.out.println("\nMano del jugador:");
                manoJugador.mostrarCartas();
                System.out.println("Valor de la mano del jugador: " + manoJugador.calcularValor());
                System.out.println("Mano del crupier:");
                manoCrupier.mostrarCartas();
                System.out.println("Valor de la mano del crupier: " + manoCrupier.calcularValor());

                // Determinamos al ganador
                if (manoCrupier.calcularValor() > 21) {
                    System.out.println("¡El crupier se ha pasado! ¡Tú ganas!");
                    saldo += apuesta * 2; // El jugador gana el doble de su apuesta
                } else if (manoJugador.calcularValor() > manoCrupier.calcularValor()) {
                    System.out.println("¡Tú ganas!");
                    saldo += apuesta * 2; // El jugador gana el doble de su apuesta
                } else if (manoJugador.calcularValor() < manoCrupier.calcularValor()) {
                    System.out.println("El crupier gana.");
                    saldo -= apuesta; // El jugador pierde su apuesta
                } else {
                    System.out.println("Es un empate.");
                    saldo += apuesta; // El jugador recupera su apuesta
                }

                jugando = false; // El juego termina
            } else {
                System.out.println("Opción no válida. Elige 1 para Pedir o 2 para Plantarse.");
            }
        }

        // Mostrar el saldo final y ofrecer jugar nuevamente si hay saldo
        if (saldo == 0) {
            System.out.println("\nTu saldo ha llegado a 0. ¡Juego terminado!");
        } else {
            System.out.println("\nTu saldo final es: " + saldo);
        }

        return saldo; // Retornar el saldo actualizado
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double saldo = 1000; // Saldo inicial
        boolean jugando = true;

        while (jugando) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    if (saldo > 0) {
                        saldo = jugar(saldo); // Actualizamos el saldo con el retorno de jugar()
                    } else {
                        System.out.println("\nNo tienes suficiente saldo para jugar.");
                    }
                    break;
                case 2:
                    mostrarReglas();
                    break;
                case 3:
                    jugando = false;
                    System.out.println("¡Gracias por jugar!");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }
    }
}