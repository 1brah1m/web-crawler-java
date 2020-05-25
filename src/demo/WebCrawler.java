package demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonObject;

public class WebCrawler {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Document doc = null; 
		try {
			doc = Jsoup.connect("https://www.mcc-mnc.com/").get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element table = doc.getElementById("mncmccTable");
		
		Elements data = table.getElementsByTag("td");
		
		WebCrawler obj = new WebCrawler();  
		
		String dataString = ""; 
		
		for(int i=0; i<data.size(); i+=6) {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("MCC", data.get(i).html());
			jsonObj.addProperty("MNC", data.get(i+1).html());
			jsonObj.addProperty("ISO", data.get(i+2).html());
			jsonObj.addProperty("Country", data.get(i+3).html());
			jsonObj.addProperty("Country Code", data.get(i+4).html());
			jsonObj.addProperty("Network", data.get(i+5).html());
			
			//System.out.println(jsonObj.toString());
			
			dataString += jsonObj.toString() + "\n"; 
		}
		
		OutputStream os = null;
        try {
            os = new FileOutputStream(new File("C:\\Users\\HP\\Desktop\\data.json"));
            os.write(dataString.getBytes(), 0, dataString.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
		
	}

}

