package de.amthor.gendb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
	
	@Column(name = "tablename", nullable = false)
	/** unique table name */
	String tablename;
	
	@Column(name = "description", length = 5000)
	/** documentation / comment of this table */
	private String description;
	
	@Column(name = "database_id", nullable = false)
	/** enclosing database */
	long databaseId;
	
	@OneToOne
	/** the collation of this table */
	Collation collation;
	
	@OneToOne
	/** format or "storage engine" */
	Tableformat tableformat;
	
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
}
