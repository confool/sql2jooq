/**
 * This class is generated by jOOQ
 */
package gudusoft.sakila.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CustomerRecord extends org.jooq.impl.UpdatableRecordImpl<gudusoft.sakila.tables.records.CustomerRecord> implements org.jooq.Record9<org.jooq.types.UShort, org.jooq.types.UByte, java.lang.String, java.lang.String, java.lang.String, org.jooq.types.UShort, java.lang.Byte, java.sql.Timestamp, java.sql.Timestamp> {

	private static final long serialVersionUID = 967036659;

	/**
	 * Setter for <code>sakila.customer.customer_id</code>. 
	 */
	public void setCustomerId(org.jooq.types.UShort value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>sakila.customer.customer_id</code>. 
	 */
	public org.jooq.types.UShort getCustomerId() {
		return (org.jooq.types.UShort) getValue(0);
	}

	/**
	 * Setter for <code>sakila.customer.store_id</code>. 
	 */
	public void setStoreId(org.jooq.types.UByte value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>sakila.customer.store_id</code>. 
	 */
	public org.jooq.types.UByte getStoreId() {
		return (org.jooq.types.UByte) getValue(1);
	}

	/**
	 * Setter for <code>sakila.customer.first_name</code>. 
	 */
	public void setFirstName(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>sakila.customer.first_name</code>. 
	 */
	public java.lang.String getFirstName() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>sakila.customer.last_name</code>. 
	 */
	public void setLastName(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>sakila.customer.last_name</code>. 
	 */
	public java.lang.String getLastName() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>sakila.customer.email</code>. 
	 */
	public void setEmail(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>sakila.customer.email</code>. 
	 */
	public java.lang.String getEmail() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>sakila.customer.address_id</code>. 
	 */
	public void setAddressId(org.jooq.types.UShort value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>sakila.customer.address_id</code>. 
	 */
	public org.jooq.types.UShort getAddressId() {
		return (org.jooq.types.UShort) getValue(5);
	}

	/**
	 * Setter for <code>sakila.customer.active</code>. 
	 */
	public void setActive(java.lang.Byte value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>sakila.customer.active</code>. 
	 */
	public java.lang.Byte getActive() {
		return (java.lang.Byte) getValue(6);
	}

	/**
	 * Setter for <code>sakila.customer.create_date</code>. 
	 */
	public void setCreateDate(java.sql.Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>sakila.customer.create_date</code>. 
	 */
	public java.sql.Timestamp getCreateDate() {
		return (java.sql.Timestamp) getValue(7);
	}

	/**
	 * Setter for <code>sakila.customer.last_update</code>. 
	 */
	public void setLastUpdate(java.sql.Timestamp value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>sakila.customer.last_update</code>. 
	 */
	public java.sql.Timestamp getLastUpdate() {
		return (java.sql.Timestamp) getValue(8);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<org.jooq.types.UShort> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<org.jooq.types.UShort, org.jooq.types.UByte, java.lang.String, java.lang.String, java.lang.String, org.jooq.types.UShort, java.lang.Byte, java.sql.Timestamp, java.sql.Timestamp> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<org.jooq.types.UShort, org.jooq.types.UByte, java.lang.String, java.lang.String, java.lang.String, org.jooq.types.UShort, java.lang.Byte, java.sql.Timestamp, java.sql.Timestamp> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UShort> field1() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.CUSTOMER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UByte> field2() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.STORE_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UShort> field6() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.ADDRESS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Byte> field7() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.ACTIVE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field8() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.CREATE_DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field9() {
		return gudusoft.sakila.tables.Customer.CUSTOMER.LAST_UPDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UShort value1() {
		return getCustomerId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UByte value2() {
		return getStoreId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getEmail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UShort value6() {
		return getAddressId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Byte value7() {
		return getActive();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value8() {
		return getCreateDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value9() {
		return getLastUpdate();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached CustomerRecord
	 */
	public CustomerRecord() {
		super(gudusoft.sakila.tables.Customer.CUSTOMER);
	}
}