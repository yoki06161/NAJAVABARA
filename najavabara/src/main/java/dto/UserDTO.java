package dto;

public class UserDTO {
	private int idx;
	private String id;
	private String pw;
	private String name;
	private String area;
	private String role;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	private String regDate;
	
	// 기본 생성자
	public UserDTO() {
		
	}
	
	public UserDTO(int idx, String id, String pw, String name, String area, String regDate) {
		super();
		this.idx = idx;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.area = area;
		this.regDate = regDate;
	}
	public UserDTO(String id, String pw, String name, String area) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.area = area;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegdate(String regDate) {
		this.regDate = regDate;
	}
}
