package fr.iclario.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Resources
{

	// Clear the screen (have to be modified)
	public final static void clear ()
	{
		// Printing some new lines if the clear doesn't work
		for (int i=0; i<50; i++)
			System.out.println();
		
		try
		{
			final String os = System.getProperty("os.name");
			if (os.contains("Windows"))
			{
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
			else
			{
				Runtime.getRuntime().exec("clear");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Detect if modpacks.xml is present and create it if not
	public static void generateXML (boolean thirdparty)
	{

		new File(Constants.path).mkdir();
		java.io.File file = new java.io.File(Constants.path + (thirdparty ? Constants.modpackFile : Constants.thirdpartyFile));
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		if (!file.exists())
		{
			System.out.print("==Modpacks.xml file was missing ");
			try
			{
				file.createNewFile();
				System.out.println("and has been created==\n");
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document = builder.newDocument();
				final Element racine = document.createElement("modpacks");
				document.appendChild(racine);

				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer transformer = transformerFactory.newTransformer();
				final DOMSource source = new DOMSource(document);
				final StreamResult sortie = new StreamResult(file);

				transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");

				transformer.transform(source, sortie);

			}
			catch (ParserConfigurationException | IOException | TransformerException e)
			{
				e.printStackTrace();
			}
		}
	}
}
