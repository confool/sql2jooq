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
public class SakilaTest0044 extends SakilaTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select first_name, last_name, actor_id from actor order by 2, 1, 3";
		
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

Result<Record3<String, String, UShort>> result = create.select( Actor.ACTOR.FIRST_NAME, Actor.ACTOR.LAST_NAME, Actor.ACTOR.ACTOR_ID )
	.from( Actor.ACTOR )
	.orderBy( DSL.inline( 2 ), DSL.inline( 1 ), DSL.inline( 3 ) ).fetch( );

		return result;
	}
}
