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
public class SakilaTest0048 extends SakilaTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select first_name, last_name, count(*) from actor group by first_name, last_name order by count(*) desc limit 1";
		
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

Result<Record3<String, String, Integer>> result = create.select( Actor.ACTOR.FIRST_NAME, Actor.ACTOR.LAST_NAME, DSL.count(  ) )
	.from( Actor.ACTOR )
	.groupBy( Actor.ACTOR.FIRST_NAME, Actor.ACTOR.LAST_NAME )
	.orderBy( DSL.count(  ).desc( ) )
	.limit( 1 ).fetch( );

		return result;
	}
}
