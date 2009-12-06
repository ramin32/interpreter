package interpreter;
import interpreter.Token;
import interpreter.Type;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
    
public class Lexer
{
    public static List<Token> tokenizeFile(String fileName)
    {
        try
        {
            String[] inputList = new Scanner(new File(fileName)).
                                     useDelimiter("\\Z+").
                                     next().
                                     split("\\s+");

            List<Token> tokens = new ArrayList<Token>();

            for(String input: inputList)
            {
                Token token = Token.constructToken(input);
                if(token == null)
                {
                    throw new RuntimeException("Syntax error: " + input);
                }
                tokens.add(token);
            }
            tokens.add(new Token(Type.EOF));

            return tokens;
        }
        catch(java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

