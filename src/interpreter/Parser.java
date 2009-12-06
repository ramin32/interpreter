package interpreter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import interpreter.Lexer;
import interpreter.TokenValue;
import interpreter.TokenType;
import interpreter.SymbolTable;

public class Parser
{
    private int index_;
    private final List<TokenValue> tokens_;
    private final SymbolTable symbolTable_;

    public Parser(List<TokenValue> tokens)
    {
        index_ = 0;
        tokens_ = tokens;
        symbolTable_ = new SymbolTable(tokens);
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
           symbolTable_.addIdentifier(tokens_.get(index_).getValue(), parseOperation());
       }
    }

    private Object parseOperation()
    {
        TokenValue tokenValue = tokens_.get(index_ + 1);
        if(tokenValue.isOp())
        {
            Object result = tokenValue.evaluate(extractValue(index_),extractValue(index_ + 2));
            index_ += 3;
            return result;
        }

        index_++;
        return extractValue(index_ - 1);
    }

    private Double extractValue(int index)
    {
        TokenValue tokenValue = tokens_.get(index_);
        if(tokenValue.is(TokenType.IDENTIFIER))
            return (Double) symbolTable_.getIdentifier(tokenValue.getValue());
        else if(tokenValue.is(TokenType.NUMBER))
            return Double.parseDouble(tokenValue.getValue());
        return null;
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
