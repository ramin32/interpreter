package interpreter;

import java.util.Arrays;
import java.util.List;

public enum Type
{
    IF ("if"),
    GOTO ("goto"),
    PRINT ("print"),
    HALT ("halt"),
    IF_FALSE ("iffalse"),
    MINUS ("minus"),
    LABEL ("(L\\d+:)+"),
    NUMBER ("-?(\\d+)|(\\d*\\.\\d+)"),
    TRUE ("true"),
    FALSE ("false"),
    IDENTIFIER ("\\p{Alpha}\\w*"),
    ASSIGNMENT ("="),
    PLUS ("\\+") {public Object evaluate(double x, double y) { return x + y;}},
    SUBTRACT ("-") {public Object evaluate(double x, double y) { return x - y;}},
    TIMES ("\\*") {public Object evaluate(double x, double y) { return x * y;}},
    DIVIDE ("/") {public Object evaluate(double x, double y) { return x / y;}},
    LESS ("<") {public Object evaluate(double x, double y) { return x < y;}},
    GREATER (">") {public Object evaluate(double x, double y) { return x > y;}},
    LESS_OR_EQUAL ("<=") {public Object evaluate(double x, double y) { return x <= y;}},
    GREATER_OR_EQUAL (">=") {public Object evaluate(double x, double y) { return x >= y;}},
    EQUAL ("=="){public Object evaluate(double x, double y) { return x == y;}
    		     public Object evaluate(boolean x, boolean y) { return x == y ;}},
    NOT_EQUAL ("!="){public Object evaluate(double x, double y) { return x != y;}
			         public Object evaluate(boolean x, boolean y) { return x != y ;}};

    public final static List<Type> OPERATORS = 
        Arrays.asList(new Type[]{ PLUS, SUBTRACT,TIMES, DIVIDE, LESS, GREATER, LESS_OR_EQUAL, GREATER_OR_EQUAL, EQUAL, NOT_EQUAL });
    
    public final static List<Type> VARYING_TOKENS = 
        Arrays.asList(new Type[]{ IDENTIFIER, LABEL, NUMBER });

    private final String pattern_;
    Type(String pattern)
    {
        pattern_ = pattern;
    }

    public boolean containedIn(List<Type> types)
    {
        return types.contains(this); 
    }

    public boolean matches(String input) 
    {
        return input.matches(pattern_);
    }

    public Object evaluate(double x, double y)
    {
        throw new UnsupportedOperationException("Not implemented for: " + this);
    }
    
    public Object evaluate(boolean x, boolean y)
    {
        throw new UnsupportedOperationException("Not implemented for: " + this);
    }
}

