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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.hibernate.annotations.ColumnDefault;

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
@Table(	name = "gdb_column",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"colname", "table_id"})}
)
public class Columns { // need to take the plural here as "Column" is multiply reserved !
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long columnid;
	
	@Pattern(regexp = AppConstants.SQL_NAME_CONSTRAINT, flags = Flag.UNICODE_CASE)
	private String colname;
	
	private String description;
	
	@Column(nullable = false) 
	@ColumnDefault(value = "0")
	private long length;
	
	@Column(name = "iprecision", nullable = false) // "precision" is a reserved word
	@ColumnDefault(value = "0") 
	private long precision;
	
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
	
	@Column(name = "table_id", nullable = false, updatable = false, insertable = true)
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
