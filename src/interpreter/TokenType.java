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
        PLUS ("\\+"),
        MINUS ("-"),
        TIMES ("\\*"),
        DIVIDE ("/"),
        L ("<"),
        G (">"),
        LE ("<="),
        GE (">="),
        E ("=="),
        NE ("!=");

        public final static List<TokenType> OPERATORS = Arrays.asList(new TokenType[] 
                                                        { PLUS, MINUS,TIMES, DIVIDE });

        public final static List<TokenType> RELATIONS = Arrays.asList(new TokenType[] 
                                                        { L, G, LE, GE, E, NE });

        private final String pattern_;
        TokenType(String pattern)
        {
            pattern_ = pattern;
        }

        public boolean isOp()
        {
           return OPERATORS.contains(this); 
        }
        
        public boolean isRelation()
        {
           return RELATIONS.contains(this); 
        }

        public String pattern() 
        {
            return pattern_;
        }

}
