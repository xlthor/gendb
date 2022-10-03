package de.amthor.gendb.entity;

import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"loginname"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String surname;
    private String lastname;
    
    /*
     * for security reasons we hide the login name, the password and the roles from the responses!
     */
    @JsonIgnore
    private String loginname;
    private String email;
    
    @JsonIgnore
    private String password;

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
    
    @ManyToMany(fetch = FetchType.EAGER) // no cascading!
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles;
    
}