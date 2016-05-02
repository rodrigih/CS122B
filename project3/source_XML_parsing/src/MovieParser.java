import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class MovieParser extends DefaultHandler
{
    private HashMap<String,Movie> movieToId; 
    private String buffer;
    private Movie currentMovie;

    // Constructor
    public MovieParser()
    {
        movieToId = new HashMap<String,Movie>(); 
        buffer = "";
    }

    public HashMap<String,Movie> parseDocument(String file)
    {
        SAXParserFactory spf = SAXParserFactory.newInstance();

        try
        {
            SAXParser sp = spf.newSAXParser();

            sp.parse(file,this);

        }catch(SAXException se)
        {
            se.printStackTrace(); 
        }catch(ParserConfigurationException pce)
        {
            pce.printStackTrace(); 
        }catch(IOException ie)
        {
            ie.printStackTrace(); 
        }

        return movieToId;
    }

    // Event Handlers
    public void startElement(String uri, String localName, String qName,
           Attributes attributes) throws SAXException
    {
        // Reset buffer
        buffer = "";

        if(qName.equalsIgnoreCase("film"))
        {
            //Create new movie class     
            currentMovie = new Movie();
        }
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
            case "fid":
                currentMovie.setId(buffer);
            case "t":
                currentMovie.setTitle(buffer);
                break;
            case "year":
                currentMovie.setYear(buffer);
                break;
            case "dirn":
                currentMovie.addDirector(buffer);
                break;
            case "cat":
                currentMovie.addGenre(buffer);
                break;
            case "film":
                movieToId.put(currentMovie.getId(),currentMovie);
                break;
            
            default:
                // DO NOTHING
                break;
        }
    }
}
