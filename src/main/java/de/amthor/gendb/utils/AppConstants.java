package de.amthor.gendb.utils;

public class AppConstants {

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public  static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    
    
    public static final String API_BASE = "/api/v1";
    
    public static final String PROJECTS = "projects";
    public static final String RELEASES = "releases";
    public static final String DATABASES = "databases";
    public static final String TABLES = "tables";
    public static final String COLUMNS = "columns";
    public static final String CODE = "sql";
    
    public static final String DBTYPES_FILE = "dbtypes.csv";
	public static final String COLLATIONS_FILE = "collations.csv";
	public static final String TABLEFORMATS_FILE = "tableformats.csv";
	public static final String COLTYPES_FILE = "coltypes.csv";
	public static final String COLPARAMS_FILE = "colparams.csv";
	
	/**
	 * REGEX for proper SQL names for columns, tables and databases 
	 */
	public static final String SQL_NAME_CONSTRAINT = "^[a-zA-Z_][a-zA-Z0-9_]*";
	
	
	
}
