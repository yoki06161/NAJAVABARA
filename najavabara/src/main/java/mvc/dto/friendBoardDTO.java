package mvc.dto;

import java.sql.Timestamp;

public class friendBoardDTO {
    private int num;
    private String title;
    private String content;
    private Timestamp postdate;
    private int visitcount;
    private String id;
    private int commentCount;
    private int likeCount;
    private String fileName;
    private String area;
    
    public friendBoardDTO() {
    }
    
    public friendBoardDTO(String title, String content, String id, String fileName, String area) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.fileName = fileName;
        this.area = area;
    }
    
    public friendBoardDTO(int num, String title, String content, String id, Timestamp postdate, int visitcount) {
        super();
        this.num = num;
        this.title = title;
        this.content = content;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
    }
    
    public friendBoardDTO(int num, String title, String content, String id, Timestamp postdate, int visitcount, String fileName) {
        super();
        this.num = num;
        this.title = title;
        this.content = content;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
        this.fileName = fileName;
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

    
    public Timestamp getPostdate() {
        return postdate;
    }

    public void setPostdate(Timestamp postdate) {
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

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
    
	
}
