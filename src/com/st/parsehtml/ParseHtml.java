package com.st.parsehtml;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author frank
 * Class ParseHtml takes in an HTML file and parses for table columns using
 * jsoup to extract the DOM.  
 * Once it has this it looks for the first and fifth columns that contain the repo name and time 
 * change.
 * Anything over 4 weeks is deemed to be over 30 days, and we assume months contain 30 days
 * Output is the list of repos and the time since last change in days.
 *
 */
public class ParseHtml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length != 1) {
			System.out.println("Usage: parsehtml <filename>");
			return;
		}
		
		ParseHtml parse = new ParseHtml();
		parse.processRepoFile(args[0]);
	}

	public void processRepoFile(String fileName) {
		File input = new File(fileName);
		try {
			Document doc = Jsoup.parse(input, "ISO-8859-1", "http://example.com/");
			
			Elements table = doc.getElementsByTag("table");
			Elements rows = table.get(0).getElementsByTag("tr");
			for (Element row : rows) {
				Elements cols = row.getElementsByTag("td");
				if (cols.size() == 6) {
					String repo = cols.get(0).getElementsByTag("a").get(0).text();
					String date = cols.get(4).getElementsByTag("span").get(0).text();
					if (tooLong(date)) {
						int days = getNumberOfDays(date);
   				  		System.out.println("Repo "+repo+": " + days + " days since last change");
					}
				}
			}
		} catch (IOException e) {
		    System.err.format("IOException: %s%n", e);
		}

	}
	public String processHtml(String html) {
		
		
		Document doc = Jsoup.parse(html);
		Elements reps = doc.getElementsByClass("list");
		String res = null;
		if (!reps.isEmpty()) {
			Elements times = doc.getElementsByClass("age4");
			if (!times.isEmpty()) {
				res = reps.get(0) + " : " + times.get(0);
			}
		}
		return res;
	}
	
	private boolean tooLong(String dt) {
		if (dt.contains("months")  || dt.contains("years"))
			return true;
		else {
			int num = Integer.parseInt(dt.split(" ")[0]);
			if (dt.contains("weeks") && num > 4) 
				return true;
		}
			
		return false;
	}

	private int getNumberOfDays(String dt) {
		int days=0;
		int num = Integer.parseInt(dt.split(" ")[0]);
		if (dt.contains("months"))
			days = num * 30;
		else if (dt.contains("weeks"))
			days = num * 7;
			
		return days;
	}

}
