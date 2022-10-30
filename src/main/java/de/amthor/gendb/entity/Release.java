/**
 * 
 */
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
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import de.amthor.gendb.exception.ChildRecordExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author axel
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Data
@Entity
@Table(name = "releases",
	uniqueConstraints = {@UniqueConstraint(columnNames = {"version"})}
)
public class Release {

	private static final Logger LOGGER = LoggerFactory.getLogger(Release.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long releaseid;
	
	@Column(name = "version", nullable = false, unique=true)
	private String version;
	
	private String name;
	
	@Column(name = "description", length = 5000)
	private String description;
	
	@Column(name = "project_id", nullable = false)
	private long projectId;
	
	@OneToMany(fetch = FetchType.LAZY) // no cascade here!
	@JoinColumn(name = "release_id", unique=false)
	private Set<Database> databases;
	
	private Date since;
	
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
     * Check if we have existing databases
     */
    @PreRemove
    protected void deleteReleases() {
    	LOGGER.info("==============> existing databases?");
    	if ( databases.size() > 0 )
    		throw new ChildRecordExists("Database");
    }
}
