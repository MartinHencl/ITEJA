package strom.parserClass;

import strom.parserClass.Faktory.*;
import strom.parserClass.Podminky.*;
import strom.parserClass.Prikazy.*;
import strom.parserClass.Procedura.Procedura;
import strom.parserClass.Vyjimky.ChybaSyntaxeException;
import strom.parserClass.Vyrazy.*;
import strom.tokenClass.*;

import java.util.ArrayList;

public class Parser {

    public ArrayList<Token> tokenList;
    private int tokenListInitSizeCONST = 0;

    public Parser(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
        tokenListInitSizeCONST = tokenList.size();
    }

    public Blok blok() throws ChybaSyntaxeException {
        Blok blok = new Blok();
        Token t = tokenList.get(0);     //  get == peek()
        if (t.getTypeOfToken() == TokenType.T_CONST) {      //  [ "const" ident "=" číslo {"," ident "=" číslo} ";"]
            blok.konstanty.add(nactiKonstantu());
        }
        t = tokenList.get(0);
        if (t.getTypeOfToken() == TokenType.T_VAR) {        //  [ "var" ident {"," ident} ";"]
            blok.promenne.add(nactiPromennou());
        }
        while (true) {                                      //  { "procedure" ident ";" blok ";" }
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_PROCEDURE) {
                break;
            }
            blok.procedury.add(nactiProceduru());
        }

        blok.prikazy.add(nactiPrikaz());                    //  prikaz

