package interpreter;

import java.util.List;
import java.util.Arrays;

public enum Type
{
    IF ("if"),
    GOTO ("goto"),
    PRINT ("print"),
    HALT ("halt"),
    IF_FALSE ("iffalse"),
    READ_CH ("readch"),
    LABEL ("(L\\d+:)+"),
    NUMBER ("(\\d+)|(\\d*\\.\\d+)"),
    TRUE ("true"),
    FALSE ("false"),
    IDENTIFIER ("\\p{Alpha}\\w*"),
    ASSIGNMENT ("="),
    PLUS ("\\+") {public Object evaluate(double x, double y) { return x + y;}},
    MINUS ("-") {public Object evaluate(double x, double y) { return x - y;}},
    TIMES ("\\*") {public Object evaluate(double x, double y) { return x * y;}},
    DIVIDE ("/") {public Object evaluate(double x, double y) { return x / y;}},
    L ("<") {public Object evaluate(double x, double y) { return x < y;}},
    G (">") {public Object evaluate(double x, double y) { return x > y;}},
    LE ("<=") {public Object evaluate(double x, double y) { return x <= y;}},
    GE (">=") {public Object evaluate(double x, double y) { return x >= y;}},
    E ("==") {public Object evaluate(double x, double y) { return x == y;}},
    NE ("!=") {public Object evaluate(double x, double y) { return x != y;}};

    public final static List<Type> OPERATORS = 
        Arrays.asList(new Type[]{ PLUS, MINUS,TIMES, DIVIDE, L, G, LE, GE, E, NE });
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
}

