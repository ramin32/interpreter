package interpreter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import interpreter.Lexer;
import interpreter.TokenValue;
import interpreter.TokenType;

public class SymbolTable
{

    private final Map<String, Object> identifierTable_;
    private final Map<String, Integer> labelTable_;

    public SymbolTable(List<TokenValue> tokens)
    {
        identifierTable_ = new HashMap<String, Double>();
        labelTable_ = computeLabelTable(tokens);
    }

    public static Map<String, Integer> computeLableTable(List<TokenValue> tokens)
    {
        Map<String, Integer> labelTable = new HashMap<String, Integer>();
        for(int i = 0; i < tokens.size(); i++)
            if(tokens.get(i).is(TokenType.LABEL))
                labelTable.put(tokens.get(i).getValue(), i + 1);
        return labelTable;
    }

    public void getLabelIndex(String label)
    {
        return labelTable_.get(label);
    }

    public void addIdentifier(String id, Object value)
    {
        identifierTable_.put(id, value);
    }
    public Object getIdentifier(String id)
    {
        return identifierTable_.get(id);
    }
}
