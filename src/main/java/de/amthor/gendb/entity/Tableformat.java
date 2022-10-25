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
@Table(	name = "tableformat",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"formatname", "typename"})}
)
public class Tableformat {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long formatid;
	
	private String typename;
	private String formatname;
	private String description;

}
