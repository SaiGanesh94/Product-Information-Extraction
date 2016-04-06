package comcrawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
public class RakhisaleListCrawler extends ListCrawler{
	
	
	public void getProducts(String url) throws Exception {
		
		
			try {
		//		log.info("Fetching products from url: " + url);
				Document doc = Jsoup.connect(url)
						.userAgent(ListPageCrawler.userAgent).maxBodySize(0)
						.timeout(600000).get();
			
				
				File f=new File("E:\\e-book\\package\\PaginationURL.txt");
				File f1=new File("E:\\e-book\\package\\ProductNames.txt");
				//Pagination URL
				if(!f1.exists()){
					f1.createNewFile();
				}
				
					String str=doc.select("div[class=fright]").text();
					
					String[] split=str.split("of ");
					int itr=Integer.valueOf(split[1]);
					
					
					for(int i=1;i<=itr;i++){
						String paginationURL=url.replaceAll(".asp", "")+"/"+i+".asp";
					
						FileWriter fw=new FileWriter(f.getAbsoluteFile(),true);
						BufferedWriter bufferWritter = new BufferedWriter(fw);
						bufferWritter.write(paginationURL);
						bufferWritter.newLine();
						bufferWritter.close();
					}
	    	       
				
				Scanner s=new Scanner(f);
				
				while(s.hasNextLine()){
	    	        url=s.nextLine();
	    	        doc = Jsoup.connect(url)
							.userAgent(ListPageCrawler.userAgent).maxBodySize(0)
							.timeout(600000).get();
				
				for (Element elem : doc.select("div[class=left]")
						.select("div[class=box-shadow]")) {
				
					

					// Title
					String Title = elem.select("div").get(2).text();
					
					
					FileWriter fw=new FileWriter(f1.getAbsoluteFile(),true);
					
					BufferedWriter bufferWritter = new BufferedWriter(fw);
	    	        bufferWritter.write(Title);
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
					}
				}
				s.close();
			//	log.info("Products fetched: " + (index - 1));

			

		} catch (Exception e) {
		//	log.error(e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
}
