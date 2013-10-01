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
public class SakilaTest4 extends MySQLTest
{

	@Test
	public void test() throws Exception 
	{
		String sql = "select first_name, last_name"
+ "from actor"
+ "join film_actor on actor.actor_id = film_actor.actor_id"
+ "join film on film.film_id = film_actor.film_id;";
		
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
Result result = create.select( ((Field)Actor.ACTOR.FIRST_NAME), ((Field)Actor.ACTOR.LAST_NAME) )
	.from( Actor.ACTOR )
	.join( Film_actor.FILM_ACTOR ).on( ((Field)Actor.ACTOR.ACTOR_ID).equal( ((Field)Film_actor.FILM_ACTOR.ACTOR_ID) ) )
	.join( Film.FILM ).on( ((Field)Film.FILM.FILM_ID).equal( ((Field)Film_actor.FILM_ACTOR.FILM_ID) ) ).fetch( );

		return result;
	}
}
