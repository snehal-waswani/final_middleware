package com.citi.bridge.repository;

import org.springframework.data.repository.CrudRepository;

import com.citi.bridge.pojo.UserStocksDAO;

public interface UserStockRepository extends CrudRepository<UserStocksDAO,Long>{
	
	public UserStocksDAO findByUidAndCid(Integer uid,Integer cid);
	public UserStocksDAO findByUid(Integer uid);
}
