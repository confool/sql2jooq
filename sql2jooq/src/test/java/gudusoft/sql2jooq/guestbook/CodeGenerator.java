
package gudusoft.sql2jooq.guestbook;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.sql2jooq.SQL2jOOQ;
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

			SQL2jOOQ convert = new SQL2jOOQ( metaData,
					EDbVendor.dbvmysql,
					"select id, title, body, address from posts a, mails b where a.body = 'Hello World' and a.timestamp = '2003-10-01 00:24:08' and a.id = 1 group by a.body, a.id having count(*) > 0 order by a.id;" );
			convert.convert( );
			if ( convert.getErrorMessage( ) != null )
			{
				System.err.println( convert.getErrorMessage( ) );
				return;
			}
			String result = convert.getConvertResult( );

            File dir = new File("./src/test/java/gudusoft/sql2jooq/guestbook/tests");
            dir.mkdirs();
            File templateFile = new File( "./src/test/resources/templates/GuestbookTest.template.java" );
            File saveFile = new File(dir, "/GuestbookTest.java" );
            
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
