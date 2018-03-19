package com.citi.bridge.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "login")
public class UserDAO {
 
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Integer uid;
    public String username;
    public String password;
}

