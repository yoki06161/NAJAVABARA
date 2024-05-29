package dto;

public class BoardDTO {
	private int num;
	private String title;
	private String content;
	private String id;
	private String free;
	private String name;
	private String Ofile;
	private String Sfile;
	private String postdate;
	private int visitcount;
	
	public BoardDTO() {
	}
	
	public BoardDTO(String title, String content, String id) {
		super();
		this.title = title;
		this.content = content;
		this.id = id;
	}
	public BoardDTO(int num, String title, String content, String id, String free,  String ofile, String sfile, String postdate, int visitcount, String likes) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.free = free;
		this.Ofile = Ofile;
		this.Sfile = Sfile;
		this.postdate = postdate;
		this.visitcount = visitcount;

	}
	public BoardDTO(int num, String title, String content, String id, String free, String name, String ofile, String sfile, String postdate, int visitcount, String likes) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.free = free;
		this.name = name;
		this.Sfile = Sfile;
		this.Ofile = Ofile;
		this.postdate = postdate;
		this.visitcount = visitcount;
	}

	public BoardDTO(int num, String title, String content, String id, String free, String postdate,
			int visitcount) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.free = free;
		this.visitcount = visitcount;
	}

	public BoardDTO(int num, String title, String content, String id, String postdate, int visitcount) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.postdate = postdate;
		this.visitcount = visitcount;
	}
	public BoardDTO(int num, String title, String content, String id, String postdate, int visitcount, String name) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
		this.postdate = postdate;
		this.visitcount = visitcount;
		this.name = name;
	}
	public BoardDTO(String Ofile, String Sfile) {
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

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
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

	public void setOfile(String ofile) {
		Ofile = ofile;
	}

	public String getSfile() {
		return Sfile;
	}

	public void setSfile(String sfile) {
		Sfile = sfile;
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

	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", title=" + title + ", content=" + content + ", id=" + id + ", free=" + free
				+ ", name=" + name + ", Ofile=" + Ofile + ", Sfile=" + Sfile + ", postdate=" + postdate
				+ ", visitcount=" + visitcount + "]";
	}


	
}