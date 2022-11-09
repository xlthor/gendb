package de.amthor.gendb.ddlgenerators;

import java.util.Set;

import org.springframework.stereotype.Service;

import de.amthor.gendb.exception.GeneratorException;
import de.amthor.gendb.payload.ColumnsDto;
import de.amthor.gendb.payload.DatabaseDto;
import de.amthor.gendb.payload.TableDto;
import de.amthor.gendb.utils.CodeBuffer;

@Service("MySQL_GeneratorService")
public class MySQL_Generator extends GeneratorBase implements DdlGeneratorServiceInterface {

	MySQL_Generator() {
		this.setDbType("MySQL");
	}

	@Override
	public void writeDatabaseDdl(CodeBuffer codeBuffer, DatabaseDto database, Boolean comments, Boolean transaction) {
		
		if ( comments )
			codeHeader(codeBuffer, database);
		
		if ( transaction )
			codeBuffer
				.append("START TRANSACTION;")
				.appendNl();
		
		codeBuffer
			.append("CREATE DATABASE IF NOT EXISTS `")
			.append(database.getDbname())
			.append("` DEFAULT CHARACTER SET ")
			.append(database.getCollation().getCharacterset())
			.append(" COLLATE ")
			.append(database.getCollation().getCollation())
			.append(";")
			.appendNl();
		
		if ( transaction )
			codeBuffer
				.append("COMMIT;")
				.appendNl();
		
		codeBuffer
			.append("USE `" + database.getDbname() + "`;")
			.appendNl(2);
		
	}

	@Override
	public void writeTableDdl(CodeBuffer codeBuffer, TableDto table, Boolean comments, Boolean transaction) throws GeneratorException {
		
		if ( table.getColumns().size() == 0 )
			throw new GeneratorException("This table does not contain any columns", table.getTablename());
		
		if ( comments )
			codeBuffer
				.appendDottedLine()
				.appendComment("Table structure for table " + table.getTablename())
				.appendDottedLine()
				.appendNl();
		
		if ( transaction ) 
			codeBuffer
				.append("START TRANSACTION;")
				.appendNl();
		
		codeBuffer
			.append("CREATE TABLE `")
			.append(table.getTablename())
			.append("` (")
			.appendNl();
		
		LOGGER.info("==================> map columns set");
		Set<ColumnsDto> columns = table.getColumns();
		
		int index = columns.size();
		for ( ColumnsDto column : columns ) {   // can't work with streams here, need to detect the last element
			writeColumnDdl(codeBuffer, column, table, comments, transaction);
			if ( index-- > 1 ) {
				codeBuffer .append(",");
			}
			codeBuffer.appendNl();
		}
		
		codeBuffer
			.appendNl()
			.append(") ENGINE=")
			.append(table.getTableformat().getFormatname())
			.blank()
			.append("DEFAULT CHARSET=")
			.append(table.getCollation().getCharacterset())
			.blank().append("COLLATE").blank()
			.append(table.getCollation().getCollation())
			.append(";")
			.appendNl();
		
		if ( transaction )
			codeBuffer
				.append("COMMIT;")
				.appendNl();
	}

	@Override
	public void writeColumnDdl(CodeBuffer codeBuffer, ColumnsDto columnDto, TableDto tableDto, Boolean comments, Boolean transaction) throws GeneratorException {
		
		if ( comments ) 
			codeBuffer
				.appendNl()
				.appendDottedLine()
				.appendComment("Column " + columnDto.getColname())
				.appendDottedLine();
			

		codeBuffer
			.append("`" + columnDto.getColname() + "`")
			.blank()
			.append(columnDto.getColtype().getType());
		
		boolean lengthRequired = false;
		switch ( columnDto.getColtype().getType().toUpperCase() ) {
			case "CHAR":
			case "VARCHAR":
			case "BINARY":
			case "VARBINARY":
			case "BLOB":
			case "TEXT":
				if ( columnDto.getLength() > 0 )
					codeBuffer.append("(" + columnDto.getLength() + ")");
				else 
					throw new GeneratorException("Text columns require a length > 0", tableDto.getTablename(), columnDto.getColname());
				lengthRequired = true;
			
				// fall through case!
				
			case "LONGTEXT":
			case "MDEIUMTEXT":
			case "TINYTEXT":
				codeBuffer
					.blank()
					.append("CHARACTER SET")
					.blank()
					.append(columnDto.getCollation().getCharacterset())
					.blank()
					.append("COLLATE")
					.blank()
					.append(columnDto.getCollation().getCollation())
					.blank()
					.append(columnDto.getNullable()? "NULL" : "NOT NULL")
					.blank();
				break;
				
			case "INT":
			case "BIT":
				if ( columnDto.getPrecision() > 0 )
					throw new GeneratorException("Int or Bit do not have a precision, do you mean Decimal?", tableDto.getTablename(), columnDto.getColname());
				
				if ( columnDto.getLength() > 0 )
					codeBuffer.append("(" + columnDto.getLength() + ")").blank();
				break;
				
			case "DECIMAL":
				if ( columnDto.getLength() > 0 )
					if ( columnDto.getPrecision() > 0 )
						codeBuffer.append("(" + columnDto.getLength() + "," + columnDto.getPrecision() + ")").blank();
					else
						codeBuffer.append("(" + columnDto.getLength() + ")").blank();
				break;
		}
		
		
		switch ( columnDto.getDefaultType().toUpperCase() ) {
			case "":
			case "NONE":
				break;
				
			case "CURRENT_TIMESTAMP":
				codeBuffer.append("DEFAULT CURRENT_TIMESTAMP").blank();
				break;
				
			case "NULL":
				codeBuffer
					.append("DEFAULT NULL")
					.blank();
				break;
				
			case "VALUE":
				if ( lengthRequired && columnDto.getDefaultValues().length() > columnDto.getLength() )
					throw new GeneratorException("Length of default value is larger than column length", tableDto.getTablename(), columnDto.getColname());
				
				codeBuffer
					.append("DEFAULT '")
					.append(columnDto.getDefaultValues())
					.append("'")
					.blank();
				break;
		}
		
		codeBuffer
			.append("COMMENT '")
			.append(columnDto.getDescription())
			.append("'");
			
			
		
	}
	
	

}
