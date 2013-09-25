
package gudusoft.gsqlparser.sql2jooq;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TBaseType;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TFunctionCall;
import gudusoft.gsqlparser.nodes.TGroupByItem;
import gudusoft.gsqlparser.nodes.TGroupByItemList;
import gudusoft.gsqlparser.nodes.TJoin;
import gudusoft.gsqlparser.nodes.TJoinItem;
import gudusoft.gsqlparser.nodes.TOrderByItem;
import gudusoft.gsqlparser.nodes.TOrderByItemList;
import gudusoft.gsqlparser.nodes.TResultColumn;
import gudusoft.gsqlparser.nodes.TTable;
import gudusoft.gsqlparser.nodes.TTableList;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import gudusoft.gsqlparser.stmt.TMergeSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;

import java.io.File;

public class jooqConverter
{

	private String errorMessage;
	private int resultCode;
	private StringBuffer convertResult = new StringBuffer( );

	public String getConvertResult( )
	{
		return convertResult.toString( );
	}

	public String getErrorMessage( )
	{
		return errorMessage;
	}

	public int getResultCode( )
	{
		return resultCode;
	}

	private void clearResult( )
	{
		errorMessage = null;
		resultCode = 0;
		convertResult.delete( 0, convertResult.length( ) );
	}

	public jooqConverter( EDbVendor vendor, String sqlText )
	{
		TGSqlParser sqlparser = new TGSqlParser( vendor );
		sqlparser.sqltext = sqlText;
		convert( sqlparser );
	}

	public jooqConverter( EDbVendor vendor, File sqlFile )
	{
		TGSqlParser sqlparser = new TGSqlParser( vendor );
		sqlparser.sqlfilename = sqlFile.getAbsolutePath( );
		convert( sqlparser );
	}

	private void convert( TGSqlParser sqlparser )
	{
		clearResult( );

		resultCode = sqlparser.parse( );
		if ( resultCode != 0 )
		{
			errorMessage = sqlparser.getErrormessage( );
			return;
		}

		for ( int i = 0; i < sqlparser.getSqlstatements( ).size( ); i++ )
		{
			TCustomSqlStatement stmt = sqlparser.getSqlstatements( ).get( i );
			if ( stmt instanceof TSelectSqlStatement )
			{
				convertSelectStmt( (TSelectSqlStatement) stmt );
			}
			else if ( stmt instanceof TInsertSqlStatement )
			{
				convertInsertStmt( (TInsertSqlStatement) stmt );
			}
			else if ( stmt instanceof TDeleteSqlStatement )
			{
				converDeleteStmt( (TDeleteSqlStatement) stmt );
			}
			else if ( stmt instanceof TUpdateSqlStatement )
			{
				convertUpdateStmt( (TUpdateSqlStatement) stmt );
			}
			else if ( stmt instanceof TMergeSqlStatement )
			{
				convertMergeStmt( (TMergeSqlStatement) stmt );
			}
		}

	}

	private void convertMergeStmt( TMergeSqlStatement stmt )
	{
		// TODO Auto-generated method stub

	}

	private void convertUpdateStmt( TUpdateSqlStatement stmt )
	{
		// TODO Auto-generated method stub

	}

	private void converDeleteStmt( TDeleteSqlStatement stmt )
	{
		// TODO Auto-generated method stub

	}

	private void convertInsertStmt( TInsertSqlStatement stmt )
	{
		// TODO Auto-generated method stub

	}

