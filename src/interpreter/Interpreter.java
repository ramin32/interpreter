package interpreter;

import interpreter.Parser;
import interpreter.Lexer;
import interpreter.Token;
import java.util.List;
import java.util.ArrayList;


public class Interpreter
{
    public static void main(String... args)
    {
        if(args.length != 1)
        {
            System.err.println("Error: no input file.");
            return;
        }
        List<Token> tokens = Lexer.tokenizeFile(args[0]);
        Parser parser = new Parser(tokens);
    }
}
