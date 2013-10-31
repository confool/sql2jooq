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
public class SakilaTest0052 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select  "
+ "  case actor_id when 1 then 1 end a1, "
+ "  case actor_id when 1 then 1 when 2 then 2 end a2, "
+ "  case actor_id when 1 then 1 else 0 end a3, "
+ "  case actor_id when 1 then 1 when 2 then 2 else 0 end a4 "
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
Field a1 = DSL.decode( ).value( ((Field)Actor.ACTOR.ACTOR_ID) ).when( DSL.inline( 1 ), DSL.inline( 1 ) ).as("a1");
Field a2 = DSL.decode( ).value( ((Field)Actor.ACTOR.ACTOR_ID) ).when( DSL.inline( 1 ), DSL.inline( 1 ) ).when( DSL.inline( 2 ), DSL.inline( 2 ) ).as("a2");
Field a3 = DSL.decode( ).value( ((Field)Actor.ACTOR.ACTOR_ID) ).when( DSL.inline( 1 ), DSL.inline( 1 ) ).otherwise( DSL.inline( 0 ) ).as("a3");
Field a4 = DSL.decode( ).value( ((Field)Actor.ACTOR.ACTOR_ID) ).when( DSL.inline( 1 ), DSL.inline( 1 ) ).when( DSL.inline( 2 ), DSL.inline( 2 ) ).otherwise( DSL.inline( 0 ) ).as("a4");

Result result = create.select( a1, a2, a3, a4 )
	.from( Actor.ACTOR )
	.where( ((Field)Actor.ACTOR.ACTOR_ID).lessThan( DSL.inline( 10 ) ) ).fetch( );

		return result;
	}
}
