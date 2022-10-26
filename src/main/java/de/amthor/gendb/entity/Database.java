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
@Table(	name = "gdb_database",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"dbname","release_id"})}
	)
public class Database {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long dbid;
	
	@Column(name = "dbname", nullable = false)
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
