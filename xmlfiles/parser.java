import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class parser {

	//No generics
	List<Movie> movieList;
	List<Cast> castList;
	
	//Map<String, ArrayList<ArrayList>> movieCastMap;
	List<MovieCast> movieCastList;
	
	Document dom_mains;
	Document dom_cast;


	public parser(){
		//create a list to hold the employee objects
		movieList = new ArrayList<Movie>();
		castList = new ArrayList<Cast>();
		
		movieCastList = new ArrayList<MovieCast>();
		
	}
	

	public void runExample() {
		
		//parse the xml file and get the dom object
		parseXmlFile();
		
		//get each employee element and create a Employee object
		parseMainDocument();
		parseCastDocument();
		
		//Iterate through the list and print the data
		//printData();
	}
	
	private void parseXmlFile(){
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//connect to server then create resultset using movielist
			//connect movies and genres together using movieid genreid
			
			//parse using builder to get DOM representation of the XML file
			dom_mains = db.parse("mains243.xml");
			dom_cast = db.parse("casts124.xml");
			

		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void parseCastDocument(){
		//get the root elememt
		Element docEle = dom_cast.getDocumentElement();
		String title;
		String stagename = "";
		String director = "";
		String fid;

		NodeList dList = docEle.getElementsByTagName("dirfilms");
		if(dList != null && dList.getLength() > 0) {
			for(int j = 0 ; j < dList.getLength();j++) {
				Element d = (Element)dList.item(j);
				
				//get director
				director = getTextValue(d, "is");
				//System.out.println("\n\n--------------------\nDirector: " + director);
				
				NodeList fList = d.getElementsByTagName("filmc");
				if(fList != null && fList.getLength() > 0) {
					for(int i = 0 ; i < fList.getLength();i++) {
						Element f = (Element)fList.item(i);
						
						//get title
						title = getTextValue(f, "t");
						//System.out.println("\nTitle: " + title);
						
						fid = getTextValue(f, "f");
						//System.out.println("ID: " + fid);
						
						NodeList aList = f.getElementsByTagName("m");
						if(aList != null && aList.getLength() > 0) {
							for(int k = 0 ; k < aList.getLength();k++) {
								Element a = (Element)aList.item(k);
								//get actorname
								stagename = getTextValue(a, "a");
								
								
								if (stagename.equals("s a")){
									k++;
								}
								else{
									//System.out.println("Actor Name: " + stagename);
								}
								
								Cast c = new Cast(title, stagename, director, fid);
								castList.add(c);
							}
						}
					}
				}		
			}
		}
		//System.out.println(castList.size());
	}
	
	private void parseMainDocument(){
		//get the root elememt
		Element docEle = dom_mains.getDocumentElement();
		String director = "";
		String title;
		int year;
		String id;
		
		//get a nodelist of <employee> elements
		NodeList nl = docEle.getElementsByTagName("directorfilms");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				//get the employee element
				Element el = (Element)nl.item(i);
				NodeList dList = el.getElementsByTagName("director");
				if(dList != null && dList.getLength() > 0) {
					for(int j = 0 ; j < dList.getLength();j++) {
						Element d = (Element)dList.item(j);
						director = getTextValue(d, "dirname");
						//System.out.println("Director: " + director);
					}
				}
				NodeList fList = el.getElementsByTagName("film");
				//System.out.println("	Film Count: " + fList.getLength());
				if(fList != null && fList.getLength() > 0) {
					for(int k = 0 ; k < fList.getLength();k++) {
						Element f = (Element)fList.item(k);
						String genre = getTextValue(f, "cat");
						if (genre == null)
							genre = "none";
							//System.out.println("	Genre: none");
//						else{
//							//System.out.println("	Genre: " + genre);
//						}
						title = getTextValue(f, "t");
						year = getIntValue(f, "year");
						id = getTextValue(f, "fid");
						//System.out.println("	Title: " + title + ";" + year + " ID: " + id);
						
						
						Movie m = new Movie(title, year, director, genre, id);
						movieList.add(m);
					}
				}
				

			}
		}
//		System.out.println("hello");
//		System.out.println(movieList.size());
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			if (el.getFirstChild() == null)
				textVal = "";
			else
				textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	
	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		//in production application you would catch the exception
		try{
			int r = Integer.parseInt(getTextValue(ele,tagName));
			return r;
		} catch(Exception e){
			return 0;
		}
	}
	
	/**
	 * Iterate through the list and print the 
	 * content to console
	 */
	private void printData(){
		
		System.out.println("No of Employees '" + movieList.size() + "'.");
		
		//Iterator<Movie> it_mv = movieList.iterator();
//		Iterator<Cast> it_ct = castList.iterator();
		Iterator<MovieCast> it_mvc = movieCastList.iterator();
//		
		while(it_mvc.hasNext()) {
			System.out.println(it_mvc.next().getId().toString());
		}
	}
	
	public ArrayList<MovieCast> combine(List<Movie> m, List<Cast> c){
		ArrayList<MovieCast> returnList = new ArrayList<MovieCast>();
		for (int i=0; i<m.size(); i++){
		//	ArrayList<String> nameList = new ArrayList<String>();
			MovieCast mc = new MovieCast(m.get(i).getTitle(), m.get(i).getYear(), m.get(i).getDirector(), m.get(i).getGenre(), m.get(i).getId());
			returnList.add(mc);
		}
		for (int i=0; i < returnList.size(); i++){
			for (int j=0; j < c.size(); j++){
				if (returnList.get(i).getId() == null){
				}
				else if (returnList.get(i).getId().compareTo(c.get(j).getFid()) == 0){
					returnList.get(i).getStagename().add(c.get(j).getStagename());
				}
					
			}		
		}
		
		return returnList;
	}
	
//	public static void main(String[] args){
		//create an instance
//		parser dpe = new parser();
//		//call run example
//		dpe.runExample();
//		dpe.movieCastList = dpe.combine(dpe.movieList, dpe.castList);
		//for (int i =0; i < bam.size(); i++){
			//bam.get(i).getStagename();
	//	}
//		dpe.printData();
		
//		System.out.println(dpe.movieList.size());
		
//	}

}

