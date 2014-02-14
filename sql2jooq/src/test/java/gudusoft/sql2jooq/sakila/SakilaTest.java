package gudusoft.sql2jooq.sakila;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
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
public class SakilaTest 
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
				Object value1 = expected.get(row).getValue(col, type);
				Object value2 = actual.getValue(row, col);
				
				// Plain SQL may return other number types than jOOQ. This might not
				// be a bug, or at least, it's hard to get correctly
				if (value1 instanceof Number && value2 instanceof Number) {
					Number n1 = widen((Number) value1);
					Number n2 = widen((Number) value2);
					
					// Don't make a difference between 1.0 and 1
					String s1 = n1.toString().replaceAll("\\.0$", "");
					String s2 = n2.toString().replaceAll("\\.0$", "");
					assertEquals(new BigDecimal(s1), new BigDecimal(s2));
				}
				
				// Plain SQL may return byte arrays when it should return a String
				else if (value1 instanceof String || value2 instanceof String) {
					String s1 = value1 instanceof byte[] ? new String((byte[]) value1) : value1.toString();
					String s2 = value2 instanceof byte[] ? new String((byte[]) value2) : value2.toString();
					
					assertEquals(s1, s2);
				}
				
				// Default cause
				else {
					assertEquals(value1, value2);
				}
			}
		}
	}

	private Number widen(Number value) {
		if (value instanceof Byte) 
			return new BigInteger(value.toString());
		if (value instanceof Short)
			return new BigInteger(value.toString());
		if (value instanceof Integer)
			return new BigInteger(value.toString());
		if (value instanceof Long)
			return new BigInteger(value.toString());
		
		if (value instanceof Float)
			return value.doubleValue();
		
		return value;
	}
}
