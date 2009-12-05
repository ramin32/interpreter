package interpreter;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

import interpreter.Token;
    
public class Lexer
{
    public static List<Token> tokenizeFile(String fileName)
    {
        try
        {
            String[] tokens = new Scanner(new File(fileName)).
                                     useDelimiter("\\Z+").
                                     next().
                                     split("\\s+");
            List<Token> tokenList = new ArrayList<Token>();
            for(String token: tokens)
            {
                Token t = matchToken(token);
                if(t == null)
                {
                    throw new RuntimeException("Syntax error: " + token);
                }
                tokenList.add(t);
            }
            System.out.println(tokenList);
            return tokenList;

        }
        catch(java.io.FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static Token matchToken(String input)
    {
        for(Token token: Token.values())
        {
            if(input.matches(token.pattern()))
            {
                return new Token(tokenEnum, input);
            }
        }
        return null;
    }
}

class TokenValue()
{
    Token token_;
    String value_;

   public TokenValue(Token token, String value)
   {
       token_ = token;
       value_ = value;
   } 

   public String toString()
   {
       return token_ + " " + value_;
   }
}
