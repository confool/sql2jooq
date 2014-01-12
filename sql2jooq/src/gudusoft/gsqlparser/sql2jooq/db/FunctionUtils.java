
package gudusoft.gsqlparser.sql2jooq.db;

import java.util.ArrayList;
import java.util.List;

public class FunctionUtils
{

	private static List<String> unsupportFunctions = new ArrayList<String>( );
	static
	{
		unsupportFunctions.add( "IF" );
		unsupportFunctions.add( "TO_BASE64" );
		unsupportFunctions.add( "CONVERT" );

		unsupportFunctions.add( "DEFAULT" );
		unsupportFunctions.add( "GET_LOCK" );
		unsupportFunctions.add( "INET_ATON" );
		unsupportFunctions.add( "INET_NTOA" );
		unsupportFunctions.add( "INET6_ATON" );
		unsupportFunctions.add( "INET6_NTOA" );
		unsupportFunctions.add( "IS_FREE_LOCK" );
		unsupportFunctions.add( "IS_IPV4_COMPAT" );
		unsupportFunctions.add( "IS_IPV4_MAPPED" );
		unsupportFunctions.add( "IS_IPV4" );
		unsupportFunctions.add( "IS_IPV6" );
		unsupportFunctions.add( "IS_USED_LOCK" );
		unsupportFunctions.add( "MASTER_POS_WAIT" );
		unsupportFunctions.add( "NAME_CONST" );
		unsupportFunctions.add( "RELEASE_LOCK" );
		unsupportFunctions.add( "SLEEP" );
		unsupportFunctions.add( "UUID_SHORT" );
		unsupportFunctions.add( "UUID" );
		unsupportFunctions.add( "VALUES" );
		unsupportFunctions.add( "RAND" );

		unsupportFunctions.add( "GTID_SUBSET" );
		unsupportFunctions.add( "GTID_SUBTRACT" );
		unsupportFunctions.add( "SQL_THREAD_WAIT_AFTER_GTIDS" );
		unsupportFunctions.add( "WAIT_UNTIL_SQL_THREAD_AFTER_GTIDS" );

		unsupportFunctions.add( "BENCHMARK" );
		unsupportFunctions.add( "CHARSET" );
		unsupportFunctions.add( "COERCIBILITY" );
		unsupportFunctions.add( "COLLATION" );
		unsupportFunctions.add( "CONNECTION_ID" );
		unsupportFunctions.add( "DATABASE" );
		unsupportFunctions.add( "FOUND_ROWS" );
		unsupportFunctions.add( "LAST_INSERT_ID" );
		unsupportFunctions.add( "ROW_COUNT" );
		unsupportFunctions.add( "SCHEMA" );
		unsupportFunctions.add( "SESSION_USER" );
		unsupportFunctions.add( "SYSTEM_USER" );
		unsupportFunctions.add( "USER" );
		unsupportFunctions.add( "VERSION" );

		unsupportFunctions.add( "ENCRYPT" );
		unsupportFunctions.add( "OLD_PASSWORD" );
		unsupportFunctions.add( "VALIDATE_PASSWORD_STRENGTH" );

		unsupportFunctions.add( "ExtractValue" );
		unsupportFunctions.add( "UpdateXML" );

		unsupportFunctions.add( "CONV" );
		unsupportFunctions.add( "CRC32" );

		unsupportFunctions.add( "LEFT" );
		unsupportFunctions.add( "RIGHT" );
		unsupportFunctions.add( "STRCMP" );
		unsupportFunctions.add( "SPACE" );
		unsupportFunctions.add( "REVERSE" );

		unsupportFunctions.add( "CONVERT_TZ" );
		unsupportFunctions.add( "DATE_FORMAT" );
		unsupportFunctions.add( "DAYNAME" );
		unsupportFunctions.add( "DAYOFMONTH" );
		unsupportFunctions.add( "DAYOFWEEK" );
		unsupportFunctions.add( "DAYOFYEAR" );
		unsupportFunctions.add( "FROM_DAYS" );
		unsupportFunctions.add( "FROM_UNIXTIME" );
		unsupportFunctions.add( "GET_FORMAT" );
		unsupportFunctions.add( "LAST_DAY" );
		unsupportFunctions.add( "MAKEDATE" );
		unsupportFunctions.add( "MAKETIME" );
		unsupportFunctions.add( "MICROSECOND" );
		unsupportFunctions.add( "MONTHNAME" );
		unsupportFunctions.add( "PERIOD_ADD" );
		unsupportFunctions.add( "PERIOD_DIFF" );
		unsupportFunctions.add( "QUARTER" );
		unsupportFunctions.add( "SEC_TO_TIME" );
		unsupportFunctions.add( "STR_TO_DATE" );
		unsupportFunctions.add( "SYSDATE" );
		unsupportFunctions.add( "TIME_FORMAT" );
		unsupportFunctions.add( "TIME_TO_SEC" );
		unsupportFunctions.add( "TIMEDIFF" );
		unsupportFunctions.add( "TIMESTAMP" );
		unsupportFunctions.add( "TO_DAYS" );
		unsupportFunctions.add( "TO_SECONDS" );
		unsupportFunctions.add( "UNIX_TIMESTAMP" );
		unsupportFunctions.add( "UTC_DATE" );
		unsupportFunctions.add( "UTC_TIME" );
		unsupportFunctions.add( "UTC_TIMESTAMP" );
		unsupportFunctions.add( "WEEK" );
		unsupportFunctions.add( "WEEKDAY" );
		unsupportFunctions.add( "WEEKOFYEAR" );
		unsupportFunctions.add( "YEARWEEK" );

		unsupportFunctions.add( "ADDDATE" );
		unsupportFunctions.add( "DATE_ADD" );
		unsupportFunctions.add( "ADDTIME" );
		unsupportFunctions.add( "SUBDATE" );
		unsupportFunctions.add( "DATE_SUB" );
		unsupportFunctions.add( "TIMESTAMPADD" );
		unsupportFunctions.add( "SUBTIME" );

	}

