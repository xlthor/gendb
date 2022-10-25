package de.amthor.gendb.payload;

/**
 * Jackson Views definitions
 * 
 * @author axel
 *
 */
public class Views {

	
	public class TableCreate {};
	public class TableUpdate extends TableCreate {};

	public class DatabaseCreate  {};
	public class DatabaseUpdate extends DatabaseCreate {};
	public class DatabaseResponse {};

	/**
	 * Attributes for Create operations
	 */
	public static class Create {};
	
	/**
	 * Attributes for the response.
	 * Usually this includes all attributes of all nested objects
	 */
	public static class Response  {};
	
	
	/**
	 * Attributes for object containing sub-elements which should only referenced by their IDs.
	 * <p>Tag the ID with <code>@JsonView({Views.ParentUpdate.class})</code> and all others with <code>...Views.Update.class...</code> then.
	 */
	public static class ParentUpdate {};
	
	/**
	 * Attributes for Update / Patch operations
	 */
	public static class Update extends ParentUpdate {};
	
	public static class ReleaseUpdate  {};
}
