
package gudusoft.gsqlparser.sql2jooq.db;

public class ColumnMetaData
{

	private String columnName;
	private int sqlType;
	private String javaTypeClass;

	public String getColumnName( )
	{
		return columnName;
	}

	public void setColumnName( String columnName )
	{
		this.columnName = columnName;
	}

	public int getSqlType( )
	{
		return sqlType;
	}

	public void setSqlType( int sqlType )
	{
		this.sqlType = sqlType;
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
