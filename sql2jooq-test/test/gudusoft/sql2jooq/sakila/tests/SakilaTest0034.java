package gudusoft.sql2jooq.sakila.tests;

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
public class SakilaTest0034 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select (select count(*) c from actor), (select max(actor_id) i from actor)";
		
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
Field<Integer> c = DSL.count(  ).as("c");
Field<UShort> i = DSL.max( Actor.ACTOR.ACTOR_ID ).as("i");

Result<Record2<Object, Object>> result = create.select( create.select( c )
	.from( Actor.ACTOR ).asField( ), create.select( i )
	.from( Actor.ACTOR ).asField( ) ).fetch( );

		return result;
	}
}
