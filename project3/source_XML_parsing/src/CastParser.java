import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class CastParser extends DefaultHandler
{
    private HashMap<String,Star> nameToStar;
    private String buffer;
    private String currentFilm;
    private String starName;

    //Constructor
    public CastParser()
    {
        nameToStar = new HashMap<String,Star>();
        buffer = "";
        currentFilm = "";
        starName = "";
    }

    public HashMap<String,Star> parseDocument(String file, HashMap<String,Star> map)
    {
        nameToStar = map;
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
            case "f":
                currentFilm = buffer;
                break;
            case "a":
                starName = buffer;
                break;
            case "m":
                // Add film to Stars.movie
                // If Star does not exist in HashMap, create new Star
                if(nameToStar.get(starName) == null) 
                {
                    Star newStar = new Star();
                    newStar.setName(starName); 
                    nameToStar.put(starName,newStar);
                }

                nameToStar.get(starName).addMovie(currentFilm);
                break;
            default:
                // DO NOTHING
                break;
        }
    }

}
