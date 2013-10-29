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
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class SakilaTest0012 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select country, city from country left join city on city.country_id = country.country_id";
		
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

Result result = create.select( ((Field)Country.COUNTRY.COUNTRY_), ((Field)City.CITY.CITY_) )
	.from( Country.COUNTRY )
	.leftOuterJoin( City.CITY ).on( ((Field)City.CITY.COUNTRY_ID).equal( ((Field)Country.COUNTRY.COUNTRY_ID) ) ).fetch( );

		return result;
	}
}
