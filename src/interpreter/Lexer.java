package interpreter;
import interpreter.TokenValue;
import interpreter.TokenType;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
    
public class Lexer
{
    public static List<TokenValue> tokenizeFile(String fileName)
    {
        try
        {
            String[] tokens = new Scanner(new File(fileName)).
                                     useDelimiter("\\Z+").
                                     next().
                                     split("\\s+");
            List<TokenValue> tokenList = new ArrayList<TokenValue>();
            for(String token: tokens)
            {
                TokenValue tokenValue = computeTokenValue(token);
                if(tokenValue == null)
                {
                    throw new RuntimeException("Syntax error: " + token);
                }
                tokenList.add(tokenValue);
            }
            return tokenList;

        }
        catch(java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static TokenValue computeTokenValue(String input)
    {
        for(TokenType token: TokenType.values())
        {
            if(input.matches(token.pattern()))
            {
                return new TokenValue(token, input);
            }
        }
        return null;
    }
}

