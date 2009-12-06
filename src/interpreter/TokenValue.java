package interpreter;

class TokenValue
{
    TokenType type_;
    String value_;

   public TokenValue(TokenType token, String value)
   {
       type_ = token;
       value_ = value;
   } 

   public TokenType getType()
   {
       return type_;
   }

   public String getValue()
   {
       return value_;
   }

   public boolean is(TokenType type)
   {
       return type == type_;
   }

   public boolean isOp()
   {
       return type_.isOp();
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
