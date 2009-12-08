package interpreter;

import java.util.List;


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
        parser.parseProgram();
    }
}
