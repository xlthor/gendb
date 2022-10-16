package de.amthor.gendb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(	name = "collation") 
public class Collation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long collationid;
	
	private String typename;
	
	private String collation;
	private String characterset;
	
}
