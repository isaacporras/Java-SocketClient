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


class TCPClient {
    public static void main(String argv[]) throws Exception {
        Socket clientSocket = new Socket("localhost",1723);

        while (true) {

            System.out.print("Nombre de la Cancion:");
            String sentence;
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            if(inFromUser.toString().equals("Close")){
                clientSocket.close();
                return;
            }



            sentence = inFromUser.readLine();

//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//
//            // root element
//            Element SongElement = doc.createElement("Message");
//            doc.appendChild(SongElement);
//
//            Element carname = doc.createElement("Cancion");
//            Attr attrType = doc.createAttribute("type");
//            attrType.setValue("formula one");
//            carname.setAttributeNode(attrType);
//            carname.appendChild(doc.createTextNode(sentence));
//            SongElement.appendChild(carname);
//
//            XML_to_String converter = new XML_to_String();
//            String xml = converter.toString(doc).replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
            System.out.println("SENDING... " + sentence);
            outToServer.writeBytes(sentence + '\n');


            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);
        }


    }
}




