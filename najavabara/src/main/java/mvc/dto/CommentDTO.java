package mvc.dto;

public class CommentDTO {
    private int commentNum;
    private int postNum;
    private String comment;
    private String writer;
    private String regDate;

	public CommentDTO() {}

    public CommentDTO(int postNum, String comment, String writer) {
    	super();
        this.postNum = postNum;
        this.comment = comment;
        this.writer = writer;
    }

    public CommentDTO(int commentNum, int postNum, String comment, String writer, String regDate) {
    	super();
        this.commentNum = commentNum;
        this.postNum = postNum;
        this.comment = comment;
        this.writer = writer;
        this.regDate = regDate;
    }

	public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}