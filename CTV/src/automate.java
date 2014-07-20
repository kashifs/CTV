
/**
 * 
 * @author Kashif Smith main method for taking a US patent number and inputting the 
 *		   correctly formatted data.
 */




import java.io.IOException;
import java.math.BigInteger;


import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.docx4j.openpackaging.parts.relationships.Namespaces;

import org.docx4j.XmlUtils;
import org.docx4j.wml.P;
import org.docx4j.wml.P.Hyperlink;


public class automate {
	
	private static final String strtAppDate = "<td><span class=\"ncDetailLabel\">Application Date: </span></td> \n               <td>";
	private static final String strtInventors = "<td class=\"ncDetailLabel\">Inventors: </td> \n               <td class=\"ncDetailText\"><span class=\"notranslate\">";
	private static final String strtApplicants = "<td class=\"ncDetailLabel\">Applicants: </td> \n               <td class=\"ncDetailText\"><span class=\"notranslate\">";
	
	private static String wholePage;

	
	private static WordprocessingMLPackage  wordMLPackage;
    private static ObjectFactory factory;
    
    private static String url;

	public static String getMonth(String month)  {
		
		if (month.equals("01")){
			return "Jan";
		} else if (month.equals("02")){
			return "Feb";
		} else if (month.equals("03")){
			return "Mar";
		} else if (month.equals("04")){
			return "Apr";
		} else if (month.equals("05")){
			return "May";
		} else if (month.equals("06")){
			return "Jun";
		} else if (month.equals("07")){
			return "Jul";
		} else if (month.equals("08")){
			return "Aug";
		} else if (month.equals("09")){
			return "Sep";
		} else if (month.equals("10")){
			return "Oct";
		} else if (month.equals("11")){
			return "Nov";
		} else if (month.equals("12")){
			return "Dec";
		} else {
			return "XXXXXXXXXXXXX";
		}
	    
	}
	
	
    
    /**
     *  In this method we'll add the borders to the table.
     */
    private static void addBorders(Tbl table) {
        table.setTblPr(new TblPr());
        CTBorder border = new CTBorder();
        border.setColor("auto");
        border.setSz(new BigInteger("4"));
        border.setSpace(new BigInteger("0"));
        border.setVal(STBorder.SINGLE);
        
 
        TblBorders borders = new TblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }
 
	public static void main(String[] args) throws IOException, Docx4JException {
		
//		url = "http://patentscope.wipo.int/search/en/detail.jsf?docId=WO2012136993";
//		url = "http://patentscope.wipo.int/search/en/detail.jsf?docId=US74058396";
		url = "http://patentscope.wipo.int/search/en/detail.jsf?docId=US76429465";
//		url = "http://patentscope.wipo.int/search/en/detail.jsf?docId=US73463068";

		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
				
		wholePage = doc.toString();

		
		
		String title = doc.title();
		String patNum = title.substring(0, title.indexOf(' '));
		String invName =  title.substring(title.indexOf(' ') + 1);


		if(patNum.charAt(0) == '0')
			patNum = patNum.substring(1);
		
		String patentColumnNum;
		
		if(patNum.length() == 7) {
			patentColumnNum = "US" + patNum;
			System.out.println(patentColumnNum);
		}
		else {
			patentColumnNum = "US Patent Application #:\n" + "US" + patNum;
			System.out.println(patentColumnNum);
		}
		
		
		int start = wholePage.indexOf(strtAppDate);
		int skip = strtAppDate.length();


		String appDate = wholePage.substring(start + skip, start + skip + 1 + 9);
		
		
		String day = appDate.split("\\.")[0];
		String month = appDate.split("\\.")[1];
		String year = appDate.split("\\.")[2];
		
		String patentColumnDate = getMonth(month) + " " + day + ", " + year + "\n";
		System.out.println(patentColumnDate);

		
		
		start = wholePage.indexOf(strtInventors);
		skip = strtInventors.length();	
		
		System.out.println("Inventor(s):");
		getNames(start, skip);
		

		start = wholePage.indexOf(strtApplicants);
		skip = strtApplicants.length();
		
		System.out.println("Assignee(s):");
		getNames(start, skip);

				
		System.out.println(invName + "\n");

		Element descriptionElement = doc.select("meta[name=description]").get(1);
		
		String description = descriptionElement.getAllElements().toString();
		
		description = description.split("&gt;")[1];
		description = description.substring(0, description.length() - 6);
		
		System.out.println(description);
		
		
		wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.LETTER, true);
        factory = Context.getWmlObjectFactory();
 
        Tbl table = factory.createTbl();
        Tr tableRow = factory.createTr();
        
        addFirstColumn(tableRow, patentColumnNum, patentColumnDate);
        addSecondColumn(tableRow, wholePage.indexOf(strtInventors), strtInventors.length(),
        							wholePage.indexOf(strtApplicants), strtApplicants.length());
        
