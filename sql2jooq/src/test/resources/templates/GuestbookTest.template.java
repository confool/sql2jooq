package gudusoft.sql2jooq.guestbook.tests;

import gudusoft.db.mysql.guestbook.tables.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.*;
import org.jooq.impl.*;
import org.junit.*;
import org.jooq.types.*;

@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class GuestbookTest
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
		${DSL}
		return result;
	}

}
