

// Clase para representar una carta en formato ASCII
public class Carta {
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
        if (valor.equals("10"))
            middle = "| " + valor + "    |"; // Para el valor 10
        if (valor.length() == 1)
            middle = "|  " + valor + "    |"; // Para los valores 1-9

        String palo = "   ";
        if (this.palo.equals("Corazones"))
            palo = " ♥ ";
        else if (this.palo.equals("Diamantes"))
            palo = " ♦ ";
        else if (this.palo.equals("Tréboles"))
            palo = " ♣ ";
        else if (this.palo.equals("Picas"))
            palo = " ♠ ";

        return topBottom + "\n" +
                middle + "\n" +
                "|  " + palo + "   |" + "\n" +
                topBottom;
    }
}
