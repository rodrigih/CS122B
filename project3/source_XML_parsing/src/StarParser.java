import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class StarParser extends DefaultHandler
{
    private HashMap<String,Star> nameToStar;
    private String buffer;
    private Star currentStar;

    // Constructor
    public StarParser()
    {
        nameToStar = new HashMap<String,Star>();
        buffer = "";
    }

    public HashMap<String,Star> parseDocument(String file)
    {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        
        try
        {
            SAXParser sp = spf.newSAXParser();

            sp.parse(file,this);
        }
        catch(SAXException se)
        {
            se.printStackTrace();
        }
        catch(ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
        catch(IOException ie)
        {
            ie.printStackTrace();    
        }

        return nameToStar;
    }

    // Event Handlers
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException
    {
        // Reset buffer
        buffer = "";

        if(qName.equalsIgnoreCase("actor"))
            currentStar = new Star();
    }

    public void characters(char[] ch, int start, int length) throws SAXException
    {
        buffer = new String(ch,start,length);
    }

    public void endElement(String uri, String localName, String qName)
        throws SAXException
    {
        switch(qName.toLowerCase())
        {
            case "stagename":
                // Split name to first and last
                currentStar.setName(buffer);
                break;
            case "dob":
                currentStar.setDOB(buffer);
                break;
            case "actor":
                if(!(nameToStar.containsKey(currentStar.getFullName())))
                    nameToStar.put(currentStar.getFullName(),currentStar); 
            default:
                // DO NOTHING
                break;
        }
    }
}
