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
@SuppressWarnings({ "unchecked", "rawtypes", "unused", "all" })
public class SakilaTest0040 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select 1 from dual where ('a', 1) <> ('b', 2)";
		
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

Result result = create.select( DSL.inline( 1 ) )
	.from( DSL.dual() )
	.where( DSL.row( DSL.inline( "a" ), DSL.inline( 1 ) ).notEqual( DSL.row( DSL.inline( "b" ), DSL.inline( 2 ) ) ) ).fetch( );

		return result;
	}
}
