package dto;

public class RegionDTO {
	private int num;
	private String title;
	private String content;
	private String id;
	private String name;
	private String Ofile;
	private String Sfile;
	private String postdate;
	private int visitcount;
	private int postNum;
	
	public RegionDTO(int num, String title, String content, String id, String postdate, int visitcount, String name) {
		// TODO Auto-generated constructor stub
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.postdate = postdate;
		this.visitcount = visitcount;
		this.name = name;
	}
	//기본 생성자
	public RegionDTO() {
		// TODO Auto-generated constructor stub
	}
	public RegionDTO(int num, String title, String content, String id, String postdate, int visitcount) {
		// TODO Auto-generated constructor stub
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.postdate = postdate;
		this.visitcount = visitcount;
	}
	public RegionDTO(String title, String content, String id) {
		super();
		this.title = title;
		this.content = content;
		this.id = id;
	}
	public RegionDTO(String Ofile, String Sfile) {
		super();
		this.Ofile = Ofile;
		this.Sfile = Sfile;
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
		return Ofile;
	}
	public void setOfile(String Ofile) {
		this.Ofile = Ofile;
	}
	public String getSfile() {
		return Sfile;
	}
	public void setSfile(String Sfile) {
		this.Sfile = Sfile;
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
	public int getpostNum() {
		return postNum;
	}
	public void setpostNum(int postNum) {
		this.postNum = postNum;
	}
}
