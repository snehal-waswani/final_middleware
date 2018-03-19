package com.citi.bridge.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.citi.bridge.pojo.CompanyDAO;
import com.citi.bridge.pojo.StockDTO;
import com.citi.bridge.pojo.StockData;
import com.citi.bridge.pojo.SystemConststs;
import com.citi.bridge.repository.CompanyRepository;

@RestController
@RequestMapping(path = "/populate-stocks")
public class StockController {

	@Autowired
	public CompanyRepository CompanyRepository;

	StockData[] st = new StockData[500];
	StockDTO[] large = new StockDTO[100];
	StockDTO[] small = new StockDTO[100];
	StockDTO[] mid = new StockDTO[100];

	int duration = 14;
	int lg = 0;
	int sm = 0;
	int md = 0;
	//LocalDate eDate = LocalDate.now();
	//LocalDate sDate = eDate.minusDays(duration);
	String eDate="2018-03-15";
	String sDate="2018-03-01";
	
	
	@GetMapping(path = "/get-stock-data")
	public String getStockdata() throws Exception {
		int j = 0;
		for (int cid = 1; cid < 30; cid++) 
		{
			try 
			{
				CompanyDAO c = CompanyRepository.findByCid(cid);
				URL url = new URL(constructURL(c.companyCode, sDate, eDate));
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", SystemConststs.USER_AGENT);
				
				if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;

					while ((inputLine = in.readLine()) != null) {
						System.out.println("\n J : " + j + " -----> " + inputLine);
						String tempStr[] = inputLine.split(",");
						if (!tempStr[0].equals("Date")) {
							st[j] = new StockData();
							st[j].companyName = c.companyName;
							st[j].date = tempStr[0];
							st[j].open = tempStr[1];
							st[j].high = tempStr[2];
							st[j].low = tempStr[3];
							st[j].close = tempStr[4];
							st[j].volume = tempStr[5];
							j++;
						}
					}
				}
			} catch (Exception ex) {
			}

		}
		for (int k = 0; k < j; k++) {
			System.out.println(st[k].companyName + " " + st[k].date + " " + st[k].open + " " + st[k].high + " "
					+ st[k].low + " " + st[k].close + " " + st[k].volume);
		}
		return null;

	}

	@GetMapping(path = "/store-change")
	public  String capitalization() {

		String str;
		String company_name;
		String cap;

		for (int i = 1; i < 30; i++) 
		{
			CompanyDAO temp = CompanyRepository.findByCid(i);// This returns a JSON or
			company_name = temp.companyName;
			cap = temp.cap;
			double change = 0;
			double open1 = 0;
			double o = 0;
			double h = 0;
			double l = 0;
			double c = 0;
			double v = 0;
			for (int j = 0; st[j] != null; j++) 
			{
				str = st[j].date;
				if (company_name.equals(st[j].companyName) && sDate.toString().equals(str)) {
					str = st[j].open;
					open1 = Double.parseDouble(str);
					System.out.println("\n  open1" + open1 + "str :" + str);
				}
				if (company_name.equals(st[j].companyName) && eDate.toString().equals(str)) {
					str = st[j].open;
					double open = Double.parseDouble(str);
					o = open;
					str = st[j].high;
					double high = Double.parseDouble(str);
					h = high;
					str = st[j].low;
					double low = Double.parseDouble(str);
					l = low;
					str = st[j].close;
					double close = Double.parseDouble(str);
					c = close;
					str = st[j].volume;
					double volume = Double.parseDouble(str);
					v = volume;
				}
			}
			change = ((c - open1) / open1) * 100;
			if (cap.equals("LARGE")) 
			{
				large[lg] = new StockDTO();
				large[lg].cid = i;
				large[lg].companyName = company_name;
				large[lg].percentChange = change;
				large[lg].cap = cap;
				large[lg].open = o;
				large[lg].high = h;
				large[lg].close = c;
				large[lg].low = l;
				large[lg].volume = v;
				lg++;
			}
			if (cap.equals("SMALL")) 
			{
				small[sm] = new StockDTO();
				small[sm].cid = i;
				small[sm].companyName = company_name;
				small[sm].percentChange = change;
				small[sm].cap = cap;
				small[sm].open = o;
				small[sm].high = h;
				small[sm].close = c;
				small[sm].low = l;
				small[sm].volume = v;
				sm++;
			}
			if (cap.equals("MID")) 
			{
				mid[md] = new StockDTO();
				mid[md].cid = i;
				mid[md].companyName = company_name;
				mid[md].percentChange = change;
				mid[md].cap = cap;
				mid[md].open = o;
				mid[md].high = h;
				mid[md].close = c;
				mid[md].low = l;
				mid[md].volume = v;
				md++;
			}

		}
		System.out.println("\n\n large cap companies ");
		int i = 0;
		for (i = 0; i < lg; i++) {
			System.out.println("\n Company name:" + large[i].companyName + " ------> Change:" + large[i].percentChange);
		}
		System.out.println("\n\n small cap companies ");
		
		for (i = 0; i < sm; i++) {
			System.out.println("\n Company name:" + small[i].companyName + " ------> Change:" + small[i].percentChange);
		}
		System.out.println("\n\n mid cap companies ");
		
		for (i = 0; i < md; i++) {
			System.out.println("\n Company name:" + mid[i].companyName + " ------> Change:" + mid[i].percentChange);
		}
		System.out.println("\n\n ");
		return "Done";
	}

	public String constructURL(String code, String sDate2, String eDate2) {
		return "https://www.quandl.com/api/v3/datasets/EOD/" + code + ".csv?start_date=" + sDate2 + "&end_date=" + eDate2
				+ "&api_key=b--k_vTGmHWc782UMm5E";
	}

	
	@RequestMapping(path = "/view-large-cap")
	public  StockDTO[] showLargeCap() {
		
		try
		{
			getStockdata();
			
		}catch(Exception ex ){
			
		}
		capitalization();
		
		int i = 0;
		int j = 0;
		int k=0 ;
		StockDTO temp = new StockDTO();
		StockDTO[] tempLarge = new StockDTO[5]; 
				
				
		// sort large cap
		for (i = 0; i < (lg - 1); i++) {
			for (j = 0; j < (lg - 1 - i); j++) {

				double n1 = large[j].percentChange;
				double n2 = large[j+1].percentChange;
				if (n1 < n2) {
					temp = large[j];
					large[j] = large[j + 1];
					large[j + 1] = temp;
				}
			}
		}
		System.out.println("\n ----lg = "+lg);
		for (i = 0; i < 5; i++) {
			System.out.println("Company name:" + large[i].companyName + "Change:" + large[i].percentChange);
			tempLarge[k]=large[i] ;
			k++;
			//l.append(large[i]);
		}
		lg=0;
		return tempLarge;
	}

	@GetMapping(path = "/view-small-cap")
	public StockDTO[] showSmallCap() {
		
		try
		{
			getStockdata();
			
		}catch(Exception ex ){
			
		}
		capitalization();
		
		int i = 0;
		int j = 0;
		int k = 0 ;
		StockDTO temp = new StockDTO();
		StockDTO[] tempSmall = new StockDTO[5]; 
		// sort small cap
		for (i = 0; i < (sm - 1); i++) {
			for (j = 0; j < (sm - 1 - i); j++) {
				
				double n1 = small[j].percentChange;
				double n2 = small[j+1].percentChange;
				if (n1 < n2) {
					temp = small[j];
					small[j] = small[j + 1];
					small[j + 1] = temp;
				}
			}
		}
		System.out.println("\n\n ----sm = "+sm);
		for (i = 0; i < sm; i++) {
			System.out.println("Company name:" + small[i].companyName + "Change:" + small[i].percentChange);
			tempSmall[k]=small[i] ;
			k++ ;
		}
		sm=0;
		return tempSmall;
	}

	@GetMapping(path = "/view-mid-cap")
	public StockDTO[] showMidCap() {
		
		try
		{
			getStockdata();
			
		}catch(Exception ex ){
			
		}
		capitalization();
		
		int i = 0;
		int j = 0;
		int k = 0 ;
		StockDTO temp = new StockDTO();
		StockDTO[] tempMid = new StockDTO[5]; 
		// sort mid cap
		for (i = 0; i < (md - 1); i++) {
			for (j = 0; j < (md - 1 - i); j++) {
				
				double n1 = mid[j].percentChange;
				double n2 = mid[j+1].percentChange;
				if (n1 < n2) {
					temp = mid[j];
					mid[j] = mid[j + 1];
					mid[j + 1] = temp;
				}
			}
		}
		System.out.println("\n\n ----md = "+md);
		for (i = 0; i > md; i++) {
			System.out.println("Company name:" + mid[i].companyName + "Change:" + mid[i].percentChange);
			tempMid[k]=mid[i] ;
			k++ ;
		}
		md=0;
		return mid;
	}


}
