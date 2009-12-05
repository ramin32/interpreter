package interpreter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import interpreter.Lexer;
import interpreter.TokenValue;
import interpreter.TokenType;

public class Parser
{
    private int index_;
    private final List<TokenValue> tokens_;

    public Parser(List<TokenValue> tokens)
    {
        index_ = 0;
        tokens_ = tokens;

    }

    public void parseProgram()
    {
        parseExpr();
    }

    private void parseAssignment()
    {

       if(tokens_.get(index_).is(TokenType.IDENTIFIER) && tokens_.get(index_ + 1).is(TokenType.ASSIGNMENT))
       {
           index_ += 2;
           symbolTable_.put(tokens_.get(index_).getValue(), parseOperation());
       }
    }

    private Double parseOperation()
    {
        return 0.0;
    }
    
    private Boolean parseRelation()
    {
        return true;
    }
    
    private void parseExpr()
    {
         
       if(tokens_.get(index_).is(TokenType.PRINT))
       {
          System.out.println(tokens_.get(++index_).getValue()); 
       }
       if(tokens_.get(index_).is(TokenType.HALT))
           System.exit(1);
    }
}
