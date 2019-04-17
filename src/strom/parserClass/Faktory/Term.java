package strom.parserClass.Faktory;

import strom.parserClass.Vyrazy.Operace;

import java.util.ArrayList;

public class Term {
    public ArrayList<Faktor> faktorList;
    public ArrayList<Operace> operaceList;

    public Term(){
        faktorList = new ArrayList<>();
        operaceList = new ArrayList<>();
    }

//    @Override
//    public String toString() {
//        StringBuilder vypis = new StringBuilder();
//        for (Faktor faktor : faktorList) {
//            vypis.append(faktor.toString()).append("\n");
//        }
//        for (Operace operace : operaceList) {
//            vypis.append(operace.toString()).append("\n");
//        }
//        return vypis.toString();
//    }
}
