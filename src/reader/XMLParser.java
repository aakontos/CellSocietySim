package reader;

import java.io.IOException;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import resources.Resources;


/**
 * @author Alexi Kontos
 */


/**
 * Class created to parse through given XML files and transform it into text
 */
public class XMLParser {


    private static final DocumentBuilder DOCUMENT_BUILDER = getNewDocumentBuilder();

    private File myFile;
    private Element myRoot;


    /**
     * Constructor for XMLParser
     * @param file
     */
    public XMLParser(File file) {
        myRoot = getRoot(file);
        myFile = file;
    }

    /**
     * Method to return the file
     * @return
     */
    public File getFile(){
        return myFile;
    }

    /**
     * Method to return a string of text from the XML file
     * @param tag
     * @return
     */
    public String getTextFromXML(String tag) {
        return getTextFromXML(myRoot, tag);
    }

    /**
     * Method to get the root of the XML file
     * @param xmlFile
     * @return
     */
    private Element getRoot(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDoc = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDoc.getDocumentElement();
        }
        catch (SAXException | IOException e){
            throw new XMLException(e);
        }
    }

    /**
     * Method to iterate through the nodeList and extract text from the XML file
     * @param elem
     * @param tag
     * @return
     */
    private String getTextFromXML (Element elem, String tag) {
        NodeList nodeList = elem.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            throw new XMLException(Resources.getString("XMLInvalidTagError"), tag);
        }
    }

    /**
     * Method to create a new DocumentBuilder, need to catch errors in case a new instance of DocumentBuilder
     * cannot be created
     * @return
     */
    private static DocumentBuilder getNewDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e.getMessage());
        }
    }


}
