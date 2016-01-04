package main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NewFile {

	public static void main() {
		
		new File(Locations.path).mkdir();
		java.io.File monFichier = new java.io.File(Locations.path + Locations.inputFile);
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		if ( !monFichier.exists() ) {
			System.out.print("==Modpacks.xml file was missing ");
			try {
				monFichier.createNewFile();
				System.out.println("and has been created==\n");
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document= builder.newDocument();
				final Element racine = document.createElement("modpacks");
				document.appendChild(racine);

				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer transformer = transformerFactory.newTransformer();
				final DOMSource source = new DOMSource(document);
				final StreamResult sortie = new StreamResult(new File(Locations.path + Locations.inputFile));
				
				transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				transformer.transform(source, sortie);
				
			} catch (IOException e) {
				System.out.println("but couldn't be created (IOException)==\n");
			} catch (ParserConfigurationException e) {
				System.out.println("but couldn't be created (ParserConfigurationException)==\n");
			} catch (TransformerConfigurationException e) {
				System.out.println("but couldn't be created (TransformerConfigurationException)==\n");
			} catch (TransformerException e) {
				System.out.println("but couldn't be created (TransformerException)==\n");
			}
		}
	}
}
