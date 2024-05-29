package proj.dto;

public class HBoardDTO {
    private int num;
    private String hobby;
    private String title;
    private String content;
    private String id;
    private String postdate;
    private int visitcount;
    private String name;
    private String orifile;
    private String newfile;

    public HBoardDTO() {
    }

    public HBoardDTO(int num) {
        super();
        this.num = num;
    }

    public HBoardDTO(int num, String title, String content) {
        super();
        this.num = num;
        this.title = title;
        this.content = content;
    }

    public HBoardDTO(String title, String id, String hobby, String content) {
        super();
        this.title = title;
        this.id = id;
        this.hobby = hobby;
        this.content = content;
    }

    public HBoardDTO(int num, String title, String content, String id, String postdate, int visitcount) {
        super();
        this.num = num;
        this.title = title;
        this.content = content;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
    }

    public HBoardDTO(int num, String title, String id, String hobby, String content) {
        super();
        this.num = num;
        this.hobby = hobby;
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public HBoardDTO(int num, String title, String hobby, String content, String id, String postdate, int visitcount) {
        super();
        this.num = num;
        this.title = title;
        this.hobby = hobby;
        this.content = content;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
    }

    public HBoardDTO(int num, String title, String hobby, String id, String postdate, int visitcount, 
            String orifile, String newfile) {
        super();
        this.num = num;
        this.title = title;
        this.hobby = hobby;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
        this.orifile = orifile;
        this.newfile = newfile;
    }

    public HBoardDTO(int num, String hobby, String title, String content, String id, String postdate, int visitcount,
            String orifile, String newfile) {
        super();
        this.num = num;
        this.hobby = hobby;
        this.title = title;
        this.content = content;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
        this.orifile = orifile;
        this.newfile = newfile;
    }

    public HBoardDTO(int num, String hobby, String title, String content, String id, String postdate, int visitcount,
            String name, String orifile, String newfile) {
        super();
        this.num = num;
        this.hobby = hobby;
        this.title = title;
        this.content = content;
        this.id = id;
        this.postdate = postdate;
        this.visitcount = visitcount;
        this.name = name;
        this.orifile = orifile;
        this.newfile = newfile;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrifile() {
        return orifile;
    }

    public void setOrifile(String orifile) {
        this.orifile = orifile;
    }

    public String getNewfile() {
        return newfile;
    }

    public void setNewfile(String newfile) {
        this.newfile = newfile;
    }

    @Override
    public String toString() {
        return "BoardDTO [num=" + num + ", hobby=" + hobby + ", title=" + title + ", content=" + content + ", id=" + id
                + ", postdate=" + postdate + ", visitcount=" + visitcount + ", name=" + name + ", orifile=" + orifile
                + ", newfile=" + newfile + "]";
    }
}
