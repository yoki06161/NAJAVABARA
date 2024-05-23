package dto;

public class RegionLikeDTO {
	private int likenum;
	private String id;
	private String likedate;
	private int num;
	public RegionLikeDTO(int likenum, String id, int num) {
		super();
		this.likenum = likenum;
		this.id = id;
		this.num = num;
	}
	// 기본 생성자
	public RegionLikeDTO() {

	}
	public int getLikenum() {
		return likenum;
	}
	public void setLikenum(int likenum) {
		this.likenum = likenum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLikedate() {
		return likedate;
	}
	public void setLikedate(String likedate) {
		this.likedate = likedate;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
