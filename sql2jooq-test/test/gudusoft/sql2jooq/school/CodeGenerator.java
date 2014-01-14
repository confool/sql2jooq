
package gudusoft.sql2jooq.school;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.sql2jooq.jooqConverter;
import gudusoft.gsqlparser.sql2jooq.db.DatabaseMetaData;
import gudusoft.gsqlparser.sql2jooq.tool.DatabaseMetaUtil;
import gudusoft.sql2jooq.util.FileUtil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.regex.Pattern;

public class CodeGenerator
{

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		try
		{
			String userName = "postgres";
			String password = "cnfree";
			String url = "jdbc:postgresql://localhost:5432/school";

			Class.forName( "org.postgresql.Driver" ).newInstance( );
			Connection conn = DriverManager.getConnection( url,
					userName,
					password );
			DatabaseMetaData metaData = DatabaseMetaUtil.getDataBaseMetaData( conn,
					"school" );

			jooqConverter convert = new jooqConverter( metaData,
					EDbVendor.dbvpostgresql,
					"select * from student;" );
			convert.convert( );
			if ( convert.getErrorMessage( ) != null )
			{
				System.err.println( convert.getErrorMessage( ) );
				return;
			}
			String result = convert.getConvertResult( );
			File templateFile = new File( "./xml/postgresql/testSchool.template" );
			File saveFile = new File( "./test/gudusoft/sql2jooq/school/testSchool.java" );
			FileUtil.replaceFile( templateFile,
					Pattern.quote( "${DSL}" ),
					Pattern.quote( "${DSL}" ),
					result,
					saveFile );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
	}

}
