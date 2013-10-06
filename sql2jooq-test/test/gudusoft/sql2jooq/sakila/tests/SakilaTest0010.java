package gudusoft.sql2jooq.sakila.tests;

import static org.jooq.impl.DSL.*;
import static org.junit.Assert.*;
import static gudusoft.sakila.Tables.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;
import org.junit.*;

import gudusoft.sakila.tables.*;
import gudusoft.sql2jooq.sakila.MySQLTest;

/**
 * @author Lukas Eder
 */
public class SakilaTest0010 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select c.city, co.country "
+ "from city c  "
+ "join country co  "
+ "  on c.country_id = co.country_id";
		
		if (sql.toLowerCase().startsWith("select")) 
		{
			assertSame(ctx.fetch(sql), generatedSQL(conn));
		}
		else 
		{
			throw new UnsupportedOperationException("DML is not yet supported");
		}
	}
	
	private static Result generatedSQL( Connection conn )
	{
		DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
City c = City.CITY.as("c");
Country co = Country.COUNTRY.as("co");
Result result = create.select( ((Field)c.CITY_), ((Field)co.COUNTRY_) )
	.from( c )
	.join( co ).on( ((Field)c.COUNTRY_ID).equal( ((Field)co.COUNTRY_ID) ) ).fetch( );

		return result;
	}
}
