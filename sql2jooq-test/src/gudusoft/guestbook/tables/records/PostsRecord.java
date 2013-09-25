/**
 * This class is generated by jOOQ
 */
package gudusoft.guestbook.tables.records;

/**
 * This class is generated by jOOQ.
 *
 * InnoDB free: 4096 kB
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.1.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PostsRecord extends org.jooq.impl.TableRecordImpl<gudusoft.guestbook.tables.records.PostsRecord> implements org.jooq.Record4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.lang.String> {

	private static final long serialVersionUID = -773797515;

	/**
	 * Setter for <code>guestbook.posts.id</code>. 
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>guestbook.posts.id</code>. 
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>guestbook.posts.body</code>. 
	 */
	public void setBody(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>guestbook.posts.body</code>. 
	 */
	public java.lang.String getBody() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>guestbook.posts.timestamp</code>. 
	 */
	public void setTimestamp(java.sql.Timestamp value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>guestbook.posts.timestamp</code>. 
	 */
	public java.sql.Timestamp getTimestamp() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>guestbook.posts.title</code>. 
	 */
	public void setTitle(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>guestbook.posts.title</code>. 
	 */
	public java.lang.String getTitle() {
		return (java.lang.String) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.lang.String> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.lang.String> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return gudusoft.guestbook.tables.Posts.POSTS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return gudusoft.guestbook.tables.Posts.POSTS.BODY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return gudusoft.guestbook.tables.Posts.POSTS.TIMESTAMP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return gudusoft.guestbook.tables.Posts.POSTS.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getBody();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getTitle();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PostsRecord
	 */
	public PostsRecord() {
		super(gudusoft.guestbook.tables.Posts.POSTS);
	}
}