package interpreter;
import java.util.Arrays;
import java.util.List;

public enum TokenType{
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

        public final static List<TokenType> OPERATORS = 
          Arrays.asList(new TokenType[]{ PLUS, MINUS,TIMES, DIVIDE, L, G, LE, GE, E, NE });

        private final String pattern_;
        TokenType(String pattern)
        {
            pattern_ = pattern;
        }

        public boolean isOp()
        {
           return OPERATORS.contains(this); 
        }
        
        public String pattern() 
        {
            return pattern_;
        }

        public Object evaluate(double x, double y)
        {
            throw new UnsupportedOperationException("Not implemented for: " + this);
        }

}
