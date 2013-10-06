/**
 * This class is generated by jOOQ
 */
package gudusoft.sakila.tables;

/**
 * This class is generated by jOOQ.
 *
 * VIEW
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StaffList extends org.jooq.impl.TableImpl<gudusoft.sakila.tables.records.StaffListRecord> {

	private static final long serialVersionUID = 862310540;

	/**
	 * The singleton instance of <code>sakila.staff_list</code>
	 */
	public static final gudusoft.sakila.tables.StaffList STAFF_LIST = new gudusoft.sakila.tables.StaffList();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<gudusoft.sakila.tables.records.StaffListRecord> getRecordType() {
		return gudusoft.sakila.tables.records.StaffListRecord.class;
	}

	/**
	 * The column <code>sakila.staff_list.ID</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, org.jooq.types.UByte> ID = createField("ID", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this);

	/**
	 * The column <code>sakila.staff_list.name</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, java.lang.String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(91), this);

	/**
	 * The column <code>sakila.staff_list.address</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, java.lang.String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(50), this);

	/**
	 * The column <code>sakila.staff_list.zip code</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, java.lang.String> ZIP_CODE = createField("zip code", org.jooq.impl.SQLDataType.VARCHAR.length(10), this);

	/**
	 * The column <code>sakila.staff_list.phone</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, java.lang.String> PHONE = createField("phone", org.jooq.impl.SQLDataType.VARCHAR.length(20), this);

	/**
	 * The column <code>sakila.staff_list.city</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, java.lang.String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(50), this);

	/**
	 * The column <code>sakila.staff_list.country</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, java.lang.String> COUNTRY = createField("country", org.jooq.impl.SQLDataType.VARCHAR.length(50), this);

	/**
	 * The column <code>sakila.staff_list.SID</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StaffListRecord, org.jooq.types.UByte> SID = createField("SID", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this);

	/**
	 * Create a <code>sakila.staff_list</code> table reference
	 */
	public StaffList() {
		super("staff_list", gudusoft.sakila.Sakila.SAKILA);
	}

	/**
	 * Create an aliased <code>sakila.staff_list</code> table reference
	 */
	public StaffList(java.lang.String alias) {
		super(alias, gudusoft.sakila.Sakila.SAKILA, gudusoft.sakila.tables.StaffList.STAFF_LIST);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public gudusoft.sakila.tables.StaffList as(java.lang.String alias) {
		return new gudusoft.sakila.tables.StaffList(alias);
	}
}