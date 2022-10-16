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
import de.amthor.gendb.entity.DbType;
import de.amthor.gendb.repository.CollationRepository;
import de.amthor.gendb.repository.DBTypeRepository;
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
	
    @PostConstruct
    private void setupData() {
    	
    	LOGGER.debug("============> Entering Setup Bean ....");
        setUpDBTypes();
        setUpCollations();
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
        	
        	DbType dbType = typeCache.get(impCollation.getTypename());
        	if ( dbType == null ) {
        		Optional<DbType> optionalDbType = dbTypeRepository.findByTypename(impCollation.getTypename());
        		if ( optionalDbType.isPresent() ) {
        			dbType = optionalDbType.get();
        			typeCache.put(dbType.getTypename(), dbType);
        		}
        		else
        			continue;
        	}
        	
        	if ( !collationRepository.existsByCollationAndTypename(impCollation.getCollation(), dbType.getTypename()) ) {
	        	LOGGER.debug(impCollation.toString());
	        	
	        	Collation col = new Collation();
	        	
	        	col.setCharacterset(impCollation.getCharacterset());
	        	col.setCollation(impCollation.getCollation());
	        	col.setTypename(dbType.getTypename());
	        	
        		collationRepository.save(col);
        	}
        }
    }
    
}
