/**
 * 
 * @author Kashif Smith 
 * main method for taking a US patent number and inputting the 
 *		   correctly formatted data.
 */

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBElement;

import org.apache.poi.hwpf.usermodel.Paragraph;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.XmlUtils;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.P.Hyperlink;

import antlr.collections.impl.Vector;

public class automate {

	private static WordprocessingMLPackage wordMLPackage;
	private static ObjectFactory factory;

	private static String url;

	private static Vector invNames, assignNames;

	public static String getMonth(String month) {

		if (month.equals("01")) {
			return "Jan";
		} else if (month.equals("02")) {
			return "Feb";
		} else if (month.equals("03")) {
			return "Mar";
		} else if (month.equals("04")) {
			return "Apr";
		} else if (month.equals("05")) {
			return "May";
		} else if (month.equals("06")) {
			return "Jun";
		} else if (month.equals("07")) {
			return "Jul";
		} else if (month.equals("08")) {
			return "Aug";
		} else if (month.equals("09")) {
			return "Sep";
		} else if (month.equals("10")) {
			return "Oct";
		} else if (month.equals("11")) {
			return "Nov";
		} else if (month.equals("12")) {
			return "Dec";
		} else {
			return "XXXXXXXXXXXXX";
		}

	}

	private static List<Object> getAllElementFromObject(Object obj,
			Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement)
			obj = ((JAXBElement<?>) obj).getValue();

