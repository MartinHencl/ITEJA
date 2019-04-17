package strom.parserClass.Prikazy;

import strom.parserClass.Podminky.Podminka;

public class PrikazIf extends Prikaz {
    public Podminka podminka = new Podminka();
    public Prikaz prikaz = new Prikaz();

//    @Override
//    public String toString() {
//        return this.getClass().getSimpleName() + " " + podminka.toString() + " " + prikaz.toString() + " ";
//    }
}
