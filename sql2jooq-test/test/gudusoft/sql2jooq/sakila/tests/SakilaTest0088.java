package gudusoft.sql2jooq.sakila.tests;

import static org.junit.Assert.*;
import static gudusoft.sakila.Tables.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;
import org.jooq.util.mysql.MySQLDSL;
import org.junit.*;
import org.jooq.types.*;

import gudusoft.sakila.tables.*;
import gudusoft.sql2jooq.sakila.MySQLTest;

/**
 * @author Lukas Eder
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class SakilaTest0088 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select 1 from actor where actor_id = 1 order by rand(3) asc";
		
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

Result<Record1<Integer>> result = create.select( DSL.inline( 1 ) )
	.from( Actor.ACTOR )
	.where( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 1 ) ) ) )
	.orderBy( DSL.field( "rand(3)" ).asc( ) ).fetch( );

		return result;
	}
}
