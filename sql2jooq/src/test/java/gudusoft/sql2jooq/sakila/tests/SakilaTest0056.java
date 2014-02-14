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
public class SakilaTest0056 extends SakilaTest
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

Result<Record1<Object>> result = create.select( DSL.field( "if (actor_id = 1, 1, null)" ) )
	.from( Actor.ACTOR )
	.where( Actor.ACTOR.ACTOR_ID.lessThan( DSL.inline( UShort.valueOf( 10 ) ) ) ).fetch( );

		return result;
	}
}
