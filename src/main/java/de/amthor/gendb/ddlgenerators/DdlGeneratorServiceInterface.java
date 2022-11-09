package de.amthor.gendb.ddlgenerators;

import de.amthor.gendb.ddlgenerators.GeneratorBase.Scope;
import de.amthor.gendb.exception.GeneratorException;
import de.amthor.gendb.payload.ColumnsDto;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.utils.CodeBuffer;

/**
 * The DDL code generator interface as a basis for the specific DB Type generators
 * 
 * @author axel
 *
 */
public interface DdlGeneratorServiceInterface {

	public void writeDatabaseDdl(CodeBuffer writer, DatabaseDto database, Boolean comments, Boolean transaction);
	public void writeTableDdl(CodeBuffer writer, TableDto table, Boolean comments, Boolean transaction) throws GeneratorException;
	void writeColumnDdl(CodeBuffer codeBuffer, ColumnsDto column, TableDto tableDto, Boolean comments, Boolean transaction) throws GeneratorException;
	
	public String getDbType();
	
	/**
	 * @return the scope
	 */
	public Scope getScope();

	/**
	 * @param scope the scope to set
	 */
	public void setScope(Scope scope);
	
}
