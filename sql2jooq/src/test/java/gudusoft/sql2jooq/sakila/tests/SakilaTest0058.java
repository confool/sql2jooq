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
public class SakilaTest0058 extends SakilaTest
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
Field<Integer> a = DSL.nvl( DSL.inline( 1 ), DSL.inline( 2 ) ).as("a");
Field b = DSL.nvl( DSL.inline( (Object)null ), DSL.inline( 2 ) ).as("b");

Result<Record2<Integer, Object>> result = create.select( a, b ).fetch( );

		return result;
	}
}
