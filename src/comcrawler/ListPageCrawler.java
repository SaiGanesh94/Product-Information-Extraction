/**
 * 
 */
package comcrawler;

import com.ranking.VSM;


/**
 * @author Shanmugam
 * 
 */
public class ListPageCrawler {

	public final static String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36";
	

	public static ListCrawler getSeeder(String source) throws Exception {

		try {
			if(source.equalsIgnoreCase("rakhisaleSeeder"))
				return  new RakhisaleSeeder();
			else if(source.equalsIgnoreCase("rakhisalecrawler"))
				return new RakhisaleListCrawler();
			else if(source.equalsIgnoreCase("RakhisaleProducts"))
				return  new RakhisaleProducts();
			else if(source.equalsIgnoreCase("vsm"))
				return new VSM();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

}
