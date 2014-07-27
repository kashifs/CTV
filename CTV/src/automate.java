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

import javax.swing.JFileChooser;
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
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.XmlUtils;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.P.Hyperlink;

public class automate {

	private static final String strtAppDate = "<td><span class=\"ncDetailLabel\">Application Date: </span></td> \n               <td>";
	private static final String strtInventors = "<td class=\"ncDetailLabel\">Inventors: </td> \n               <td class=\"ncDetailText\"><span class=\"notranslate\">";
	private static final String strtApplicants = "<td class=\"ncDetailLabel\">Applicants: </td> \n               <td class=\"ncDetailText\"><span class=\"notranslate\">";

	private static String wholePage;

	private static WordprocessingMLPackage wordMLPackage;
	private static ObjectFactory factory;

	private static String url;

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

	public static void main(String[] args) throws IOException, Docx4JException {

		// url =
		// "http://patentscope.wipo.int/search/en/detail.jsf?docId=WO2012136993";
		url = "http://patentscope.wipo.int/search/en/detail.jsf?docId=US74058396";
		// url =
		// "http://patentscope.wipo.int/search/en/detail.jsf?docId=EP96220449";
		// url =
		// "http://patentscope.wipo.int/search/en/detail.jsf?docId=US76429465";
		// url =
		// "http://patentscope.wipo.int/search/en/detail.jsf?docId=US73463068";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

		wholePage = doc.toString();

		String title = doc.title();
		String patNum = title.substring(0, title.indexOf(' '));
		String invName = title.substring(title.indexOf(' ') + 1);

		if (patNum.charAt(0) == '0')
			patNum = patNum.substring(1);

		String patentColumnNum = "US" + patNum;

		int start = wholePage.indexOf(strtAppDate);
		int skip = strtAppDate.length();

		String appDate = wholePage
				.substring(start + skip, start + skip + 1 + 9);

		String day = appDate.split("\\.")[0];
		String month = appDate.split("\\.")[1];
		String year = appDate.split("\\.")[2];

		String patentColumnDate = getMonth(month) + " " + day + ", " + year
				+ "\n";

		start = wholePage.indexOf(strtInventors);
		skip = strtInventors.length();

		start = wholePage.indexOf(strtApplicants);
		skip = strtApplicants.length();

		Element descriptionElement = doc.select("meta[name=description]")
				.get(1);
		String description = descriptionElement.getAllElements().toString();

		description = description.split("&gt;")[1];
		description = description.substring(0, description.length() - 6);

		String fileName = null;

		// JFileChooser fileChooser = new JFileChooser();
		// FileNameExtensionFilter filter = new FileNameExtensionFilter(
		// ".docx files", "docx");
		// fileChooser.setFileFilter(filter);
		// fileChooser.setCurrentDirectory(new File(System
		// .getProperty("user.home")));
		// int result = fileChooser.showOpenDialog(null);
		// if (result == JFileChooser.APPROVE_OPTION) {
		// File selectedFile = fileChooser.getSelectedFile();
		// fileName = selectedFile.getAbsolutePath();
		// }

		fileName = "/Users/kashif/Desktop/IR-Assessment-CU15002_20140717.docx";

		// if (fileName != null) {
		wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File(fileName));

		Hyperlink link = createHyperlink(wordMLPackage, url, patentColumnNum);

		factory = Context.getWmlObjectFactory();

		MainDocumentPart mp = wordMLPackage.getMainDocumentPart();
		Styles styles = mp.getStyleDefinitionsPart().getJaxbElement();
		changeNormalFont(styles, factory, "Arial");

		List<Object> tables = getAllElementFromObject(
				wordMLPackage.getMainDocumentPart(), Tbl.class);

		Tr tableRow = factory.createTr();

		addFirstColumn(tableRow, patentColumnNum, patentColumnDate, link);
		addSecondColumn(tableRow, wholePage.indexOf(strtInventors),
				strtInventors.length(), wholePage.indexOf(strtApplicants),
				strtApplicants.length());

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
					+ // TODO: enable this style in the document!
					"</w:rPr>" + "<w:t>" 
					+ patentColumnNum + "</w:t>" + "</w:r>"
					+ "</w:hyperlink>";

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

	private static void addSecondColumn(Tr tableRow, int invStart, int invSkip,
			int appStart, int appSkip) {
		Tc tableCell = factory.createTc();

		P spc = factory.createP();
		R rspc = factory.createR();

		Text invTitle = factory.createText();
		Br br = factory.createBr();

		invTitle.setValue("Inventor(s):");

		rspc.getContent().add(invTitle);
		rspc.getContent().add(br);

		String names = wholePage.substring(invStart + invSkip, invStart + 200);
		String[] nameArray = names.split("<br />");

		Text nextName = factory.createText();

		for (int i = 0; i < nameArray.length - 1; i++) {
			nextName = factory.createText();
			nextName.setValue(nameArray[i]);
			rspc.getContent().add(nextName);
			rspc.getContent().add(br);
		}

		rspc.getContent().add(br);

		Text appTitleText = factory.createText();
		appTitleText.setValue("Applicants(s):");

		rspc.getContent().add(appTitleText);
		rspc.getContent().add(br);

		names = wholePage.substring(appStart + appSkip, appStart + 200);
		nameArray = names.split("<br />");

		for (int i = 0; i < nameArray.length - 1; i++) {
			nextName = factory.createText();
			nextName.setValue(nameArray[i]);
			rspc.getContent().add(nextName);

			if (i != nameArray.length - 2)
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
