package interpreter;
import java.util.List;

public class Token
{
    private final Type type_;
    private final String value_;

    private Token(Type type, String value)
    {
        type_ = type;
        value_ = value;
    }

    public Token(Type type)
    {
        this(type, null);
    }

    public static Token constructToken(String input)
    {
        for(Type type: Type.values())
        {
            if(type.matches(input))
            {
                if(type.containedIn(Type.VARYING_TOKENS))
                    return new Token(type, input);
                else 
                    return new Token(type);
            }
        }
        throw new RuntimeException("Syntax error: " + input);
    }

    public Type getType()
    {
        return type_;
    }

    public String getValue()
    {
        return value_;
    }

    public boolean containedIn(List<Type> types)
    {
        return type_.containedIn(types);
    }

    public boolean isType(Type type)
    {
        return type_ == type;
    }

    public Object evaluate(double x, double y)
    {
        return type_.evaluate(x,y);
    }

    public String toString()
    {
        return type_ + " " + value_;
    }
}
