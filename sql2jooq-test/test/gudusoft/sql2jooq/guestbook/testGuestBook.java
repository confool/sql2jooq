
package gudusoft.sql2jooq.guestbook;

import gudusoft.guestbook.tables.Posts;

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

	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		Connection conn = null;

		String userName = "root";
		String password = "cnfree";
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
						+ " desciption: "
						+ description );
			}
		}
		catch ( Exception e )
		{
			// For the sake of this tutorial, let's keep exception handling
			// simple
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
		DSLContext create = DSL.using( conn, SQLDialect.MYSQL );
		Posts a = Posts.POSTS.as( "a" );
		Result result = create.select( )
				.from( a )
				.where( ( (Field) a.BODY ).equal( "Hello World" )
						.and( ( (Field) a.ID ).equal( 1 ) ) )
				.groupBy( ( (Field) a.BODY ), ( (Field) a.ID ) )
				.having( DSL.count( ).greaterThan( 0 ) )
				.orderBy( ( (Field) a.ID ) )
				.fetch( );

		return result;
	}

}
