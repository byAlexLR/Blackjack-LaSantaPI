import java.util.*;

public class Mazo {
    private final List<Carta> cartas; // Lista de cartas en el mazo

    public Mazo() {
        cartas = new ArrayList<>();
        reiniciarMazo(); // Llenar y mezclar el mazo al crearlo
    }

    private void reiniciarMazo() {
        cartas.clear(); // Limpiar mazo antes de llenarlo
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};

        for (String palo : palos) {
            for (String valor : valores) {
                cartas.add(new Carta(valor, palo));
            }
        }

        mezclarMazo();
    }

    public void mezclarMazo() {
        Collections.shuffle(cartas);
    }

    public Carta repartir() {
        if (cartas.isEmpty()) {
            System.out.println("El mazo está vacío. Se reinicia automáticamente.");
            reiniciarMazo();
        }
        return cartas.remove(0);
    }

    public int cartasRestantes() {
        return cartas.size();
    }
}