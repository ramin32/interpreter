package interpreter;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;
import interpreter.Lexer;
import interpreter.Token;
import interpreter.Type;
import interpreter.SymbolTable;

public class Parser
{
    private final SymbolTable symbolTable_;
    public Parser(List<Token> tokens)
    {
        Map<String,Integer> labels = extractLabels(tokens);
        symbolTable_ = new SymbolTable();
        System.out.println(tokens);
        System.out.println(labels);
        symbolTable_.addLabelMapping(labels);
    }

    private static Map<String, Integer> extractLabels(List<Token> tokens)
    {
        Map<String, Integer> labelTable = new HashMap<String, Integer>();
        ListIterator<Token> iterator = tokens.listIterator();
        while(iterator.hasNext())
        {
            int index = iterator.nextIndex();
            Token token = iterator.next();
            if(token.isType(Type.LABEL))
            {
                String[] labels = splitLabels(token.getValue());
                for(String l: labels)
                    labelTable.put(l, index);
                iterator.remove();
            }
        }
        return labelTable;
    }
    public static String[] splitLabels(String string)
    {
        return string.split(":");
    }
}
