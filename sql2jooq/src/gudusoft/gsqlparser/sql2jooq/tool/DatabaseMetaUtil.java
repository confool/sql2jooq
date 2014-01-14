
package gudusoft.gsqlparser.sql2jooq.tool;

import gudusoft.gsqlparser.sql2jooq.db.ColumnMetaData;
import gudusoft.gsqlparser.sql2jooq.db.TableMetaData;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDataType;
import org.jooq.tools.jdbc.JDBCUtils;

@SuppressWarnings({
		"unchecked", "rawtypes", "unused", "all"
})
public class DatabaseMetaUtil
{

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
					try
					{
						ColumnMetaData columnMetaData = new ColumnMetaData( );
						columnMetaData.setColumnName( columns.getString( "COLUMN_NAME" ) );
						columnMetaData.setTypeName( columns.getString( "TYPE_NAME" ) );
						columnMetaData.setJavaTypeClass( getDataTypeClassName( con,
								columnMetaData.getTypeName( ) ) );
						tableMetaDatas[i].addColumnMetaData( columnMetaData );
					}
					catch ( Exception e )
					{
						System.err.println( "Exception : " + e.getMessage( ) );
					}
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
		if ( javaTypeClass.startsWith( "org.jooq.types." ) )
		{
			return javaTypeClass.replace( "org.jooq.types.", "" );
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

			Object obj = new Object( );
			className = ( obj.getClass( ) ).getName( );
			clazzes.add( getSimpleJavaClass( className ) );
		}

		return clazzes;
	}

	public static String getDataTypeClassName( Connection conn, String typeName )
	{
		SQLDialect dialect = JDBCUtils.dialect( conn );

		DataType dataType = DefaultDataType.getDataType( dialect, typeName );
		String name = dataType.getType( ).getName( );

		List<String> clazzes = getDataTypeClassNames( );
		if ( !clazzes.contains( getSimpleJavaClass( name ) ) )
			clazzes.add( 0, getSimpleJavaClass( name ) );

		return name;
	}
}