        if (t.getTypeOfToken() == TokenType.T_EOF) {        //  .
            //  konec hry
            return blok;
        }
        return null;
    }

    private Konstanta nactiKonstantu() throws ChybaSyntaxeException {
        //  "const" ident "=" číslo {"," ident "=" číslo} ";"
        Konstanta konst = new Konstanta();
        Token t;
        //  "const"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_CONST) {
            return null;
        }
        tokenList.remove(0);        //  remove == get()

        while (true) {                     //  { ... }
            //  ident
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_IDENTIFIER_GENERAL) {
                throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
            }
            konst.jmeno = (String) tokenList.remove(0).getValueOfToken();

            //  "="
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_EQUAL) {
                throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
            }
            tokenList.remove(0);

            //  číslo
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_NUMBER_GENERAL) {
                throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
            }
            konst.hodnota = (int) tokenList.remove(0).getValueOfToken();

            //  ","
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_COLON) {
                break;
            }
            tokenList.remove(0);

        }
        //   ";"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_SEMICOLON) {
            throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
        }
        tokenList.remove(0);

        return konst;
    }

    private Promenna nactiPromennou() throws ChybaSyntaxeException {
        //  "var" ident {"," ident} ";"
        Promenna prom = new Promenna();
        Token t;
        //  "var"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_VAR) {
            return null;
        }
        tokenList.remove(0);        //  remove == get()

        while (true) {                     //  { ... }
            //  ident
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_IDENTIFIER_GENERAL) {
                throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
            }
            prom.jmeno = (String) tokenList.remove(0).getValueOfToken();

            //  ","
            t = tokenList.get(0);
            if (t.getTypeOfToken() != TokenType.T_COLON) {
                break;
            }
            tokenList.remove(0);

        }
        //   ";"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_SEMICOLON) {
            throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
        }
        tokenList.remove(0);

        return prom;
    }

    private Procedura nactiProceduru() throws ChybaSyntaxeException {
        //  "procedure" ident ";" blok ";"
        Procedura proc = new Procedura();
        Token t;
        //  "procedure"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_PROCEDURE) {
            return null;
        }
        tokenList.remove(0);

        //  ident
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_IDENTIFIER_GENERAL) {
            throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
        }
        proc.jmeno = (String) tokenList.remove(0).getValueOfToken();

        //   ";"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_SEMICOLON) {
            throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
        }
        tokenList.remove(0);

        //   blok
        proc.blok = blok();
        //   ";"
        t = tokenList.get(0);
        if (t.getTypeOfToken() != TokenType.T_SEMICOLON) {
            throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
        }
        tokenList.remove(0);

        return proc;
    }

    private Prikaz nactiPrikaz() throws ChybaSyntaxeException {
        /*    ident ":=" výraz
            | "call" ident
            | "?" ident     // vynecham
            | "!" výraz     // vynecham
            | "begin" příkaz {";" příkaz } "end"
            | "if" podmínka "then" příkaz
            | "while" podmínka "do" příkaz
        */
        Prikaz prikazy = new Prikaz();
        Token t = tokenList.get(0);     //  get == peek()
        switch (t.getTypeOfToken()) {
            //  ident ":=" výraz
            case T_IDENTIFIER_GENERAL:
                //  ident
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_IDENTIFIER_GENERAL) {
                    return null;
                }
                PrikazSet prikazSet = new PrikazSet();
                prikazSet.jmeno = (String) tokenList.remove(0).getValueOfToken();

                //  ":="
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_SET) {
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
                }
                tokenList.remove(0);

                //  "výraz"
                prikazSet.vyraz = nactiVyraz();
                prikazy.prikazyList.add(prikazSet);
                break;
            //  "call" ident
            case T_CALL:
                //  "call"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_CALL) {
                    return null;
                }
                tokenList.remove(0);
                //  ident
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_IDENTIFIER_GENERAL) {
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
                }
                PrikazCall prikazCall = new PrikazCall();
                prikazCall.jmenoProcedury = (String) tokenList.remove(0).getValueOfToken();
                prikazy.prikazyList.add(prikazCall);
                break;
            //  "begin" příkaz {";" příkaz } "end"
            case T_BEGIN:
                //  "begin"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_BEGIN) {
                    return null;
                }
                tokenList.remove(0);

                //  příkaz
                prikazy.prikazyList.add(nactiPrikaz());
                // {";" příkaz }
                while (true) {          //  { ... }
                    //   ";"
                    t = tokenList.get(0);
                    if (t.getTypeOfToken() != TokenType.T_SEMICOLON) {
                        break;
                    }
                    tokenList.remove(0);

                    //  příkaz
                    prikazy.prikazyList.add(nactiPrikaz());
                }
                //  "end"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_END) {
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
                }
                tokenList.remove(0);

                break;
            //  "if" podmínka "then" příkaz
            case T_IF:
                //  "if"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_IF) {
                    return null;
                }
                tokenList.remove(0);

                //  podmínka
                PrikazIf prikazIf = new PrikazIf();
                prikazIf.podminka = nactiPodminku();
                //  "then"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_THEN) {
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
                }
                tokenList.remove(0);

                //  příkaz
                prikazIf.prikaz = nactiPrikaz();
                prikazy.prikazyList.add(prikazIf);
                break;
            //  "while" podmínka "do" příkaz
            case T_WHILE:
                //  "while"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_WHILE) {
                    return null;
                }
                tokenList.remove(0);

                //  podmínka
                PrikazWhile prikazWhile = new PrikazWhile();
                prikazWhile.podminka = nactiPodminku();
                //  "do"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_DO) {
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
                }
                tokenList.remove(0);

                //  příkaz
                prikazWhile.prikaz = nactiPrikaz();
                prikazy.prikazyList.add(prikazWhile);
                break;
                //  "write" vyraz                                     -------     tohle je navic oproti gramatice
            case T_WRITE:
                //  "write"
                t = tokenList.get(0);
                if (t.getTypeOfToken() != TokenType.T_WRITE) {
                    return null;
                }
                tokenList.remove(0);
                //  vyraz
                PrikazWrite prikazWrite = new PrikazWrite();
                prikazWrite.toWrite = nactiVyraz();
            default:
                return null;
        }
        return prikazy;
    }

    private Podminka nactiPodminku() throws ChybaSyntaxeException {
       /*    "odd" výraz
            | výraz ("="|"#"|"<"|"<="|">"|">=") výraz
       */
        Podminka podminka;
        Token t = tokenList.get(0);
        //  "odd"
        t = tokenList.get(0);
        if (t.getTypeOfToken() == TokenType.T_ODD) {
            tokenList.remove(0);

            //  výraz
            podminka = new PodminkaOdd();
            podminka.vyrazLeva = nactiVyraz();
            return podminka;
        } else {    //  výraz ("="|"#"|"<"|"<="|">"|">=") výraz
            //  vyraz
            Vyraz levyVyraz = nactiVyraz();
            //  ("="|"#"|"<"|"<="|">"|">=")
            t = tokenList.get(0);
            switch (t.getTypeOfToken()) {
                case T_EQUAL:
                    podminka = new PodminkaEqual();
                    break;
                case T_BIGGER_EQUAL:
                    podminka = new PodminkaBiggerEqual();
                    break;
                case T_SMALLER_EQUAL:
                    podminka = new PodminkaSmallerEqual();
                    break;
                case T_NOT_EQUAL:
                    podminka = new PodminkaNotEqual();
                    break;
                case T_SMALLER:
                    podminka = new PodminkaSmaller();
                    break;
                case T_BIGGER:
                    podminka = new PodminkaBigger();
                    break;
                default:
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
            }
            tokenList.remove(0);

            //  vyraz
            Vyraz pravyVyraz = nactiVyraz();
            podminka.vyrazLeva = levyVyraz;
            podminka.vyrazPrava = pravyVyraz;
            return podminka;
        }
    }

    private Vyraz nactiVyraz() throws ChybaSyntaxeException {
        //  term { ("+"|"-") term}
        Vyraz vyraz = new Vyraz();
        //  term
        vyraz.termList.add(nactiTerm());
        Token t;
        while (true) {  //  { ... }
            // ("+"|"-")
            t = tokenList.get(0);
            if (t.getTypeOfToken() == TokenType.T_PLUS) {
                vyraz.operaceList.add(new OperacePlus());
                tokenList.remove(0);

            } else if (t.getTypeOfToken() == TokenType.T_MINUS) {
                vyraz.operaceList.add(new OperaceMinus());
                tokenList.remove(0);

            } else {
                break;
            }
            //  term
            vyraz.termList.add(nactiTerm());
        }
        return vyraz;
    }

    private Term nactiTerm() throws ChybaSyntaxeException {
        //  faktor {("*"|"/") faktor}
        Term term = new Term();
        Token t;
        //  faktor
        term.faktorList.add(nactiFaktor());
        while (true) {  //  { ... }
            //  ("*"|"/")
            t = tokenList.get(0);
            if (t.getTypeOfToken() == TokenType.T_MULTIPLY) {
                term.operaceList.add(new OperaceKrat());
                tokenList.remove(0);

            } else if (t.getTypeOfToken() == TokenType.T_DIVIDE) {
                term.operaceList.add(new OperaceDeleno());
                tokenList.remove(0);

            } else {
                break;
            }
            //  faktor
            term.faktorList.add(nactiFaktor());
        }
        return term;
    }

    private Faktor nactiFaktor() throws ChybaSyntaxeException {
        //  ident | číslo | "(" výraz ")"
        Faktor faktor;
        Token t = tokenList.get(0); //  TODO PO 2 CHYBA SKIP WHILE
        switch (t.getTypeOfToken()) {
            case T_IDENTIFIER_GENERAL:
                //  ident
                faktor = new Promenna();
                ((Promenna) faktor).hodnota = (String) t.getValueOfToken();
                tokenList.remove(0);

                break;
            //  číslo
            case T_NUMBER_GENERAL:
                faktor = new Cislo();
                ((Cislo) faktor).hodnota = (int) t.getValueOfToken();
                faktor.vyraz = null;
                tokenList.remove(0);

                break;
            //  "(" výraz ")"
            case T_BRACKET_LEFT:
                faktor = new Faktor();
                faktor.vyraz = nactiVyraz();
                if (t.getTypeOfToken() != TokenType.T_BRACKET_RIGHT) {
                    throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
                }
                tokenList.remove(0);

                break;
            default:
                throw new ChybaSyntaxeException("Chyba syntaxe; trida: " + this.getClass().getSimpleName() + " token.Key: " + t.getTypeOfToken() + " token.Value: " + t.getValueOfToken() + " index: " + (tokenListInitSizeCONST - tokenList.size()));
        }
        return faktor;
    }
}