	private static List<String> supportFunctions = new ArrayList<String>( );
	static
	{
		supportFunctions.add( "(?i)RAND\\(\\s*\\)" );
	}

	private static List<String> intTypefunctions = new ArrayList<String>( );
	static
	{
		intTypefunctions.add( "DSL.count(" );
		intTypefunctions.add( "DSL.ascii(" );
		intTypefunctions.add( "DSL.bitLength(" );
		intTypefunctions.add( "DSL.bitCount(" );
		intTypefunctions.add( "DSL.charLength(" );
		intTypefunctions.add( "DSL.position(" );
		intTypefunctions.add( "DSL.length(" );
		intTypefunctions.add( "DSL.octetLength(" );
		intTypefunctions.add( "DSL.dateDiff(" );
		intTypefunctions.add( "DSL.day(" );
		intTypefunctions.add( "DSL.denseRank(" );
		intTypefunctions.add( "DSL.extract(" );
		intTypefunctions.add( "DSL.grouping(" );
		intTypefunctions.add( "DSL.groupingId(" );
		intTypefunctions.add( "DSL.hour(" );
		intTypefunctions.add( "DSL.level(" );
		intTypefunctions.add( "DSL.minute(" );
		intTypefunctions.add( "DSL.month(" );
		intTypefunctions.add( "DSL.ntile(" );
		intTypefunctions.add( "DSL.one(" );
		intTypefunctions.add( "DSL.rank(" );
		intTypefunctions.add( "DSL.rowNumber(" );
		intTypefunctions.add( "DSL.second(" );
		intTypefunctions.add( "DSL.sign(" );
		intTypefunctions.add( "DSL.two(" );
		intTypefunctions.add( "DSL.year(" );
		intTypefunctions.add( "DSL.zero(" );
		intTypefunctions.add( "MySQLDSL.uncompressedLength(" );
	}

