import java.io.*;
import java.net.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;


class TCPClient {
    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost", 1959);

        while (true) {

            System.out.print("Nombre de la Cancion:");
            String sentence;
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            if (inFromUser.toString().equals("Close")) {
                clientSocket.close();
                return;
            }


            sentence = inFromUser.readLine();


            outToServer.writeBytes(makeXML(sentence) + '\n');


            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
            makeXML(sentence);


        }

    }

    public static String makeXML(String atr1) {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.out.println("NO SE CREO EL DOCUMENTO");
            e.printStackTrace();
        }

        Document doc = dBuilder.newDocument();


        Element operation = doc.createElement("OperationCode");
        doc.appendChild(operation);

        Attr attr = doc.createAttribute("ID");
        attr.setValue("1");
        operation.setAttributeNode(attr);


        Element username = doc.createElement("UserName");
        operation.appendChild(username);

        username.appendChild(doc.createTextNode(atr1));




//        System.out.println(convertDocumentToString(doc));
        return convertDocumentToString(doc);

    }
    private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }
    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


