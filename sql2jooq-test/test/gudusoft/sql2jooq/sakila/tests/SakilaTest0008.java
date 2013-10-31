package gudusoft.sql2jooq.sakila.tests;

import static org.jooq.impl.DSL.*;
import static org.junit.Assert.*;
import static gudusoft.sakila.Tables.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;
import org.junit.*;
import org.jooq.types.*;

import gudusoft.sakila.tables.*;
import gudusoft.sql2jooq.sakila.MySQLTest;

/**
 * @author Lukas Eder
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class SakilaTest0008 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select city, country "
+ "from city join country on city.country_id = country.country_id";
		
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

Result result = create.select( ((Field)City.CITY.CITY_), ((Field)Country.COUNTRY.COUNTRY_) )
	.from( City.CITY )
	.join( Country.COUNTRY ).on( ((Field)City.CITY.COUNTRY_ID).equal( ((Field)Country.COUNTRY.COUNTRY_ID) ) ).fetch( );

		return result;
	}
}
