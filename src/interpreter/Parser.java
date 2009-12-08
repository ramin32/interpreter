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
import java.util.Scanner;

public class Parser
{
	private final SymbolTable symbolTable_;
	private int index_;
	private final List<Token> tokens_;
	private boolean skipMode_ = false;
	private static final Scanner stdinScanner_ = new Scanner(System.in);

	public Parser(List<Token> tokens)
	{
		Map<String,Integer> labels = extractLabels(tokens);
		symbolTable_ = new SymbolTable();
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

	public void parseProgram()
	{
		if(!indexInRange(index_))
			return;
		parseStatement();
		parseProgram();
	}

	private boolean isTypeAt(Type type, int... index)
	{
		int i = index_;
		if (index.length > 0)
			i = index[0];
		
		return tokens_.get(i).isType(type);
	}

	private void parseStatement()
	{
		if(indexInRange(index_ + 1))
		{
			if(isTypeAt(Type.ASSIGNMENT, index_ + 1))
			{
				parseAssignment();
				return;
			}
			else if(isTypeAt(Type.GOTO))
			{
				if(skipMode_)
				{
					index_ += 2;
					return;
				}
				index_ = symbolTable_.lookupLabel(tokens_.get(index_ + 1).getValue());
				parseProgram();
				return;
			}
			else if(isTypeAt(Type.PRINT))
			{
				index_++;
				Object expr = parseExpression();
				if(!skipMode_)
					System.out.println(expr); 
				return;
			}
		}

		if(isTypeAt(Type.HALT))
		{
			if(!skipMode_)
				System.exit(0);
			index_++;
		}
		else if(isTypeAt(Type.IF) || isTypeAt(Type.IF_FALSE))
		{
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
		if(!skipMode_)
			symbolTable_.addIdentifier(identifier, expr);
	}

	private void parseBooleanStatement()
	{
		Token booleanKeyword = tokens_.get(index_);
		index_++;
		boolean b = (Boolean) parseExpression(); 
		if(booleanKeyword.isType(Type.IF))
		{
			if(b)
				parseStatement();
			else 
				skipStatement();
		}
		else if(booleanKeyword.isType(Type.IF_FALSE))
		{
			if(!b)
				parseStatement();
			else 
				skipStatement();
		}


	}

	private Object parseExpression()
	{
		if(indexInRange(index_ + 2))
		{
			Token token = tokens_.get(index_ + 1);
			if(token.containedIn(Type.OPERATORS))
			{
				Object operand1 = parseValueAt();
				Object operand2 = parseValueAt(index_ + 2);
				Object result = null;
				if(operand1 instanceof Boolean && operand2 instanceof Boolean)
					result = token.getType().evaluate((Boolean) operand1,(Boolean) operand2);
				else if(operand1 instanceof Double && operand2 instanceof Double)
					result = token.getType().evaluate((Double) operand1, (Double) operand2);
				else
					throw new IllegalStateException("Incompatible operands: " + 
													 operand1 + " " + 
													 token + " " + 
													 operand2);
				index_ += 3;
				return result;
			}
		}
		
		if(isTypeAt(Type.MINUS))
		{
			index_ += 2;
			return - (Double) parseValueAt(index_ - 1);
			
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
		{
			Object idValue = symbolTable_.lookupIdentifier(tokens_.get(i).getValue());
			if(idValue == null)
				throw new IllegalStateException(tokens_.get(i).getValue() + " may have not been initialized ");
			return idValue;
		}
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
