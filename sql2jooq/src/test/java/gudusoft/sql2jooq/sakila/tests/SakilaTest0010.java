package gudusoft.sql2jooq.sakila.tests;

import static org.junit.Assert.*;
import static gudusoft.db.mysql.sakila.Tables.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;
import org.jooq.util.mysql.MySQLDSL;
import org.junit.*;
import org.jooq.types.*;

import gudusoft.db.mysql.sakila.tables.*;
import gudusoft.sql2jooq.sakila.SakilaTest;

/**
 * @author Lukas Eder
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class SakilaTest0010 extends SakilaTest
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

Result<Record2<String, String>> result = create.select( c.CITY_, co.COUNTRY_ )
	.from( c )
	.join( co ).on( c.COUNTRY_ID.equal( co.COUNTRY_ID ) ).fetch( );

		return result;
	}
}