        addColumn(tableRow, invName, 2690);
        addColumn(tableRow, description, 5150);
 
 
        table.getContent().add(tableRow);
        addBorders(table);
        
 
        wordMLPackage.getMainDocumentPart().addObject(table);
        wordMLPackage.save(new java.io.File("/Users/kashif/Desktop/" + invName.replace(" ", "_") + ".docx") );	
	}	
	
    
    private static void addFirstColumn(Tr tableRow, String patentColumnNum, String patentColumnDate) {
        Tc tableCell = factory.createTc();
        
        Hyperlink link = createHyperlink(wordMLPackage, patentColumnNum, url);
        

   
        P spc = factory.createP();
        R rspc = factory.createR();
     
        Text numTitle = factory.createText();
        Text numText = factory.createText();
        
        Text dateTitle = factory.createText();
        Text dateText = factory.createText();
        Br br = factory.createBr();

        numTitle.setValue("US Patent #:");
        numText.setValue(patentColumnNum);
//      rspc.getContent().add(link);
        
        dateTitle.setValue("Filing date: ");
        dateText.setValue(patentColumnDate);
        
        rspc.getContent().add(numTitle);
        rspc.getContent().add(br);
        rspc.getContent().add(numText);
        
        rspc.getContent().add(br);
        rspc.getContent().add(br);
        
        rspc.getContent().add(dateTitle);
        rspc.getContent().add(br);
        rspc.getContent().add(dateText);
        
//        rspc.getContent().add(link);

        spc.getContent().add(rspc);
        tableCell.getContent().add(spc);
        
        setCellWidth(tableCell, 2625);

        
        tableRow.getContent().add(tableCell);
    }
    
    public static Hyperlink createHyperlink(WordprocessingMLPackage wordMLPackage, String linkText, String url) {
		
		try {

			// We need to add a relationship to word/_rels/document.xml.rels
			// but since its external, we don't use the 
			// usual wordMLPackage.getMainDocumentPart().addTargetPart
			// mechanism
			org.docx4j.relationships.ObjectFactory factory =
				new org.docx4j.relationships.ObjectFactory();
			
			org.docx4j.relationships.Relationship rel = factory.createRelationship();
			rel.setType( Namespaces.HYPERLINK  );
			rel.setTarget(url);
			rel.setTargetMode("External");  
									
			wordMLPackage.getMainDocumentPart().getRelationshipsPart().addRelationship(rel);
			
			
			// addRelationship sets the rel's @Id
			
			String hpl = "<w:hyperlink r:id=\"" + rel.getId() + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" " +
            "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" >" +
            "<w:r>" +
            "<w:rPr>" +
            "<w:rStyle w:val=\"Hyperlink\" />" +  // TODO: enable this style in the document!
            "</w:rPr>" +
            "<w:t>" + 
            linkText +
            "</w:t>" +
            "</w:r>" +
            "</w:hyperlink>";

			//return (Hyperlink)XmlUtils.unmarshalString(hpl, Context.jc, P.Hyperlink.class);
			return (Hyperlink)XmlUtils.unmarshalString(hpl);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
		
	}
    
    
    
    private static void addSecondColumn(Tr tableRow, int invStart, int invSkip, int appStart, int appSkip) {
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

		for(int i = 0; i < nameArray.length - 1; i++){
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
		
		for(int i = 0; i < nameArray.length - 1; i++){
			nextName = factory.createText();
			nextName.setValue(nameArray[i]);
			rspc.getContent().add(nextName);
			
			if(i != nameArray.length - 2)
				rspc.getContent().add(br);
		}
		
		
        spc.getContent().add(rspc);
        tableCell.getContent().add(spc);
        
        setCellWidth(tableCell, 2705);
        
        
        tableRow.getContent().add(tableCell);
    }
    
    private static void addColumn(Tr tableRow, String content) {
        Tc tableCell = factory.createTc();

        tableCell.getContent().add(
            wordMLPackage.getMainDocumentPart().createParagraphOfText(
                content));        
        
        tableRow.getContent().add(tableCell);
    }
    
    private static void addColumn(Tr tableRow, String content, int width) {
        Tc tableCell = factory.createTc();

        tableCell.getContent().add(
            wordMLPackage.getMainDocumentPart().createParagraphOfText(
                content));
        
        setCellWidth(tableCell, width);
        
        tableRow.getContent().add(tableCell);
    }
    

    /**
     *  In this method we create a table cell properties object and a table width
     *  object. We set the given width on the width object and then add it to
     *  the properties object. Finally we set the properties on the table cell.
     */
    private static void setCellWidth(Tc tableCell, int width) {
        TcPr tableCellProperties = new TcPr();
        TblWidth tableWidth = new TblWidth();
        tableWidth.setW(BigInteger.valueOf(width));
        tableCellProperties.setTcW(tableWidth);
        tableCell.setTcPr(tableCellProperties);
    }
    
    /**
     *  In this method we create a cell and add the given content to it.
     *  If the given width is greater than 0, we set the width on the cell.
     *  Finally, we add the cell to the row.
     */
    private static void addTableCellWithWidth(Tr row, String content, int width){
        Tc tableCell = factory.createTc();
        tableCell.getContent().add(
            wordMLPackage.getMainDocumentPart().createParagraphOfText(
                content));
 
        if (width > 0) {
            setCellWidth(tableCell, width);
        }
        row.getContent().add(tableCell);
    }
    

    
    
	
	private static void getNames(int start, int skip) {
		String names = wholePage.substring(start + skip, start + 200);
		String[] nameArray = names.split("<br />");
		
		
		for(int i = 0; i < nameArray.length - 1; i++){
			System.out.println(nameArray[i]);
		}
		System.out.println();
	}
		
	
	
}





