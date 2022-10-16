package de.amthor.gendb.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.amthor.gendb.exception.ChildRecordExists;
import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class Project {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Project.class);
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER ) // no cascade here, as otherwise users etc are deleted as well!
	@JoinTable(name = "project_users",
			joinColumns = @JoinColumn(name = "project_id", referencedColumnName= "id"),
			inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName= "id"))
	private Set<User> users;
	
	@OneToMany(fetch = FetchType.LAZY) // no cascade here!
	@JoinColumn(name = "project_id", unique=false)
	private Set<Release> releases;
	
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
     * Check if we have existing releases
     */
    @PreRemove
    protected void deleteProject() {
    	LOGGER.info("==============> existing Releases?");
    	if ( releases.size() > 0 )
    		throw new ChildRecordExists("Release");
    }
    
    
}
