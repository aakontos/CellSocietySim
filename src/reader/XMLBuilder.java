package reader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import resources.Resources;
import alerts.Alerts;

/**
 * @author Alexi Kontos
 */

/**
 * Class created to be used in the saveXML method in Reader.java. XMLBuilder creates a new XML file based off
 * of existing cell states and place it in the src/resources/ folder.
 */
public class XMLBuilder {

    private Document myDoc;

    /**
     * Constructor for XMLBuilder
     * @param doc
     */
    public XMLBuilder (Document doc) {
        this.myDoc = doc;
    }


    /**
     * Helper method to add an element to the root for creating the XML file
     * @param elementName
     * @param elementData
     * @param root
     */
    private void addElement(String elementName, String elementData, Element root) {
        Element elem = myDoc.createElement(elementName);
        elem.appendChild(myDoc.createTextNode(elementData));
        root.appendChild(elem);
    }

    /**
     * Method to take in relevant values necessary for XML files, and create a working XML file to be saved that
     * can then be loaded from the dropdown menu later on by the user
     * @param simulationType
     * @param simulationName
     * @param numRows
     * @param numCols
     * @param param1
     * @param param2
     * @param param3
     * @param states
     */
    public void buildXML (String simulationType, String simulationName, int numRows, int numCols,
                          double param1, double param2, double param3, List<String> states) {
        try {
            DocumentBuilderFactory docFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFact.newDocumentBuilder();
            myDoc = docBuilder.newDocument();

            Element dataElement = myDoc.createElement("data");
            myDoc.appendChild(dataElement);

            addElement(Resources.SIMULATION_TYPE, simulationType, dataElement);
            addElement(Resources.SIMULATION_NAME, simulationName, dataElement);
            addElement(Resources.INPUT_TYPE, Resources.SPECIFIC, dataElement);
            addElement(Resources.NUM_ROWS, Integer.toString(numRows), dataElement);
            addElement(Resources.NUM_COL, Integer.toString(numCols), dataElement);

            for (int x = 0; x < numRows; x++) {
                addElement(String.format("row%d", x), states.get(x), dataElement);
            }
            addElement(Resources.PARAM1, Double.toString(param1), dataElement);
            addElement(Resources.PARAM2, Double.toString(param2), dataElement);
            addElement(Resources.PARAM3, Double.toString(param3), dataElement);

            saveXMLFile(String.format(Resources.XML_FILE_PATH));
        }
        catch (Exception e){
            throw new XMLException(Resources.getString("XMLGeneratorError"));
        }

    }

    /**
     * Method to save the XML file to the correct filePath, and then notify the user of its success
     * with a javafx Alert
     * @param filePath
     * @throws TransformerException
     * @throws IOException
     */
    public void saveXMLFile(String filePath) throws TransformerException, IOException {
        TransformerFactory tfact = TransformerFactory.newInstance();
        Transformer transformer = tfact.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter stringWriter = new StringWriter();
        StreamResult result = new StreamResult(stringWriter);
        DOMSource source = new DOMSource(myDoc);
        transformer.transform(source, result);
        String xml = stringWriter.toString();
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(xml);
        fileWriter.close();
        Alerts.XMLCreated(filePath);
    }
}
