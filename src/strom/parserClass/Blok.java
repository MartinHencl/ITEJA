package strom.parserClass;

import strom.parserClass.Faktory.Konstanta;
import strom.parserClass.Faktory.Promenna;
import strom.parserClass.Prikazy.Prikaz;
import strom.parserClass.Procedura.Procedura;

import java.util.ArrayList;

public class Blok {
    public ArrayList<Konstanta> konstanty;
    public ArrayList<Promenna> promenne;
    public ArrayList<Procedura> procedury;
    public ArrayList<Prikaz> prikazy;

    public Blok() {
        konstanty = new ArrayList<>();
        promenne = new ArrayList<>();
        procedury = new ArrayList<>();
        prikazy = new ArrayList<>();
    }

//    @Override
//    public String toString() {
//        StringBuilder vypis = new StringBuilder();
//        for (Konstanta konst : konstanty) {
//            vypis.append(konst.toString()).append("\n");
//        }
//        for (Promenna prom : promenne) {
//            vypis.append(prom.toString()).append("\n");
//        }
//        for (Procedura proc : procedury) {
//            vypis.append(proc.toString()).append("\n");
//        }
//        for (Prikaz prik : prikazy) {
//            vypis.append(prik.toString()).append("\n");
//        }
//        return vypis.toString();
//    }
}
