
package gudusoft.gsqlparser.sql2jooq;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.EExpressionType;
import gudusoft.gsqlparser.EJoinType;
import gudusoft.gsqlparser.ETableSource;
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
import gudusoft.gsqlparser.nodes.TMultiTarget;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TObjectNameList;
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
import gudusoft.gsqlparser.sql2jooq.db.FunctionUtils;
import gudusoft.gsqlparser.sql2jooq.db.TableMetaData;
import gudusoft.gsqlparser.sql2jooq.exception.PlainSQLException;
import gudusoft.gsqlparser.sql2jooq.tool.DatabaseMetaUtil;
import gudusoft.gsqlparser.sql2jooq.tool.GenerationUtil;
import gudusoft.gsqlparser.stmt.TDeleteSqlStatement;
import gudusoft.gsqlparser.stmt.TInsertSqlStatement;
import gudusoft.gsqlparser.stmt.TMergeSqlStatement;
import gudusoft.gsqlparser.stmt.TSelectSqlStatement;
import gudusoft.gsqlparser.stmt.TUpdateSqlStatement;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDataType;
import org.jooq.tools.StringUtils;

public class SQL2jOOQ
{

	private String errorMessage;
	private int resultCode;
	private DatabaseMetaData metadata;
	private StringBuffer convertResultBuffer = new StringBuffer( );
	private StringBuffer predefineBuffer = new StringBuffer( );
	private Map<String, String> fieldAliases = new HashMap<String, String>( );
	private List<String> asTables = new ArrayList<String>( );
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
		return predefineBuffer.toString( )
				+ "\n"
				+ convertResultBuffer.toString( );
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
		convertResultBuffer.delete( 0, convertResultBuffer.length( ) );
		fieldAliases.clear( );
		asTables.clear( );
	}

	public SQL2jOOQ( DatabaseMetaData metadata, EDbVendor vendor,
			String sqlText )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqltext = sqlText;
		this.metadata = metadata;
	}

	public SQL2jOOQ( DatabaseMetaData metadata, EDbVendor vendor,
			File sqlFile )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqlfilename = sqlFile.getAbsolutePath( );
		this.metadata = metadata;
	}

	public SQL2jOOQ( EDbVendor vendor, String sqlText )
	{
		sqlparser = new TGSqlParser( vendor );
		sqlparser.sqltext = sqlText;
	}

	public SQL2jOOQ( EDbVendor vendor, File sqlFile )
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
		predefineStmt( stmt );
		convertResultBuffer.append( "Result" );
		convertResultBuffer.append( getReturnType( stmt ) );
		convertResultBuffer.append( " result = " );
		convertResultBuffer.append( getQueryJavaCode( stmt ) );
		convertResultBuffer.append( ".fetch( );\n" );
	}

	private String getReturnType( TSelectSqlStatement stmt )
	{
		if ( stmt.isCombinedQuery( ) )
		{
			return getReturnType( stmt.getLeftStmt( ) );
		}
		StringBuffer buffer = new StringBuffer( );
		if ( metadata == null
				|| stmt.getResultColumnList( ) == null
				|| ignoreGeneric
				|| isUnknownType( stmt ) )
		{
			buffer.append( "" );
		}
		else if ( stmt.getResultColumnList( ).toString( ).trim( ).equals( "*" ) )
		{
			buffer.append( "<Record>" );
		}
		else
		{
			TResultColumnList columns = stmt.getResultColumnList( );
			buffer.append( "<Record" ).append( columns.size( ) ).append( "<" );
			for ( int i = 0; i < columns.size( ); i++ )
			{
				buffer.append( getResultsetColumnType( stmt,
						columns.getResultColumn( i ) ) );
				if ( i < columns.size( ) - 1 )
				{
					buffer.append( ", " );
				}
			}
			buffer.append( ">>" );
		}
		return buffer.toString( );
	}

	private boolean isUnknownType( TSelectSqlStatement stmt )
	{
		String function = getOutermostFunction( getResultsetColumnsJavaCode( stmt ) );
		if ( function != null )
		{
			for ( int i = 0; i < FunctionUtils.getUnknownTypefunctions( )
					.size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getUnknownTypefunctions( )
						.get( i ) ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	private String getResultsetColumnType( TCustomSqlStatement stmt,
			TResultColumn field )
	{
		try
		{
			ColumnMetaData column = getColumnMetaDataBySql( getExpressionJavaCode( field.getExpr( ),
					stmt ),
					stmt );
			if ( column != null )
			{
				return DatabaseMetaUtil.getSimpleJavaClass( column.getJavaTypeClass( ) );
			}
			else if ( field.getExpr( ).getExpressionType( ) == EExpressionType.subquery_t
					&& field.getAliasClause( ) == null )
			{
				return DatabaseMetaUtil.getSimpleJavaClass( "java.lang.Object" );
			}
			else
			{
				return DatabaseMetaUtil.getSimpleJavaClass( guessExpressionJavaTypeClass( getExpressionJavaCode( field.getExpr( ),
						stmt ) ) );
			}
		}
		catch ( PlainSQLException e )
		{
			return DatabaseMetaUtil.getSimpleJavaClass( "java.lang.Object" );
		}
	}

	private void predefineStmt( TSelectSqlStatement stmt )
	{
		predefineBuffer.append( "DSLContext create = DSL.using(conn, SQLDialect." )
				.append( GenerationUtil.getSQLDialect( stmt.dbvendor ) )
				.append( ");\n" );

		predefineTableAndField( stmt );
	}

	private void predefineTableAndField( TSelectSqlStatement stmt )
	{
		if ( stmt.isCombinedQuery( ) )
		{
			TSelectSqlStatement leftStmt = stmt.getLeftStmt( );
			TSelectSqlStatement rightStmt = stmt.getRightStmt( );
			predefineTableAndField( leftStmt );
			predefineTableAndField( rightStmt );
		}
		else
		{
			TTableList tables = stmt.tables;
			if ( tables != null )
			{
				for ( int i = 0; i < tables.size( ); i++ )
				{
					TTable table = tables.getTable( i );
					if ( table.getAliasClause( ) != null
							&& table.getTableType( ) != ETableSource.rowList )
					{
						StringBuffer buffer = new StringBuffer( );
						if ( table.getSubquery( ) == null )
						{
							buffer.append( getTableJavaName( table ) )
									.append( " " )
									.append( table.getAliasClause( )
											.toString( )
											.toLowerCase( ) )
									.append( " = " )
									.append( getTableRealName( table, stmt ) )
									.append( ".as(\"" )
									.append( table.getAliasClause( )
											.getAliasName( )
											.toString( )
											.toLowerCase( ) );
							buffer.append( "\");\n" );
						}
						else
						{
							String tableAlias = table.getAliasClause( )
									.toString( )
									.toLowerCase( );
							if ( !asTables.contains( tableAlias ) )
							{
								buffer.append( "Table" );
								buffer.append( getReturnType( table.getSubquery( ) ) );
								buffer.append( " " )
										.append( tableAlias )
										.append( " = " );
								buffer.append( getQueryJavaCode( table.getSubquery( ) ) );
								buffer.append( ".asTable(\""
										+ tableAlias
										+ "\");\n" );
								asTables.add( tableAlias );
							}
						}
						predefineBuffer.append( buffer );
					}
				}
			}
		}
	}

	private String getQueryJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );

		if ( stmt.isCombinedQuery( ) )
		{
			TSelectSqlStatement leftStmt = stmt.getLeftStmt( );
			TSelectSqlStatement rightStmt = stmt.getRightStmt( );
			buffer.append( getQueryJavaCode( leftStmt ) );
			switch ( stmt.getSetOperator( ) )
			{
				case TSelectSqlStatement.setOperator_union :
					buffer.append( ".union" );
					break;
				case TSelectSqlStatement.setOperator_unionall :
					buffer.append( ".unionAll" );
					break;
				case TSelectSqlStatement.setOperator_intersect :
					buffer.append( ".intersect" );
					break;
				case TSelectSqlStatement.setOperator_intersectall :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: INTERSECT ALL" );
					// convertResult.append( ".intersectAll" );
					// break;
				case TSelectSqlStatement.setOperator_minus :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: MINUS" );
					// convertResult.append( ".minus" );
					// break;
				case TSelectSqlStatement.setOperator_minusall :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: MINUS ALL" );
					// convertResult.append( ".minusAll" );
					// break;
				case TSelectSqlStatement.setOperator_except :
					buffer.append( ".except" );
					break;
				case TSelectSqlStatement.setOperator_exceptall :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: EXCEPT ALL" );
					// convertResult.append( ".exceptAll" );
					// break;
			}
			buffer.append( "( " )
					.append( getCombineQueryJavaCode( stmt, rightStmt ) )
					.append( " )" );
		}
		else
		{
			buffer.append( getResultsetColumnsJavaCode( stmt ) );
			buffer.append( getTableJoinsJavaCode( stmt ) );
			buffer.append( getWhereClauseJavaCode( stmt ) );
			buffer.append( getGroupByClauseJavaCode( stmt ) );
			buffer.append( getOrderbyClauseJavaCode( stmt ) );
			buffer.append( getLimitClauseJavaCode( stmt ) );
			buffer.append( getForUpdateClauseJavaCode( stmt ) );
		}

		trimResult( buffer );

		return buffer.toString( );
	}

	private Object getCombineQueryJavaCode( TSelectSqlStatement root,
			TSelectSqlStatement stmt )
	{
		StringBuffer convertResult = new StringBuffer( );

		if ( stmt.isCombinedQuery( ) )
		{
			TSelectSqlStatement leftStmt = stmt.getLeftStmt( );
			TSelectSqlStatement rightStmt = stmt.getRightStmt( );
			convertResult.append( getQueryJavaCode( leftStmt ) );
			switch ( stmt.getSetOperator( ) )
			{
				case TSelectSqlStatement.setOperator_union :
					convertResult.append( ".union" );
					break;
				case TSelectSqlStatement.setOperator_unionall :
					convertResult.append( ".unionAll" );
					break;
				case TSelectSqlStatement.setOperator_intersect :
					convertResult.append( ".intersect" );
					break;
				case TSelectSqlStatement.setOperator_intersectall :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: INTERSECT ALL" );
					// convertResult.append( ".intersectAll" );
					// break;
				case TSelectSqlStatement.setOperator_minus :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: MINUS" );
					// convertResult.append( ".minus" );
					// break;
				case TSelectSqlStatement.setOperator_minusall :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: MINUS ALL" );
					// convertResult.append( ".minusAll" );
					// break;
				case TSelectSqlStatement.setOperator_except :
					convertResult.append( ".except" );
					break;
				case TSelectSqlStatement.setOperator_exceptall :
					throw new UnsupportedOperationException( "\nDoesn't support the operation: EXCEPT ALL" );
					// convertResult.append( ".exceptAll" );
					// break;
			}
			convertResult.append( "( " )
					.append( getCombineQueryJavaCode( root, rightStmt ) )
					.append( " )" );
		}
		else
		{
			convertResult.append( getResultsetColumnsJavaCode( stmt ) );
			convertResult.append( getTableJoinsJavaCode( stmt ) );
			convertResult.append( getWhereClauseJavaCode( stmt ) );
			convertResult.append( getGroupByClauseJavaCode( root ) );
			convertResult.append( getOrderbyClauseJavaCode( root ) );
			convertResult.append( getLimitClauseJavaCode( root ) );
			convertResult.append( getForUpdateClauseJavaCode( root ) );
		}

		trimResult( convertResult );

		return convertResult.toString( );
	}

	private String getResultsetColumnsJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		buffer.append( "create.select( " );
		if ( stmt.getResultColumnList( ) != null )
		{
			for ( int i = 0; i < stmt.getResultColumnList( ).size( ); i++ )
			{
				TResultColumn column = stmt.getResultColumnList( )
						.getResultColumn( i );
				if ( column.getExpr( ).toString( ).indexOf( '*' ) != -1
						&& column.getExpr( ).toString( ).trim( ).length( ) == 1 )
				{
					continue;
				}
				else
				{
					try
					{
						String columnName = getColumnName( column, stmt );
						if ( columnName == null
								|| columnName.trim( ).length( ) == 0 )
						{
							columnName = getExpressionJavaCode( column.getExpr( ),
									stmt );
							if ( column.getExpr( ).getExpressionType( ) == EExpressionType.subquery_t )
							{
								columnName += ".asField( )";
							}
						}
						buffer.append( columnName );
					}
					catch ( PlainSQLException e )
					{
						buffer.append( "DSL.field( \""
								+ escape( column.toString( ) )
								+ "\" )" );
					}
					if ( i < stmt.getResultColumnList( ).size( ) - 1 )
					{
						buffer.append( ", " );
					}
				}
			}
		}
		buffer.append( " )\n\t" );
		return buffer.toString( );
	}

	private String getTableJoinsJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( stmt.joins != null && stmt.joins.size( ) > 0 )
		{
			boolean closeFrom = false;
			buffer.append( ".from( " );
			for ( int i = 0; i < stmt.joins.size( ); i++ )
			{
				TJoin join = stmt.joins.getJoin( i );

				buffer.append( getTableName( join.getTable( ), stmt ) );

				if ( join.getJoinItems( ) != null
						&& join.getJoinItems( ).size( ) > 0 )
				{
					if ( !closeFrom )
					{
						closeFrom = true;
						buffer.append( " )\n\t" );
					}

					for ( int j = 0; j < join.getJoinItems( ).size( ); j++ )
					{
						TJoinItem joinItem = join.getJoinItems( )
								.getJoinItem( j );
						if ( joinItem.getJoinType( ) == EJoinType.leftouter
								|| joinItem.getJoinType( ) == EJoinType.left )
						{
							buffer.append( ".leftOuterJoin( " );
						}
						else if ( joinItem.getJoinType( ) == EJoinType.rightouter
								|| joinItem.getJoinType( ) == EJoinType.right )
						{
							buffer.append( ".rightOuterJoin( " );
						}
						else if ( joinItem.getJoinType( ) == EJoinType.natural
								|| joinItem.getJoinType( ) == EJoinType.natural_inner )
						{
							buffer.append( ".naturalJoin( " );
						}
						else if ( joinItem.getJoinType( ) == EJoinType.natural_leftouter
								|| joinItem.getJoinType( ) == EJoinType.natural_left )
						{
							buffer.append( ".naturalLeftOuterJoin( " );
						}
						else if ( joinItem.getJoinType( ) == EJoinType.natural_rightouter
								|| joinItem.getJoinType( ) == EJoinType.natural_right )
						{
							buffer.append( ".naturalRightOuterJoin( " );
						}
						else if ( joinItem.getJoinType( ) == EJoinType.cross )
						{
							buffer.append( ".crossJoin( " );
						}
						else if ( joinItem.getJoinType( ) == EJoinType.fullouter
								|| joinItem.getJoinType( ) == EJoinType.full )
						{
							buffer.append( ".fullOuterJoin( " );
						}
						else
						{
							buffer.append( ".join( " );
						}
						buffer.append( getTableName( joinItem.getTable( ), stmt ) );
						if ( joinItem.getOnCondition( ) != null )
						{
							buffer.append( " ).on( " );
							buffer.append( getExpressionJavaCode( joinItem.getOnCondition( ),
									stmt ) );
							buffer.append( " )\n\t" );
						}
						else if ( joinItem.getUsingColumns( ) != null )
						{
							buffer.append( " ).using( " );
							for ( int z = 0; z < joinItem.getUsingColumns( )
									.size( ); z++ )
							{
								TObjectName column = joinItem.getUsingColumns( )
										.getObjectName( z );
								buffer.append( getObjectColumnName( column.toString( ),
										stmt ) );
								if ( z < joinItem.getUsingColumns( ).size( ) - 1 )
								{
									buffer.append( ", " );
								}
							}
							buffer.append( " )\n\t" );
						}
						else
						{
							buffer.append( " )\n\t" );
						}
					}
				}

				if ( !closeFrom )
				{
					if ( i < stmt.joins.size( ) - 1 )
					{
						buffer.append( ", " );
					}
				}
			}

			if ( !closeFrom )
			{
				buffer.append( " )\n\t" );
			}
		}
		return buffer.toString( );
	}

	private String getWhereClauseJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( stmt.getWhereClause( ) != null )
		{
			buffer.append( ".where( " );
			TExpression expression = stmt.getWhereClause( ).getCondition( );
			buffer.append( getExpressionJavaCode( expression, stmt ) );
			buffer.append( " )\n\t" );
		}
		return buffer.toString( );
	}

	private String getGroupByClauseJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( stmt.getGroupByClause( ) != null )
		{
			buffer.append( ".groupBy( " );
			boolean isRollUp = stmt.getGroupByClause( ).isRollupModifier( );
			if ( isRollUp )
			{
				buffer.append( "DSL.rollup( " );
			}
			TGroupByItemList items = stmt.getGroupByClause( ).getItems( );
			for ( int i = 0; i < items.size( ); i++ )
			{
				TGroupByItem item = items.getGroupByItem( i );
				buffer.append( getExpressionJavaCode( item.getExpr( ), stmt ) );
				if ( i < items.size( ) - 1 )
				{
					buffer.append( ", " );
				}
			}
			if ( isRollUp )
			{
				buffer.append( " )" );
			}
			buffer.append( " )\n\t" );

			if ( stmt.getGroupByClause( ).getHavingClause( ) != null )
			{
				buffer.append( ".having( " );
				buffer.append( getExpressionJavaCode( stmt.getGroupByClause( )
						.getHavingClause( ), stmt ) );
				buffer.append( " )\n\t" );
			}

		}
		return buffer.toString( );
	}

	private String getOrderbyClauseJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( stmt.getOrderbyClause( ) != null )
		{
			buffer.append( ".orderBy( " );
			TOrderByItemList items = stmt.getOrderbyClause( ).getItems( );
			for ( int i = 0; i < items.size( ); i++ )
			{
				TOrderByItem item = items.getOrderByItem( i );
				buffer.append( getExpressionJavaCode( item.getSortKey( ), stmt ) );
				if ( item.getSortType( ) == TBaseType.srtAsc )
				{
					buffer.append( ".asc( )" );
				}
				else if ( item.getSortType( ) == TBaseType.srtDesc )
				{
					buffer.append( ".desc( )" );
				}
				if ( i < items.size( ) - 1 )
				{
					buffer.append( ", " );
				}
			}
			buffer.append( " )\n\t" );
		}
		return buffer.toString( );
	}

	private String getLimitClauseJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( stmt.getLimitClause( ) != null
				&& stmt.getLimitClause( ).getRow_count( ) != null )
		{
			buffer.append( ".limit( " );
			if ( stmt.getLimitClause( ).getOffset( ) != null )
			{
				buffer.append( stmt.getLimitClause( ).getOffset( ).toString( )
						+ ", " );
			}
			buffer.append( stmt.getLimitClause( ).getRow_count( ) );
			buffer.append( " )\n\t" );
		}
		return buffer.toString( );
	}

	private String getForUpdateClauseJavaCode( TSelectSqlStatement stmt )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( stmt.getForUpdateClause( ) != null )
		{
			buffer.append( ".forUpdate( )\n\t" );
		}
		return buffer.toString( );
	}

	private void trimResult( StringBuffer convertResult )
	{
		int index = convertResult.lastIndexOf( "\n" );
		if ( index >= 0
				&& convertResult.substring( index ).trim( ).length( ) == 0 )
		{
			convertResult.delete( index, convertResult.length( ) );
		}
	}

	private String getOutermostFunction( String javaCode )
	{
		int index = -1;
		String function = null;
		for ( int i = 0; i < FunctionUtils.getFixTypeFunctions( ).size( ); i++ )
		{
			String func = FunctionUtils.getFixTypeFunctions( ).get( i );
			int funcIndex = javaCode.indexOf( func );
			if ( funcIndex != -1 && ( index == -1 || funcIndex < index ) )
			{
				index = funcIndex;
				function = func;
			}
		}
		return function;
	}

	private String guessExpressionJavaTypeClass( String javaCode )
	{

		String function = getOutermostFunction( javaCode );

		if ( function != null )
		{
			for ( int i = 0; i < FunctionUtils.getIntTypefunctions( ).size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getIntTypefunctions( )
						.get( i ) ) )
				{
					return Integer.class.getName( );
				}
			}

			for ( int i = 0; i < FunctionUtils.getStringTypefunctions( ).size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getStringTypefunctions( )
						.get( i ) ) )
				{
					return String.class.getName( );
				}
			}

			for ( int i = 0; i < FunctionUtils.getBigDecimalTypefunctions( )
					.size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getBigDecimalTypefunctions( )
						.get( i ) ) )
				{
					return BigDecimal.class.getName( );
				}
			}

			for ( int i = 0; i < FunctionUtils.getDateTypefunctions( ).size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getDateTypefunctions( )
						.get( i ) ) )
				{
					return Date.class.getName( );
				}
			}

			for ( int i = 0; i < FunctionUtils.getTimeTypefunctions( ).size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getTimeTypefunctions( )
						.get( i ) ) )
				{
					return Time.class.getName( );
				}
			}

			for ( int i = 0; i < FunctionUtils.getTimestampTypefunctions( )
					.size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getTimestampTypefunctions( )
						.get( i ) ) )
				{
					return Timestamp.class.getName( );
				}
			}

			for ( int i = 0; i < FunctionUtils.getUnknownTypefunctions( )
					.size( ); i++ )
			{
				if ( function.equals( FunctionUtils.getUnknownTypefunctions( )
						.get( i ) ) )
				{
					return Object.class.getName( );
				}
			}
		}

		String className = null;
		int classIndex = -1;
		List<String> javaClasses = DatabaseMetaUtil.getDataTypeClassNames( );
		for ( int i = 0; i < javaClasses.size( ); i++ )
		{
			String clazz = javaClasses.get( i );
			int index = javaCode.lastIndexOf( clazz );
			if ( index > classIndex )
			{
				if ( index > 0 )
				{
					char ch = javaCode.charAt( index - 1 );
					if ( ( ch >= 'a' && ch <= 'z' )
							|| ( ch >= 'A' && ch <= 'Z' )
							|| ( ch >= '0' && ch <= '9' ) )
					{
						continue;
					}
				}
				className = clazz;
				classIndex = index;
			}
		}
		if ( className != null )
			return className;

		ColumnMetaData column = findColumn( javaCode );
		if ( column != null )
		{
			return DatabaseMetaUtil.getSimpleJavaClass( column.getJavaTypeClass( ) );
		}

		Pattern pattern = Pattern.compile( "\".+?\"" );
		Matcher matcher = pattern.matcher( javaCode );
		if ( matcher.find( ) )
			return String.class.getName( );

		pattern = Pattern.compile( "\\d+\\.\\d+" );
		matcher = pattern.matcher( javaCode );
		if ( matcher.find( ) )
			return Double.class.getName( );

		pattern = Pattern.compile( "\\d+" );
		matcher = pattern.matcher( javaCode );
		if ( matcher.find( ) )
			return Integer.class.getName( );

		return Object.class.getName( );
	}

	private ColumnMetaData findColumn( String javaCode )
	{
		if ( metadata != null )
		{
			String[] tableNames = metadata.getTableNames( );
			for ( int i = 0; i < tableNames.length; i++ )
			{
				TableMetaData tableMetaData = metadata.getTableMetaData( tableNames[i] );
				String[] columnNames = tableMetaData.getColumnNames( );
				for ( int j = 0; j < columnNames.length; j++ )
				{
					if ( javaCode.toLowerCase( )
							.indexOf( columnNames[j].toLowerCase( ) ) != -1 )
					{
						return tableMetaData.getColumnMetaData( columnNames[j] );
					}
				}
			}
		}
		return null;
	}

	private String getExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt )
	{
		return getExpressionJavaCode( expression, stmt, null );
	}

	private String getTableName( TTable table, TCustomSqlStatement stmt )
	{
		if ( table.getTableName( ) != null
				&& table.getName( ).equalsIgnoreCase( "DUAL" ) )
		{
			return "DSL.dual()";
		}
		else if ( table.getAliasClause( ) != null
				&& table.getTableType( ) != ETableSource.rowList )
		{
			return table.getAliasClause( ).toString( ).toLowerCase( );
		}
		else if ( table.getAliasClause( ) != null
				&& table.getTableType( ) == ETableSource.rowList )
		{
			StringBuffer buffer = new StringBuffer( );
			buffer.append( getTableRealName( table, stmt ) );
			buffer.append( ".as( \"" )
					.append( table.getAliasClause( )
							.getAliasName( )
							.toString( )
							.toLowerCase( ) )
					.append( "\"" );
			TObjectNameList columnNames = table.getAliasClause( ).getColumns( );
			if ( columnNames != null )
			{
				for ( int j = 0; j < columnNames.size( ); j++ )
				{
					buffer.append( ", \"" );
					buffer.append( columnNames.getObjectName( j )
							.toString( )
							.toLowerCase( ) ).append( "\"" );
				}
			}
			buffer.append( " )" );
			return buffer.toString( );
		}
		else
		{
			return getTableRealName( table, stmt );
		}
	}

	private String getTableRealName( TTable table, TCustomSqlStatement stmt )
	{
		if ( table.getTableType( ) == ETableSource.rowList )
		{
			StringBuffer buffer = new StringBuffer( );
			buffer.append( "DSL.values( " );
			for ( int i = 0; i < table.getRowList( ).size( ); i++ )
			{
				TMultiTarget target = table.getRowList( ).getMultiTarget( i );
				TResultColumnList columns = target.getColumnList( );
				buffer.append( "DSL.row( " );
				for ( int j = 0; j < columns.size( ); j++ )
				{
					TResultColumn column = columns.getResultColumn( j );
					buffer.append( getExpressionJavaCode( column.getExpr( ),
							stmt ) );
					if ( j < columns.size( ) - 1 )
					{
						buffer.append( ", " );
					}
				}
				buffer.append( " )" );
				if ( i < table.getRowList( ).size( ) - 1 )
				{
					buffer.append( ", " );
				}
			}
			buffer.append( " )" );
			return buffer.toString( );
		}
		return getTableJavaName( table )
				+ "."
				+ table.getTableName( ).toString( ).toUpperCase( );
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
		try
		{
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
							stmt,
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
				case unary_bitwise_not_t :
					buffer.append( getUnaryBitwiseNotExpressionJavaCode( expression,
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
				case bitwise_xor_t :
					buffer.append( getBitwiseXorExpressionJavaCode( expression,
							stmt,
							column ) );
					break;
				case left_shift_t :
					buffer.append( getBitwiseLeftShiftExpressionJavaCode( expression,
							stmt,
							column ) );
					break;
				case right_shift_t :
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
					break;
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
				case exists_t :
					buffer.append( getExistsExpressionJavaCode( expression ) );
					break;
				case subquery_t :
					buffer.append( getQueryJavaCode( expression.getSubQuery( ) ) );
					break;
				case unary_squareroot_t :
					buffer.append( "DSL.sqrt( "
							+ getExpressionJavaCode( expression.getRightOperand( ),
									stmt,
									columns )
							+ " )" );
					break;
				case unary_absolutevalue_t :
					buffer.append( "DSL.abs( "
							+ getExpressionJavaCode( expression.getRightOperand( ),
									stmt,
									columns )
							+ " )" );
					break;
				case unary_binary_operator_t :
					throw new PlainSQLException( expression, stmt );
				case typecast_t :
					throw new PlainSQLException( expression, stmt );
				case row_constructor_t :
					buffer.append( "DSL.row( "
							+ getListExpressionJavaCode( expression,
									stmt,
									columns ) + " )" );
					break;
				case array_constructor_t :
					buffer.append( getArrayExpressionJavaCode( expression,
							stmt,
							columns ) );
					break;
				case unknown_t :
					throw new PlainSQLException( expression, stmt );
				default :
					System.err.println( "Expression: "
							+ expression.toString( )
							+ "\nDoesn't support the operation: "
							+ expression.getExpressionType( )
							+ "\nConvert it to the plain sql." );
					throw new PlainSQLException( expression, stmt );
			}
		}
		catch ( PlainSQLException e )
		{
			if ( !e.isFromField( ) )
			{
				buffer.append( "DSL.condition( \""
						+ escape( expression.toString( ) )
						+ "\" )" );
			}
			else if ( e.needConvertToField( ) )
			{
				buffer.append( "DSL.field( \""
						+ escape( expression.toString( ) )
						+ "\" )" );
			}
			else
				throw e;
		}
		return buffer.toString( );
	}

	private String escape( String content )
	{
		return content.replace( "\"", "\\\"" );
	}

	private String getExistsExpressionJavaCode( TExpression expression )
	{
		return "DSL"
				+ getOperateSubqueryJavaCode( "exists",
						expression.getSubQuery( ) );
	}

	private String getOperateSubqueryJavaCode( String operation,
			TSelectSqlStatement subquery )
	{
		StringBuffer buffer = new StringBuffer( );
		buffer.append( "." ).append( operation ).append( "( " );
		buffer.append( getQueryJavaCode( subquery ) );
		buffer.append( " )" );
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
			if ( column == null )
			{
				column = findColumn( getExpressionJavaCode( inputExpr, stmt ) );
			}
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
					if ( column == null )
					{
						column = findColumn( getExpressionJavaCode( item.getComparison_expr( ),
								stmt ) );
					}
				}
				buffer.append( ", " );
				if ( item.getReturn_expr( ) != null )
				{
					buffer.append( getExpressionJavaCode( item.getReturn_expr( ),
							stmt,
							column ) );
					if ( column == null )
					{
						column = findColumn( getExpressionJavaCode( item.getReturn_expr( ),
								stmt ) );
					}
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

	private String getArrayExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData[] columns )
	{
		TExpressionList exprList = expression.getExprList( );
		StringBuffer buffer = new StringBuffer( );
		buffer.append( "new Field[]{ " );
		for ( int i = 0; i < exprList.size( ); i++ )
		{
			buffer.append( "DSL.field( \""
					+ escape( exprList.getExpression( i ).toString( ) )
					+ "\" )" );
			if ( i < exprList.size( ) - 1 )
			{
				buffer.append( ", " );
			}
		}
		buffer.append( " }" );
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
			return "String.valueOf( " + content + " )";
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
		return content.equals( "null" ) ? "(Object)null" : content;
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
		buffer.append( "DSL." ).append( operation ).append( "( " );
		buffer.append( getExpressionJavaCode( expression.getLeftOperand( ),
				stmt,
				column ) );
		if ( expression.getLeftOperand( ).getExpressionType( ) == EExpressionType.subquery_t )
		{
			buffer.append( ".asField( )" );
		}
		buffer.append( ", " );
		buffer.append( getExpressionJavaCode( expression.getRightOperand( ),
				stmt,
				column ) );
		if ( expression.getRightOperand( ).getExpressionType( ) == EExpressionType.subquery_t )
		{
			buffer.append( ".asField( )" );
		}
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
		if ( ( useExpressionSelf ? expression : expression.getLeftOperand( ) ).getExpressionType( ) == EExpressionType.subquery_t )
		{
			buffer.append( ".asField( )" );
		}
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getLeftOperateRightJavaCode( String operation,
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( expression.getLeftOperand( ).getExpressionType( ) == EExpressionType.list_t )
		{
			buffer.append( "DSL.row( " );
			buffer.append( getExpressionJavaCode( expression.getLeftOperand( ),
					stmt,

					column ) );
			buffer.append( " )" );
		}
		else
		{
			buffer.append( getExpressionJavaCode( expression.getLeftOperand( ),
					stmt,
					column ) );
			if ( expression.getLeftOperand( ).getExpressionType( ) == EExpressionType.subquery_t )
			{
				buffer.append( ".asField( )" );
			}
		}

		buffer.append( "." ).append( operation ).append( "( " );
		if ( expression.getRightOperand( ) != null )
		{
			buffer.append( getExpressionJavaCode( expression.getRightOperand( ),
					stmt,
					column ) );

			if ( !operation.equals( "in" )
					&& !operation.equals( "notIn" )
					&& expression.getRightOperand( ).getExpressionType( ) == EExpressionType.subquery_t )
			{
				buffer.append( ".asField( )" );
			}
		}
		buffer.append( " )" );
		return buffer.toString( );
	}

	private String getLeftOperateRightJavaCode( String operation,
			TCustomSqlStatement stmt, ColumnMetaData column,
			TExpression leftExpression, Object... rightExpressions )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( leftExpression.getExpressionType( ) == EExpressionType.list_t )
		{
			buffer.append( "DSL.row( " );
			buffer.append( getExpressionJavaCode( leftExpression, stmt, column ) );
			buffer.append( " )" );
		}
		else
		{
			buffer.append( getExpressionJavaCode( leftExpression, stmt, column ) );
			if ( leftExpression.getExpressionType( ) == EExpressionType.subquery_t )
			{
				buffer.append( ".asField( )" );
			}
		}
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
					if ( ( (TExpression) rightExpressions[i] ).getExpressionType( ) == EExpressionType.subquery_t )
					{
						buffer.append( ".asField( )" );
					}
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

	private String getBitwiseXorExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return getTwoArgmentsOperationJavaCode( "bitXor",
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

	private String getUnaryBitwiseNotExpressionJavaCode(
			TExpression expression, TCustomSqlStatement stmt,
			ColumnMetaData column )
	{
		return "DSL"
				+ getOneArgmentsOperationJavaCode( "bitNot",
						expression.getRightOperand( ),
						stmt,
						column,
						true );
	}

	private String getLogicNotExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData column )
	{
		return "DSL"
				+ getOneArgmentsOperationJavaCode( "not",
						expression.getRightOperand( ),
						stmt,
						column,
						true );
	}

	private String getObjectNameExpressionJavaCode( TExpression expression,
			TCustomSqlStatement stmt )
	{
		return getObjectColumnName( expression.toString( ), stmt );
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

		if ( expr.getQuantifier( ) != null )
		{
			String quantifier = expr.getQuantifier( )
					.toString( )
					.trim( )
					.toLowerCase( );
			if ( quantifier.equals( "all" ) || quantifier.equals( "any" ) )
			{
				buffer.append( "DSL." + quantifier + "( " );
			}
			else
			{
				throw new PlainSQLException( expr, stmt );
			}
		}
		buffer.append( "DSL.row( "
				+ getExpressionJavaCode( expr.getRightOperand( ), stmt, columns )
				+ " )" );
		buffer.append( " )" );
		if ( expr.getQuantifier( ) != null )
		{
			buffer.append( " )" );
		}
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
		if ( expr.getLeftOperand( ).getExpressionType( ) == EExpressionType.subquery_t )
		{
			buffer.append( ".asField( )" );
		}
		buffer.append( "." + operation + "( " );
		Object column = null;
		if ( expr.getLeftOperand( ).getExpressionType( ) == EExpressionType.function_t
				&& expr.getLeftOperand( ).getFunctionCall( ).getArgs( ).size( ) == 1 )
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
		if ( expr.getRightOperand( ).getExpressionType( ) == EExpressionType.subquery_t )
		{
			buffer.append( ".asField( )" );
		}
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
			TTable table = getTableFromName( tableName, stmt );
			if ( table != null && table.getTableName( ) != null )
			{
				TableMetaData tableMetaData = metadata.getTableMetaData( table.getTableName( )
						.toString( ) );
				if ( tableMetaData != null )
				{
					return tableMetaData.getColumnMetaData( columnName );
				}
			}
		}
		else
		{
			List<TTable> tables = getStmtTables( stmt );
			if ( tables.size( ) > 0 )
			{
				TTable table = tables.get( 0 );
				if ( table != null && table.getTableName( ) != null )
				{
					TableMetaData tableMetaData = metadata.getTableMetaData( table.getTableName( )
							.toString( ) );
					if ( tableMetaData != null )
					{
						return tableMetaData.getColumnMetaData( columnName );
					}
				}
			}
			else
				return null;
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
				TTable table = getTableFromName( tableName, stmt );
				if ( table != null && table.getTableName( ) != null )
				{
					TableMetaData tableMetaData = metadata.getTableMetaData( table.getTableName( )
							.toString( ) );
					if ( tableMetaData != null )
					{
						metaDatas[i] = tableMetaData.getColumnMetaData( columnName );
					}
				}
			}
			else
			{
				List<TTable> tables = getStmtTables( stmt );
				if ( tables.size( ) > 0 )
				{
					TTable table = tables.get( 0 );
					if ( table != null && table.getTableName( ) != null )
					{
						TableMetaData tableMetaData = metadata.getTableMetaData( table.getTableName( )
								.toString( ) );
						if ( tableMetaData != null )
						{
							metaDatas[i] = tableMetaData.getColumnMetaData( columnName );
						}
					}
				}
				else
					metaDatas[i] = null;
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

	private String getColumnName( TResultColumn column, TCustomSqlStatement stmt )
	{
		return getResultColumnName( column, stmt, null );
	}

	private String getExpressionColumnName( TExpression expr,
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
		switch ( expr.getExpressionType( ) )
		{
			case simple_object_name_t :
				return getObjectColumnName( expr.toString( ), stmt );
			case subquery_t :
				break;
			case simple_constant_t :
				return "DSL.inline( "
						+ getConstantExpressionJavaCode( expr, column )
						+ " )";
			case function_t :
				return getFunctionJavaCode( expr, stmt, columns );
			default :
				break;
		}
		return "";
	}

	private String getResultColumnName( TResultColumn field,
			TCustomSqlStatement container, Object columnInfo )
	{
		String name = getExpressionColumnName( field.getExpr( ),
				container,
				columnInfo );
		TExpression expr = field.getExpr( );
		if ( expr.getExpressionType( ) == EExpressionType.subquery_t )
		{
			if ( field.getAliasClause( ) != null )
			{
				String alias = field.getAliasClause( )
						.toString( )
						.toLowerCase( );
				if ( fieldAliases.containsKey( alias ) )
				{
					return alias;
				}
				TSelectSqlStatement stmt = field.getExpr( ).getSubQuery( );
				StringBuffer buffer = new StringBuffer( );
				if ( metadata == null || ignoreGeneric )
				{
					buffer.append( "Field " );
				}
				else
				{
					String type = getResultsetColumnType( stmt,
							stmt.getResultColumnList( ).getResultColumn( 0 ) );
					if ( type.equals( DatabaseMetaUtil.getSimpleJavaClass( Object.class.getName( ) ) ) )
					{
						buffer.append( "Field " );
					}
					else
					{
						buffer.append( "Field<" + type + "> " );
					}
				}
				String queryJavaCode = getQueryJavaCode( field.getExpr( )
						.getSubQuery( ) );
				buffer.append( alias )
						.append( " = " )
						.append( queryJavaCode )
						.append( ".asField(\"" + alias + "\");\n" );
				predefineBuffer.append( buffer );
				fieldAliases.put( alias, queryJavaCode );
				return alias;
			}
			else
				return name;
		}
		else
		{
			if ( field.getAliasClause( ) != null )
			{
				String alias = field.getAliasClause( )
						.toString( )
						.toLowerCase( );
				if ( fieldAliases.containsKey( alias ) )
				{
					return alias;
				}
				if ( metadata == null || ignoreGeneric )
				{
					predefineBuffer.append( "Field " );
				}
				else
				{
					String type = getResultsetColumnType( container, field );
					if ( type.equals( DatabaseMetaUtil.getSimpleJavaClass( Object.class.getName( ) ) ) )
					{
						predefineBuffer.append( "Field " );
					}
					else
					{
						predefineBuffer.append( "Field<" + type + "> " );
					}
				}
				String queryJavaCode = getExpressionJavaCode( field.getExpr( ),
						container,
						null );
				predefineBuffer.append( alias )
						.append( " = " )
						.append( queryJavaCode )
						.append( ".as(\"" + alias + "\");\n" );
				fieldAliases.put( alias, queryJavaCode );
				return alias;
			}
			else
				return name;
		}
	}

	private String getFunctionJavaCode( TExpression expression,
			TCustomSqlStatement stmt, ColumnMetaData[] columns )
	{
		StringBuffer buffer = new StringBuffer( );
		TFunctionCall function = expression.getFunctionCall( );

		if ( !supportFunction( function ) )
		{
			throw new PlainSQLException( expression, stmt );
		}
		else
		{
			String content = function.toString( ).toLowerCase( ).trim( );
			if ( content.indexOf( '(' ) > -1 )
			{
				content = content.substring( 0, content.indexOf( '(' ) ).trim( );
			}

			if ( FunctionUtils.isMysqlDSL( content ) )
			{
				buffer.append( "MySQLDSL." );
			}
			else if ( FunctionUtils.isPostgresDSL( content ) )
			{
				buffer.append( "PostgresDSL." );
			}
			else
			{
				buffer.append( "DSL." );
			}

			if ( needCompileFunction( function ) )
			{
				try
				{
					buffer.append( compileFunction( function, stmt, columns ) );
					return buffer.toString( );
				}
				catch ( PlainSQLException e )
				{
					throw new PlainSQLException( expression, stmt );
				}
			}

			buffer.append( FunctionUtils.convertFunctionToDSLMethod( content ) );
			buffer.append( "( " );
			buffer.append( getFunctionArgsJavaCode( function, stmt, columns ) );
			buffer.append( " )" );
		}
		return buffer.toString( );
	}

	private String getFunctionArgsJavaCode( TFunctionCall function,
			TCustomSqlStatement stmt, ColumnMetaData[] columns )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( function.getArgs( ) != null )
		{
			int argLength = function.getArgs( ).size( );
			for ( int i = 0; i < argLength; i++ )
			{
				TExpression arg = function.getArgs( ).getExpression( i );
				buffer.append( getFunctionArgExpressionJavaCode( arg,
						i,
						argLength,
						stmt,
						columns ) );
			}
		}
		else
		{
			if ( function.getExpr3( ) != null )
			{
				buffer.append( getFunctionArgExpressionJavaCode( function.getExpr1( ),
						0,
						3,
						stmt,
						columns ) );
				buffer.append( getFunctionArgExpressionJavaCode( function.getExpr2( ),
						1,
						3,
						stmt,
						columns ) );
				buffer.append( getFunctionArgExpressionJavaCode( function.getExpr3( ),
						0,
						3,
						stmt,
						columns ) );
			}
			else if ( function.getExpr2( ) != null )
			{
				buffer.append( getFunctionArgExpressionJavaCode( function.getExpr1( ),
						0,
						2,
						stmt,
						columns ) );
				buffer.append( getFunctionArgExpressionJavaCode( function.getExpr2( ),
						1,
						2,
						stmt,
						columns ) );
			}
			else if ( function.getExpr1( ) != null )
			{
				buffer.append( getFunctionArgExpressionJavaCode( function.getExpr1( ),
						0,
						1,
						stmt,
						columns ) );
			}
			else if ( function.getExprList( ) != null )
			{
				int argLength = function.getExprList( ).size( );
				for ( int i = 0; i < function.getExprList( ).size( ); i++ )
				{
					TExpression arg = function.getExprList( ).getExpression( i );
					buffer.append( getFunctionArgExpressionJavaCode( arg,
							i,
							argLength,
							stmt,
							columns ) );
				}
			}
			else if ( function.getTrimArgument( ) != null )
			{
				TExpression expr = function.getTrimArgument( )
						.getStringExpression( );
				buffer.append( getFunctionArgExpressionJavaCode( expr,
						0,
						1,
						stmt,
						columns ) );
			}
		}
		return buffer.toString( );
	}

	private boolean needCompileFunction( TFunctionCall function )
	{
		String content = function.toString( ).toLowerCase( ).trim( );
		if ( content.indexOf( '(' ) != -1 )
		{
			content = content.substring( 0, content.indexOf( '(' ) ).trim( );
		}
		if ( function.getArgs( ) != null && function.getArgs( ).size( ) == 1 )
		{
			if ( content.equalsIgnoreCase( "log" )
					|| content.equalsIgnoreCase( "log2" )
					|| content.equalsIgnoreCase( "log10" ) )
			{
				return true;
			}
		}
		if ( function.getArgs( ) != null && function.getArgs( ).size( ) == 2 )
		{
			if ( content.equalsIgnoreCase( "log" ) )
			{
				return true;
			}
		}
		if ( content.equalsIgnoreCase( "cast" ) )
		{
			return true;
		}
		return false;
	}

	private String compileFunction( TFunctionCall function,
			TCustomSqlStatement stmt, ColumnMetaData[] columns )
	{
		String content = function.toString( ).toLowerCase( );
		content = content.substring( 0, content.indexOf( '(' ) ).trim( );
		if ( function.getArgs( ) != null && function.getArgs( ).size( ) == 1 )
		{
			if ( content.equalsIgnoreCase( "log" ) )
			{
				return "ln( "
						+ getFunctionArgsJavaCode( function, stmt, columns )
						+ " )";
			}
			else if ( content.equalsIgnoreCase( "log2" ) )
			{
				return "log( "
						+ getFunctionArgsJavaCode( function, stmt, columns )
						+ ", 2 )";
			}
			else if ( content.equalsIgnoreCase( "log10" ) )
			{
				return "log( "
						+ getFunctionArgsJavaCode( function, stmt, columns )
						+ ", 10 )";
			}
		}
		if ( function.getArgs( ) != null && function.getArgs( ).size( ) == 2 )
		{
			if ( content.equalsIgnoreCase( "log" ) )
			{
				String firstArg = getFunctionArgExpressionJavaCode( function.getArgs( )
						.getExpression( 0 ),
						0,
						2,
						stmt,
						columns ).trim( );
				firstArg = firstArg.substring( 0, firstArg.lastIndexOf( ',' ) );
				return "log( "
						+ getFunctionArgExpressionJavaCode( function.getArgs( )
								.getExpression( 1 ), 1, 2, stmt, columns )
						+ ", "
						+ firstArg
						+ ".getValue( ) )";
			}
		}
		if ( content.equalsIgnoreCase( "cast" )
				&& function.getTypename( ) != null )
		{
			String typeName = function.getTypename( ).toString( ).toUpperCase( );
			SQLDialect dialect = GenerationUtil.getSQLDialect( function.getGsqlparser( )
					.getDbVendor( ) );
			try
			{
				DefaultDataType.getDataType( dialect, typeName );
				return "cast( "
						+ getFunctionArgsJavaCode( function, stmt, columns )
						+ ", DefaultDataType.getDataType( SQLDialect."
						+ dialect
						+ ", \""
						+ typeName
						+ "\" ) )";
			}
			catch ( Exception e )
			{
				throw new PlainSQLException( null, stmt );
			}
		}
		throw new PlainSQLException( null, stmt );
	}

	private String getFunctionArgExpressionJavaCode( TExpression argExpression,
			int argIndex, int argLength, TCustomSqlStatement stmt,
			ColumnMetaData[] columns )
	{
		StringBuffer buffer = new StringBuffer( );
		if ( argExpression.toString( ).indexOf( '*' ) != -1 )
		{
			if ( argExpression.toString( ).trim( ).length( ) == 1 )
			{
				return buffer.toString( );
			}
			else
			{
				buffer.append( argExpression.toString( )
						.replace( "*", "" )
						.toLowerCase( ) );
				if ( argIndex < argLength - 1 )
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
				if ( columns.length > argIndex )
				{
					column = columns[argIndex];
				}
				else if ( columns.length >= 1 )
				{
					column = columns[0];
				}
			}
			buffer.append( getExpressionJavaCode( argExpression, stmt, column ) );
			if ( argIndex < argLength - 1 )
			{
				buffer.append( ", " );
			}
		}
		return buffer.toString( );
	}

	private boolean supportFunction( TFunctionCall function )
	{
		String content = function.toString( ).trim( ).toLowerCase( );
		if ( content.indexOf( '(' ) != -1 )
		{
			content = content.substring( 0, content.indexOf( '(' ) ).trim( );
		}
		boolean flag = false;
		List<String> unsupportFunctions = FunctionUtils.getUnsupportFunctions( sqlparser.getDbVendor( ) );
		for ( int i = 0; i < unsupportFunctions.size( ); i++ )
		{
			if ( unsupportFunctions.get( i ).equalsIgnoreCase( content ) )
			{
				flag = true;
				break;
			}
		}

		if ( !flag )
		{
			for ( int i = 0; i < FunctionUtils.getUnSupportFunctionRegexs( )
					.size( ); i++ )
			{
				String pattern = FunctionUtils.getUnSupportFunctionRegexs( )
						.get( i );
				if ( function.toString( ).matches( pattern ) )
				{
					flag = true;
					break;
				}
			}
		}

		if ( flag )
		{

			for ( int i = 0; i < FunctionUtils.getSupportFunctionRegexs( )
					.size( ); i++ )
			{
				String pattern = FunctionUtils.getSupportFunctionRegexs( )
						.get( i );
				if ( function.toString( ).matches( pattern ) )
				{
					return true;
				}
			}
		}
		return !flag;
	}

	private String getObjectColumnName( String columnName,
			TCustomSqlStatement stmt )
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

			if ( asTables.contains( tableName.toLowerCase( ) ) )
			{
				if ( metadata == null || ignoreGeneric )
				{
					return "((Field)"
							+ tableName.toLowerCase( )
							+ ".field( \""
							+ columnName.toLowerCase( )
							+ "\" ))";
				}
				else
				{
					String fieldAlias = columnName.toLowerCase( );
					if ( fieldAliases.keySet( ).contains( fieldAlias ) )
					{
						return "((Field<"
								+ DatabaseMetaUtil.getSimpleJavaClass( guessExpressionJavaTypeClass( fieldAliases.get( fieldAlias ) ) )
								+ ">)"
								+ tableName.toLowerCase( )
								+ ".field( \""
								+ columnName.toLowerCase( )
								+ "\" ))";
					}
					else
						return "((Field<"
								+ DatabaseMetaUtil.getSimpleJavaClass( guessExpressionJavaTypeClass( columnName.toLowerCase( ) ) )
								+ ">)"
								+ tableName.toLowerCase( )
								+ ".field( \""
								+ columnName.toLowerCase( )
								+ "\" ))";
				}
			}

			TTable table = getTableFromName( tableName, stmt );

			tableName = caseTableName( tableName, stmt );

			if ( table != null
					&& table.getTableName( ) != null
					&& columnName.equalsIgnoreCase( table.getTableName( )
							.toString( ) ) )
			{
				columnName += "_";
			}

			if ( metadata == null || ignoreGeneric )
				return "((Field)" + tableName + "." + columnName + ")";
			else
				return tableName + "." + columnName;
		}
		else if ( fieldAliases.keySet( ).contains( columnName.toLowerCase( ) ) )
		{
			return columnName.toLowerCase( );
		}
		else
		{
			List<TTable> tables = getStmtTables( stmt );
			TTable table = null;
			if ( metadata != null && tables.size( ) > 0 )
			{
				for ( int i = 1; i < tables.size( ); i++ )
				{
					TObjectName tableName = tables.get( i ).getTableName( );
					if ( tableName == null )
						continue;
					TableMetaData tableMetaData = metadata.getTableMetaData( tableName.toString( ) );
					if ( tableMetaData != null )
					{
						ColumnMetaData columnMetaData = tableMetaData.getColumnMetaData( columnName );
						if ( columnMetaData != null )
						{
							table = tables.get( i );
							break;
						}
					}
				}
			}

			if ( table == null && tables.size( ) > 0 )
			{
				table = tables.get( 0 );
			}

			if ( table == null )
			{
				return columnName;
			}

			if ( table.getTableName( ) != null
					&& columnName.equalsIgnoreCase( table.getTableName( )
							.toString( ) ) )
			{
				columnName += "_";
			}

			if ( metadata == null || ignoreGeneric )
				return "((Field)"
						+ getTableName( table, stmt )
						+ "."
						+ columnName.toUpperCase( )
						+ ")";
			else
				return getTableName( table, stmt )
						+ "."
						+ columnName.toUpperCase( );
		}
	}

	private List<TTable> getStmtTables( TCustomSqlStatement stmt )
	{
		List<TTable> tables = new ArrayList<TTable>( );
		appendStmtTable( tables, stmt );
		return tables;
	}

	private void appendStmtTable( List<TTable> tables, TCustomSqlStatement stmt )
	{
		if ( stmt instanceof TSelectSqlStatement )
		{
			TSelectSqlStatement select = (TSelectSqlStatement) stmt;
			if ( select.isCombinedQuery( ) )
			{
				appendStmtTable( tables, select.getLeftStmt( ) );
				appendStmtTable( tables, select.getRightStmt( ) );
			}
		}
		if ( stmt != null && stmt.tables != null )
		{
			for ( int i = 0; i < stmt.tables.size( ); i++ )
			{
				TTable table = stmt.tables.getTable( i );
				if ( !tables.contains( table ) )
					tables.add( table );
			}
		}
	}

	private TTable getTableFromName( String tableName, TCustomSqlStatement stmt )
	{
		List<TTable> tables = getStmtTables( stmt );

		for ( int i = 0; i < tables.size( ); i++ )
		{
			TTable table = tables.get( i );
			if ( table.getTableName( ) != null
					&& table.getTableName( )
							.toString( )
							.equalsIgnoreCase( tableName ) )
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

		if ( stmt.getParentStmt( ) != null )
		{
			return getTableFromName( tableName, stmt.getParentStmt( ) );
		}
		return null;
	}

	private String caseTableName( String tableName, TCustomSqlStatement stmt )
	{
		List<TTable> tables = getStmtTables( stmt );

		for ( int i = 0; i < tables.size( ); i++ )
		{
			TTable table = tables.get( i );
			if ( table.getTableName( ) != null
					&& table.getTableName( )
							.toString( )
							.equalsIgnoreCase( tableName ) )
			{
				return getTableName( table, stmt );
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

	private String getTableJavaName( TTable table )
	{
		if ( table.getTableName( ) != null )
		{
			return GenerationUtil.convertToJavaIdentifier( StringUtils.toCamelCase( table.getName( ) ) );
		}
		else if ( table.getAliasClause( ) != null )
		{
			return GenerationUtil.convertToJavaIdentifier( StringUtils.toCamelCase( table.getAliasClause( )
					.toString( ) ) );
		}
		return null;
	}
}