	private static List<String> bigDecimalTypefunctions = new ArrayList<String>( );
	static
	{
		bigDecimalTypefunctions.add( "DSL.rand(" );
		bigDecimalTypefunctions.add( "DSL.deg(" );
		bigDecimalTypefunctions.add( "DSL.rad(" );
		bigDecimalTypefunctions.add( "DSL.acos(" );
		bigDecimalTypefunctions.add( "DSL.asin(" );
		bigDecimalTypefunctions.add( "DSL.atan(" );
		bigDecimalTypefunctions.add( "DSL.atan2(" );
		bigDecimalTypefunctions.add( "DSL.avg(" );
		bigDecimalTypefunctions.add( "DSL.avgDistinct(" );
		bigDecimalTypefunctions.add( "DSL.cos(" );
		bigDecimalTypefunctions.add( "DSL.cosh(" );
		bigDecimalTypefunctions.add( "DSL.cot(" );
		bigDecimalTypefunctions.add( "DSL.coth(" );
		bigDecimalTypefunctions.add( "DSL.cumeDist(" );
		bigDecimalTypefunctions.add( "DSL.e(" );
		bigDecimalTypefunctions.add( "DSL.exp(" );
		bigDecimalTypefunctions.add( "DSL.ln(" );
		bigDecimalTypefunctions.add( "DSL.log(" );
		bigDecimalTypefunctions.add( "DSL.median(" );
		bigDecimalTypefunctions.add( "DSL.percentRank(" );
		bigDecimalTypefunctions.add( "DSL.pi(" );
		bigDecimalTypefunctions.add( "DSL.power(" );
		bigDecimalTypefunctions.add( "DSL.regrAvgX(" );
		bigDecimalTypefunctions.add( "DSL.regrCount(" );
		bigDecimalTypefunctions.add( "DSL.regrIntercept(" );
		bigDecimalTypefunctions.add( "DSL.regrR2(" );
		bigDecimalTypefunctions.add( "DSL.regrSlope(" );
		bigDecimalTypefunctions.add( "DSL.regrSXX(" );
		bigDecimalTypefunctions.add( "DSL.regrSXY(" );
		bigDecimalTypefunctions.add( "DSL.regrSYY(" );
		bigDecimalTypefunctions.add( "DSL.sin(" );
		bigDecimalTypefunctions.add( "DSL.sinh(" );
		bigDecimalTypefunctions.add( "DSL.sqrt(" );
		bigDecimalTypefunctions.add( "DSL.stddevPop(" );
		bigDecimalTypefunctions.add( "DSL.stddevSamp(" );
		bigDecimalTypefunctions.add( "DSL.sum(" );
		bigDecimalTypefunctions.add( "DSL.sumDistinct(" );
		bigDecimalTypefunctions.add( "DSL.tan(" );
		bigDecimalTypefunctions.add( "DSL.tanh(" );
		bigDecimalTypefunctions.add( "DSL.varPop(" );
		bigDecimalTypefunctions.add( "DSL.varSamp(" );
	}

