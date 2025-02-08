

import java.util.*;

public class Mano {
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
