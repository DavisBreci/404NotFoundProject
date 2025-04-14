package com.urock;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.model.Rest;

import javafx.scene.image.ImageView;
import javafx.scene.shape.SVGPath;

public class RestView extends ImageView{
    Rest rest;

    public RestView(String url, Rest rest){
        this.rest = rest;
    }
    
    
    public static void main(String [] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException{
        RestView rv = new RestView(null, null);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = rv.getClass().getResource("Images/Rests/QuarterNoteRest.svg").openStream();
        Document document = builder.parse(is);
    
        String xpathExpression = "//path/@d";
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression = xpath.compile(xpathExpression);
        NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
        System.out.println(svgPaths.item(0).getNodeValue());
        SVGPath p = new SVGPath();
        p.setContent(svgPaths.item(0).getNodeValue());
        is.close();
        loadSVG(null);
        
    }

    public static SVGPath loadSVG(String filename){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPathFactory xpf = XPathFactory.newInstance();

        DocumentBuilder builder;
        SVGPath p = null;
        try {
            builder = factory.newDocumentBuilder();
            InputStream is = RestView.class.getClass().getResource("Images/Rests/QuarterNoteRest.svg").openStream();
            Document document = builder.parse(is);
        
            String xpathExpression = "//path/@d";
            XPath xpath = xpf.newXPath();
            XPathExpression expression = xpath.compile(xpathExpression);
            NodeList svgPaths = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
            p = new SVGPath();
            p.setContent(svgPaths.item(0).getNodeValue());
            is.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
