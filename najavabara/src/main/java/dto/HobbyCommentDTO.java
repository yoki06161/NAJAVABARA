package dto;

public class HobbyCommentDTO {
	private int numx;
	private int num;
	private String content;
	private String postdate;
	private String id;
	
	public HobbyCommentDTO() {
	}
	
	
	
	public HobbyCommentDTO(int num) {
		super();
		this.num = num;
	}



	public HobbyCommentDTO(String content) {
		super();
		this.content = content;
	}



	public HobbyCommentDTO(String id, int num, String content) {
		super();
		this.id = id;
		this.num = num;
		this.content = content;
	}


	public HobbyCommentDTO(int numx, int num, String id, String postdate, String content) {
		super();
		this.numx = numx;
		this.num = num;
		this.id = id;
		this.postdate = postdate;
		this.content = content;
	}

	public int getNumx() {
		return numx;
	}

	public void setNumx(int numx) {
		this.numx = numx;
	}

	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getPostdate() {
		return postdate;
	}


	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BoardCTO [numx=" + numx + ", num=" + num + ", content=" + content + ", postdate=" + postdate + ", id="
				+ id + "]";
	}


}
