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
public class SakilaTest0038 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select * from (select 1 x) a join (select 1 y) b on a.x = b.y";
		
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
Field x = DSL.inline( 1 ).as("x");
Table a = create.select( x ).asTable("a");
Field y = DSL.inline( 1 ).as("y");
Table b = create.select( y ).asTable("b");

Result result = create.select(  )
	.from( a )
	.join( b ).on( ((Field)a.field( "x" )).equal( ((Field)b.field( "y" )) ) ).fetch( );

		return result;
	}
}
