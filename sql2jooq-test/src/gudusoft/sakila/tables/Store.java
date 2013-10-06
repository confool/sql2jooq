/**
 * This class is generated by jOOQ
 */
package gudusoft.sakila.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Store extends org.jooq.impl.TableImpl<gudusoft.sakila.tables.records.StoreRecord> {

	private static final long serialVersionUID = -1603214892;

	/**
	 * The singleton instance of <code>sakila.store</code>
	 */
	public static final gudusoft.sakila.tables.Store STORE = new gudusoft.sakila.tables.Store();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<gudusoft.sakila.tables.records.StoreRecord> getRecordType() {
		return gudusoft.sakila.tables.records.StoreRecord.class;
	}

	/**
	 * The column <code>sakila.store.store_id</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StoreRecord, org.jooq.types.UByte> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this);

	/**
	 * The column <code>sakila.store.manager_staff_id</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StoreRecord, org.jooq.types.UByte> MANAGER_STAFF_ID = createField("manager_staff_id", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this);

	/**
	 * The column <code>sakila.store.address_id</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StoreRecord, org.jooq.types.UShort> ADDRESS_ID = createField("address_id", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED, this);

	/**
	 * The column <code>sakila.store.last_update</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.StoreRecord, java.sql.Timestamp> LAST_UPDATE = createField("last_update", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * Create a <code>sakila.store</code> table reference
	 */
	public Store() {
		super("store", gudusoft.sakila.Sakila.SAKILA);
	}

	/**
	 * Create an aliased <code>sakila.store</code> table reference
	 */
	public Store(java.lang.String alias) {
		super(alias, gudusoft.sakila.Sakila.SAKILA, gudusoft.sakila.tables.Store.STORE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<gudusoft.sakila.tables.records.StoreRecord, org.jooq.types.UByte> getIdentity() {
		return gudusoft.sakila.Keys.IDENTITY_STORE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<gudusoft.sakila.tables.records.StoreRecord> getPrimaryKey() {
		return gudusoft.sakila.Keys.KEY_STORE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<gudusoft.sakila.tables.records.StoreRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<gudusoft.sakila.tables.records.StoreRecord>>asList(gudusoft.sakila.Keys.KEY_STORE_PRIMARY, gudusoft.sakila.Keys.KEY_STORE_IDX_UNIQUE_MANAGER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<gudusoft.sakila.tables.records.StoreRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<gudusoft.sakila.tables.records.StoreRecord, ?>>asList(gudusoft.sakila.Keys.FK_STORE_STAFF, gudusoft.sakila.Keys.FK_STORE_ADDRESS);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public gudusoft.sakila.tables.Store as(java.lang.String alias) {
		return new gudusoft.sakila.tables.Store(alias);
	}
}