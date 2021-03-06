package com.revature.shms.auth;
import com.revature.shms.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    //to be sent in post request
    //add role too, later
    private String username;
    private String password;
	private Roles role;

}