
package gudusoft.gsqlparser.sql2jooq;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.TBaseType;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.nodes.TCaseExpression;
import gudusoft.gsqlparser.nodes.TExpression;
import gudusoft.gsqlparser.nodes.TExpressionList;
import gudusoft.gsqlparser.nodes.TFunctionCall;
import gudusoft.gsqlparser.nodes.TGroupByItem;
import gudusoft.gsqlparser.nodes.TGroupByItemList;
import gudusoft.gsqlparser.nodes.TJoin;
import gudusoft.gsqlparser.nodes.TJoinItem;
import gudusoft.gsqlparser.nodes.TOrderByItem;
import gudusoft.gsqlparser.nodes.TOrderByItemList;
import gudusoft.gsqlparser.nodes.TResultColumn;
import gudusoft.gsqlparser.nodes.TResultColumnList;
import gudusoft.gsqlparser.nodes.TTable;
import gudusoft.gsqlparser.nodes.TTableList;
import gudusoft.gsqlparser.nodes.TWhenClauseItem;
import gudusoft.gsqlparser.nodes.TWhenClauseItemList;
import gudusoft.gsqlparser.sql2jooq.db.ColumnMetaData;
import gudusoft.gsqlparser.sql2jooq.db.DatabaseMetaData;
import gudusoft.gsqlparser.sql2jooq.db.TableMetaData;
import gudusoft.gsqlparser.sql2jooq.tool.DatabaseMetaUtil;
import gudusoft.gsqlparser.sql2jooq.tool.GenerationUtil;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import gudusoft.gsqlparser.stmt.TMergeSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jooq.tools.StringUtils;

public class jooqConverter
{

	private String errorMessage;
	private int resultCode;
	private DatabaseMetaData metadata;
	private StringBuffer convertResult = new StringBuffer( );
	private boolean ignoreGeneric = false;
	private TGSqlParser sqlparser;

	public DatabaseMetaData getMetadata( )
	{
		return metadata;
	}

	public void setMetadata( DatabaseMetaData metadata )
	{
		this.metadata = metadata;
	}

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

