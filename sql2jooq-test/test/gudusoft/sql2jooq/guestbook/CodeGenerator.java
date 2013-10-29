
package gudusoft.sql2jooq.guestbook;

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
			String userName = "root";
			String password = "";
			String url = "jdbc:mysql://localhost:3306/guestbook";

			Class.forName( "com.mysql.jdbc.Driver" ).newInstance( );
			Connection conn = DriverManager.getConnection( url,
					userName,
					password );
			DatabaseMetaData metaData = DatabaseMetaUtil.getDataBaseMetaData( conn,
					"guestbook" );

			jooqConverter convert = new jooqConverter( metaData,
					EDbVendor.dbvmysql,
					"select id, title, body, address from posts a, mails b where a.body = 'Hello World' and a.timestamp = '2003-10-01 00:24:08' and a.id = 1 group by a.body, a.id having count(*) > 0 order by a.id;" );
			convert.convert( );
			if ( convert.getErrorMessage( ) != null )
			{
				System.err.println( convert.getErrorMessage( ) );
				return;
			}
			String result = convert.getConvertResult( );
			File templateFile = new File( "./xml/guestbook/testGuestBook.template" );
			File saveFile = new File( "./test/gudusoft/sql2jooq/guestbook/testGuestBook.java" );
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
