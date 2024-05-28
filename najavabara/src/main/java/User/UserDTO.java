package User;

import java.util.Date;

public class UserDTO {
	int idx;
	String id;
	String password;
	String name;
	String role;
	String area;
	Date regdate;
	
	public UserDTO(int idx, String id, String password, String name, String role, String area, Date regdate) {
		super();
		this.idx = idx;
		this.id = id;
		this.password = password;
		this.name = name;
		this.role = role;
		this.area = area;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
}
