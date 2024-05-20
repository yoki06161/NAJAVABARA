package mvc.dto;

public class UserDTO {
    private int num;
    private String title;
    private String content;
    private java.sql.Timestamp postdate;
    private int visitcount;
    private String id;

    // 기본 생성자
    public UserDTO() {}

    // 모든 필드를 포함한 생성자
    public UserDTO(int num, String title, String content, java.sql.Timestamp postdate, int visitcount, String id) {
        this.num = num;
        this.title = title;
        this.content = content;
        this.postdate = postdate;
        this.visitcount = visitcount;
        this.id = id;
    }

    // Getter와 Setter 메소드
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