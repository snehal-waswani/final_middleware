package com.citi.bridge.pojo;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "user_stocks")
@IdClass(UserStocksDAO.class)
public class UserStocksDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id public Integer uid;
	@Id public Integer cid;
	
	public UserStocksDAO()
	{
		
	}
	public UserStocksDAO(Integer uid,Integer cid)
	{
		this.cid=cid;
		this.uid=uid;
	}
    @Override
	public int hashCode() {
    	return Objects.hash(uid, cid);
    }

}
