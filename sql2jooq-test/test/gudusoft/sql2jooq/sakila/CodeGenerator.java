
package gudusoft.sql2jooq.sakila;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.sql2jooq.jooqConverter;
import gudusoft.sql2jooq.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Lukas Eder
 */
public class CodeGenerator
{

    public static void main( String[] args ) throws IOException
    {

        // jOOQ's MockFileDatabase can be used for unit testing
        // It allows for matching queries with expected results
        String[] queries = FileUtil
                .read(new File("./test/gudusoft/sql2jooq/sakila/queries.txt"))
                .split(";\\s+");

        int i = 0;
        for (final String query : queries)
        {
            if (query == null || query.length() == 0)
                continue;

            i++;

            try {
            	final String id = String.format("%1$4s", i).replace(" ", "0");
            	System.out.println("Generating code for : (" + id + ") " + query.replaceAll("[\\n\\r]+", "$0                             "));

                jooqConverter convert = new jooqConverter( EDbVendor.dbvmysql, query );
                final String result = convert.getConvertResult( );

                File templateFile = new File( "./xml/guestbook/SakilaTest.template.java" );
                File saveFile = new File( "./test/gudusoft/sql2jooq/sakila/tests/SakilaTest" + id + ".java" );

                Map<String, String> replacements = new HashMap<String, String>();
                replacements.put(Pattern.quote( "${ID}" ), id);
                replacements.put(Pattern.quote( "${DSL}" ), result);
                replacements.put(Pattern.quote( "${SQL}" ), query.replace("\"", "\\\"").replaceAll("[\\n\\r]+", "\"$0+ \""));

                FileUtil.replaceFile( templateFile,
                        ".*",
                        replacements,
                        saveFile );
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
