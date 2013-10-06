/**
 * This class is generated by jOOQ
 */
package gudusoft.sakila.tables.records;

/**
 * This class is generated by jOOQ.
 *
 * VIEW
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SalesByFilmCategoryRecord extends org.jooq.impl.TableRecordImpl<gudusoft.sakila.tables.records.SalesByFilmCategoryRecord> implements org.jooq.Record2<java.lang.String, java.math.BigDecimal> {

	private static final long serialVersionUID = 104540192;

	/**
	 * Setter for <code>sakila.sales_by_film_category.category</code>. 
	 */
	public void setCategory(java.lang.String value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>sakila.sales_by_film_category.category</code>. 
	 */
	public java.lang.String getCategory() {
		return (java.lang.String) getValue(0);
	}

	/**
	 * Setter for <code>sakila.sales_by_film_category.total_sales</code>. 
	 */
	public void setTotalSales(java.math.BigDecimal value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>sakila.sales_by_film_category.total_sales</code>. 
	 */
	public java.math.BigDecimal getTotalSales() {
		return (java.math.BigDecimal) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, java.math.BigDecimal> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.String, java.math.BigDecimal> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return gudusoft.sakila.tables.SalesByFilmCategory.SALES_BY_FILM_CATEGORY.CATEGORY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.math.BigDecimal> field2() {
		return gudusoft.sakila.tables.SalesByFilmCategory.SALES_BY_FILM_CATEGORY.TOTAL_SALES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getCategory();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.math.BigDecimal value2() {
		return getTotalSales();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached SalesByFilmCategoryRecord
	 */
	public SalesByFilmCategoryRecord() {
		super(gudusoft.sakila.tables.SalesByFilmCategory.SALES_BY_FILM_CATEGORY);
	}
}