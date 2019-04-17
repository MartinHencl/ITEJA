package strom.parserClass.Faktory;

public class Konstanta extends Faktor{
    public String jmeno;
    public int hodnota;

    public Konstanta() {
        jmeno = "";
        hodnota = 0;
    }

//    @Override
//    public String toString() {
//        StringBuilder vypis = new StringBuilder();
//        if (!jmeno.equals("")) {
//            vypis.append(" ").append(jmeno).append(" ");
//        }
//        vypis.append(hodnota);
//        if (vyraz != null) {
//            vypis.append(" ").append(vyraz.toString()).append(" ");
//        }
//        return vypis.toString();
//    }
}
