package de.amthor.gendb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Implements a simple key/value store for each DB type
 * 
 * @author axel
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Data
@Entity
@Table(	name = "colparams" ,
				uniqueConstraints = {@UniqueConstraint(columnNames = {"paramvalue", "paramkey", "dbtype"})}
)
public class ColumnParameter {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long paramid;
	
	private String dbtype;
	private String paramkey;
	private String paramvalue;
	
}
