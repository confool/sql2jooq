How to use the SQL2jOOQ utility
-------------------------------

It's very simple!

1. Create a JDBC connection
2. Create a new SQL2jOOQ converter object
3. Convert your SQL code
4. Get the result

Example:

```java
package gudusoft.sql2jooq.readme;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.sql2jooq.SQL2jOOQ;
import gudusoft.gsqlparser.sql2jooq.db.DatabaseMetaData;
import gudusoft.gsqlparser.sql2jooq.tool.DatabaseMetaUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class Test {
    public static void main(String[] args) throws Exception {

        // 1. Create a JDBC connection
        // ---------------------------
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/guestbook";
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, userName, password);
        
        // 2. Create a new SQL2jOOQ converter object
        // -----------------------------------------
        DatabaseMetaData metaData = DatabaseMetaUtil
            .getDataBaseMetaData(conn, "guestbook");
            
        SQL2jOOQ convert = new SQL2jOOQ(metaData,
            EDbVendor.dbvmysql,
            "select first_name, last_name from actor where actor_id = 1;");
            
        // 3. Convert your SQL code
        // ------------------------
        convert.convert();
        if (convert.getErrorMessage() != null) {
            System.err.println(convert.getErrorMessage());
            System.exit(-1);
        }
        
        // 4. Get the result
        // -----------------
        System.out.println(convert.getConvertResult());
    }
}

```

If all goes well, the above program yields:

```java
DSLContext create = DSL.using(conn, SQLDialect.MYSQL);

Result<Record2<String, String>> result = create.select( Actor.ACTOR.FIRST_NAME, Actor.ACTOR.LAST_NAME )
    .from( Actor.ACTOR )
    .where( Actor.ACTOR.ACTOR_ID.equal( DSL.inline( UShort.valueOf( 1 ) ) ) ).fetch( );
```


Questions?
----------

Don't hesitate to ask us. We'll help on:

- The jOOQ User Group: https://groups.google.com/d/forum/jooq-user
- The sql2jooq GitHub project: https://github.com/sqlparser/sql2jooq/issues