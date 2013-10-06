
package gudusoft.gsqlparser.sql2jooq.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableMetaData
{

	private Map<String, ColumnMetaData> columns = new LinkedHashMap<String, ColumnMetaData>( );

	private String tableName;

	private String tableSchema;

	
	public String getTableSchema( )
	{
		return tableSchema;
	}

	
	public void setTableSchema( String tableSchema )
	{
		this.tableSchema = tableSchema;
	}

	public String getTableName( )
	{
		return tableName;
	}

	public void setTableName( String tableName )
	{
		this.tableName = tableName;
	}

	public ColumnMetaData getColumnMetaData( String columnName )
	{
		return columns.get( columnName.toUpperCase( ) );
	}

	public void addColumnMetaData( ColumnMetaData columnMetaData )
	{
		columns.put( columnMetaData.getColumnName( ).toUpperCase( ),
				columnMetaData );
	}

	public String[] getColumnNames( )
	{
		return columns.keySet( ).toArray( new String[0] );
	}

	public ColumnMetaData[] getTableMetaDatas( )
	{
		return columns.values( ).toArray( new ColumnMetaData[0] );
	}

}
