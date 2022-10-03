package de.amthor.gendb.payload;


import lombok.Data;

@Data
public class SignUpDto {
    private String surname;
    private String lastname;
    private String loginname;
    private String email;
    private String password;
}