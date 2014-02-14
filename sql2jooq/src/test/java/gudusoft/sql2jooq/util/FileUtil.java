/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/

package gudusoft.sql2jooq.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil
{

	public static String read(File file) throws IOException {
        RandomAccessFile f = null;
	    try 
	    {
	    	f = new RandomAccessFile(file, "r");
            byte[] contents = new byte[(int) f.length()];
            f.readFully(contents);
            return new String(contents);
        }
        finally {
        	try 
        	{
        		f.close();
        	}
        	catch (IOException ignore) 
        	{
        	}
        }
	}
	
	
	public static void replaceFile( File file, String regex1, String regex2,
			String replacement, File saveFile )
	{
		Map<String, String> map = new HashMap<String, String>( );
		map.put( regex2, replacement );
		replaceFile( file, regex1, map, saveFile );
	}

	public static void replaceFile( File file, String regex,
			Map<String, String> map, File saveFile )
	{
		try
		{
			int sizeL = (int) file.length( );
			int chars_read = 0;
			BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream( file ) ) );
			char[] data = new char[sizeL];
			while ( in.ready( ) )
			{
				chars_read += in.read( data, chars_read, sizeL - chars_read );
			}
			in.close( );
			char[] v = new char[chars_read];
			System.arraycopy( data, 0, v, 0, chars_read );
			String temp = new String( v );
			Pattern pattern = Pattern.compile( regex );
			Matcher matcher = pattern.matcher( temp );
			StringBuffer sbr = new StringBuffer( );
			while ( matcher.find( ) )
			{
				Iterator<String> iter = map.keySet( ).iterator( );
				String group = matcher.group( );
				while ( iter.hasNext( ) )
				{
					String key = (String) iter.next( );
					Pattern pattern1 = Pattern.compile( key );
					Matcher matcher1 = pattern1.matcher( group );
					group = matcher1.replaceAll( Matcher.quoteReplacement( (String) map.get( key ) ) );
				}
				matcher.appendReplacement( sbr,
						Matcher.quoteReplacement( group ) );
			}
			matcher.appendTail( sbr );
			PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter( new FileOutputStream( saveFile ) ) ),
					false );
			out.print( sbr );
			out.close( );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
