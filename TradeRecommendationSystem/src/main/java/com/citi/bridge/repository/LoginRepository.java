package com.citi.bridge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.citi.bridge.pojo.UserDAO;

@Transactional
public interface LoginRepository extends CrudRepository<UserDAO, Long> {

	public UserDAO findByUsername(String Username);
	public UserDAO findByPassword(String Password);
	public UserDAO findByUsernameAndPassword(String username, String password);
}
