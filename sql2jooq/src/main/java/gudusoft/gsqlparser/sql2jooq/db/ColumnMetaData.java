
package gudusoft.gsqlparser.sql2jooq.db;

public class ColumnMetaData
{

	private String columnName;
	private String javaTypeClass;
	private String typeName;

	public String getTypeName( )
	{
		return typeName;
	}

	public void setTypeName( String typeName )
	{
		this.typeName = typeName;
	}

	public String getColumnName( )
	{
		return columnName;
	}

	public void setColumnName( String columnName )
	{
		this.columnName = columnName;
	}

	public String getJavaTypeClass( )
	{
		return javaTypeClass;
	}

	public void setJavaTypeClass( String javaTypeClass )
	{
		this.javaTypeClass = javaTypeClass;
	}
}
