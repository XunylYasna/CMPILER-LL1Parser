import java.util.HashMap;

public class Parser {

    boolean isError;

    public Parser(){

    }

    public String parse(String lexedString){

        // Since every token is just a character, medyo daya way na string lang yung stack instead of a stack of string, pero parehas lang naman either way
        String stack = "S"; // start the stack
        String input = lexedString;
        input += "$"; //append ending symbol to the lexed input
        String remainingInput = input;

        ParsingTableHelper parsingTableHelper = new ParsingTableHelper();
        HashMap<String, HashMap<String, String>> parsingTable = parsingTableHelper.getParsingTable();
        String error = "";

        isError = false;

        while (stack.length() > 0 && !isError && remainingInput.length() > 0){
            // This serves as peeking the stack
            String currentRule = stack.charAt(stack.length()-1)+"";
            String currentToken;
            System.out.println(remainingInput);
            if(!remainingInput.isEmpty()){
                currentToken = remainingInput.charAt(0)+"";
            }
            else{
                currentToken = "";
            }

            // Extend production rule
            if(isProductionRule(currentRule)){
                String newRule = parsingTable.get(currentRule).get(currentToken);
                // Check if there is look ahead based on the transition table, pag wala error
                if(newRule == null){
                    isError = true;
                    error = "No lookahead on production rule - " + currentRule + " with token " + currentToken;
                }
                else{
                    System.out.println(newRule + "===========prod:" + currentRule + "==========token:" + currentToken + "=======stack: " + stack);
                    //Equivalent of popping the stack
                    stack = stack.substring(0, stack.length() - 1);
                    //Equivalent of adding new stack to the string
                    stack = stack + reverseString(newRule);
                }
            }
            else{
                //The current rule is a terminal so match it to the input
                if(remainingInput.charAt(0) == currentRule.charAt(currentRule.length()-1)){
                    //If the current rule terminal and the remaining input matches
                    //remove from the current input
                    remainingInput = remainingInput.substring(1);
                    //and pop the current rule stack
                    stack = stack.substring(0, stack.length() - 1);
                }
                else{
                    isError = true;
                    error = "Token unmatched: " + remainingInput.charAt(0);
                }
            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }


        if(!isError && remainingInput.isEmpty() && stack.isEmpty()){
            return "Accepted";
        }
        else{
            return "Rejected. " + error;
        }
    }

    public boolean isError() {
        return isError;
    }

    private boolean isProductionRule(String current){
        switch (current){
            case "S":
            case "E":
            case "F":
            case "T":
            case "I":
            case "R":
            case "Y":
                return true;
            default:
                return false;
        }
    }

    private String reverseString(String rule){
        StringBuilder newString = new StringBuilder();

        for(int i = rule.length()-1; i >= 0; i--) {
            newString.append(rule.charAt(i));
        }

        return newString.toString();
    }
}
