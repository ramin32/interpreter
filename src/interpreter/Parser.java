/*************************************************
 * Predictive Parser for following Grammar:
 *
 * program -> Statement Program | EOF
 * statement -> Assignment | print Expr | halt | goto label
 *              | if BooleanExpr Statement
 *              | iffalse BooleanExpr Statement
 *
 *************************************************/
package interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Parser
{
    private final SymbolTable symbolTable_;
    private int index_;
    private final List<Token> tokens_;
    private boolean skipMode_ = false;

    public Parser(List<Token> tokens)
    {
        Map<String,Integer> labels = extractLabels(tokens);
        symbolTable_ = new SymbolTable();
        System.out.println(tokens);
        System.out.println(labels);
        symbolTable_.addLabelMapping(labels);

        index_ = 0;
        tokens_ = tokens;
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

    public void parse()
    {
        parseProgram(); 
    }

    private void parseProgram()
    {
        if(index_ >= tokens_.size())
            return;
        parseStatement();
        parseProgram();
    }

    private boolean isTypeAt(Type type, int... index)
    {
        int i = index_;
        if (index.length > 0)
            i = index[0];
        if(i == 4)
        	System.out.println();

        return tokens_.get(i).isType(type);
    }

    private void parseStatement()
    {
        if(indexInRange(index_ + 1))
        {
            if(isTypeAt(Type.ASSIGNMENT, index_ + 1))
                parseAssignment();
            else if(isTypeAt(Type.GOTO))
            {
                index_ = symbolTable_.lookupLabel(tokens_.get(index_ + 1).getValue());
                parseProgram();
                return;
            }
            else if(isTypeAt(Type.PRINT))
            {
                index_++;
                System.out.println(parseExpression()); 
            }
            return;
        }

        if(isTypeAt(Type.HALT))
            System.exit(0);
        else if(isTypeAt(Type.IF) || isTypeAt(Type.IF_FALSE))
        {
            System.out.println("Parsing boolean exp");
            parseBooleanStatement();
        }

    }

   
    private void skipStatement()
    {
        skipMode_ = true;
        parseStatement();
        skipMode_ = false;
    }
    private void parseAssignment()
    {
        String identifier = tokens_.get(index_).getValue(); 
        index_ += 2;
        Object expr = parseExpression();
        symbolTable_.addIdentifier(identifier, expr);
    }

    private void parseBooleanStatement()
    {
        Token booleanToken = tokens_.get(index_);
        index_++;
        System.out.println("Processing booleanToken: " + booleanToken);
        boolean b = (Boolean) parseExpression(); 
        System.out.println("Boolean expr = " + b);
        if(booleanToken.isType(Type.IF))
        {
            if(b)
                parseStatement();
            else 
                skipStatement();
        }
        else if(booleanToken.isType(Type.IF_FALSE))
        {
            if(!b)
                parseStatement();
            else 
                skipStatement();
        }


    }

    private Object parseExpression()
    {
        if(indexInRange(index_ + 3))
        {
            Token token = tokens_.get(index_ + 1);
            if(token.containedIn(Type.OPERATORS))
            {
                Object result = token.getType().evaluate((Double) parseValueAt(), 
                                                         (Double) parseValueAt(index_ + 2));
                index_ += 3;
                return result;
            }
        }
        index_++;
        return parseValueAt(index_ - 1);
    } 

    private boolean indexInRange(int index)
    {
        return index < tokens_.size();
    }

    private Object parseValueAt(int...index)
    {
        int i = index_;
        if(index.length > 0)
            i = index[0];
        if(isTypeAt(Type.IDENTIFIER, i))
            return symbolTable_.lookupIdentifier(tokens_.get(i).getValue());
        else if(isTypeAt(Type.NUMBER, i))
            return Double.parseDouble(tokens_.get(i).getValue());
        else if(isTypeAt(Type.FALSE, i))
            return false;
        else if(isTypeAt(Type.TRUE, i))
            return true;
        else 
            return null;

    }

}
