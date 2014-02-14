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
public class SakilaTest0052 extends SakilaTest
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
Field<UShort> a1 = DSL.decode( ).value( Actor.ACTOR.ACTOR_ID ).when( DSL.inline( UShort.valueOf( 1 ) ), DSL.inline( UShort.valueOf( 1 ) ) ).as("a1");
Field<UShort> a2 = DSL.decode( ).value( Actor.ACTOR.ACTOR_ID ).when( DSL.inline( UShort.valueOf( 1 ) ), DSL.inline( UShort.valueOf( 1 ) ) ).when( DSL.inline( UShort.valueOf( 2 ) ), DSL.inline( UShort.valueOf( 2 ) ) ).as("a2");
Field<UShort> a3 = DSL.decode( ).value( Actor.ACTOR.ACTOR_ID ).when( DSL.inline( UShort.valueOf( 1 ) ), DSL.inline( UShort.valueOf( 1 ) ) ).otherwise( DSL.inline( UShort.valueOf( 0 ) ) ).as("a3");
Field<UShort> a4 = DSL.decode( ).value( Actor.ACTOR.ACTOR_ID ).when( DSL.inline( UShort.valueOf( 1 ) ), DSL.inline( UShort.valueOf( 1 ) ) ).when( DSL.inline( UShort.valueOf( 2 ) ), DSL.inline( UShort.valueOf( 2 ) ) ).otherwise( DSL.inline( UShort.valueOf( 0 ) ) ).as("a4");

Result<Record4<UShort, UShort, UShort, UShort>> result = create.select( a1, a2, a3, a4 )
	.from( Actor.ACTOR )
	.where( Actor.ACTOR.ACTOR_ID.lessThan( DSL.inline( UShort.valueOf( 10 ) ) ) ).fetch( );

		return result;
	}
}
