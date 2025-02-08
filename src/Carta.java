// Clase para representar una carta en formato ASCII
public class Carta {
    private final String valor;
    private final String palo;

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

    // Representación de la carta en formato ASCII con bordes decorados
    @Override
    public String toString() {

        // Asignar el símbolo del palo correspondiente
        String simboloPalo = switch (palo) {
            case "Corazones" -> "♥";
            case "Diamantes" -> "♦";
            case "Tréboles" -> "♣";
            case "Picas" -> "♠";
            default -> "?";
        };

        // Ajuste del valor en la parte superior izquierda e inferior derecha
        String valorIzq = (valor.equals("10")) ? "10" : " " + valor; // Alineado a la izquierda
        String valorDer = (valor.equals("10")) ? "10" : valor + " "; // Alineado a la derecha

        // Representación de la carta en ASCII con mejor alineación
        return String.format("""
                ┌─────────┐
                │  %s             │
                │                  │
                │       %s        │
                │                  │
                │             %s  │
                └─────────┘
                """,valorIzq, simboloPalo, valorDer);
    }
}
