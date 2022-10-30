package de.amthor.gendb;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.amthor.gendb.entity.Collation;
import de.amthor.gendb.entity.Coltype;
import de.amthor.gendb.entity.ColumnParameter;
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.entity.Tableformat;
import de.amthor.gendb.repository.CollationRepository;
import de.amthor.gendb.repository.ColtypesRepository;
import de.amthor.gendb.repository.ColumnParamRepository;
import de.amthor.gendb.repository.DBTypeRepository;
import de.amthor.gendb.repository.FormatRepository;
import de.amthor.gendb.utils.AppConstants;
import de.amthor.gendb.utils.CsvReader;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * @author axel
 * 
 * Load initial data
 * 
 */
@Component
public class Setup {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(Setup.class);
	
	@Autowired
	DBTypeRepository dbTypeRepository;
	
	@Autowired
	CollationRepository collationRepository;
	
	@Autowired
	FormatRepository formatRepository;
	
	@Autowired
	ColtypesRepository coltypesRepository;
	
	@Autowired
	ColumnParamRepository columnParamRepository;
	
    @PostConstruct
    private void setupData() {
    	
    	LOGGER.info("============> Entering Setup Bean ....");
        setUpDBTypes();
        setUpCollations();
        setUpTableformats();
        setUpColtypes();
        setUpColParams();
        
        
        LOGGER.info("============> Setup finished.");
        
    }
    
    private void setUpDBTypes() {
    	List<DbType> dbtypes = getDBTypes();
        for (DbType dbtype : dbtypes) {
            setupDBType(dbtype);
        }
	}


	private void setupDBType(DbType dbtype) {
		if ( !dbTypeRepository.existsByTypename(dbtype.getTypename()) ) {
	        dbTypeRepository.save(dbtype);
	    }
	}

	public List<DbType> getDBTypes() {
    
    	CsvReader csvDataLoader = new CsvReader();
    	
        List<DbType> dbtypes = csvDataLoader.loadObjectList(DbType.class, AppConstants.DBTYPES_FILE);
        
        for (DbType dbtype : dbtypes) {
        	LOGGER.debug(dbtype.toString());
        }
        
        return dbtypes;
    }
    
	@Getter
	@Setter
	@Data
	static class ImportCollation {
		private String characterset;
		private String collationid;
		private String typename;
		private String collation;
	}
	
	private Hashtable<String,DbType> typeCache = new Hashtable<>();
	
	/**
	 * Initial load of the collation table
	 */
	public void setUpCollations() {
	    
    	CsvReader csvDataLoader = new CsvReader();
    	
        List<ImportCollation> impCollations = csvDataLoader.loadObjectList(ImportCollation.class, AppConstants.COLLATIONS_FILE);
        
        for (ImportCollation impCollation : impCollations) {
        	
        	String dbTypeName = impCollation.getTypename();
        	DbType dbType = getDbType(dbTypeName);
        	
        	if ( dbType == null )
        		continue;
        	
        	if ( !collationRepository.existsByCollationAndTypename(impCollation.getCollation(), dbType.getTypename()) ) {
	        	
	        	Collation col = new Collation();
	        	
	        	col.setCharacterset(impCollation.getCharacterset());
	        	col.setCollation(impCollation.getCollation());
	        	col.setTypename(dbType.getTypename());
	        	
        		collationRepository.save(col);
        	}
        }
    }

	/**
	 * @param dbTypeName
	 * @return
	 */
	private DbType getDbType(String dbTypeName) {
		DbType dbType = typeCache.get(dbTypeName);
		if ( dbType == null ) {
			Optional<DbType> optionalDbType = dbTypeRepository.findByTypename(dbTypeName);
			if ( optionalDbType.isPresent() ) {
				dbType = optionalDbType.get();
				typeCache.put(dbType.getTypename(), dbType);
			}
		}
		return dbType;
	}
	
	@Getter
	@Setter
	@Data
	static class ImportTableformat {
		private String typename;
		private String formatname;
		private String description;
	}
	
	/**
	 * Initial load of the table format / engine table
	 */
	public void setUpTableformats() {
	    
    	CsvReader csvDataLoader = new CsvReader();
    	
    	
        List<ImportTableformat> impFormats = csvDataLoader.loadObjectList(ImportTableformat.class, AppConstants.TABLEFORMATS_FILE, ';');
        
        for (ImportTableformat impFormat : impFormats) {
        	
        	String dbTypeName = impFormat.getTypename();
        	DbType dbType = getDbType(dbTypeName);
        	
        	if ( dbType == null )
        		continue;
        	
        	if ( !formatRepository.existsByFormatnameAndTypename(impFormat.getFormatname(), dbType.getTypename()) ) {
	        	
	        	Tableformat format = new Tableformat();
	        	
	        	format.setFormatname(impFormat.getFormatname());
	        	format.setTypename(dbType.getTypename());
	        	format.setDescription(impFormat.getDescription());
	        	
	        	formatRepository.save(format);
        	}
        }
    }
	
	@Getter
	@Setter
	@Data
	static class ImportColtype {
		private String type;
		private String tgroup; 
		private String description;
		private String dbtype;
	}
	
	/**
	 * Initial load of the table format / engine table
	 */
	public void setUpColtypes() {
	    
    	CsvReader csvDataLoader = new CsvReader();
    	
    	
        List<ImportColtype> impColtypes = csvDataLoader.loadObjectList(ImportColtype.class, AppConstants.COLTYPES_FILE, ';');
        
        for (ImportColtype impColtype : impColtypes) {
        	
        	String dbTypeName = impColtype.getDbtype();
        	DbType dbType = getDbType(dbTypeName);
        	
        	if ( dbType == null )
        		continue;
        	
        	if ( !coltypesRepository.existsByTypeAndDbtype(impColtype.getType(), dbType.getTypename()) ) {
	        	
	        	Coltype coltype = new Coltype();
	        	
	        	coltype.setType(impColtype.getType());
	        	coltype.setDbtype(dbType.getTypename());
	        	coltype.setDescription(impColtype.getDescription());
	        	coltype.setTgroup(impColtype.getTgroup());
	        	
	        	coltypesRepository.save(coltype);
        	}
        }
    }
	
	@Getter
	@Setter
	@Data
	static class ImportColParam {
		private String dbtype;
		private String paramkey;
		private String paramvalue;
	}
	
	/**
	 * Initial load of the table format / engine table
	 */
	public void setUpColParams() {
	    
    	CsvReader csvDataLoader = new CsvReader();
    	
    	
        List<ImportColParam> importColParams = csvDataLoader.loadObjectList(ImportColParam.class, AppConstants.COLPARAMS_FILE, ';');
        
        for (ImportColParam importColParam : importColParams ) {
        	
        	String dbTypeName = importColParam.getDbtype();
        	DbType dbType = getDbType(dbTypeName);
        	
        	if ( dbType == null )
        		continue;
        	
        	if ( !columnParamRepository.existsByParamkeyAndParamvalueAndDbtype(importColParam.getParamkey(), importColParam.getParamvalue(), dbType.getTypename()) ) {
	        	
        		ColumnParameter cp = new ColumnParameter();
        		cp.setDbtype(importColParam.getDbtype());
        		cp.setParamkey(importColParam.getParamkey());
        		cp.setParamvalue(importColParam.getParamvalue());
        		
        		columnParamRepository.save(cp);
        	}
        }
    }
    
}
