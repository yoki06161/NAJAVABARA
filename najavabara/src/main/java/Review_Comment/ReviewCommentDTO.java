package Review_Comment;

import java.util.Date;

public class ReviewCommentDTO {
	int num;
	String comment;
	Date date;
	String id;
	String tdate;
	
	public ReviewCommentDTO() {
	}

	// 댓글쓰기용
	public ReviewCommentDTO(int num, String comment, String id) {
		super();
		this.num = num;
		this.comment = comment;
		this.id = id;
	}

	public ReviewCommentDTO(int num, String comment, Date date, String id, String tdate) {
		super();
		this.num = num;
		this.comment = comment;
		this.date = date;
		this.id = id;
		this.tdate = tdate;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	
}
