package com.citi.bridge.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.citi.bridge.pojo.CompanyDAO;

@Transactional
public interface CompanyRepository extends CrudRepository<CompanyDAO,Long>{

	  public CompanyDAO findByCid(Integer Cid);

	  
}