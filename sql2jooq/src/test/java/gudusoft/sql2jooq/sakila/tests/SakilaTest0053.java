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
public class SakilaTest0053 extends SakilaTest
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
Field<UShort> b1 = DSL.decode( ).when( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 1 ) ) ), DSL.inline( UShort.valueOf( 1 ) ) ).as("b1");
Field<UShort> b2 = DSL.decode( ).when( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 1 ) ) ), DSL.inline( UShort.valueOf( 1 ) ) ).when( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 2 ) ) ), DSL.inline( UShort.valueOf( 2 ) ) ).as("b2");
Field<UShort> b3 = DSL.decode( ).when( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 1 ) ) ), DSL.inline( UShort.valueOf( 1 ) ) ).otherwise( DSL.inline( UShort.valueOf( 0 ) ) ).as("b3");
Field<UShort> b4 = DSL.decode( ).when( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 1 ) ) ), DSL.inline( UShort.valueOf( 1 ) ) ).when( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 2 ) ) ), DSL.inline( UShort.valueOf( 2 ) ) ).otherwise( DSL.inline( UShort.valueOf( 0 ) ) ).as("b4");

Result<Record4<UShort, UShort, UShort, UShort>> result = create.select( b1, b2, b3, b4 )
	.from( Actor.ACTOR )
	.where( Actor.ACTOR.ACTOR_ID.lessThan( DSL.inline( UShort.valueOf( 10 ) ) ) ).fetch( );

		return result;
	}
}
