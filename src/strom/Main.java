package strom;

import strom.parserClass.Blok;
import strom.parserClass.Parser;
import strom.parserClass.Vyjimky.ChybaSyntaxeException;

import java.nio.charset.StandardCharsets;

public class Main {

    //  04. 04. 2019 - vytvoreni ast pres parser
    //              tridy   -   Blok { konstanty Const, promenne Var, procedury, prikaz   }
    //                                  Const {   konstatnty: Map<String, Double>    }
    //                                  Var   {   promenne: List<String> }
    //                                  Procedury {   identifikator: String, blok: Blok }
    //              pro vsechny spolecny predek Prikaz -   PrikazSet   {   Ident: String,  vyraz:  Vyrazy   }
    //                                                      PrikazCall  {   ident: string   }
    //                                                      PrikazBeginEnd  {   prikz: List<Prikaz> }
    //                                                      PrikazIf    {   podminka: Podminka, prikaz: Prikaz  }
    //                                                      PrikazWhile {   podminka: Podminka, prikaz: Prikaz  }
    //              predek Podminka -   PodminkaOdd    {   Vyrazy: vyraz   }
    //                                  PodBiRovnost {  Vyrazy: levy;    Vyrazy: pravy    }, PodBiVetsi, ...
    //              Term vyraz -    trida na VyrazPromenna {    }, VyrazKonstanta   {   }
    //                              trida na OpPlus {      }, OpMinus...
    //              parser jako takovy slozitost L1 -   proud tokenu a nad nimi delam jne dve operace   -   get()   -   vezme token     // v ArrazList - remove()
    //                                                                                                      peek()  -   nahledne na dalsi token     // v ArrazList - get()
    //      pseudokod   -
    //      funkce blok() {
    //          Const const;
    //          Var vars;
    //          List<Procedura> procedury = new ....;
    //          t := peek();
    //          if (t.type == CONST)
    //              const := nactiConst();
    //          t := peek()
    //          if (t.type == VAR)
    //              var := nactiVar();
    //          while((t := peek()).type == PROCEDURE)
    //              procedury.append(nactiProceduru())
    //          prikaz:=    nactiPrikaz()
    //          ...
    //          return new Blok(..)
    //      }
    //
    //      funkce nactiPrikaz() {
    //      t := peek()
    //      if (t.type == IDENT)
    //           return nactiPrikazSet()
    //      elseif (t.type == CALL)
    //          return nactiPrikazCall();
    //
    //      }
    //
    //      funkce nactiPrikazSet() {
    //      t:= get()
    //      if(t.type != IDENT)
    //          throw Exception
    //      String idnetifikator := t.hodnota
    //      t:=get()
    //      if(t.type != dvojtecka_rovnase)
    //          throw exception
    //      Vyrazy vyraz := nactiVyraz()
    //      ...
    //      return new PrikazSet(...)

    //  28. 03. 2019
    //          rozšíření lexeru o všechna klíčová slova PL/0
    //

    //  vyukovy jazyk PL/0              //  nalezeno: http://www.kiv.zcu.cz/~rohlik/vyuka/fjp/PrekladacePL0_2002/Java_BeranMichal/
    //  poradi:     kod -> LEXER -> TOKENY -> PARSER -> ast

    //  21. 03. lexer - rozklad textu na jednotlive tokeny
    //          z lexeru leze seznam tokenu: typ tokenu a pripadne hodnotu  var x = 1;
    //                                          var
    //                                          ident       x
    //                                          oper_assign
    //                                          literal     1
    //                                          strednik
    //  class Token { typ: TypTokenu; hodnota: Object; }

    // dnes 14.03. prace na stromu - vytvorit strom vyrazu a potom z toho vyplyvnout - prefix, postfix, infix notaci

    //  gramatiky:      Chomskeho,
    //                  LL(1) - staci nam cist jen jeden token dopredu a vime, co dalsiho tam muze byt

    static String algebraicOperation = "2+5*4-8/2";    //  18

    public Main() {
        algebraicOperation = "2+5*4-8/2";    //  18
    }

    public static void main(String[] args) {
        /*AlgebraicTree algebraicLexerTree = new AlgebraicTree();
        //algebraicLexerTree.createTreeFromExpression();
        algebraicLexerTree.createListOfTokens();
        algebraicLexerTree.createTreeFromTokens();*/

        Lexer lexer = new Lexer();
        algebraicOperation = lexer.readFile("../cv4_strom/src/strom/pl0_code.txt", StandardCharsets.UTF_8);
        lexer.createListOfTokensForPL0(algebraicOperation);
        lexer.printTokenList();

        Parser parser = new Parser(lexer.getTokenList());
        try {
            Blok blok = parser.program();
            System.out.println();
            System.out.print(blok.toString());
        } catch (ChybaSyntaxeException e) {
            e.printStackTrace();
        }
    }
}