	public jooqConverter( DatabaseMetaData metadata, EDbVendor vendor,
			String sqlText )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqltext = sqlText;
		this.metadata = metadata;
	}

	public jooqConverter( DatabaseMetaData metadata, EDbVendor vendor,
			File sqlFile )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqlfilename = sqlFile.getAbsolutePath( );
		this.metadata = metadata;
	}

	public jooqConverter( EDbVendor vendor, String sqlText )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqltext = sqlText;
	}

	public jooqConverter( EDbVendor vendor, File sqlFile )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqlfilename = sqlFile.getAbsolutePath( );
	}

	public void convert( )
	{
		convert( false );
	}

	public void convert( boolean ignoreGeneric )
	{
		this.ignoreGeneric = ignoreGeneric;

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
					convertResult.append( getTableJavaName( table.getName( ) ) )
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

		if ( metadata == null
				|| stmt.getResultColumnList( ) == null
				|| ignoreGeneric )
		{
			convertResult.append( "Result result = create.select( " );
		}
		else if ( stmt.getResultColumnList( ).toString( ).trim( ).equals( "*" ) )
		{
			convertResult.append( "Result<Record> result = create.select( " );
		}
		else
		{
			TResultColumnList columns = stmt.getResultColumnList( );
			convertResult.append( "Result<Record" )
					.append( columns.size( ) )
					.append( "<" );
			for ( int i = 0; i < columns.size( ); i++ )
			{
				ColumnMetaData column = getColumnMetaDataBySql( getExpressionJavaCode( columns.getResultColumn( i )
						.getExpr( ),
						stmt ),
						stmt );
				if ( column != null )
				{
					convertResult.append( DatabaseMetaUtil.getSimpleJavaClass( column.getJavaTypeClass( ) ) );
				}
				else
				{
					convertResult.append( DatabaseMetaUtil.getSimpleJavaClass( guessExpressionJavaTypeClass( getExpressionJavaCode( columns.getResultColumn( i )
							.getExpr( ),
							stmt ) ) ) );
				}
				if ( i < columns.size( ) - 1 )
				{
					convertResult.append( ", " );
				}
			}
			convertResult.append( ">> result = create.select( " );
		}
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
								tables,
								null ) );
						if ( i < stmt.getResultColumnList( ).size( ) - 1 )
						{
							convertResult.append( ", " );
						}
					}
				}
				else
				{
					String columnName = getColumnName( column, tables );
					if ( columnName == null
							|| columnName.trim( ).length( ) == 0 )
					{
						columnName = getExpressionJavaCode( column.getExpr( ),
								stmt );
					}
					convertResult.append( columnName );
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
						convertResult.append( getExpressionJavaCode( joinItem.getOnCondition( ),
								stmt ) );
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
			convertResult.append( getExpressionJavaCode( expression, stmt ) );
			convertResult.append( " )\n\t" );
		}

		if ( stmt.getGroupByClause( ) != null )
		{
			convertResult.append( ".groupBy( " );
			TGroupByItemList items = stmt.getGroupByClause( ).getItems( );
			for ( int i = 0; i < items.size( ); i++ )
			{
				TGroupByItem item = items.getGroupByItem( i );
				convertResult.append( getExpressionJavaCode( item.getExpr( ),
						stmt ) );
				if ( i < items.size( ) - 1 )
				{
					convertResult.append( ", " );
				}
			}
			convertResult.append( " )\n\t" );

			if ( stmt.getGroupByClause( ).getHavingClause( ) != null )
			{
				convertResult.append( ".having( " );
				convertResult.append( getExpressionJavaCode( stmt.getGroupByClause( )
						.getHavingClause( ),
						stmt ) );
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
				convertResult.append( getExpressionJavaCode( item.getSortKey( ),
						stmt ) );
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

	private String guessExpressionJavaTypeClass( String javaCode )
	{
		List<String> javaClasses = DatabaseMetaUtil.getDataTypeClassNames( );
		for ( int i = 0; i < javaClasses.size( ); i++ )
		{
			String className = javaClasses.get( i );
			if ( javaCode.indexOf( className ) != -1 )
				return className;
		}
		if ( javaCode.toLowerCase( ).indexOf( "DSL.count".toLowerCase( ) ) != -1 )
			return Integer.class.getName( );

		Pattern pattern = Pattern.compile( "\".+?\"" );
		Matcher matcher = pattern.matcher( javaCode );
		if ( matcher.find( ) )
			return String.class.getName( );

		pattern = Pattern.compile( "\\d+" );
		matcher = pattern.matcher( javaCode );
		if ( matcher.find( ) )
			return Integer.class.getName( );
		
		return String.class.getName( );
	}

	private String getExpressionJavaCode( TExpression expression,
			TSelectSqlStatement stmt )
	{
		return getExpressionJavaCode( expression, stmt, null );
	}

	private String getTableName( TTable table )
	{
		if ( table.getName( ).equalsIgnoreCase( "DUAL" ) )
		{
			return "DSL.dual()";
		}
		else if ( table.getAliasClause( ) != null )
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
		return getTableJavaName( table.getTableName( ).toString( ) )
				+ "."
				+ table.getTableName( ).toString( ).toUpperCase( );
	}

	private void appendTable( TTable table )
	{
		convertResult.append( getTableName( table ) );
	}

	private String getExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, Object columnInfo )
	{
		ColumnMetaData column = null;
		ColumnMetaData[] columns = null;
		if ( columnInfo instanceof ColumnMetaData )
		{
			column = (ColumnMetaData) columnInfo;
			columns = new ColumnMetaData[]{
				column
			};
		}
		else if ( columnInfo instanceof ColumnMetaData[] )
		{
			columns = (ColumnMetaData[]) columnInfo;
			column = columns[0];
		}
		StringBuffer buffer = new StringBuffer( );
		switch ( expression.getExpressionType( ) )
		{
			case parenthesis_t :
				buffer.append( getExpressionJavaCode( expression.getLeftOperand( ),
						stmt,
						column ) );
				break;
			case simple_comparison_t :
				buffer.append( getComparisonExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case simple_object_name_t :
				buffer.append( getObjectNameExpressionJavaCode( expression,
						stmt ) );
				break;
			case logical_and_t :
				buffer.append( getLogicAndExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case simple_constant_t :
				buffer.append( "DSL.inline( "
						+ getConstantExpressionJavaCode( expression, column )
						+ " )" );
				break;
			case function_t :
				buffer.append( getFunctionJavaCode( expression,
						stmt.tables,
						columns ) );
				break;
			case logical_or_t :
				buffer.append( getLogicOrExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case logical_not_t :
				buffer.append( getLogicNotExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case bitwise_and_t :
				buffer.append( getBitwiseAndExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case bitwise_or_t :
				buffer.append( getBitwiseOrExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case bitwise_shift_left_t :
				buffer.append( getBitwiseLeftShiftExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case bitwise_shift_right_t :
				buffer.append( getBitwiseRightShiftExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case arithmetic_plus_t :
				buffer.append( getArithmeticPlusExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case arithmetic_minus_t :
				buffer.append( getArithmeticMinusExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case arithmetic_divide_t :
				buffer.append( getArithmeticDivideExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case arithmetic_times_t :
				buffer.append( getArithmeticTimesExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case arithmetic_modulo_t :
				buffer.append( getArithmeticModuloExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case power_t :
				buffer.append( getPowerExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case in_t :
				buffer.append( getInExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case list_t :
				buffer.append( getListExpressionJavaCode( expression,
						stmt,
						columns ) );
				break;
			case null_t :
				buffer.append( getNullExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case between_t :
				buffer.append( getBetweenExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case pattern_matching_t :
				buffer.append( getLikeExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case unary_minus_t :
				buffer.append( getUnaryMinusExpressionJavaCode( expression,
						stmt,
						column ) );
			case unary_plus_t :
				buffer.append( getUnaryPlusExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case group_comparison_t :
				buffer.append( getGroupComparisonExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			case case_t :
				buffer.append( getCaseExpressionJavaCode( expression,
						stmt,
						column ) );
				break;
			default :
				throw new UnsupportedOperationException( "\r\nExpression: "
						+ expression.toString( )
						+ "\r\nDoesn't support the operation: "
						+ expression.getExpressionType( ) );
		}
		return buffer.toString( );
	}

	private String getCaseExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		TCaseExpression caseExpr = expression.getCaseExpression( );
		TExpression inputExpr = caseExpr.getInput_expr( );
		TExpression defaultExpr = caseExpr.getElse_expr( );
		TWhenClauseItemList whenItems = caseExpr.getWhenClauseItemList( );

		StringBuffer buffer = new StringBuffer( );
		buffer.append( "DSL.decode( )" );
		if ( inputExpr != null )
		{
			buffer.append( getOneArgmentsOperationJavaCode( "value",
					inputExpr,
					stmt,
					column,
					true ) );
		}
		if ( whenItems != null )
		{
			for ( int i = 0; i < whenItems.size( ); i++ )
			{
				TWhenClauseItem item = whenItems.getWhenClauseItem( i );
				buffer.append( ".when( " );
				if ( item.getComparison_expr( ) != null )
				{
					buffer.append( getExpressionJavaCode( item.getComparison_expr( ),
							stmt,
							column ) );
				}
				buffer.append( ", " );
				if ( item.getReturn_expr( ) != null )
				{
					buffer.append( getExpressionJavaCode( item.getComparison_expr( ),
							stmt,
							column ) );
				}
				buffer.append( " )" );
			}
		}
		if ( defaultExpr != null )
		{
			buffer.append( getOneArgmentsOperationJavaCode( "otherwise",
					defaultExpr,
					stmt,
					column,
					true ) );
		}
		return buffer.toString( );
	}

	private String getListExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData[] columns )
	{
		TExpressionList exprList = expression.getExprList( );
		StringBuffer buffer = new StringBuffer( );
		for ( int i = 0; i < exprList.size( ); i++ )
		{
			ColumnMetaData column = null;
			if ( columns != null )
			{
				if ( columns.length > i )
				{
					column = columns[i];
				}
				else if ( columns.length >= 1 )
				{
					column = columns[0];
				}
			}
			buffer.append( getExpressionJavaCode( exprList.getExpression( i ),
					stmt,
					column ) );
			if ( i < exprList.size( ) - 1 )
			{
				buffer.append( ", " );
			}
		}
		return buffer.toString( );

	}

	private String getNullExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData parentColumn )
	{
		ColumnMetaData column = getColumnMetaDataBySql( expression.getLeftOperand( )
				.toString( ),
				stmt );
		if ( column == null )
		{
			column = parentColumn;
		}
		if ( expression.getNotToken( ) != null )
		{
			return getLeftOperateRightJavaCode( "isNotNull",
					expression,
					stmt,
					column );
		}
		else
		{
			return getLeftOperateRightJavaCode( "isNull",
					expression,
					stmt,
					column );
		}
	}

	private String getBetweenExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData parentColumn )
	{
		ColumnMetaData column = getColumnMetaDataBySql( expression.getLeftOperand( )
				.toString( ),
				stmt );
		if ( column == null )
		{
			column = parentColumn;
		}
		if ( expression.getNotToken( ) != null )
		{
			return getLeftOperateRightJavaCode( "notBetween",
					stmt,
					column,
					expression.getBetweenOperand( ),
					expression.getLeftOperand( ) )
					+ getOneArgmentsOperationJavaCode( "and",
							expression.getRightOperand( ),
							stmt,
							column,
							true );
		}
		else
		{
			return getLeftOperateRightJavaCode( "between",
					stmt,
					column,
					expression.getBetweenOperand( ),
					expression.getLeftOperand( ) )
					+ getOneArgmentsOperationJavaCode( "and",
							expression.getRightOperand( ),
							stmt,
							column,
							true );
		}
	}

	private String getInExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData parentColumn )
	{
		ColumnMetaData column = getColumnMetaDataBySql( expression.getLeftOperand( )
				.toString( ),
				stmt );
		if ( column == null )
		{
			column = parentColumn;
		}
		if ( expression.getNotToken( ) != null )
		{
			return getLeftOperateRightJavaCode( "notIn",
					expression,
					stmt,
					column );
		}
		else
		{
			return getLeftOperateRightJavaCode( "in", expression, stmt, column );
		}
	}

	private String getLikeExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData parentColumn )
	{
		ColumnMetaData column = getColumnMetaDataBySql( expression.getLeftOperand( )
				.toString( ),
				stmt );
		if ( column == null )
		{
			column = parentColumn;
		}

		String operation = null;
		if ( expression.getNotToken( ) == null )
		{
			if ( expression.getOperatorToken( )
					.toString( )
					.equalsIgnoreCase( "REGEXP" ) )
			{
				operation = "likeRegex";
			}
			else
			{
				operation = "like";

			}
		}
		else
		{
			if ( expression.getOperatorToken( )
					.toString( )
					.equalsIgnoreCase( "REGEXP" ) )
			{
				operation = "notLikeRegex";
			}
			else
			{
				operation = "notLike";
			}
		}

		if ( expression.getLikeEscapeOperand( ) == null )
		{
			if ( expression.getNotToken( ) != null )
			{
				return getLeftOperateRightJavaCode( operation,
						expression,
						stmt,
						column );
			}
			else
			{
				return getLeftOperateRightJavaCode( operation,
						expression,
						stmt,
						column );
			}
		}
		else
		{
			if ( expression.getNotToken( ) != null )
			{
				return getLeftOperateRightJavaCode( operation,
						stmt,
						column,
						expression.getLeftOperand( ),
						expression.getRightOperand( ),
						convertStringToChar( expression.getLikeEscapeOperand( ) ) );
			}
			else
			{
				return getLeftOperateRightJavaCode( operation,
						stmt,
						column,
						expression.getLeftOperand( ),
						expression.getRightOperand( ),
						convertStringToChar( expression.getLikeEscapeOperand( ) ) );
			}
		}
	}

	private String convertStringToChar( TExpression expression )
	{
		String content = expression.toString( ).trim( );
		if ( content.startsWith( "'" ) && content.endsWith( "'" ) )
		{
			content = content.substring( 1, content.length( ) - 1 );
		}
		return "'" + content.charAt( 0 ) + "'";
	}

	private String getConstantExpressionJavaCode( TExpression expression,
			ColumnMetaData column )
	{
		String content = expression.toString( );
		if ( content.startsWith( "'" ) && content.endsWith( "'" ) )
		{
			content = "\""
					+ content.substring( 1, content.length( ) - 1 )
					+ "\"";
		}
		if ( column != null && !ignoreGeneric )
		{
			if ( column.getJavaTypeClass( ).equals( "java.lang.Short" )
					&& !( content.startsWith( "\"" ) && content.endsWith( "\"" ) ) )
			{
				content = "(short)" + content;
			}
			return DatabaseMetaUtil.getSimpleJavaClass( column.getJavaTypeClass( ) )
					+ ".valueOf( "
					+ content
					+ " )";
		}
		return content;
	}

	private String getUnaryMinusExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "neg",
				stmt,
				column,
				expression.getRightOperand( ) );
	}

	private String getUnaryPlusExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getExpressionJavaCode( expression.getRightOperand( ),
				stmt,
				column );
	}

	private String getTwoArgmentsOperationJavaCode( String operation,
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		StringBuffer buffer = new StringBuffer( );
		buffer.append( "." ).append( operation ).append( "( " );
		buffer.append( getExpressionJavaCode( expression.getLeftOperand( ),
				stmt,
				column ) );
		buffer.append( ", " );
		buffer.append( getExpressionJavaCode( expression.getRightOperand( ),
				stmt,
				column ) );
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getOneArgmentsOperationJavaCode( String operation,
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column, boolean useExpressionSelf )
	{
		StringBuffer buffer = new StringBuffer( );
		buffer.append( "." ).append( operation ).append( "( " );
		buffer.append( getExpressionJavaCode( useExpressionSelf ? expression
				: expression.getLeftOperand( ), stmt, column ) );
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getOneArgmentsOperationJavaCode( String operation,
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getOneArgmentsOperationJavaCode( operation,
				expression,
				stmt,
				column,
				false );
	}

	private String getLeftOperateRightJavaCode( String operation,
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		StringBuffer buffer = new StringBuffer( );
		buffer.append( getExpressionJavaCode( expression.getLeftOperand( ),
				stmt,
				column ) );
		buffer.append( "." ).append( operation ).append( "( " );
		if ( expression.getRightOperand( ) != null )
		{
			buffer.append( getExpressionJavaCode( expression.getRightOperand( ),
					stmt,
					column ) );
		}
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getLeftOperateRightJavaCode( String operation,
			TCustomSqlStatement stmt, ColumnMetaData column,
			TExpression leftExpression, Object... rightExpressions )
	{
		StringBuffer buffer = new StringBuffer( );
		buffer.append( getExpressionJavaCode( leftExpression, stmt, column ) );
		buffer.append( "." ).append( operation ).append( "( " );
		if ( rightExpressions != null )
		{
			for ( int i = 0; i < rightExpressions.length; i++ )
			{
				if ( rightExpressions[i] instanceof TExpression )
				{
					buffer.append( getExpressionJavaCode( (TExpression) rightExpressions[i],
							stmt,
							column ) );
				}
				else if ( rightExpressions[i] instanceof String )
				{
					buffer.append( rightExpressions[i].toString( ) );
				}
				if ( i < rightExpressions.length - 1 )
				{
					buffer.append( ", " );
				}
			}
		}
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getPowerExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getTwoArgmentsOperationJavaCode( "power",
				expression,
				stmt,
				column );
	}

	private String getArithmeticModuloExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "mod", expression, stmt, column );
	}

	private String getArithmeticTimesExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "mul", expression, stmt, column );
	}

	private String getArithmeticDivideExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "div", expression, stmt, column );
	}

	private String getArithmeticMinusExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "sub", expression, stmt, column );
	}

	private String getArithmeticPlusExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "add", expression, stmt, column );
	}

	private String getBitwiseAndExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getTwoArgmentsOperationJavaCode( "bitAnd",
				expression,
				stmt,
				column );
	}

	private String getBitwiseOrExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getTwoArgmentsOperationJavaCode( "bitOr",
				expression,
				stmt,
				column );
	}

	private String getBitwiseLeftShiftExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getTwoArgmentsOperationJavaCode( "shl", expression, stmt, column );
	}

	private String getBitwiseRightShiftExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return getTwoArgmentsOperationJavaCode( "shr", expression, stmt, column );
	}

	private String getLogicAndExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "and", expression, stmt, column );
	}

	private String getLogicOrExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getLeftOperateRightJavaCode( "or", expression, stmt, column );
	}

	private String getLogicNotExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getOneArgmentsOperationJavaCode( "not", expression, stmt, column );
	}

	private String getObjectNameExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt )
	{
		return getObjectColumnName( expression.toString( ), stmt.tables );
	}

	private String getComparisonExpressionJavaCode( TExpression expr,
			TCustomSqlStatement stmt, ColumnMetaData parentColumn )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( expr.getComparisonOperator( ).tokencode == (int) '=' )
		{
			buffer.append( getComparisonOperationJavaCode( "equal",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.not_equal )
		{
			buffer.append( getComparisonOperationJavaCode( "notEqual",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == (int) '>' )
		{
			buffer.append( getComparisonOperationJavaCode( "greaterThan",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == (int) '<' )
		{
			buffer.append( getComparisonOperationJavaCode( "lessThan",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.less_equal )
		{
			buffer.append( getComparisonOperationJavaCode( "lessOrEqual",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.great_equal )
		{
			buffer.append( getComparisonOperationJavaCode( "greaterOrEqual",
					expr,
					stmt,
					parentColumn ) );
		}
		return buffer.toString( );
	}

	private String getGroupComparisonExpressionJavaCode( TExpression expr,
			TCustomSqlStatement stmt, ColumnMetaData parentColumn )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( expr.getComparisonOperator( ).tokencode == (int) '=' )
		{
			buffer.append( getGroupComparisonOperationJavaCode( "equal",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.not_equal )
		{
			buffer.append( getGroupComparisonOperationJavaCode( "notEqual",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == (int) '>' )
		{
			buffer.append( getGroupComparisonOperationJavaCode( "greaterThan",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == (int) '<' )
		{
			buffer.append( getGroupComparisonOperationJavaCode( "lessThan",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.less_equal )
		{
			buffer.append( getGroupComparisonOperationJavaCode( "lessOrEqual",
					expr,
					stmt,
					parentColumn ) );
		}
		else if ( expr.getComparisonOperator( ).tokencode == TBaseType.great_equal )
		{
			buffer.append( getGroupComparisonOperationJavaCode( "greaterOrEqual",
					expr,
					stmt,
					parentColumn ) );
		}
		return buffer.toString( );
	}

	private Object getGroupComparisonOperationJavaCode( String operation,
			TExpression expr, TCustomSqlStatement stmt,
			ColumnMetaData parentColumn )
	{
		StringBuffer buffer = new StringBuffer( );
		String left = "DSL.row( "
				+ getExpressionJavaCode( expr.getLeftOperand( ),
						stmt,
						parentColumn );
		buffer.append( left );
		buffer.append( " )." + operation + "( " );
		ColumnMetaData[] columns = getColumnMetaDatasBySql( left, stmt );
		if ( columns == null )
		{
			columns = new ColumnMetaData[]{
				parentColumn
			};
		}

		buffer.append( "DSL.row( "
				+ getExpressionJavaCode( expr.getRightOperand( ), stmt, columns )
				+ " )" );

		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getComparisonOperationJavaCode( String operation,
			TExpression expr, TCustomSqlStatement stmt,
			ColumnMetaData parentColumn )
	{
		StringBuffer buffer = new StringBuffer( );
		String left = getExpressionJavaCode( expr.getLeftOperand( ),
				stmt,
				parentColumn );
		buffer.append( left );
		buffer.append( "." + operation + "( " );
		Object column = null;
		if ( expr.getLeftOperand( ).getExpressionType( ) == EExpressionType.function_t
				&& expr.getLeftOperand( )
						.getFunctionCall( )
						.getFunctionName( )
						.toString( )
						.equalsIgnoreCase( "row" ) )
		{
			column = getColumnMetaDatasBySql( expr.getLeftOperand( )
					.getFunctionCall( )
					.getArgs( )
					.toString( ), stmt );
		}
		else
		{
			column = getColumnMetaDataBySql( left, stmt );
		}
		if ( column == null )
		{
			column = parentColumn;
		}

		buffer.append( getExpressionJavaCode( expr.getRightOperand( ),
				stmt,
				column ) );

		buffer.append( " )" );
		return buffer.toString( );
	}

	private ColumnMetaData getColumnMetaDataBySql( String sql,
			TCustomSqlStatement stmt )
	{
		if ( metadata == null )
			return null;

		sql = removeParenthesis( sql );
		int index = sql.lastIndexOf( '.' );
		String columnName = sql;
		if ( index != -1 )
		{
			columnName = sql.substring( index + 1 );
		}
		if ( columnName.endsWith( "_" ) )
		{
			columnName = columnName.substring( 0, columnName.length( ) - 1 );
		}
		if ( index != -1 )
		{
			sql = sql.substring( 0, index );
			String tableName = sql;
			index = sql.lastIndexOf( '.' );
			if ( index != -1 )
			{
				tableName = sql.substring( index + 1 );
			}
			TTable table = getTableFromName( tableName, stmt.tables );
			if ( table != null )
			{
				TableMetaData tableMetaData = metadata.getTableMetaData( table.getTableName( )
						.toString( ) );
				if ( tableMetaData != null )
				{
					return tableMetaData.getColumnMetaData( columnName );
				}
			}
		}
		return null;
	}

	private ColumnMetaData[] getColumnMetaDatasBySql( String sql,
			TCustomSqlStatement stmt )
	{
		if ( metadata == null )
			return null;
		String[] splits = sql.split( "," );
		ColumnMetaData[] metaDatas = new ColumnMetaData[splits.length];
		for ( int i = 0; i < splits.length; i++ )
		{
			String content = removeParenthesis( splits[i] );
			int index = content.lastIndexOf( '.' );
			String columnName = content;
			if ( index != -1 )
			{
				columnName = content.substring( index + 1 );
			}
			if ( columnName.endsWith( "_" ) )
			{
				columnName = columnName.substring( 0, columnName.length( ) - 1 );
			}
			if ( index != -1 )
			{
				content = content.substring( 0, index );
				String tableName = content;
				index = content.lastIndexOf( '.' );
				if ( index != -1 )
				{
					tableName = content.substring( index + 1 );
				}
				TTable table = getTableFromName( tableName, stmt.tables );
				if ( table != null )
				{
					TableMetaData tableMetaData = metadata.getTableMetaData( table.getTableName( )
							.toString( ) );
					if ( tableMetaData != null )
					{
						metaDatas[i] = tableMetaData.getColumnMetaData( columnName );
					}
				}
			}
		}
		return metaDatas;
	}

	private String removeParenthesis( String sql )
	{
		sql = sql.trim( );
		if ( sql.startsWith( "(" ) && sql.endsWith( ")" ) )
			return sql.substring( 1, sql.length( ) - 1 );
		return sql;
	}

	private String getColumnName( TResultColumn column, TTableList tables )
	{
		return getExpressionColumnName( column.getExpr( ), tables, null );
	}

	private String getExpressionColumnName( TExpression expr,
			TTableList tables, Object columnInfo )
	{
		ColumnMetaData column = null;
		ColumnMetaData[] columns = null;
		if ( columnInfo instanceof ColumnMetaData )
		{
			column = (ColumnMetaData) columnInfo;
			columns = new ColumnMetaData[]{
				column
			};
		}
		else if ( columnInfo instanceof ColumnMetaData[] )
		{
			columns = (ColumnMetaData[]) columnInfo;
			column = columns[0];
		}
		switch ( expr.getExpressionType( ) )
		{
			case simple_object_name_t :
				return getObjectColumnName( expr.toString( ), tables );
			case subquery_t :
				break;
			case simple_constant_t :
				return "DSL.inline( "
						+ getConstantExpressionJavaCode( expr, column )
						+ " )";
			case function_t :
				return getFunctionJavaCode( expr, tables, columns );
			default :
				break;
		}
		return "";
	}

	private String getFunctionJavaCode( TExpression expression,
			TTableList tables, ColumnMetaData[] columns )
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
				ColumnMetaData column = null;
				if ( columns != null )
				{
					if ( columns.length > i )
					{
						column = columns[i];
					}
					else if ( columns.length >= 1 )
					{
						column = columns[0];
					}
				}
				buffer.append( getExpressionColumnName( arg, tables, column ) );
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

			columnName = columnName.substring( index + 1 ).toUpperCase( );

			TTable table = getTableFromName( tableName, tables );

			tableName = caseTableName( tableName, tables );

			if ( columnName.equalsIgnoreCase( table.getTableName( ).toString( ) ) )
			{
				columnName += "_";
			}

			if ( metadata == null || ignoreGeneric )
				return "((Field)" + tableName + "." + columnName + ")";
			else
				return tableName + "." + columnName;
		}
		else
		{
			TTable table = tables.getTable( 0 );
			if ( metadata != null && tables.size( ) > 0 )
			{
				for ( int i = 1; i < tables.size( ); i++ )
				{
					TableMetaData tableMetaData = metadata.getTableMetaData( tables.getTable( i )
							.getTableName( )
							.toString( ) );
					if ( tableMetaData != null )
					{
						ColumnMetaData columnMetaData = tableMetaData.getColumnMetaData( columnName );
						if ( columnMetaData != null )
						{
							table = tables.getTable( i );
							break;
						}
					}
				}
			}

			if ( columnName.equalsIgnoreCase( table.getTableName( ).toString( ) ) )
			{
				columnName += "_";
			}

			if ( metadata == null || ignoreGeneric )
				return "((Field)"
						+ getTableName( table )
						+ "."
						+ columnName.toUpperCase( )
						+ ")";
			else
				return getTableName( table ) + "." + columnName.toUpperCase( );
		}
	}

	private TTable getTableFromName( String tableName, TTableList tables )
	{
		for ( int i = 0; i < tables.size( ); i++ )
		{
			TTable table = tables.getTable( i );
			if ( table.getTableName( ).toString( ).equalsIgnoreCase( tableName ) )
			{
				return table;
			}
			else if ( table.getAliasClause( ) != null
					&& table.getAliasClause( )
							.toString( )
							.equalsIgnoreCase( tableName ) )
			{
				return table;
			}
		}
		return null;
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

	private String getTableJavaName( String table )
	{
		return GenerationUtil.convertToJavaIdentifier( StringUtils.toCamelCase( table ) );
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
