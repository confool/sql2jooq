
package gudusoft.sql2jooq.school.tests;

import gudusoft.db.postgres.school.tables.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;
import org.junit.*;
import org.jooq.types.*;
import org.jooq.util.postgres.*;

@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class SchoolTest
{

	public static void main( String[] args )
	{
		Connection conn = null;

		String userName = "postgres";
		String password = "test";
		String url = "jdbc:postgresql://localhost:5434/postgres";

		try
		{
			Class.forName( "org.postgresql.Driver" ).newInstance( );
			conn = DriverManager.getConnection( url, userName, password );

			Result<Record> result = getResultSet( conn );

			for ( Record r : result )
			{
				String name = r.getValue( Student.STUDENT.NAME );
				Integer age = r.getValue( Student.STUDENT.AGE );
				String sex = r.getValue( Student.STUDENT.SEX );
				Date sign = r.getValue( Student.STUDENT.SIGNUPDATE );

				System.out.println( "NAME: "
						+ name
						+ " AGE: "
						+ age
						+ " SEX: "
						+ sex
						+ " SIGN UP DATE: "
						+ sign );
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
		DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);

Result<Record> result = create.select(  )
	.from( Student.STUDENT ).fetch( );

		return result;
	}

}
