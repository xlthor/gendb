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
@Table(	name = "gdb_column",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"colname", "tableid"})}
)
public class Columns { // need to take the plural here as "Column" is multiply reserved !
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long columnid;
	
	private String colname;
	private String description;
	private long length;
	private String defaultValues;
	private String defaultType;
	private String attributes;
	private Boolean nullable;
	private String indexType;
	private Boolean autoincrement;
	private String serial;
	private String virtuality;
	
	@OneToOne
	private Coltype coltype;
	
	@OneToOne
	Collation collation;
	
	@Column(nullable = false, updatable = false, insertable = true)
	/** enclosing table */
	long tableid;
	
	
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
