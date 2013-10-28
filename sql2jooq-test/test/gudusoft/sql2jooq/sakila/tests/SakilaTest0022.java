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
public class SakilaTest0022 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select 1 from dual where 3 between 2 and 4";
		
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
	.where( DSL.inline( 3 ).between( DSL.inline( 2 ) ).and( DSL.inline( 4 ) ) ).fetch( );

		return result;
	}
}
