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
public class SakilaTest4 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select actor_id, first_name, last_name "
+ "from actor;";
		
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
Result result = create.select( ((Field)Actor.ACTOR.ACTOR_ID), ((Field)Actor.ACTOR.FIRST_NAME), ((Field)Actor.ACTOR.LAST_NAME) )
	.from( Actor.ACTOR ).fetch( );

		return result;
	}
}
