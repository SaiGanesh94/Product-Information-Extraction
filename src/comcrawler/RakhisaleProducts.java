package comcrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RakhisaleProducts extends ListCrawler  {
public void getProducts() throws Exception{
		
		File f=new File("E:\\e-book\\package\\SeedURL.txt");
		File f1=new File("E:\\e-book\\package\\PaginationURL.txt");
		if(!f1.exists()){
			f1.createNewFile();
		}
		Scanner s=new Scanner(f);
		RakhisaleListCrawler r=new RakhisaleListCrawler();
		
		while(s.hasNextLine()){
			String URL=s.nextLine();
			System.out.println(URL);
			r.getProducts(URL);
		}
		
}



	
}
