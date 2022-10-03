package de.amthor.gendb.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class Project {
	
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
	
	@ManyToMany(fetch = FetchType.LAZY ) // no cascade here!
	@JoinTable(name = "project_releases",
			joinColumns = @JoinColumn(name = "project_id", referencedColumnName= "id"),
			inverseJoinColumns = @JoinColumn(name = "release_id", referencedColumnName= "id"))
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
    
    
}
