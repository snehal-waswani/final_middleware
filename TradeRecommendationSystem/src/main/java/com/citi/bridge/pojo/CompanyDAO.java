package com.citi.bridge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "company")
public class CompanyDAO {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	
    public Integer cid;
    public String companyName;
    
    @Column(name="companyCode")
    public String companyCode;
    public String cap;
}