	private void convertSelectStmt( TSelectSqlStatement stmt )
	{
		convertResult.append( "DSLContext create = DSL.using(conn, SQLDialect." )
				.append( stmt.dbvendor.toString( ).substring( 3 ).toUpperCase( ) )
				.append( ");\n" );

		TTableList tables = stmt.tables;
		if ( tables != null )
		{
			for ( int i = 0; i < tables.size( ); i++ )
			{
				TTable table = tables.getTable( i );
				if ( table.getAliasClause( ) != null )
				{
					convertResult.append( firstUpper( table.getName( ) ) )
							.append( " " )
							.append( table.getAliasClause( )
									.toString( )
									.toLowerCase( ) )
							.append( " = " )
							.append( getTableRealName( table ) )
							.append( ".as(\"" )
							.append( table.getAliasClause( )
									.toString( )
									.toLowerCase( ) )
							.append( "\");\n" );
				}
			}
		}

		convertResult.append( "Result result = create.select( " );
		if ( stmt.getResultColumnList( ) != null )
		{
			for ( int i = 0; i < stmt.getResultColumnList( ).size( ); i++ )
			{
				TResultColumn column = stmt.getResultColumnList( )
						.getResultColumn( i );
				if ( column.getExpr( ).toString( ).indexOf( '*' ) != -1 )
				{
					if ( column.getExpr( ).toString( ).trim( ).length( ) == 1 )
					{
						continue;
					}
					else
					{
						convertResult.append( getExpressionColumnName( column.getExpr( ),
								tables ) );
						if ( i < stmt.getResultColumnList( ).size( ) - 1 )
						{
							convertResult.append( ", " );
						}
					}
				}
				else
				{
					convertResult.append( getColumnName( column, tables ) );
					if ( i < stmt.getResultColumnList( ).size( ) - 1 )
					{
						convertResult.append( ", " );
					}
				}
			}
		}
		convertResult.append( " )\n\t" );

		convertResult.append( ".from( " );

		boolean closeFrom = false;

		if ( stmt.joins != null )
		{
			for ( int i = 0; i < stmt.joins.size( ); i++ )
			{
				TJoin join = stmt.joins.getJoin( i );
				appendTable( join.getTable( ) );

				if ( join.getJoinItems( ) != null
						&& join.getJoinItems( ).size( ) > 0 )
				{
					if ( !closeFrom )
					{
						closeFrom = true;
						convertResult.append( " )\n\t" );
					}

					for ( int j = 0; j < join.getJoinItems( ).size( ); j++ )
					{
						TJoinItem joinItem = join.getJoinItems( )
								.getJoinItem( j );
						convertResult.append( ".join( " );
						appendTable( joinItem.getTable( ) );
						convertResult.append( " ).on( " );
						appendExpression( joinItem.getOnCondition( ), stmt );
						convertResult.append( " )\n\t" );
					}
				}

				if ( !closeFrom )
				{
					if ( i < stmt.joins.size( ) - 1 )
					{
						convertResult.append( ", " );
					}
				}
			}

			if ( !closeFrom )
			{
				convertResult.append( " )\n\t" );
			}
		}

		if ( stmt.getWhereClause( ) != null )
		{
			convertResult.append( ".where( " );
			TExpression expression = stmt.getWhereClause( ).getCondition( );
			appendExpression( expression, stmt );
			convertResult.append( " )\n\t" );
		}

		if ( stmt.getGroupByClause( ) != null )
		{
			convertResult.append( ".groupBy( " );
			TGroupByItemList items = stmt.getGroupByClause( ).getItems( );
			for ( int i = 0; i < items.size( ); i++ )
			{
				TGroupByItem item = items.getGroupByItem( i );
				appendExpression( item.getExpr( ), stmt );
				if ( i < items.size( ) - 1 )
				{
					convertResult.append( ", " );
				}
			}
			convertResult.append( " )\n\t" );

			if ( stmt.getGroupByClause( ).getHavingClause( ) != null )
			{
				convertResult.append( ".having( " );
				appendExpression( stmt.getGroupByClause( ).getHavingClause( ),
						stmt );
				convertResult.append( " )\n\t" );
			}
		}

		if ( stmt.getOrderbyClause( ) != null )
		{
			convertResult.append( ".orderBy( " );
			TOrderByItemList items = stmt.getOrderbyClause( ).getItems( );
			for ( int i = 0; i < items.size( ); i++ )
			{
				TOrderByItem item = items.getOrderByItem( i );
				appendExpression( item.getSortKey( ), stmt );
				if ( item.getSortType( ) == TBaseType.srtAsc )
				{
					convertResult.append( ".asc( )" );
				}
				else if ( item.getSortType( ) == TBaseType.srtDesc )
				{
					convertResult.append( ".desc( )" );
				}
				if ( i < items.size( ) - 1 )
				{
					convertResult.append( ", " );
				}
			}
			convertResult.append( " )\n\t" );
		}

		if ( stmt.getLimitClause( ) != null
				&& stmt.getLimitClause( ).getRow_count( ) != null )
		{
			convertResult.append( ".limit( " );
			convertResult.append( stmt.getLimitClause( ).getRow_count( ) );
			convertResult.append( " )\n\t" );
		}

		if ( stmt.getForUpdateClause( ) != null )
		{
			convertResult.append( ".forUpdate( )\n\t" );
		}

		int index = convertResult.lastIndexOf( "\n" );
		if ( convertResult.substring( index ).trim( ).length( ) == 0 )
		{
			convertResult.delete( index, convertResult.length( ) );
		}
		convertResult.append( ".fetch( );\n" );
	}

