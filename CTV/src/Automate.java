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
import java.util.List;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBElement;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.XmlUtils;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.P.Hyperlink;
import org.docx4j.wml.Tc;

import antlr.collections.impl.Vector;

public class Automate {

	private static WordprocessingMLPackage wordMLPackage;
	private static ObjectFactory factory;

	private static String url;
	private static String fileName;
	private static Vector invNames, assignNames;
	private static boolean isGranted;

	private static Tbl appendixII, mainTable;

	private static String patentNumber, filingDate, invName, description;

	private static String irNum, tloInitials, dateReceived, ogcInitials,
			userInitials, stageOfDev;

	private static TreeSet<String> keywords;
	private static TreeSet<String> categories;


	private static List<Object> getAllElementsFromObject(Object obj,
			Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		if (obj instanceof JAXBElement)
			obj = ((JAXBElement<?>) obj).getValue();

		if (obj.getClass().equals(toSearch))
			result.add(obj);
		else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementsFromObject(child, toSearch));
			}
		}
		return result;
	}

	private static boolean isNotGooglePatentLink(String input) {
		if (input == null)
			System.exit(0);

		if (input.startsWith("https://www.google.com/patents/"))
			return false;
		else
			return true;
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

		if (isGranted)
			numTitle.setValue("US Patent #:");
		else
			numTitle.setValue("US Patent Application #:");

		numText.setValue(patentColumnNum);

		dateTitle.setValue("Filing date: ");
		dateText.setValue(patentColumnDate);

		rspcTop.getContent().add(numTitle);
		rspcTop.getContent().add(br);

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

	private static void populateMainTable() {
		populateIRNum();
		populateTLOInitials();
		populateDateReceived();
		populateUserInitials();
		populateOGC();
		populateKeywords();
		populateCategories();
		populateStageOfDevelopment();

	}

	private static void populateIRNum() {
		fillRowColumn(0, 1, irNum);
	}

	private static void populateTLOInitials() {
		fillRowColumn(0, 3, tloInitials);
	}

	private static void populateDateReceived() {
		fillRowColumn(1, 1, dateReceived);
	}

	private static void populateOGC() {
		fillRowColumn(1, 3, ogcInitials);
	}

	private static void populateUserInitials() {
		fillRowColumn(2, 3, userInitials);
	}

	private static void populateKeywords() {
		StringBuilder sb = new StringBuilder();

		for (String s : keywords) {
			if (!s.equals("HIV")) // TODO list cap words
				sb.append(s.toLowerCase() + ", ");
		}

		String all_keywords = null;

		if (sb.length() > 0)
			all_keywords = sb.substring(0, (sb.length() - 2));

		fillRowColumn(4, 1, all_keywords);
	}

	private static void populateCategories() {
		Tc tableCell = getRowColumn(5, 1);

		tableCell.getContent().clear();

		P spc = factory.createP();
		R rspc = factory.createR();

		Br br = factory.createBr();

		Text nextCateg = factory.createText();

		int cutoff = categories.size() - 1;
		int index = 0;

		for (String s : categories) {
			System.out.println("Category: " + s);
			nextCateg = factory.createText();
			nextCateg.setValue(s);
			rspc.getContent().add(nextCateg);
			if (index != cutoff)
				rspc.getContent().add(br);
			index++;
		}

		spc.getContent().add(rspc);
		tableCell.getContent().add(spc);
	}

	private static void populateStageOfDevelopment() {
		fillRowColumn(6, 1, stageOfDev);
	}

	private static Tc getRowColumn(int rowNum, int colNum) {
		List rows = mainTable.getContent();
		Tr row = (Tr) rows.get(rowNum);
		List cells = row.getContent();

		Tc tc = (Tc) XmlUtils.unwrap(cells.get(colNum));

		return tc;
	}

	private static void fillRowColumn(int rowNum, int colNum, String value) {
		Tc tc = getRowColumn(rowNum, colNum);

		tc.getContent().clear();

		R rspc = factory.createR();
		P spc = factory.createP();

		Text text = factory.createText();
		text.setValue(value);
		rspc.getContent().add(text);
		spc.getContent().add(rspc);

		tc.getContent().add(spc);
	}

	private static void populateAppendixII() {
		Hyperlink link = createHyperlink(wordMLPackage, url, patentNumber);
		Tr tableRow = factory.createTr();

		addFirstColumn(tableRow, patentNumber, filingDate, link);
		addSecondColumn(tableRow);

		addColumn(tableRow, invName);
		addColumn(tableRow, description);

		appendixII.getContent().add(tableRow);

	}

	private static void getUserLink() {
		String googleLink;
		do {
			googleLink = JOptionPane
					.showInputDialog(null, "Google patent link?", "Link",
							JOptionPane.QUESTION_MESSAGE);
		} while (isNotGooglePatentLink(googleLink));

		url = googleLink;
	}

	private static void getIRDocument() throws Docx4JException {
		fileName = null;

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

		if (fileName == null) {
			System.err.println("Could not open file.");
			System.exit(1);
		}

		// TODO add this line back in production
		// wordMLPackage = WordprocessingMLPackage
		// .load(new java.io.File(fileName));

	}

	private static void changeNormalFont(Styles styles, ObjectFactory factory,
			String string) {
		for (Style s : styles.getStyle()) {
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

	private static String promptIRNumber() {
		irNum = JOptionPane.showInputDialog(null, "IR Number?", "IR Number",
				JOptionPane.QUESTION_MESSAGE);

		irNum = irNum.toUpperCase();

		return irNum;
	}

	private static boolean isNotCorrectInitials(String userInitials) {
		if (userInitials == null)
			return true;

		boolean correctLength = (userInitials.length() == 3)
				|| (userInitials.length() == 2);

		return !userInitials.matches("[a-zA-Z]+") || !correctLength;
	}

	private static String promptUserInitials() {

		do {
			userInitials = JOptionPane.showInputDialog(null,
					"What are your initials?", "Your Initials",
					JOptionPane.QUESTION_MESSAGE);
		} while (isNotCorrectInitials(userInitials));

		if (userInitials.length() == 2) {
			userInitials = userInitials.charAt(0) + "X"
					+ userInitials.charAt(1);
		}

		userInitials = userInitials.toUpperCase();

		return userInitials;
	}

	private static String promptTLOInitials() {

		do {
			tloInitials = JOptionPane.showInputDialog(null,
					"What are the TLO's initials?", "TLO Initials",
					JOptionPane.QUESTION_MESSAGE);
		} while (isNotCorrectInitials(tloInitials));

		tloInitials = tloInitials.toUpperCase();

		return tloInitials;
	}

	private static String promptDateReceived() {

		do {
			dateReceived = JOptionPane.showInputDialog(null,
					"When was the IR received?", "Date Received",
					JOptionPane.QUESTION_MESSAGE);
		} while (dateReceived == null);

		return dateReceived;
	}

	private static void populatePatentInformation() throws IOException {

		url = "https://www.google.com/patents/US20120015839";
		// url = "https://www.google.com/patents/US20120202214";
		// url = "https://www.google.com/patents/US8352194";

		PatentInformation pInfo = new PatentInformation(url);

		invName = pInfo.getInvName();
		filingDate = pInfo.getFilingDate();
		isGranted = pInfo.isPatentGranted();
		patentNumber = pInfo.getPatentNumber();
		description = pInfo.getDescription();

		invNames = pInfo.getInventorNames();
		assignNames = pInfo.getAssigneeNames();
	}

	private static String promptUserOGC() {
		String[] choices = { "physical sciences or medical devices",
				"life sciences" };
		String input = (String) JOptionPane.showInputDialog(null,
				"What type of technology is it?", "Office of General Council",
				JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

		if (input.equals("life sciences"))
			ogcInitials = "GM/TB";
		else {
			ogcInitials = "JS";
		}

		return ogcInitials;
	}

	private static String promptStageOfDevelopment() {
		String[] choices = { "Basic R&D", "Clinical", "FDA Approval",
				"Mfg. Prototype", "On Market", "Preclinical", "In vivo data",
				"In vitro data", "Proposal", "Target only", "Working software" };
		stageOfDev = (String) JOptionPane.showInputDialog(null,
				"What is the stage of development?", "Stage of Development",
				JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

		return stageOfDev;
	}

	public static void main(String[] args) throws IOException, Docx4JException {

		// getUserLink();

		populatePatentInformation();

		promptIRNumber();
		promptTLOInitials();
		promptDateReceived();
		promptUserInitials();
		// promptDateReceived();
		promptUserOGC();
		promptStageOfDevelopment();

		keywords = new TreeSet<String>();
		new KeywordsPrompt(keywords);
		
		categories = new TreeSet<String>();
		new CategoriesPrompt(categories);

		fileName = "/Users/kashif/Desktop/IR-Assessment-CUXXXX_YYYYMMDD.docx";

		wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(fileName));
		factory = Context.getWmlObjectFactory();
		MainDocumentPart mp = wordMLPackage.getMainDocumentPart();

		Styles styles = mp.getStyleDefinitionsPart().getJaxbElement();
		changeNormalFont(styles, factory, "Arial");

		List<Object> tables = getAllElementsFromObject(
				wordMLPackage.getMainDocumentPart(), Tbl.class);

		mainTable = (Tbl) tables.get(0);
		populateMainTable();

		appendixII = (Tbl) tables.get(1);
		populateAppendixII();

		// String newFileName = System.getProperty("user.home") +
		// "/Desktop/IR-Assessment-"
		// + irNum + "_" + "XXXXXXXX_1.docx";

		String newFileName = System.getProperty("user.home")
				+ "/Desktop/work.docx";

		wordMLPackage.save(new java.io.File(newFileName));
	}
}
