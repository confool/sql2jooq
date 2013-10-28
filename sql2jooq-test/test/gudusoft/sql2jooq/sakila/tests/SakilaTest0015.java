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
public class SakilaTest0015 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select country, city, address from country left join city using (country_id) left join address using (city_id)";
		
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
	.from( Country.COUNTRY )
	.leftOuterJoin( City.CITY ).using( new Field[]{((Field)City.CITY.COUNTRY_ID)} )
	.leftOuterJoin( Address.ADDRESS ).using( new Field[]{((Field)City.CITY.CITY_ID)} ).fetch( );

		return result;
	}
}
