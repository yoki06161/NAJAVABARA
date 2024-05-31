package dto;

import java.util.Date;

public class Review_BoardDTO {
	int num;
	String title;
	String content; 
	Date postdate;
	int visitcount;
	String id;
	String oriFile;
	String newFile;
	int like;
	
	public Review_BoardDTO() {
	}
	
	public Review_BoardDTO(String title, String content, int num) {
		super();
		this.title = title;
		this.content = content;
		this.num = num;
	}
	
	public Review_BoardDTO(String title, String content, String id) {
		super();
		this.title = title;
		this.content = content;
		this.id = id;
	}

	// 전부 추가한것
	public Review_BoardDTO(int num, String title, String content, Date postdate, int visitcount, String id,
			String oriFile, String newFile, int like) {
		super();
		this.num = num;
		this.title = title;
		this.content = content;
		this.postdate = postdate;
		this.visitcount = visitcount;
		this.id = id;
		this.oriFile = oriFile;
		this.newFile = newFile;
		this.like = like;
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

	public Date getPostdate() {
		return postdate;
	}

	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}

	public int getVisitcount() {
		return visitcount;
	}

	public void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOriFile() {
		return oriFile;
	}

	public void setOriFile(String oriFile) {
		this.oriFile = oriFile;
	}

	public String getNewFile() {
		return newFile;
	}

	public void setNewFile(String newFile) {
		this.newFile = newFile;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}
	
}