		if (obj.getClass().equals(toSearch))
			result.add(obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}
		}
		return result;
	}
	
	
		
	private static boolean isNotGooglePatentLink(String input) {
		if(input == null)
			System.exit(0);
		
		if(input.startsWith("https://www.google.com/patents/"))
			return false;
		else 
			return true;
	}

	public static void main(String[] args) throws IOException, Docx4JException {

		// url = "https://www.google.com/patents/WO2012015801A1";
		url = "https://www.google.com/patents/US20120202214";
		
		String googleLink;
		do {
			googleLink = JOptionPane.showInputDialog(null,
					"Google patent link?", "Link",
					JOptionPane.QUESTION_MESSAGE);
		} while (isNotGooglePatentLink(googleLink));

		url = googleLink;

		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

		Elements titleElement = doc.select("meta[name=DC.title]");
		String invName = titleElement.get(0).attr("content");

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
		String description = descriptions.get(0).attr("content");

		Elements patnum = doc
				.select("meta[name=citation_patent_publication_number]");
		String patentNumber = patnum.get(0).attr("content"); //format: US:[patentnum]:A1
		patentNumber = patentNumber.split(":")[0] + patentNumber.split(":")[1];

		// Elements info = doc.select("table#viewport_table");

		Elements row = doc.select(".single-patent-bibdata");
		String filingDate = row.get(4).text();

		// System.out.println("Filing date: " + row.get(4).text());

		String fileName = null;

		JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".docx files", "docx");
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(new File(System
				.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			fileName = selectedFile.getAbsolutePath();
		}

		fileName = "/Users/kashif/Desktop/IR-Assessment-CU15002_20140717.docx";

		// if (fileName != null) {
		wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(fileName));

		Hyperlink link = createHyperlink(wordMLPackage, url, patentNumber);

		factory = Context.getWmlObjectFactory();

		MainDocumentPart mp = wordMLPackage.getMainDocumentPart();
		Styles styles = mp.getStyleDefinitionsPart().getJaxbElement();
		changeNormalFont(styles, factory, "Arial");

		List<Object> tables = getAllElementFromObject(
				wordMLPackage.getMainDocumentPart(), Tbl.class);

		Tr tableRow = factory.createTr();

		addFirstColumn(tableRow, patentNumber, filingDate, link);
		addSecondColumn(tableRow);

		addColumn(tableRow, invName);
		addColumn(tableRow, description);

		Tbl Appendix2 = (Tbl) tables.get(1);
		Appendix2.getContent().add(tableRow);

		String newFileName = fileName.substring(0, fileName.length() - 5)
				+ "_1.docx";

		wordMLPackage.save(new java.io.File(newFileName));
		// } else {
		// System.out.println("Could not find file.");
		// return;
		// }

	}

	private static void changeNormalFont(Styles styles, ObjectFactory factory,
			String string) {
		for (Style s : styles.getStyle()) {
			// System.out.println(s.getStyleId());
			// System.out.println(s.getName());
			if (s.getName().getVal().equals("Normal")) {
				RPr rpr = s.getRPr();
				if (rpr == null) {
					rpr = factory.createRPr();
					s.setRPr(rpr);
				}
				RFonts rf = rpr.getRFonts();
				if (rf == null) {
					rf = factory.createRFonts();
					rpr.setRFonts(rf);
					HpsMeasure size = new HpsMeasure();
					size.setVal(BigInteger.valueOf(20));
					rpr.setSz(size);
				}
				rf.setAscii(string);
			}
		}

	}

	public static Hyperlink createHyperlink(
			WordprocessingMLPackage wordMLPackage, String url,
			String patentColumnNum) {

		try {

			// We need to add a relationship to word/_rels/document.xml.rels
			// but since its external, we don't use the
			// usual wordMLPackage.getMainDocumentPart().addTargetPart
			// mechanism
			org.docx4j.relationships.ObjectFactory factory = new org.docx4j.relationships.ObjectFactory();

			org.docx4j.relationships.Relationship rel = factory
					.createRelationship();
			rel.setType(Namespaces.HYPERLINK);
			rel.setTarget(url);
			rel.setTargetMode("External");

			wordMLPackage.getMainDocumentPart().getRelationshipsPart()
					.addRelationship(rel);

			// addRelationship sets the rel's @Id

			String hpl = "<w:hyperlink r:id=\""
					+ rel.getId()
					+ "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
					+ "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" >"
					+ "<w:r>" + "<w:rPr>" + "<w:rStyle w:val=\"Hyperlink\" />" 
					+ "</w:rPr>" + "<w:t>" + patentColumnNum + "</w:t>"
					+ "</w:r>" + "</w:hyperlink>";

			return (Hyperlink) XmlUtils.unmarshalString(hpl, Context.jc,
					P.Hyperlink.class);
			// return (Hyperlink)XmlUtils.unmarshalString(hpl);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void addFirstColumn(Tr tableRow, String patentColumnNum,
			String patentColumnDate, Hyperlink link) {
		Tc tableCell = factory.createTc();

		P spc = factory.createP();
		R rspcTop = factory.createR();
		R rspcBot = factory.createR();

		Text numTitle = factory.createText();
		Text numText = factory.createText();

		Text dateTitle = factory.createText();
		Text dateText = factory.createText();
		Br br = factory.createBr();

		if (patentColumnNum.length() == 9)
			numTitle.setValue("US Patent #:");
		else
			numTitle.setValue("US Patent Application #:");

		numText.setValue(patentColumnNum);

		dateTitle.setValue("Filing date: ");
		dateText.setValue(patentColumnDate);

		rspcTop.getContent().add(numTitle);
		rspcTop.getContent().add(br);
		// rspcTop.getContent().add(numText);

		rspcBot.getContent().add(br);
		rspcBot.getContent().add(br);

		rspcBot.getContent().add(dateTitle);
		rspcBot.getContent().add(br);
		rspcBot.getContent().add(dateText);

		spc.getContent().add(rspcTop);
		spc.getContent().add(link);
		spc.getContent().add(rspcBot);
		tableCell.getContent().add(spc);

		tableRow.getContent().add(tableCell);
	}

	private static void addSecondColumn(Tr tableRow) {
		Tc tableCell = factory.createTc();

		P spc = factory.createP();
		R rspc = factory.createR();

		Text invTitle = factory.createText();
		Br br = factory.createBr();

		invTitle.setValue("Inventor(s):");

		rspc.getContent().add(invTitle);
		rspc.getContent().add(br);

		Text nextName = factory.createText();

		for (int i = 0; i < invNames.size(); i++) {
			nextName = factory.createText();
			nextName.setValue((String) invNames.elementAt(i));
			rspc.getContent().add(nextName);
			rspc.getContent().add(br);
		}

		rspc.getContent().add(br);

		Text appTitleText = factory.createText();
		appTitleText.setValue("Applicants(s):");

		rspc.getContent().add(appTitleText);
		rspc.getContent().add(br);

		for (int i = 0; i < assignNames.size(); i++) {
			nextName = factory.createText();
			nextName.setValue((String) assignNames.elementAt(i));
			rspc.getContent().add(nextName);

			if (i != assignNames.size() - 1)
				rspc.getContent().add(br);
		}

		spc.getContent().add(rspc);
		tableCell.getContent().add(spc);

		tableRow.getContent().add(tableCell);
	}

	private static void addColumn(Tr tableRow, String content) {
		Tc tableCell = factory.createTc();

		StringBuilder sb = new StringBuilder(content);

		for (int i = 1; i < content.length(); i++) {
			char c = sb.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.setCharAt(i, Character.toLowerCase(c));
			}
		}

		tableCell.getContent().add(
				wordMLPackage.getMainDocumentPart().createParagraphOfText(
						sb.toString()));

		tableRow.getContent().add(tableCell);
	}
}
