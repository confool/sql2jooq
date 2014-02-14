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
public class SakilaTest0026 extends SakilaTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select count(*) c from actor where first_name is null";
		
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

Result<Record1<Integer>> result = create.select( c )
	.from( Actor.ACTOR )
	.where( Actor.ACTOR.FIRST_NAME.isNull(  ) ).fetch( );

		return result;
	}
}
