
package gudusoft.gsqlparser.sql2jooq.tool;

import gudusoft.gsqlparser.EDbVendor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jooq.SQLDialect;

public class GenerationUtil
{

	private static Set<String> JAVA_KEYWORDS = Collections.unmodifiableSet( new HashSet<String>( Arrays.asList( new String[]{
			"abstract",
			"assert",
			"boolean",
			"break",
			"byte",
			"case",
			"catch",
			"char",
			"class",
			"const",
			"continue",
			"default",
			"double",
			"do",
			"else",
			"enum",
			"extends",
			"false",
			"final",
			"finally",
			"float",
			"for",
			"goto",
			"if",
			"implements",
			"import",
			"instanceof",
			"interface",
			"int",
			"long",
			"native",
			"new",
			"package",
			"private",
			"protected",
			"public",
			"return",
			"short",
			"static",
			"strictfp",
			"super",
			"switch",
			"synchronized",
			"this",
			"throw",
			"throws",
			"transient",
			"true",
			"try",
			"void",
			"volatile",
			"while"
	} ) ) );

	public static String convertToJavaIdentifier( String literal )
	{
		if ( JAVA_KEYWORDS.contains( literal ) )
		{
			return new StringBuilder( ).append( literal )
					.append( "_" )
					.toString( );
		}

		StringBuilder sb = new StringBuilder( );

		if ( "".equals( literal ) )
		{
			return "_";
		}

		if ( !( Character.isJavaIdentifierStart( literal.charAt( 0 ) ) ) )
		{
			sb.append( "_" );
		}

		for ( int i = 0; i < literal.length( ); ++i )
		{
			char c = literal.charAt( i );

			if ( !( Character.isJavaIdentifierPart( c ) ) )
			{
				sb.append( "_" );
			}
			else
			{
				sb.append( c );
			}
		}

		return sb.toString( );
	}

	@SuppressWarnings("incomplete-switch")
	public static SQLDialect getSQLDialect( EDbVendor dbVendor )
	{
		switch ( dbVendor )
		{
			case dbvansi :
				return SQLDialect.SQL99;
			case dbvdb2 :
				return SQLDialect.DB2;
			case dbvmysql :
				return SQLDialect.MYSQL;
			case dbvoracle :
				return SQLDialect.ORACLE;
			case dbvpostgresql :
				return SQLDialect.POSTGRES;
			case dbvsybase :
				return SQLDialect.SYBASE;
			case dbvfirebird :
				return SQLDialect.FIREBIRD;
			case dbvmssql :
				return SQLDialect.SQLSERVER;
		}
		return SQLDialect.ORACLE;
	}
}