	private static List<String> stringTypefunctions = new ArrayList<String>( );
	static
	{
		stringTypefunctions.add( "DSL.concat(" );
		stringTypefunctions.add( "DSL.left(" );
		stringTypefunctions.add( "DSL.lower(" );
		stringTypefunctions.add( "DSL.lpad(" );
		stringTypefunctions.add( "DSL.ltrim(" );
		stringTypefunctions.add( "DSL.mid(" );
		stringTypefunctions.add( "DSL.repeat(" );
		stringTypefunctions.add( "DSL.replace(" );
		stringTypefunctions.add( "DSL.reverse(" );
		stringTypefunctions.add( "DSL.right(" );
		stringTypefunctions.add( "DSL.rpad(" );
		stringTypefunctions.add( "DSL.rtrim(" );
		stringTypefunctions.add( "DSL.substring(" );
		stringTypefunctions.add( "DSL.trim(" );
		stringTypefunctions.add( "DSL.upper(" );
		stringTypefunctions.add( "DSL.currentUser(" );
		stringTypefunctions.add( "DSL.md5(" );
		stringTypefunctions.add( "DSL.escape(" );
		stringTypefunctions.add( "DSL.listAgg(" );
		stringTypefunctions.add( "DSL.sysConnectByPath(" );
		stringTypefunctions.add( "MySQLDSL.aesDecrypt(" );
		stringTypefunctions.add( "MySQLDSL.aesEncrypt(" );
		stringTypefunctions.add( "MySQLDSL.compress(" );
		stringTypefunctions.add( "MySQLDSL.decode(" );
		stringTypefunctions.add( "MySQLDSL.desDecrypt(" );
		stringTypefunctions.add( "MySQLDSL.desEncrypt(" );
		stringTypefunctions.add( "MySQLDSL.encode(" );
		stringTypefunctions.add( "MySQLDSL.uncompress(" );
		stringTypefunctions.add( "MySQLDSL.sha1(" );
		stringTypefunctions.add( "MySQLDSL.sha2(" );
		stringTypefunctions.add( "MySQLDSL.password(" );
	}

	private static List<String> dateTypefunctions = new ArrayList<String>( );
	static
	{
		dateTypefunctions.add( "DSL.currentDate(" );
		dateTypefunctions.add( "DSL.date(" );
	}

	private static List<String> timeTypefunctions = new ArrayList<String>( );
	static
	{
		timeTypefunctions.add( "DSL.currentTime(" );
		timeTypefunctions.add( "DSL.time(" );
	}

	private static List<String> timestampTypefunctions = new ArrayList<String>( );
	static
	{
		timestampTypefunctions.add( "DSL.currentTimestamp(" );
	}

	private static List<String> unknownTypefunctions = new ArrayList<String>( );
	static
	{
		unknownTypefunctions.add( "DSL.cast(" );
	}

	private static List<String> fixTypeFunctions = new ArrayList<String>( );
	static
	{
		fixTypeFunctions.addAll( intTypefunctions );
		fixTypeFunctions.addAll( bigDecimalTypefunctions );
		fixTypeFunctions.addAll( stringTypefunctions );
		fixTypeFunctions.addAll( dateTypefunctions );
		fixTypeFunctions.addAll( timeTypefunctions );
		fixTypeFunctions.addAll( timestampTypefunctions );
		fixTypeFunctions.addAll( unknownTypefunctions );
	}

	public static List<String> getUnsupportFunctions( )
	{
		return unsupportFunctions;
	}

	public static List<String> getSupportFunctions( )
	{
		return supportFunctions;
	}

	public static List<String> getIntTypefunctions( )
	{
		return intTypefunctions;
	}

	public static List<String> getBigDecimalTypefunctions( )
	{
		return bigDecimalTypefunctions;
	}

	public static List<String> getStringTypefunctions( )
	{
		return stringTypefunctions;
	}

	public static List<String> getDateTypefunctions( )
	{
		return dateTypefunctions;
	}

	public static List<String> getTimeTypefunctions( )
	{
		return timeTypefunctions;
	}

	public static List<String> getTimestampTypefunctions( )
	{
		return timestampTypefunctions;
	}

	public static List<String> getUnknownTypefunctions( )
	{
		return unknownTypefunctions;
	}

	public static List<String> getFixTypeFunctions( )
	{
		return fixTypeFunctions;
	}

