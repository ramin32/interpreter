package interpreter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import interpreter.Lexer;
import interpreter.TokenValue;
import interpreter.TokenType;

public class SymbolTable
{

    private final Map<String, Object> identifierTable_;
    private final Map<String, Integer> labelTable_;

    public SymbolTable(List<TokenValue> tokens)
    {
        identifierTable_ = new HashMap<String, Object>();
        labelTable_ = extractLabels(tokens);
        System.out.println(tokens);
        System.out.println(labelTable_);
    }

    private static Map<String, Integer> extractLabels(List<TokenValue> tokens)
    {
        Map<String, Integer> labelTable = new HashMap<String, Integer>();
        ListIterator<TokenValue> iterator = tokens.listIterator();
        while(iterator.hasNext())
        {
            int index = iterator.nextIndex();
            TokenValue tokenValue = iterator.next();
            if(tokenValue.is(TokenType.LABEL))
            {
                String label = extractLastChar(tokenValue.getValue());
                labelTable.put(label, index);
                iterator.remove();
            }
        }
        return labelTable;
    }

    public static String extractLastChar(String string)
    {
        return string.substring(0,string.length() - 1);
    }

    public static void main(String... args)
    {
        System.out.println(extractLastChar("Ramin"));
    }



    public int getLabelIndex(String label)
    {
        return labelTable_.get(label);
    }

    public void addIdentifier(String id, Object value)
    {
        if(!(value instanceof Double || value instanceof Boolean))
            throw new IllegalArgumentException("Illegal data type: " + value);
        identifierTable_.put(id, value);
    }
    public Object getIdentifier(String id)
    {
        return identifierTable_.get(id);
    }
}
