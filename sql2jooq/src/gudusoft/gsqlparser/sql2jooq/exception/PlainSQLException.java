
package gudusoft.gsqlparser.sql2jooq.exception;

import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TSourceToken;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TResultColumn;
import gudusoft.gsqlparser.nodes.TResultColumnList;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;

public class PlainSQLException extends RuntimeException
{

	private static final long serialVersionUID = -7330249860558971774L;
	private TExpression expression;
	private TCustomSqlStatement stmt;

	public TExpression getExpression( )
	{
		return expression;
	}

	public TCustomSqlStatement getStmt( )
	{
		return stmt;
	}

	public PlainSQLException( TExpression expression, TCustomSqlStatement stmt )
	{
		this.expression = expression;
		this.stmt = stmt;
	}

	public boolean isFromField( )
	{
		TSourceToken startToken = expression.getStartToken( );
		TSourceToken endToken = expression.getEndToken( );

		if ( stmt instanceof TSelectSqlStatement )
		{
			TSelectSqlStatement select = (TSelectSqlStatement) stmt;
			TResultColumnList fields = select.getResultColumnList( );
			for ( int i = 0; i < fields.size( ); i++ )
			{
				TResultColumn column = fields.getResultColumn( i );
				TExpression expr = column.getExpr( );
				if ( expr.getStartToken( ).posinlist <= startToken.posinlist
						&& expr.getEndToken( ).posinlist >= endToken.posinlist )
				{
					return true;
				}
			}
		}
		return false;
	}
}
