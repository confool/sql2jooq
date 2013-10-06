
package gudusoft.sql2jooq.guestbook;

import gudusoft.guestbook.tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class testGuestBook
{

	public static void main( String[] args )
	{
		Connection conn = null;

		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/guestbook";

		try
		{
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance( );
			conn = DriverManager.getConnection( url, userName, password );

			Result<Record> result = getResultSet( conn );

			for ( Record r : result )
			{
				Long id = r.getValue( Posts.POSTS.ID );
				String title = r.getValue( Posts.POSTS.TITLE );
				String description = r.getValue( Posts.POSTS.BODY );

				System.out.println( "ID: "
						+ id
						+ " title: "
						+ title
						+ " description: "
						+ description );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
		finally
		{
			if ( conn != null )
			{
				try
				{
					conn.close( );
				}
				catch ( SQLException ignore )
				{
				}
			}
		}

	}

	private static Result getResultSet( Connection conn )
	{
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
Posts a = Posts.POSTS.as("a");
Mails b = Mails.MAILS.as("b");
Result<org.jooq.Record4<java.lang.Long, java.lang.String, java.lang.String, java.lang.String>> result = create.select( b.ID, a.TITLE, a.BODY, b.ADDRESS )
	.from( a, b )
	.where( a.BODY.equal( java.lang.String.valueOf( "Hello World" ) ).and( a.TIMESTAMP.equal( java.sql.Timestamp.valueOf( "2003-10-01 00:24:08" ) ) ).and( a.ID.equal( java.lang.Long.valueOf( 1 ) ) ) )
	.groupBy( a.BODY, a.ID )
	.having( DSL.count(  ).greaterThan( 0 ) )
	.orderBy( a.ID ).fetch( );

		return result;
	}

}
