package gudusoft.sql2jooq.sakila;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Lukas Eder
 */
public class MySQLTest 
{

	protected static Connection conn = null;
	protected static DSLContext ctx = null;
	
	@BeforeClass
	public static void init() 
	{

		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/sakila";

		try
		{
			Class.forName( "com.mysql.jdbc.Driver" ).newInstance( );
			conn = DriverManager.getConnection( url, userName, password );
			conn.setAutoCommit(false);
			ctx = DSL.using(conn, SQLDialect.MYSQL);
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
	}
	
	@AfterClass
	public static void destroy() 
	{
		if ( conn != null )
		{
			try
			{
				conn.close( );
			}
			catch ( SQLException ignore )
			{
			}
		}
	}
	
	@Before
	public void before() 
	{
		
	}
	
	@After
	public void after() throws SQLException 
	{
		conn.rollback();
	}
	
	public void assertSame(Result<?> expected, Result<?> actual) 
	{
		
		// This method runs some more detailed tests compared to Result.equals()
		// Right now, column names are not considered yet, as this doesn't seem
		// to be supported by the sql2jooq parser yet.
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.fields().length, actual.fields().length);
		
		for (int row = 0; row < expected.size(); row++) 
		{
			for (int col = 0; col < expected.fields().length; col++) 
			{
				Class<?> type = actual.fieldsRow().type(col);
				assertEquals(expected.get(row).getValue(col, type), actual.getValue(row, col));
			}
		}
	}
}
