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
public class FilmCategoryRecord extends org.jooq.impl.UpdatableRecordImpl<gudusoft.sakila.tables.records.FilmCategoryRecord> implements org.jooq.Record3<org.jooq.types.UShort, org.jooq.types.UByte, java.sql.Timestamp> {

	private static final long serialVersionUID = -1096501730;

	/**
	 * Setter for <code>sakila.film_category.film_id</code>. 
	 */
	public void setFilmId(org.jooq.types.UShort value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>sakila.film_category.film_id</code>. 
	 */
	public org.jooq.types.UShort getFilmId() {
		return (org.jooq.types.UShort) getValue(0);
	}

	/**
	 * Setter for <code>sakila.film_category.category_id</code>. 
	 */
	public void setCategoryId(org.jooq.types.UByte value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>sakila.film_category.category_id</code>. 
	 */
	public org.jooq.types.UByte getCategoryId() {
		return (org.jooq.types.UByte) getValue(1);
	}

	/**
	 * Setter for <code>sakila.film_category.last_update</code>. 
	 */
	public void setLastUpdate(java.sql.Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>sakila.film_category.last_update</code>. 
	 */
	public java.sql.Timestamp getLastUpdate() {
		return (java.sql.Timestamp) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record2<org.jooq.types.UShort, org.jooq.types.UByte> key() {
		return (org.jooq.Record2) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<org.jooq.types.UShort, org.jooq.types.UByte, java.sql.Timestamp> fieldsRow() {
		return (org.jooq.Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<org.jooq.types.UShort, org.jooq.types.UByte, java.sql.Timestamp> valuesRow() {
		return (org.jooq.Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UShort> field1() {
		return gudusoft.sakila.tables.FilmCategory.FILM_CATEGORY.FILM_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<org.jooq.types.UByte> field2() {
		return gudusoft.sakila.tables.FilmCategory.FILM_CATEGORY.CATEGORY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return gudusoft.sakila.tables.FilmCategory.FILM_CATEGORY.LAST_UPDATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UShort value1() {
		return getFilmId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.types.UByte value2() {
		return getCategoryId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getLastUpdate();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached FilmCategoryRecord
	 */
	public FilmCategoryRecord() {
		super(gudusoft.sakila.tables.FilmCategory.FILM_CATEGORY);
	}
}
