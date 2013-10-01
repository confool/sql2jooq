/**
 * This class is generated by jOOQ
 */
package gudusoft.sakila.routines;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FilmNotInStock extends org.jooq.impl.AbstractRoutine<java.lang.Void> {

	private static final long serialVersionUID = 2044648946;

	/**
	 * The parameter <code>sakila.film_not_in_stock.p_film_id</code>. 
	 */
	public static final org.jooq.Parameter<java.lang.Integer> P_FILM_ID = createParameter("p_film_id", org.jooq.impl.SQLDataType.INTEGER);

	/**
	 * The parameter <code>sakila.film_not_in_stock.p_store_id</code>. 
	 */
	public static final org.jooq.Parameter<java.lang.Integer> P_STORE_ID = createParameter("p_store_id", org.jooq.impl.SQLDataType.INTEGER);

	/**
	 * The parameter <code>sakila.film_not_in_stock.p_film_count</code>. 
	 */
	public static final org.jooq.Parameter<java.lang.Integer> P_FILM_COUNT = createParameter("p_film_count", org.jooq.impl.SQLDataType.INTEGER);

	/**
	 * Create a new routine call instance
	 */
	public FilmNotInStock() {
		super("film_not_in_stock", gudusoft.sakila.Sakila.SAKILA);

		addInParameter(P_FILM_ID);
		addInParameter(P_STORE_ID);
		addOutParameter(P_FILM_COUNT);
	}

	/**
	 * Set the <code>p_film_id</code> parameter IN value to the routine
	 */
	public void setPFilmId(java.lang.Integer value) {
		setValue(gudusoft.sakila.routines.FilmNotInStock.P_FILM_ID, value);
	}

	/**
	 * Set the <code>p_store_id</code> parameter IN value to the routine
	 */
	public void setPStoreId(java.lang.Integer value) {
		setValue(gudusoft.sakila.routines.FilmNotInStock.P_STORE_ID, value);
	}

	/**
	 * Get the <code>p_film_count</code> parameter OUT value from the routine
	 */
	public java.lang.Integer getPFilmCount() {
		return getValue(P_FILM_COUNT);
	}
}
