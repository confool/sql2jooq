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
public class SakilaTest0041 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select * from actor where (first_name, last_name) in (select first_name, last_name from customer)";
		
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

Result result = create.select(  )
	.from( Actor.ACTOR )
	.where( DSL.row( ((Field)Actor.ACTOR.FIRST_NAME), ((Field)Actor.ACTOR.LAST_NAME) ).in( create.select( ((Field)Customer.CUSTOMER.FIRST_NAME), ((Field)Customer.CUSTOMER.LAST_NAME) )
	.from( Customer.CUSTOMER ) ) ).fetch( );

		return result;
	}
}
