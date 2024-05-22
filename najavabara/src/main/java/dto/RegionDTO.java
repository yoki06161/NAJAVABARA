package dto;

public class RegionDTO {
	private int num;
	private String title;
	private String content;
	private String id;
	private String area;
	private String name;
	private String ofile;
	private String sfile;
	private String postdate;
	private int visitcount;
	private int likes;
	
	public RegionDTO(int num, String title, String content, String id, String area, String postdate, String name, int visitcount) {
		// TODO Auto-generated constructor stub
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.area = area;
		this.postdate = postdate;
		this.visitcount = visitcount;
		this.name = name;
	}
	//기본 생성자
	public RegionDTO() {
	}
	public RegionDTO(int num, String title, String content, String id, String area, String postdate, int visitcount, String ofile, String sfile, int likes) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.area = area;
		this.postdate = postdate;
		this.visitcount = visitcount;
		this.ofile = ofile;
		this.sfile = sfile;
		this.likes = likes;
	}
	public RegionDTO(int num, String title, String content, String id, String area, String postdate, String name, int visitcount, String ofile, String sfile, int likes) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.area = area;
		this.postdate = postdate;
		this.name = name;
		this.visitcount = visitcount;
		this.ofile = ofile;
		this.sfile = sfile;
		this.likes = likes;
	}
	public RegionDTO(String title, String content, String id) {
		super();
		this.title = title;
		this.content = content;
		this.id = id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOfile() {
		return ofile;
	}
	public void setOfile(String ofile) {
		this.ofile = ofile;
	}
	public String getSfile() {
		return sfile;
	}
	public void setSfile(String sfile) {
		this.sfile = sfile;
	}
	public String getPostdate() {
		return postdate;
	}
	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}
	public int getVisitcount() {
		return visitcount;
	}
	public void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
