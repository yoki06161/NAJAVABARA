package mvc.dto;

public class BoardDTO {
    private int num;
    private String title;
    private String content;
    private java.sql.Timestamp postdate;
    private int visitcount;
    private String id;

    // 기본 생성자
    public BoardDTO() {}

    // 새로운 게시글을 삽입할 때 사용하는 생성자
    public BoardDTO(String title, String content, String id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    // 게시글 조회나 수정할 때 사용하는 생성자
    public BoardDTO(int num, String title, String content, String id) {
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
}