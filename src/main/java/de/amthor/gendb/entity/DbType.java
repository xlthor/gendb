package de.amthor.gendb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

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
@Table(	name = "dbtype")
public class DbType {

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	long typeid;
	
	/** database type */
	String typename;
	
	/** DB Version */
	String version;
	
	/** a description */
	String description;
	
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
}