	private String getTableName( TTable table )
	{
		if ( table.getAliasClause( ) != null )
		{
			return table.getAliasClause( ).toString( ).toLowerCase( );
		}
		else
		{
			return getTableRealName( table );
		}
	}

	private String getTableRealName( TTable table )
	{
		return firstUpper( table.getTableName( ).toString( ) )
				+ "."
				+ table.getTableName( ).toString( ).toUpperCase( );
	}

	private void appendTable( TTable table )
	{
		convertResult.append( getTableName( table ) );
	}

	private void appendExpression( TExpression expression,
			TCustomSqlStatement stmt )
	{
		switch ( expression.getExpressionType( ) )
		{
			case simple_comparison_t :
				appendComparisonExpression( expression, stmt );
				break;
			case simple_object_name_t :
				appendObjectNameExpression( expression, stmt );
				break;
			case logical_and_t :
				appendLogicExpression( expression, stmt );
				break;
			case simple_constant_t :
			{
				String content = getConstantExpression( expression );
				convertResult.append( content );
			}
				break;
			case function_t :
				convertResult.append( getFunction( expression, stmt.tables ) );
				break;
		}
	}

	private String getConstantExpression( TExpression expression )
	{
		String content = expression.toString( );
		if ( content.startsWith( "'" ) && content.endsWith( "'" ) )
		{
			content = "\""
					+ content.substring( 1, content.length( ) - 1 )
					+ "\"";
		}
		return content;
	}

	private void appendLogicExpression( TExpression expression,
			TCustomSqlStatement stmt )
	{
		appendExpression( expression.getLeftOperand( ), stmt );
		convertResult.append( ".and( " );
		appendExpression( expression.getRightOperand( ), stmt );
		convertResult.append( " )" );
	}

	private void appendObjectNameExpression( TExpression expression,
			TCustomSqlStatement stmt )
	{
		convertResult.append( getObjectColumnName( expression.toString( ),
				stmt.tables ) );

	}

	private void appendComparisonExpression( TExpression expr,
			TCustomSqlStatement stmt )
	{
		if ( expr.getComparisonOperator( ).tokencode == (int) '=' )
		{
			appendExpression( expr.getLeftOperand( ), stmt );
			convertResult.append( ".equal( " );
			appendExpression( expr.getRightOperand( ), stmt );
			convertResult.append( " )" );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.not_equal )
		{
			appendExpression( expr.getLeftOperand( ), stmt );
			convertResult.append( ".notEqual( " );
			appendExpression( expr.getRightOperand( ), stmt );
			convertResult.append( " )" );
		}
		else if ( expr.getComparisonOperator( ).tokencode == (int) '>' )
		{
			appendExpression( expr.getLeftOperand( ), stmt );
			convertResult.append( ".greaterThan( " );
			appendExpression( expr.getRightOperand( ), stmt );
			convertResult.append( " )" );
		}
		else if ( expr.getComparisonOperator( ).tokencode == (int) '<' )
		{
			appendExpression( expr.getLeftOperand( ), stmt );
			convertResult.append( ".lessThan( " );
			appendExpression( expr.getRightOperand( ), stmt );
			convertResult.append( " )" );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.less_equal )
		{
			appendExpression( expr.getLeftOperand( ), stmt );
			convertResult.append( ".lessOrEqual( " );
			appendExpression( expr.getRightOperand( ), stmt );
			convertResult.append( " )" );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.great_equal )
		{
			appendExpression( expr.getLeftOperand( ), stmt );
			convertResult.append( ".greaterOrEqual( " );
			appendExpression( expr.getRightOperand( ), stmt );
			convertResult.append( " )" );
		}
	}

