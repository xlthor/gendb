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
@Table(	name = "gdb_table",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"tablename", "database_id"})}
	)
public class GdbTable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "table_id")
	long tableid;
	
	@Pattern(regexp = AppConstants.SQL_NAME_CONSTRAINT, flags = Flag.UNICODE_CASE)
	@Column(name = "tablename", nullable = false)
	/** unique table name */
	String tablename;
	
	@Column(name = "description", length = 5000)
	/** documentation / comment of this table */
	private String description;
	
	@Column(name = "database_id", nullable = false)
	/** enclosing database */
	long databaseId;
	
	@OneToMany(fetch = FetchType.LAZY) // no cascade here!
	@JoinColumn(name = "table_id", unique=false)
	Set<Columns> columns;
	
	@OneToOne
	/** the collation of this table */
	Collation collation;
	
	@OneToOne
	/** format or "storage engine" */
	Tableformat tableformat;
	
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
     * Check if we have existing columns
     */
    @PreRemove
    protected void deleteTable() {
    	if ( columns.size() > 0 )
    		throw new ChildRecordExists("Column");
    }
}
