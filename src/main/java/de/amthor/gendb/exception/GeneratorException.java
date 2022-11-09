/**
 * 
 */
package de.amthor.gendb.exception;

/**
 * @author axel
 *
 */
public class GeneratorException extends RuntimeException {

	private static final long serialVersionUID = -1683664206534714164L;

	/**
	 * @param message
	 */
	public GeneratorException(String message, String table, String field) {
		super(message + " - `" + table + "`.`" + field + "`");
		
	}

	public GeneratorException(String message, String tablename) {
		super(message + " - `" + tablename + "`");
	}

}
