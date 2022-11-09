package de.amthor.gendb.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Helper class and wrapper around StringBuffer which collects and holds the generated SQL code
 * 
 * @author axel
 *
 */
public class CodeBuffer {
	
	private StringBuffer sqlCodeBuffer;
	
	
	public CodeBuffer() {
		sqlCodeBuffer = new StringBuffer();
	}
	
	public CodeBuffer append (String s) {
		sqlCodeBuffer.append(s);
		
		return this;
	}
	
	public CodeBuffer append (long s) {
		sqlCodeBuffer.append(s);
		
		return this;
	}
	
	public CodeBuffer append (int s) {
		sqlCodeBuffer.append(s);
		
		return this;
	}
	
	public CodeBuffer append (double s) {
		sqlCodeBuffer.append(s);
		
		return this;
	}
	
	public CodeBuffer appendComment(String comment) {
		append("-- " + comment);
		appendNl();
		return this;
	}
	
	public CodeBuffer blank() {
		append(" ");
		return this;
	}
	
	/**
	 * append one newline to the current buffer
	 * 
	 * @return
	 */
	public CodeBuffer appendNl() {
		return appendNl(1);		
	}
	
	/**
	 * append n newlines to the current buffer
	 * 
	 * @param n
	 * @return
	 */
	public CodeBuffer appendNl(int n) {
		for ( ; n > 0; n--)
			sqlCodeBuffer.append("\n");
		
		return this;
	}
	
	public String build() {
		return this.toString();
	}
	
	@Override
	public String toString() {
		return sqlCodeBuffer.toString();
	}
	
	public long toOutputStream(OutputStream os) throws IOException {

		byte[] chars = toString().getBytes(Charset.forName("UTF-8"));
		os.write(chars, 0, chars.length);
		os.flush();
		
		return chars.length;		
	}

	/**
	 */
	public CodeBuffer appendDottedLine() {
		append("-- ----------------------------------------------------");
		appendNl();
		return this;
	}

}
