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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Data
@Entity
@Table(	name = "coltypes",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "dbtype"})}
)
public class Coltype {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long coltypeid;
	
	private String type;
	private String tgroup; // "group" is reserved SQL!
	private String description;
	private String dbtype;

}
