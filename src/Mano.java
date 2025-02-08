import java.util.*;

public class Mano {
    private final List<Carta> cartas; // Lista de cartas en la mano

    public Mano() {
        this.cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    public int calcularValor() {
        int valor = 0;
        int ases = 0;

        for (Carta carta : cartas) {
            String valorCarta = carta.getValor();
            switch (valorCarta) {
                case "J", "Q", "K" -> valor += 10;
                case "A" -> {
                    ases++;
                    valor += 11;
                }
                default -> valor += Integer.parseInt(valorCarta);
            }
        }

        // Ajustar el valor del As si la mano supera 21
        while (valor > 21 && ases > 0) {
            valor -= 10;
            ases--;
        }

        return valor;
    }

    public List<Carta> getCartas() {
        return new ArrayList<>(cartas); // Devuelve una copia para evitar modificaciones externas
    }

    // Método para limpiar la mano
    public void limpiarMano() {
        cartas.clear();
    }

    // Método para mostrar todas las cartas en un solo bloque
    public void mostrarCartas() {
        StringBuilder sb = new StringBuilder();
        for (Carta carta : cartas) {
            sb.append(carta).append("\n");
        }
        System.out.println(sb);
    }
}