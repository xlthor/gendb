package de.amthor.gendb.ddlgenerators;

/**
 * The DDL code generator interface as a basis for the specific DB Type generators
 * 
 * @author axel
 *
 */
public interface DdlGeneratorServiceInterface {

	
	public void writeDatabaseDdl();
	public void writeTableDdl();
	public void writeColumnDdl();
	
	
}
