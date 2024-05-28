package dto;

public class accidentBoardDTO {
	private int num;
	private String title;
	private String content;
	private java.sql.Timestamp postdate;
	private int visitcount;
	private String id;
	private int likes;
	private int dislikes;
	private int postId; // 추가된 필드

	// 기본 생성자
	public accidentBoardDTO() {
	}

	// 새로운 게시글을 삽입할 때 사용하는 생성자
	public accidentBoardDTO(String title, String content, String id) {
		this.title = title;
		this.content = content;
		this.id = id;
	}

	// 게시글 조회나 수정할 때 사용하는 생성자
	public accidentBoardDTO(int num, String title, String content, String id) {
		this.num = num;
		this.title = title;
		this.content = content;
		this.id = id;
	}

	// Getters and Setters
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

	public java.sql.Timestamp getPostdate() {
		return postdate;
	}

	public void setPostdate(java.sql.Timestamp postdate) {
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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public int getPostId() { // 추가된 getter 메서드
		return postId;
	}

	public void setPostId(int postId) { // 추가된 setter 메서드
		this.postId = postId;
	}

	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", title=" + title + ", content=" + content + ", postdate=" + postdate
				+ ", visitcount=" + visitcount + ", id=" + id + ", likes=" + likes + ", dislikes=" + dislikes
				+ ", postId=" + postId + "]"; // 추가된 필드 포함
	}
}