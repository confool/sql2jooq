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
public class SakilaTest0017 extends MySQLTest
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

Result result = create.select( ((Field)Country.COUNTRY.COUNTRY_), ((Field)City.CITY.CITY_), ((Field)Address.ADDRESS.ADDRESS_) )
	.from( Address.ADDRESS )
	.rightOuterJoin( City.CITY ).on( ((Field)Address.ADDRESS.CITY_ID).equal( ((Field)City.CITY.CITY_ID) ) )
	.rightOuterJoin( Country.COUNTRY ).on( ((Field)City.CITY.COUNTRY_ID).equal( ((Field)Country.COUNTRY.COUNTRY_ID) ) ).fetch( );

		return result;
	}
}
