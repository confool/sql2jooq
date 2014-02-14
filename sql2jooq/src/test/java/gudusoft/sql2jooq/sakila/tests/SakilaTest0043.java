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
public class SakilaTest0043 extends SakilaTest
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
Field<String> f = Actor.ACTOR.FIRST_NAME.as("f");
Field<String> l = Actor.ACTOR.LAST_NAME.as("l");
Field<UShort> a = Actor.ACTOR.ACTOR_ID.as("a");

Result<Record3<String, String, UShort>> result = create.select( f, l, a )
	.from( Actor.ACTOR )
	.orderBy( f, l, a ).fetch( );

		return result;
	}
}
