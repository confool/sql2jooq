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
public class SakilaTest0014 extends SakilaTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select country, city, address from country left join city on city.country_id = country.country_id left join address on address.city_id = city.city_id";
		
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
	.from( Country.COUNTRY )
	.leftOuterJoin( City.CITY ).on( City.CITY.COUNTRY_ID.equal( Country.COUNTRY.COUNTRY_ID ) )
	.leftOuterJoin( Address.ADDRESS ).on( Address.ADDRESS.CITY_ID.equal( City.CITY.CITY_ID ) ).fetch( );

		return result;
	}
}
