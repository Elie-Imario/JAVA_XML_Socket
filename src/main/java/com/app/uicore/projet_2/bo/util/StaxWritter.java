package com.app.core.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class StaxWritter {
    private String OutPutFile;

    public void setFile(String outPutFile){
        this.OutPutFile = outPutFile;
    }

    public void saveData(Long idEtudiant, String name, String adresse, int bourse, String reqval) throws Exception{
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(OutPutFile));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        XMLEvent end = eventFactory.createDTD("\n");

        StartDocument startDocument = eventFactory.createStartDocument();

        eventWriter.add(startDocument);
        eventWriter.add(end);

        StartElement dataStartElement = eventFactory.createStartElement("", "", "etudiants");
        eventWriter.add(dataStartElement);
        eventWriter.add(end);

        if(reqval.equals("AJOUT")){
            createNode_for_add(eventWriter, name, adresse, bourse, reqval);
        }else if(reqval.equals("EDIT")){
            createNode_for_edit(eventWriter, idEtudiant, name, adresse, bourse, reqval);
        }else if(reqval.equals("DELETE")){
            createNode_for_delete(eventWriter, idEtudiant, reqval);
        }

        eventWriter.add(eventFactory.createEndElement("", "", "etudiants"));
        eventWriter.add(eventFactory.createEndDocument());
        eventWriter.add(end);
        eventWriter.close();
    }

    public void saveDataToCurrentFIle(Long IdEtudiant, String name, String adresse, int bourse, String _requestType) throws Exception{

        try {
            File xmlFile = new File("src\\exportedFile\\etudiant.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            Element documentElement = document.getDocumentElement();
            Element nodeElement = null;

            if(_requestType.equals("AJOUT")){
                Element textNode = document.createElement("nom");
                textNode.setTextContent(name);
                Element textNode1 = document.createElement("adresse");
                textNode1.setTextContent(adresse);
                Element textNode2 = document.createElement("bourse");
                textNode2.setTextContent(String.valueOf(bourse));

                nodeElement = document.createElement("etudiant");
                nodeElement.setAttribute("requestType", _requestType);
                nodeElement.appendChild(textNode);
                nodeElement.appendChild(textNode1);
                nodeElement.appendChild(textNode2);
            }else if(_requestType.equals("EDIT")){
                Element textNode = document.createElement("id");
                textNode.setTextContent(String.valueOf(IdEtudiant));
                Element textNode1 = document.createElement("nom");
                textNode1.setTextContent(name);
                Element textNode2 = document.createElement("adresse");
                textNode2.setTextContent(adresse);
                Element textNode3 = document.createElement("bourse");
                textNode3.setTextContent(String.valueOf(bourse));

                nodeElement = document.createElement("etudiant");
                nodeElement.setAttribute("requestType", _requestType);
                nodeElement.appendChild(textNode);
                nodeElement.appendChild(textNode1);
                nodeElement.appendChild(textNode2);
                nodeElement.appendChild(textNode3);
            }else if(_requestType.equals("DELETE")){
                Element textNode = document.createElement("id");
                textNode.setTextContent(String.valueOf(IdEtudiant));
                nodeElement = document.createElement("etudiant");
                nodeElement.setAttribute("requestType", _requestType);
                nodeElement.appendChild(textNode);
            }

            documentElement.appendChild(nodeElement);
            documentElement.appendChild(document.createTextNode("\n"));
            document.replaceChild(documentElement, documentElement);
            Transformer tFormer = TransformerFactory.newInstance().newTransformer();
            tFormer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "xml");
            Source source = new DOMSource(document);
            Result result = new StreamResult(xmlFile);
            tFormer.transform(source, result);

            } catch (Exception ex) {
                System.out.println(ex);
            }
    }

    
    public NodeList getNodeList() throws Exception{
        NodeList nodeList = null;
        try{
            File xmlFile = new File("src\\exportedFile\\etudiant.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            nodeList = document.getElementsByTagName("etudiant");
        }catch (Exception e){
            System.out.println(e);
        }

        return nodeList;

    }

    private void createNode_for_add(XMLEventWriter xmlEventWriter, String _name, String _adresse, int _bourse, String reqValuevalue)
    throws XMLStreamException{
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement mainstartElement = eventFactory.createStartElement("", "", "etudiant");
        xmlEventWriter.add(mainstartElement);
        xmlEventWriter.add(eventFactory.createAttribute("requestType", reqValuevalue));

        // NOM
        StartElement startElement = eventFactory.createStartElement("", "", "nom");
        xmlEventWriter.add(startElement);
        Characters name = eventFactory.createCharacters(_name);
        xmlEventWriter.add(name);
        //End Node
        EndElement endElement = eventFactory.createEndElement("","","nom");
        xmlEventWriter.add(endElement);

        // ADRESSE
        startElement = eventFactory.createStartElement("", "", "adresse");
        xmlEventWriter.add(startElement);
        Characters adresse = eventFactory.createCharacters(_adresse);
        xmlEventWriter.add(adresse);
        //End Node
        endElement = eventFactory.createEndElement("","","adresse");
        xmlEventWriter.add(endElement);

        // BOURSE
        startElement = eventFactory.createStartElement("", "", "bourse");
        xmlEventWriter.add(startElement);
        Characters bourse = eventFactory.createCharacters(Integer.valueOf(_bourse).toString());
        xmlEventWriter.add(bourse);
        //End Node
        endElement = eventFactory.createEndElement("","","bourse");
        xmlEventWriter.add(endElement);

        EndElement mainEndElement = eventFactory.createEndElement("", "", "etudiant");
        xmlEventWriter.add(mainEndElement);
        xmlEventWriter.add(end);
    }

    private void createNode_for_edit(XMLEventWriter xmlEventWriter,Long _Id, String _name, String _adresse, int _bourse, String reqValuevalue)
            throws XMLStreamException{
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement mainstartElement = eventFactory.createStartElement("", "", "etudiant");
        xmlEventWriter.add(mainstartElement);
        xmlEventWriter.add(eventFactory.createAttribute("requestType", reqValuevalue));

        // ID
        StartElement startElement = eventFactory.createStartElement("", "", "id");
        xmlEventWriter.add(startElement);
        Characters Id = eventFactory.createCharacters(String.valueOf(_Id));
        xmlEventWriter.add(Id);
        //End Node
        EndElement endElement = eventFactory.createEndElement("","","id");
        xmlEventWriter.add(endElement);

        // NOM
        startElement = eventFactory.createStartElement("", "", "nom");
        xmlEventWriter.add(startElement);
        Characters name = eventFactory.createCharacters(_name);
        xmlEventWriter.add(name);
        //End Node
        endElement = eventFactory.createEndElement("","","nom");
        xmlEventWriter.add(endElement);

        // ADRESSE
        startElement = eventFactory.createStartElement("", "", "adresse");
        xmlEventWriter.add(startElement);
        Characters adresse = eventFactory.createCharacters(_adresse);
        xmlEventWriter.add(adresse);
        //End Node
        endElement = eventFactory.createEndElement("","","adresse");
        xmlEventWriter.add(endElement);

        // BOURSE
        startElement = eventFactory.createStartElement("", "", "bourse");
        xmlEventWriter.add(startElement);
        Characters bourse = eventFactory.createCharacters(Integer.valueOf(_bourse).toString());
        xmlEventWriter.add(bourse);
        //End Node
        endElement = eventFactory.createEndElement("","","bourse");
        xmlEventWriter.add(endElement);

        EndElement mainEndElement = eventFactory.createEndElement("", "", "etudiant");
        xmlEventWriter.add(mainEndElement);
        xmlEventWriter.add(end);
    }

    private void createNode_for_delete(XMLEventWriter xmlEventWriter,Long _Id, String reqValuevalue)
            throws XMLStreamException{
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");

        StartElement mainstartElement = eventFactory.createStartElement("", "", "etudiant");
        xmlEventWriter.add(mainstartElement);
        xmlEventWriter.add(eventFactory.createAttribute("requestType", reqValuevalue));

        // ID
        StartElement startElement = eventFactory.createStartElement("", "", "id");
        xmlEventWriter.add(startElement);
        Characters id = eventFactory.createCharacters(String.valueOf(_Id));
        xmlEventWriter.add(id);
        //End Node
        EndElement endElement = eventFactory.createEndElement("","","id");
        xmlEventWriter.add(endElement);

        EndElement mainEndElement = eventFactory.createEndElement("", "", "etudiant");
        xmlEventWriter.add(mainEndElement);
        xmlEventWriter.add(end);
    }

}
