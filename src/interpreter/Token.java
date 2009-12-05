package interpreter;
public enum Token{
        LABEL ("L\\d+:"),
        INT ("\\d+"),
        FLOAT ("\\d+\\\\.\\d+"),
        TRUE ("true"),
        FALSE ("false"),
        IDENTIFIER ("\\p{Alpha}\\w+"),
        IF ("if"),
        GOTO ("goto"),
        print ("print"),
        HALT ("halt"),
        IF_FALSE ("iffalse"),
        ASSIGNMENT ("="),
        PLUS ("\\\\+"),
        MINUS ("-"),
        TIMES ("\\\\*"),
        DIVIDE ("/"),
        L ("<"),
        G (">"),
        LE ("<="),
        GE ("=>"),
        E ("==");

        private final String pattern_;
        Token(String pattern)
        {
            pattern_ = pattern;
        }
        
        public String pattern() {return pattern_;}
}
