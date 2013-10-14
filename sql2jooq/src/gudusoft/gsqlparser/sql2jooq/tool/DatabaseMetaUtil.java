
package gudusoft.gsqlparser.sql2jooq.tool;

import gudusoft.gsqlparser.sql2jooq.db.ColumnMetaData;
import gudusoft.gsqlparser.sql2jooq.db.TableMetaData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseMetaUtil
{

	private final static Map<Integer, String> dataTypeMap = new HashMap<Integer, String>( );

	static
	{
		// Get all field in java.sql.Types
		Field[] fields = java.sql.Types.class.getFields( );
		for ( int i = 0; i < fields.length; i++ )
		{
			try
			{
				String name = fields[i].getName( );
				Integer value = (Integer) fields[i].get( null );
				dataTypeMap.put( value, name );
			}
			catch ( IllegalAccessException e )
			{
			}
		}
	}

	public static gudusoft.gsqlparser.sql2jooq.db.DatabaseMetaData getDataBaseMetaData(
			Connection con, String databaseName )
	{
		gudusoft.gsqlparser.sql2jooq.db.DatabaseMetaData databaseMetaData = new gudusoft.gsqlparser.sql2jooq.db.DatabaseMetaData( );
		try
		{
			DatabaseMetaData dm = con.getMetaData( );
			ResultSet tables = dm.getTables( databaseName, null, null, null );
			while ( tables.next( ) )
			{
				TableMetaData tableMetaData = new TableMetaData( );
				tableMetaData.setTableName( tables.getString( "TABLE_NAME" ) );
				tableMetaData.setTableSchema( tables.getString( "TABLE_SCHEM" ) );
				databaseMetaData.addTableMetaData( tableMetaData );
			}

			TableMetaData[] tableMetaDatas = databaseMetaData.getTableMetaDatas( );
			for ( int i = 0; i < tableMetaDatas.length; i++ )
			{
				ResultSet columns = dm.getColumns( null,
						null,
						tableMetaDatas[i].getTableName( ),
						null );
				while ( columns.next( ) )
				{
					ColumnMetaData columnMetaData = new ColumnMetaData( );
					columnMetaData.setColumnName( columns.getString( "COLUMN_NAME" ) );
					columnMetaData.setSqlType( columns.getInt( "DATA_TYPE" ) );
					columnMetaData.setJavaTypeClass( getDataTypeClassName( columnMetaData.getSqlType( ) ) );
					tableMetaDatas[i].addColumnMetaData( columnMetaData );
				}
			}
		}
		catch ( SQLException ex )
		{
			System.err.println( "SQLException : " + ex.getMessage( ) );
		}
		finally
		{
			try
			{
				if ( con != null )
					con.close( );
			}
			catch ( Exception e )
			{
				e.printStackTrace( );
			}
		}
		return databaseMetaData;
	}

	public static String getSimpleJavaClass( String javaTypeClass )
	{
		if ( javaTypeClass.startsWith( "java.lang." ) )
		{
			return javaTypeClass.replace( "java.lang.", "" );
		}
		return javaTypeClass;
	}

	private static List<String> clazzes;

	public static List<String> getDataTypeClassNames( )
	{
		if ( clazzes == null )
		{
			clazzes = new ArrayList<String>( );
			String className = ( new java.math.BigDecimal( 0 ) ).getClass( )
					.getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Boolean( false ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Byte( "0" ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Short( "0" ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Integer( 0 ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Long( 0 ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Float( 0 ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new Double( 0 ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			byte[] b = {};
			className = ( b.getClass( ) ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new java.sql.Date( 123456 ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new java.sql.Time( 123456 ) ).getClass( ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			className = ( new java.sql.Timestamp( 123456 ) ).getClass( )
					.getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			byte[] blob = {};
			className = ( blob.getClass( ) ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );

			char[] c = {};
			className = ( c.getClass( ) ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );
		}

		return clazzes;
	}

	public static String getDataTypeClassName( int sqlType )
	{
		String className = ( new String( ) ).getClass( ).getName( );

		switch ( sqlType )
		{

			case Types.NUMERIC :
			case Types.DECIMAL :
				className = ( new java.math.BigDecimal( 0 ) ).getClass( )
						.getName( );
				break;

			case Types.BIT :
				className = ( new Boolean( false ) ).getClass( ).getName( );
				break;

			case Types.TINYINT :
				className = ( new Byte( "0" ) ).getClass( ).getName( );
				break;

			case Types.SMALLINT :
				className = ( new Short( "0" ) ).getClass( ).getName( );
				break;

			case Types.INTEGER :
				className = ( new Integer( 0 ) ).getClass( ).getName( );
				break;

			case Types.BIGINT :
				className = ( new Long( 0 ) ).getClass( ).getName( );
				break;

			case Types.REAL :
				className = ( new Float( 0 ) ).getClass( ).getName( );
				break;

			case Types.FLOAT :
			case Types.DOUBLE :
				className = ( new Double( 0 ) ).getClass( ).getName( );
				break;

			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
				byte[] b = {};
				className = ( b.getClass( ) ).getName( );
				break;

			case Types.DATE :
				className = ( new java.sql.Date( 123456 ) ).getClass( )
						.getName( );
				break;

			case Types.TIME :
				className = ( new java.sql.Time( 123456 ) ).getClass( )
						.getName( );
				break;

			case Types.TIMESTAMP :
				className = ( new java.sql.Timestamp( 123456 ) ).getClass( )
						.getName( );
				break;

			case Types.BLOB :
				byte[] blob = {};
				className = ( blob.getClass( ) ).getName( );
				break;

			case Types.CLOB :
				char[] c = {};
				className = ( c.getClass( ) ).getName( );
				break;
		}

		return className;
	}

}
