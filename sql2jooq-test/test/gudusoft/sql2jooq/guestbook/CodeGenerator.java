
package gudusoft.sql2jooq.guestbook;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.sql2jooq.jooqConverter;
import gudusoft.sql2jooq.util.FileUtil;

import java.io.File;
import java.util.regex.Pattern;

public class CodeGenerator
{

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		jooqConverter convert = new jooqConverter( EDbVendor.dbvmysql,
				"select * from posts a where a.body = 'Hello World' and a.id = 1 group by a.body, a.id having count(*) > 0 order by a.id;" );
		String result = convert.getConvertResult( );
		File templateFile = new File( "./xml/guestbook/testGuestBook.template" );
		File saveFile = new File( "./test/gudusoft/sql2jooq/guestbook/testGuestBook.java" );
		FileUtil.replaceFile( templateFile,
				Pattern.quote( "${DSL}" ),
				Pattern.quote( "${DSL}" ),
				result,
				saveFile );
	}

}
