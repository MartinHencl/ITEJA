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
}
