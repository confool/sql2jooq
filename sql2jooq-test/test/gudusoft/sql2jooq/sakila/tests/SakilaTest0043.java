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
public class SakilaTest0043 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select first_name f, last_name l, actor_id a from actor order by f, l, a";
		
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
Field f = ((Field)Actor.ACTOR.FIRST_NAME).as("f");
Field l = ((Field)Actor.ACTOR.LAST_NAME).as("l");
Field a = ((Field)Actor.ACTOR.ACTOR_ID).as("a");

Result result = create.select( f, l, a )
	.from( Actor.ACTOR )
	.orderBy( f, l, a ).fetch( );

		return result;
	}
}
