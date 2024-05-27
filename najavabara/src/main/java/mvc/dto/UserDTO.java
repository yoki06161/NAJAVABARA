package mvc.dto;

public class UserDTO {
    private int idx;
    private String id;
    private String pw;
    private String name;
    private String area;
    private String role;
    private String regdate;
    
    public UserDTO() {
    }
    
	public UserDTO(String id, String pw, String name, String role, String regdate) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.role = role;
		this.regdate = regdate;
	}
	public UserDTO(int idx, String id, String pw, String name, String area, String role, String regdate) {
		super();
		this.idx = idx;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.area = area;
		this.role = role;
		this.regdate = regdate;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}
