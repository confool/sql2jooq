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
public class SakilaTest0053 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select "
+ "  case when actor_id = 1 then 1 end b1, "
+ "  case when actor_id = 1 then 1 when actor_id = 2 then 2 end b2, "
+ "  case when actor_id = 1 then 1 else 0 end b3, "
+ "  case when actor_id = 1 then 1 when actor_id = 2 then 2 else 0 end b4 "
+ "from actor "
+ "where actor_id < 10";
		
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
Field b1 = DSL.decode( ).when( ((Field)Actor.ACTOR.ACTOR_ID).equal( DSL.inline( 1 ) ), DSL.inline( 1 ) ).as("b1");
Field b2 = DSL.decode( ).when( ((Field)Actor.ACTOR.ACTOR_ID).equal( DSL.inline( 1 ) ), DSL.inline( 1 ) ).when( ((Field)Actor.ACTOR.ACTOR_ID).equal( DSL.inline( 2 ) ), DSL.inline( 2 ) ).as("b2");
Field b3 = DSL.decode( ).when( ((Field)Actor.ACTOR.ACTOR_ID).equal( DSL.inline( 1 ) ), DSL.inline( 1 ) ).otherwise( DSL.inline( 0 ) ).as("b3");
Field b4 = DSL.decode( ).when( ((Field)Actor.ACTOR.ACTOR_ID).equal( DSL.inline( 1 ) ), DSL.inline( 1 ) ).when( ((Field)Actor.ACTOR.ACTOR_ID).equal( DSL.inline( 2 ) ), DSL.inline( 2 ) ).otherwise( DSL.inline( 0 ) ).as("b4");

Result result = create.select( b1, b2, b3, b4 )
	.from( Actor.ACTOR )
	.where( ((Field)Actor.ACTOR.ACTOR_ID).lessThan( DSL.inline( 10 ) ) ).fetch( );

		return result;
	}
}
