package com.myCompany.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;

/**
 * CSV Data Load Template
 */
public class DataLoadConfXML {
	
	public static void updateXMLFile(String filename,Map<String, String> updateData) throws FileNotFoundException {
		try{
		  
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			//Document doc = docBuilder.parse(new File(filename));
			Document doc = docBuilder.newDocument();
			DOMImplementation domImpl = doc.getImplementation();
			DocumentType doctype = domImpl.createDocumentType("beans", "-//SPRING//DTD BEAN//EN", "http://www.springframework.org/dtd/spring-beans.dtd");
			System.out.println(doctype.getName());
			System.out.println(doctype.getPublicId());
			System.out.println(doctype.getSystemId());

			doc.appendChild(doctype);
			Element beans = doc.createElement("beans");
			doc.appendChild(beans);
			
			Element bean = doc.createElement("bean");
			bean.setAttribute("id", "leadMasterProcess");
			bean.setAttribute("class","com.salesforce.dataloader.process.ProcessRunner");
			bean.setAttribute("singleton","false");
			beans.appendChild(bean);
			
			Element description = doc.createElement("description");
			description.appendChild(doc.createTextNode("uploads leads to salesforce using 'upsert'."));
			bean.appendChild(description);
			
			Element prop = doc.createElement("property");
			prop.setAttribute("name", "name");
			prop.setAttribute("value", "leadMasterProcess");
			bean.appendChild(prop);
			
			Element property = doc.createElement("property");
			property.setAttribute("name", "configOverrideMap");
			bean.appendChild(property);
			
			Element map = doc.createElement("map");
			property.appendChild(map);
			
			Element entry;
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.endpoint");
			entry.setAttribute("value", updateData.get("sfdc.endpoint"));
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.username");
			entry.setAttribute("value", updateData.get("sfdc.username"));
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.password");
			entry.setAttribute("value", updateData.get("sfdc.password"));
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "process.encryptionKeyFile");
			entry.setAttribute("value", updateData.get("process.encryptionKeyFile"));
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.assignmentRule");
			entry.setAttribute("value", "01Qa0000000cSiT");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.timeoutSecs");
			entry.setAttribute("value", "540");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.loadBatchSize");
			entry.setAttribute("value", "200");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.entity");
			entry.setAttribute("value", "Lead");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "process.operation");
			entry.setAttribute("value", "insert");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "process.mappingFile");
			entry.setAttribute("value", updateData.get("process.mappingFile"));
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "dataAccess.name");
			entry.setAttribute("value", updateData.get("dataAccess.name"));
			map.appendChild(entry);
						
			entry= doc.createElement("entry");
			entry.setAttribute("key", "dataAccess.type");
			entry.setAttribute("value", "csvRead");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.timezone");
			entry.setAttribute("value", "America/Chicago");
			map.appendChild(entry);
			
			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.proxyHost");
			entry.setAttribute("value", "proxy");
			map.appendChild(entry);

			entry= doc.createElement("entry");
			entry.setAttribute("key", "sfdc.proxyPort");
			entry.setAttribute("value", "8099");
			map.appendChild(entry);

			entry= doc.createElement("entry");
			entry.setAttribute("key", "process.initialLastRunDate");
			entry.setAttribute("value", "2005-12-01T00:00:00.000-0800");
			map.appendChild(entry);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
			DOMSource domSource = new DOMSource(doc);
			
// ----------------  Console Output --> Commented 
			System.out.println(((DOMImplementationLS) domImpl).createLSSerializer().writeToString(doc));
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(domSource, consoleResult);
// -----------------------------------------------
			
			StreamResult streamResult = new StreamResult(new File(filename));
			transformer.transform(domSource, streamResult);
			
		} catch (ParserConfigurationException | TransformerException pce) {
			pce.printStackTrace();
		   } 	}	
}
