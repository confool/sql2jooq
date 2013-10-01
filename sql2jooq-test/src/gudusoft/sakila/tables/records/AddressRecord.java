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
public class AddressRecord extends org.jooq.impl.UpdatableRecordImpl<gudusoft.sakila.tables.records.AddressRecord> implements org.jooq.Record8<org.jooq.types.UShort, java.lang.String, java.lang.String, java.lang.String, org.jooq.types.UShort, java.lang.String, java.lang.String, java.sql.Timestamp> {

	private static final long serialVersionUID = -1705603556;

	/**
	 * Setter for <code>sakila.address.address_id</code>. 
	 */
	public void setAddressId(org.jooq.types.UShort value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>sakila.address.address_id</code>. 
	 */
	public org.jooq.types.UShort getAddressId() {
		return (org.jooq.types.UShort) getValue(0);
	}

	/**
	 * Setter for <code>sakila.address.address</code>. 
	 */
	public void setAddress(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>sakila.address.address</code>. 
	 */
	public java.lang.String getAddress() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>sakila.address.address2</code>. 
	 */
	public void setAddress2(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>sakila.address.address2</code>. 
	 */
	public java.lang.String getAddress2() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>sakila.address.district</code>. 
	 */
	public void setDistrict(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>sakila.address.district</code>. 
	 */
	public java.lang.String getDistrict() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>sakila.address.city_id</code>. 
	 */
	public void setCityId(org.jooq.types.UShort value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>sakila.address.city_id</code>. 
	 */
	public org.jooq.types.UShort getCityId() {
		return (org.jooq.types.UShort) getValue(4);
	}

	/**
	 * Setter for <code>sakila.address.postal_code</code>. 
	 */
	public void setPostalCode(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>sakila.address.postal_code</code>. 
	 */
	public java.lang.String getPostalCode() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>sakila.address.phone</code>. 
	 */
	public void setPhone(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>sakila.address.phone</code>. 
	 */
	public java.lang.String getPhone() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>sakila.address.last_update</code>. 
	 */
	public void setLastUpdate(java.sql.Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>sakila.address.last_update</code>. 
	 */
	public java.sql.Timestamp getLastUpdate() {
		return (java.sql.Timestamp) getValue(7);
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
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row8<org.jooq.types.UShort, java.lang.String, java.lang.String, java.lang.String, org.jooq.types.UShort, java.lang.String, java.lang.String, java.sql.Timestamp> fieldsRow() {
		return (org.jooq.Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row8<org.jooq.types.UShort, java.lang.String, java.lang.String, java.lang.String, org.jooq.types.UShort, java.lang.String, java.lang.String, java.sql.Timestamp> valuesRow() {
		return (org.jooq.Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UShort> field1() {
		return gudusoft.sakila.tables.Address.ADDRESS.ADDRESS_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return gudusoft.sakila.tables.Address.ADDRESS.ADDRESS_;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return gudusoft.sakila.tables.Address.ADDRESS.ADDRESS2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return gudusoft.sakila.tables.Address.ADDRESS.DISTRICT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UShort> field5() {
		return gudusoft.sakila.tables.Address.ADDRESS.CITY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return gudusoft.sakila.tables.Address.ADDRESS.POSTAL_CODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return gudusoft.sakila.tables.Address.ADDRESS.PHONE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field8() {
		return gudusoft.sakila.tables.Address.ADDRESS.LAST_UPDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UShort value1() {
		return getAddressId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getAddress();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getAddress2();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getDistrict();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UShort value5() {
		return getCityId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getPostalCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getPhone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value8() {
		return getLastUpdate();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached AddressRecord
	 */
	public AddressRecord() {
		super(gudusoft.sakila.tables.Address.ADDRESS);
	}
}
