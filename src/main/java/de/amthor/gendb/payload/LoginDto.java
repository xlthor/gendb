package de.amthor.gendb.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String loginnameOrEmail;
    private String password;
}