	public static boolean isMysqlDSL( String function )
	{
		if ( function.equalsIgnoreCase( "AES_DECRYPT" ) )
			return true;
		if ( function.equalsIgnoreCase( "AES_ENCRYPT" ) )
			return true;
		if ( function.equalsIgnoreCase( "DES_DECRYPT" ) )
			return true;
		if ( function.equalsIgnoreCase( "DES_ENCRYPT" ) )
			return true;
		if ( function.equalsIgnoreCase( "UNCOMPRESSED_LENGTH" ) )
			return true;
		if ( function.equalsIgnoreCase( "COMPRESS" ) )
			return true;
		if ( function.equalsIgnoreCase( "DECODE" ) )
			return true;
		if ( function.equalsIgnoreCase( "ENCODE" ) )
			return true;
		if ( function.equalsIgnoreCase( "UNCOMPRESS" ) )
			return true;
		if ( function.equalsIgnoreCase( "SHA" ) )
			return true;
		if ( function.equalsIgnoreCase( "SHA1" ) )
			return true;
		if ( function.equalsIgnoreCase( "SHA2" ) )
			return true;
		if ( function.equalsIgnoreCase( "PASSWORD" ) )
			return true;
		return false;
	}

	public static String convertFunctionToDSLMethod( String function )
	{
		if ( function.equalsIgnoreCase( "IFNULL" ) )
			return "nvl";
		if ( function.equalsIgnoreCase( "BIT_LENGTH" ) )
			return "bitLength";
		if ( function.equalsIgnoreCase( "CHAR_LENGTH" )
				|| function.equalsIgnoreCase( "CHARACTER_LENGTH" ) )
			return "charLength";
		if ( function.equalsIgnoreCase( "INSTR" )
				|| function.equalsIgnoreCase( "LOCATE" ) )
			return "position";
		if ( function.equalsIgnoreCase( "OCTET_LENGTH" ) )
			return "octetLength";
		if ( function.equalsIgnoreCase( "SUBSTR" ) )
			return "substring";
		if ( function.equalsIgnoreCase( "UCASE" ) )
			return "upper";
		if ( function.equalsIgnoreCase( "LCASE" ) )
			return "lower";
		if ( function.equalsIgnoreCase( "AES_DECRYPT" ) )
			return "aesDecrypt";
		if ( function.equalsIgnoreCase( "AES_ENCRYPT" ) )
			return "aesEncrypt";
		if ( function.equalsIgnoreCase( "DES_DECRYPT" ) )
			return "desDecrypt";
		if ( function.equalsIgnoreCase( "DES_ENCRYPT" ) )
			return "desEncrypt";
		if ( function.equalsIgnoreCase( "UNCOMPRESSED_LENGTH" ) )
			return "uncompressedLength";
		if ( function.equalsIgnoreCase( "CURRENT_USER" ) )
			return "currentUser";
		if ( function.equalsIgnoreCase( "SHA" ) )
			return "sha1";
		if ( function.equalsIgnoreCase( "BIT_COUNT" ) )
			return "bitCount";
		if ( function.equalsIgnoreCase( "TRUNCATE" ) )
			return "trunc";
		if ( function.equalsIgnoreCase( "DEGREES" ) )
			return "deg";
		if ( function.equalsIgnoreCase( "RADIANS" ) )
			return "rad";
		if ( function.equalsIgnoreCase( "POW" ) )
			return "power";
		if ( function.equalsIgnoreCase( "CEILING" ) )
			return "ceil";
		if ( function.equalsIgnoreCase( "CURRENT_DATE" )
				|| function.equalsIgnoreCase( "CURDATE" ) )
			return "currentDate";
		if ( function.equalsIgnoreCase( "CURRENT_TIME" )
				|| function.equalsIgnoreCase( "CURTIME" ) )
			return "currentTime";
		if ( function.equalsIgnoreCase( "CURRENT_TIMESTAMP" )
				|| function.equalsIgnoreCase( "NOW" )
				|| function.equalsIgnoreCase( "LOCALTIME" )
				|| function.equalsIgnoreCase( "LOCALTIMESTAMP" ) )
			return "currentTimestamp";
		if ( function.equalsIgnoreCase( "TIMESTAMPDIFF" ) )
			return "timestampDiff";
		if ( function.equalsIgnoreCase( "DATE_DIFF" ) )
			return "dateDiff";
		return function;
	}
}
