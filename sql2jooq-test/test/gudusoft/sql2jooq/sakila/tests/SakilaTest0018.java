package gudusoft.sql2jooq.sakila.tests;

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
public class SakilaTest0018 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select country, city, address from address right join city on address.city_id = city.city_id right join country on city.country_id = country.country_id";
		
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

Result<Record3<String, String, String>> result = create.select( Country.COUNTRY.COUNTRY_, City.CITY.CITY_, Address.ADDRESS.ADDRESS_ )
	.from( Address.ADDRESS )
	.rightOuterJoin( City.CITY ).on( Address.ADDRESS.CITY_ID.equal( City.CITY.CITY_ID ) )
	.rightOuterJoin( Country.COUNTRY ).on( City.CITY.COUNTRY_ID.equal( Country.COUNTRY.COUNTRY_ID ) ).fetch( );

		return result;
	}
}
