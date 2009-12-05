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

   public String toString()
   {
       return type_ + " " + value_;
   }
}
