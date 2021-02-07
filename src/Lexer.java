public class Lexer {
    // Simple Lexer to remove all spaces and tabs and translate all characters to their following tokens:
    // t - terminal symbol
    // E - epsilon
    // U - union
    // * - 0 or more
    // + - 1 or more
    // ? - optional
    // () - parenthesis

    private boolean isError;
    private String offending;

    public Lexer() {
        isError = false;
        offending = "Unrecognized token(s) - ";
    }

    public enum State{
        // States are only limited to QSTART
        QSTART, QERROR;
    }

    // Simple logic to create a dfa to translate all strings
    public String lexString(String input){
        State currentState = State.QSTART;

        StringBuilder output = new StringBuilder();
        for(int i = 0; i < input.length(); i++){
            char character = input.charAt(i);
            if(character == '(' || character == ')' ||  character == '*' || character == '+' || character == '?'){
                currentState = State.QSTART;
                output.append(character);
            }
            else if(character == 'E'){
                currentState = State.QSTART;
                output.append('e');
            }
            else if(character == 'U'){
                currentState = State.QSTART;
                output.append('u');
            }
            else if(character >= 'a' && character <= 'z' || character >= '0' && character <= '9'){
                currentState = State.QSTART;
                output.append('t');
            }
            else if(character == ' ' || character == '\t'){
                currentState = State.QSTART;
            }
            else{
               currentState = State.QERROR;
            }

            if(currentState == State.QERROR){
                isError = true;
                offending = offending + " " + character;
            }
        }

        return output.toString();
    }

    public boolean isError() {
        return isError;
    }

    public String getOffending() {
        return offending;
    }

//    public boolean isToken(char token){
//        switch (token){
//            case '(':
//            case '[':
//            case ']':
//            case ')':
//            case '+':
//            case '*':
//            case 't':
//                return true;
//            default:
//                return false;
//        }
//    }
}
