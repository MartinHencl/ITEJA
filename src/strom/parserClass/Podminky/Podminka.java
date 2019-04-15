package strom.parserClass.Podminky;

import strom.parserClass.Vyrazy.Vyraz;

public class Podminka {
    public Vyraz vyrazLeva;
    public Vyraz vyrazPrava;

    public Podminka() {
        vyrazLeva = new Vyraz();
        vyrazPrava = new Vyraz();
    }
}
