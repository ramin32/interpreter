package interpreter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import interpreter.Lexer;
import interpreter.Token;

public class SymbolTable
{

    private final Map<String, Object> identifierTable_;
    private final Map<String, Integer> labelTable_;
    

    public SymbolTable()
    {
        identifierTable_ = new HashMap<String, Object>();
        labelTable_ = new HashMap<String, Integer>();
    }

    public void addLabelMapping(Map<String, Integer> labels)
    {
        labelTable_.putAll(labels);
    }

    public int lookupLabel(String label)
    {
        return labelTable_.get(label);
    }

    public void addIdentifier(String id, Object value)
    {
        if(!(value instanceof Double || value instanceof Boolean))
            throw new IllegalArgumentException("Illegal data type: " + value);
        identifierTable_.put(id, value);
    }

    public Object lookupIdentifier(String id)
    {
        return identifierTable_.get(id);
    }
}
