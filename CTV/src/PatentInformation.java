/**
 * 
 * @author Kashif Smith 
 * main method for taking a US patent number and inputting the 
 *		   correctly formatted data.
 */

import java.io.IOException;
import java.util.TreeSet;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.docx4j.wml.ObjectFactory;
import antlr.collections.impl.Vector;

public class PatentInformation {

	private static String url;
	private static Vector invNames, assignNames;
	private static boolean isGranted;
	private static String patentNumber, filingDate, invName, description;

	public PatentInformation(String userUrl) throws IOException {
		url = userUrl;
		
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

		Elements titleElement = doc.select("meta[name=DC.title]");
		invName = titleElement.get(0).attr("content");

		Elements nameElement = doc.select("meta[name=DC.contributor]");

		invNames = new Vector();
		assignNames = new Vector();

		for (Element element : nameElement) {
			if (element.attr("scheme").equalsIgnoreCase("inventor"))
				invNames.appendElement(element.attr("content"));
			else
				assignNames.appendElement(element.attr("content"));
		}

		Elements descriptions = doc.select("meta[name=DC.description]");
		description = descriptions.get(0).attr("content");
		Elements row = doc.select(".single-patent-bibdata");

		patentNumber = row.get(0).text().split(" ")[0];
		filingDate = row.get(4).text();

		isGranted = false;
		if (row.get(1).text().equalsIgnoreCase("grant"))
			isGranted = true;
	}

	public String getInvName() {
		return invName;
	}

	public Vector getInventorNames() {
		return invNames;
	}

	public Vector getAssigneeNames() {
		return assignNames;
	}

	public String getDescription() {
		return description;
	}

	public String getPatentNumber() {
		return patentNumber;
	}

	public String getFilingDate() {
		return filingDate;
	}

	public boolean isPatentGranted() {
		return isGranted;
	}


	public static void main(String[] args) throws IOException {
		String url;
		url = "https://www.google.com/patents/US20120015839";
		// url = "https://www.google.com/patents/US20120202214";
		// url = "https://www.google.com/patents/US8352194";

		PatentInformation pi = new PatentInformation(url);


		System.out.println("Patent Number: " + pi.getPatentNumber());
		System.out.println("Is patent granted: " + pi.isPatentGranted());
		System.out.println("Invention name: " + pi.getInvName());
		System.out.println("Description: " + pi.getDescription());
		System.out.println("Filing Date: " + pi.getFilingDate());

		Vector invNames = pi.getInventorNames();
		System.out.println("Inventor Names:");
		for (int i = 0; i < invNames.size(); i++)
			System.out.println(invNames.elementAt(i));

		Vector assignNames = pi.getAssigneeNames();
		System.out.println("Assignee Names:");
		for (int i = 0; i < assignNames.size(); i++)
			System.out.println(assignNames.elementAt(i));

	}
}