	private String getColumnName( TResultColumn column, TTableList tables )
	{
		return getExpressionColumnName( column.getExpr( ), tables );
	}

	private String getExpressionColumnName( TExpression expr, TTableList tables )
	{
		switch ( expr.getExpressionType( ) )
		{
			case simple_object_name_t :
				return getObjectColumnName( expr.toString( ), tables );
			case subquery_t :
				break;
			case simple_constant_t :
				return "inline( " + getConstantExpression( expr ) + " )";
			case function_t :
				return getFunction( expr, tables );
			default :
				break;
		}
		return "";
	}

	private String getFunction( TExpression expression, TTableList tables )
	{
		StringBuffer buffer = new StringBuffer( );
		TFunctionCall function = expression.getFunctionCall( );
		String content = function.toString( ).toLowerCase( );
		content = content.substring( 0, content.indexOf( '(' ) );
		buffer.append( "DSL." );
		buffer.append( content );
		buffer.append( "( " );
		for ( int i = 0; i < function.getArgs( ).size( ); i++ )
		{
			TExpression arg = function.getArgs( ).getExpression( i );
			if ( arg.toString( ).indexOf( '*' ) != -1 )
			{
				if ( arg.toString( ).trim( ).length( ) == 1 )
				{
					continue;
				}
				else
				{
					buffer.append( arg.toString( )
							.replace( "*", "" )
							.toLowerCase( ) );
					if ( i < function.getArgs( ).size( ) - 1 )
					{
						buffer.append( ", " );
					}
				}
			}
			else
			{
				buffer.append( getExpressionColumnName( arg, tables ) );
				if ( i < function.getArgs( ).size( ) - 1 )
				{
					buffer.append( ", " );
				}
			}
		}
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getObjectColumnName( String columnName, TTableList tables )
	{
		if ( columnName.indexOf( '.' ) != -1 )
		{
			int index = columnName.lastIndexOf( '.' );
			String tableName = columnName.substring( 0, index );
			if ( tableName.indexOf( "." ) > 0 )
			{
				tableName = tableName.substring( tableName.lastIndexOf( "." ) + 1 );
			}

			tableName = caseTableName( tableName, tables );

			return "((Field)"
					+ tableName
					+ "."
					+ columnName.substring( index + 1 ).toUpperCase( )
					+ ")";
		}
		else
		{
			TTable table = tables.getTable( 0 );
			return "((Field)"
					+ getTableName( table )
					+ "."
					+ columnName.toUpperCase( )
					+ ")";
		}
	}

	private String caseTableName( String tableName, TTableList tables )
	{
		for ( int i = 0; i < tables.size( ); i++ )
		{
			TTable table = tables.getTable( i );
			if ( table.getTableName( ).toString( ).equalsIgnoreCase( tableName ) )
			{
				return getTableName( table );
			}
			else if ( table.getAliasClause( ) != null
					&& table.getAliasClause( )
							.toString( )
							.equalsIgnoreCase( tableName ) )
			{
				return tableName.toLowerCase( );
			}
		}
		return tableName;
	}

	private String firstUpper( String table )
	{
		return Character.toUpperCase( table.charAt( 0 ) )
				+ table.substring( 1 ).toLowerCase( );
	}

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		String sqltext = "SELECT e.employee_id,\n"
				+ "       	e.last_name,\n"
				+ "       	e.department_id\n"
				+ "FROM   	employees e,\n"
				+ "       	departments d\n"
				+ "WHERE  	e.department_id = d.department_id\n"
				+ "GROUP BY	e.department_id\n"
				+ "ORDER BY	e.department_id DESC\n"
				+ "LIMIT 10";
		System.out.println( new jooqConverter( EDbVendor.dbvmysql, sqltext ).getConvertResult( ) );

	}
}
