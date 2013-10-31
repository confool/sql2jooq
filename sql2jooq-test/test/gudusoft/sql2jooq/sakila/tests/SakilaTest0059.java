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
public class SakilaTest0059 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select nullif(1, 2), nullif(1, 1)";
		
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

Result<Record2<Integer, Integer>> result = create.select( DSL.nullif( DSL.inline( 1 ), DSL.inline( 2 ) ), DSL.nullif( DSL.inline( 1 ), DSL.inline( 1 ) ) ).fetch( );

		return result;
	}
}
