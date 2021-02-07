//Creates a parsing table given the following LL1 Grammar

//    E ::= F R     - Entry point
//    F ::= T       - First
//    F ::= e       - Epsilon
//    T ::= I Y     - "Term" + "Symbol"
//    I ::= ( E )   - "Identifier inside parenthesis"
//    I ::= t       - "Identifier only"
//    R ::= u F R   - Handle repeating cases and union
//    R ::= T R
//    R ::= ''
//    Y ::= *       - "Symbols"
//    Y ::= ?
//    Y ::= +
//    Y ::= ''

// Transition Table

//                $	        e	        (   	     )   	    t   	    u   	    *   	    ?   	    +
//    =====================================================================================================================
//    S                    S->E $      S->E $                S->E $
//    E                    E->F R      E->F R                E->F R
//    F                    F->e        F->T                  F->T
//    T                                T->I Y                T->I Y
//    I                                I->(E)                I->t
//    R           R->ε                 R->T R      R->ε      R->T R      R->u F R
//    Y           Y->ε                 Y->ε        Y->ε      Y->ε        Y->ε        Y->*       Y->?         Y->+


import java.util.HashMap;

public class ParsingTableHelper {

    private HashMap<String, HashMap<String, String>> parsingTable;

    public ParsingTableHelper(){
        this.parsingTable = new HashMap<>();
        this.generateParsingTable();
    }


    // Sabi mo sir pwede i hard code ah
    private void generateParsingTable(){

        //S
        HashMap<String, String> sMap = new HashMap<>();
        sMap.put("e", "E$");
        sMap.put("(", "E$");
        sMap.put("t", "E$");
        sMap.put("$", "$");
        this.parsingTable.put("S", sMap);

        //E
        HashMap<String, String> eMap = new HashMap<>();
        eMap.put("e", "FR");
        eMap.put("(", "FR");
        eMap.put("t", "FR");
        this.parsingTable.put("E", eMap);

        //F
        HashMap<String, String> fMap = new HashMap<>();
        fMap.put("e", "e");
        fMap.put("(", "T");
        fMap.put("t", "T");
        this.parsingTable.put("F", fMap);

        //T
        HashMap<String, String> tMap = new HashMap<>();
        tMap.put("(", "IY");
        tMap.put("t", "IY");
        this.parsingTable.put("T", tMap);

        //I
        HashMap<String, String> iMap = new HashMap<>();
        iMap.put("(", "(E)");
        iMap.put("t", "t");
        this.parsingTable.put("I", iMap);

        //R
        HashMap<String, String> rMap = new HashMap<>();
        rMap.put("$", "");
        rMap.put("(", "TR");
        rMap.put(")", "");
        rMap.put("t", "TR");
        rMap.put("u", "uFR");
        rMap.put("","");
        this.parsingTable.put("R", rMap);

        //Y
        HashMap<String, String> yMap = new HashMap<>();
        yMap.put("$", "");
        yMap.put("(", "");
        yMap.put(")", "");
        yMap.put("t", "");
        yMap.put("u", "");
        yMap.put("*", "*");
        yMap.put("?", "?");
        yMap.put("+", "+");
        yMap.put("","");
        this.parsingTable.put("Y", yMap);
    }

    public HashMap<String, HashMap<String, String>> getParsingTable() {
        return parsingTable;
    }

//    public boolean isNullable(char charAt) {
//        switch (charAt){
//            case 'R':
//            case 'Y':
//                return true;
//            default:
//                return false;
//        }
//    }
}
