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
public class Inventory extends org.jooq.impl.TableImpl<gudusoft.sakila.tables.records.InventoryRecord> {

	private static final long serialVersionUID = 1744671597;

	/**
	 * The singleton instance of <code>sakila.inventory</code>
	 */
	public static final gudusoft.sakila.tables.Inventory INVENTORY = new gudusoft.sakila.tables.Inventory();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<gudusoft.sakila.tables.records.InventoryRecord> getRecordType() {
		return gudusoft.sakila.tables.records.InventoryRecord.class;
	}

	/**
	 * The column <code>sakila.inventory.inventory_id</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.InventoryRecord, org.jooq.types.UInteger> INVENTORY_ID = createField("inventory_id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED, this);

	/**
	 * The column <code>sakila.inventory.film_id</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.InventoryRecord, org.jooq.types.UShort> FILM_ID = createField("film_id", org.jooq.impl.SQLDataType.SMALLINTUNSIGNED, this);

	/**
	 * The column <code>sakila.inventory.store_id</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.InventoryRecord, org.jooq.types.UByte> STORE_ID = createField("store_id", org.jooq.impl.SQLDataType.TINYINTUNSIGNED, this);

	/**
	 * The column <code>sakila.inventory.last_update</code>. 
	 */
	public final org.jooq.TableField<gudusoft.sakila.tables.records.InventoryRecord, java.sql.Timestamp> LAST_UPDATE = createField("last_update", org.jooq.impl.SQLDataType.TIMESTAMP, this);

	/**
	 * Create a <code>sakila.inventory</code> table reference
	 */
	public Inventory() {
		super("inventory", gudusoft.sakila.Sakila.SAKILA);
	}

	/**
	 * Create an aliased <code>sakila.inventory</code> table reference
	 */
	public Inventory(java.lang.String alias) {
		super(alias, gudusoft.sakila.Sakila.SAKILA, gudusoft.sakila.tables.Inventory.INVENTORY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<gudusoft.sakila.tables.records.InventoryRecord, org.jooq.types.UInteger> getIdentity() {
		return gudusoft.sakila.Keys.IDENTITY_INVENTORY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<gudusoft.sakila.tables.records.InventoryRecord> getPrimaryKey() {
		return gudusoft.sakila.Keys.KEY_INVENTORY_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<gudusoft.sakila.tables.records.InventoryRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<gudusoft.sakila.tables.records.InventoryRecord>>asList(gudusoft.sakila.Keys.KEY_INVENTORY_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.ForeignKey<gudusoft.sakila.tables.records.InventoryRecord, ?>> getReferences() {
		return java.util.Arrays.<org.jooq.ForeignKey<gudusoft.sakila.tables.records.InventoryRecord, ?>>asList(gudusoft.sakila.Keys.FK_INVENTORY_FILM, gudusoft.sakila.Keys.FK_INVENTORY_STORE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public gudusoft.sakila.tables.Inventory as(java.lang.String alias) {
		return new gudusoft.sakila.tables.Inventory(alias);
	}
}