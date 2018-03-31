package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SenticNet {

	private HashMap<String, Double> words;

	public SenticNet() throws ParserConfigurationException, SAXException, IOException {
		words = new HashMap<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File("./Sources/senticnet4.rdf.xml"));
		document.getDocumentElement().normalize();
		NodeList nList = document.getElementsByTagName("rdf:Description");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) nNode;
				String text = element.getElementsByTagName("text").item(0).getTextContent();
				Double intensity = Double
						.parseDouble(element.getElementsByTagName("intensity").item(0).getTextContent());
				words.put(text, intensity);

			}
		}
	}

	public Double RequestSenticNet(String searchKey) {

		if (words.get(searchKey) == null) {
			//System.out.println("Not found in Sentic DB");
			return (double) 0;
		} else {

			//System.out.println(words.get(searchKey));
			return words.get(searchKey);
		}

	}

}
