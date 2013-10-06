
package gudusoft.gsqlparser.sql2jooq.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseMetaData
{

	private Map<String, TableMetaData> tables = new LinkedHashMap<String, TableMetaData>( );

	public TableMetaData getTableMetaData( String tableName )
	{
		return tables.get( tableName.toUpperCase( ) );
	}

	public void addTableMetaData( TableMetaData tableMetaData )
	{
		tables.put( tableMetaData.getTableName( ).toUpperCase( ), tableMetaData );
	}

	public String[] getTableNames( )
	{
		return tables.keySet( ).toArray( new String[0] );
	}

	public TableMetaData[] getTableMetaDatas( )
	{
		return tables.values( ).toArray( new TableMetaData[0] );
	}

}
