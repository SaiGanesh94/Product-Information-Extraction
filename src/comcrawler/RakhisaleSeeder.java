package comcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class RakhisaleSeeder extends ListCrawler{

	
	
	public void getProducts(String url) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<String> urlList = new ArrayList<String>();
		try {
		//	log.info("Crawling page to identify pagination urls");
			Document doc = Jsoup.connect(url).userAgent(ListPageCrawler.userAgent)
					.maxBodySize(0).timeout(600000).get();
			File f=new File("E:\\e-book\\package\\SeedURL.txt");
			if(!f.exists()){
				f.createNewFile();
			}
			for(Element elem: doc.select("div[class=navmenu]").select("li")){
			
				String Url=elem.select("a").attr("href");
				System.out.println("URL:"+Url);
				if(Url.contains("http")){
				FileWriter fw=new FileWriter(f.getAbsoluteFile(),true);
				
				BufferedWriter bufferWritter = new BufferedWriter(fw);
    	        bufferWritter.write(Url);
    	        bufferWritter.newLine();
    	        bufferWritter.close();
				}
			}
			 
		//	log.info("Pagination urls identified - " + urlList.size());
		} catch (Exception ex) {
			System.out.println("error Messgae outside : " + ex.getMessage());
		//	log.error(ex.getMessage(), ex);
			throw ex;
		}
	
		
	
	}
}
