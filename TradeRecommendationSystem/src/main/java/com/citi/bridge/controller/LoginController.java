package com.citi.bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citi.bridge.pojo.UserDAO;
import com.citi.bridge.repository.LoginRepository;

@RestController
@RequestMapping(path="/login")
public class LoginController
{
	@Autowired 
	public LoginRepository LoginRepository;
	
	@GetMapping(path="/validate")
	public String getByEmail(@RequestParam String username, @RequestParam String password) {   
		UserDAO dao = LoginRepository.findByUsernameAndPassword(username, password);
		return (dao != null) ? "true" : "false";
	}
}







/*//String GET_URL = "https://www.quandl.com/api/v3/datasets/NSE/GS?start_date=2018-03-01&end_date=2018-03-13&api_key=b--k_vTGmHWc782UMm5E";
//String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=AAPL&apikey=L3RLE915L2DHGIXQ";
*/