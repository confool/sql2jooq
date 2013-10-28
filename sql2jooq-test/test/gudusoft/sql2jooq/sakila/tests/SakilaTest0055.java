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
public class SakilaTest0055 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select if (actor_id = 1, 1, null) a from actor where actor_id < 10";
		
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

Result result = create.select( DSL.field( "if (actor_id = 1, 1, null)" ) )
	.from( Actor.ACTOR )
	.where( ((Field)Actor.ACTOR.ACTOR_ID).lessThan( DSL.inline( 10 ) ) ).fetch( );

		return result;
	}
}
