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
public class SakilaTest0029 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select 1 from dual where 1 not in (2, 3, 4)";
		
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
	.from( DSL.dual() )
	.where( DSL.inline( 1 ).notIn( DSL.inline( 2 ), DSL.inline( 3 ), DSL.inline( 4 ) ) ).fetch( );

		return result;
	}
}
