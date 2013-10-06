
package gudusoft.gsqlparser.sql2jooq.tool;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
}