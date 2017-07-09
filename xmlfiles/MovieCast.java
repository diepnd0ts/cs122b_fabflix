import java.util.ArrayList;

public class MovieCast{
	
	//unique to Cast
	//may need to change fid
	private ArrayList<String> stagename;
	
	//overlapping variables
	private String title;
	private String director;
	private String id;
	
	//unique to Movie
	private int year;
	private String genre;
	private String banner_url;
	private String trailer_url;

	
	public MovieCast(){
	}
	
	public MovieCast(String title, int year, String director, String genre, String id){
		this.stagename = new ArrayList<String>();
		
		this.title = title;
		this.director = director;
		this.id = id;
		
		this.year = year;
		this.genre = genre;
		this.banner_url = null;
		this.trailer_url = null;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getStagename() {
		return stagename;
	}

	public void setStagename(ArrayList<String> stagename) {
		this.stagename = stagename;
	}
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getBanner_url() {
		return banner_url;
	}

	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}

	public String getTrailer_url() {
		return trailer_url;
	}

	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}
	
}
