/***********************************************************
 * Lexer.java
 * Desc: Performs lexical analysis on a given file.
 *       Uses patterns from Type class to discern  between
 *       the different token types.
 *
 * Ramin Rakhamimov
 * CIS 707
 * Interpreter Project
 **********************************************************/

package interpreter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
    
public class Lexer
{
    public static List<Token> tokenizeFile(String fileName)
    {
        try
        {
            Scanner scanner = new Scanner(new File(fileName)).useDelimiter("\\s+");
            List<Token> tokens = new ArrayList<Token>();

            while(scanner.hasNext())
            {
                String input = scanner.next();                
                Token token = Token.constructToken(input);
                tokens.add(token);
            }

            return tokens;
        }
        catch(java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

