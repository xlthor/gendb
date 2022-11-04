package de.amthor.gendb.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import de.amthor.gendb.exception.ChildRecordExists;
import de.amthor.gendb.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Data
@Entity
@Table(	name = "gdb_database",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"dbname","release_id"})}
	)
public class Database {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long dbid;
	
	@Column(name = "dbname", nullable = false)
	@Pattern(regexp = AppConstants.SQL_NAME_CONSTRAINT, flags = Flag.UNICODE_CASE)
	String dbname;
	
	@Column(name = "description", length = 5000)
	private String description;
	
	@Column(name = "release_id", nullable = false)
	long releaseId;
	
	@OneToOne 
	DbType dbType;
	
	@OneToMany(fetch = FetchType.LAZY) // no cascade here!
	@JoinColumn(name = "database_id", unique=false)
	private Set<GdbTable> tables;
	
	@OneToOne
	/** the collation of this table */
	Collation collation;
	
	@Column(updatable = false, insertable = true)
	private Date created;
	
	private Date updated;
	
	@PrePersist
    protected void onCreate() {
      created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      updated = new Date();
    }
    
    /**
     * Check if we have existing tables
     */
    @PreRemove
    protected void deleteDatabase() {
    	if ( tables.size() > 0 )
    		throw new ChildRecordExists("Table");
    }
	
}
