package gudusoft.sql2jooq.sakila.tests;

import static org.jooq.impl.DSL.*;
import static org.junit.Assert.*;
import static gudusoft.sakila.Tables.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;
import org.junit.*;

import gudusoft.sakila.tables.*;
import gudusoft.sql2jooq.sakila.MySQLTest;

/**
 * @author Lukas Eder
 */
public class SakilaTest0056 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select ifnull(1, 2) a, ifnull(null, 2) b";
		
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
Field a = DSL.nvl( DSL.inline( 1 ), DSL.inline( 2 ) ).as("a");
Field b = DSL.nvl( DSL.inline( (Object)null ), DSL.inline( 2 ) ).as("b");

Result result = create.select( a, b ).fetch( );

		return result;
	}
}
