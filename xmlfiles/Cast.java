public class Cast{
	
	private String fid;
	private String title;
	private String stagename;
	private String director;

	
	public Cast(){
	}
	
	public Cast(String title, String stagename, String director, String fid){
		this.fid = fid; //looks like AA13
		this.title = title;
		this.stagename = stagename;
		this.director = director;

	}
	
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStagename() {
		return stagename;
	}

	public void setStagename(String stagename) {
		this.stagename = stagename;
	}
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
}
