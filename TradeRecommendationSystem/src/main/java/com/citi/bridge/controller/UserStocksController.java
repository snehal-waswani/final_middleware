/**
 * 
 */
package com.citi.bridge.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citi.bridge.pojo.CompanyDAO;
import com.citi.bridge.pojo.SystemConststs;
import com.citi.bridge.pojo.UserStocksDAO;
import com.citi.bridge.repository.CompanyRepository;
import com.citi.bridge.repository.UserStockRepository;

/**
 * @author Aditya
 *
 */
@RestController
@RequestMapping(path = "/users-stocks")
public class UserStocksController {
	
	@Autowired
	public UserStockRepository UserStockRepository;
	
	@Autowired
	public CompanyRepository CompanyRepository;
	
	int duration=3;
	LocalDate eDate = LocalDate.now();
	LocalDate sDate = eDate.minusDays(duration);
	
	@GetMapping(path = "/save-stocks")
	public String saveStockdata(@RequestParam Integer cid,@RequestParam Integer uid) throws Exception {
		
		UserStocksDAO user = new UserStocksDAO(cid,uid);
		UserStockRepository.save(user);
		CompanyDAO c = CompanyRepository.findByCid(cid);
		String code = c.companyCode;
		StringBuffer buffer = new StringBuffer();
		URL url = new URL("https://www.quandl.com/api/v3/datasets/EOD/" + code + ".csv?start_date=" + sDate 
			+ "&api_key=b--k_vTGmHWc782UMm5E");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", SystemConststs.USER_AGENT);
		
		if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null)
			{
				buffer.append(inputLine);
			}
			
		}
		return buffer.toString();
	}
	
	@GetMapping(path = "/show-stocks")
	public String viewSavedStocks(@RequestParam Integer uid) {
		

		return "true";
	}
}
