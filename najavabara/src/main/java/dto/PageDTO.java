package dto;
// https://rsorry.tistory.com/198 PageVO

public class PageDTO {
	private int startPage; // ����¡ ù��° ��ȣ
	private int endPage; // ����¡ ������ ��ȣ
	private boolean prev, next; // ������ư, ������ư Ȱ��ȭ����

	private int pageNum; // ���� ��ȸ�ϴ� ��������ȣ
	private int amount; // ȭ�� ����Ʈ ���
	private int total; // ��ü ������ ��

	// �����ڿ����� ��ü�� �����ɶ� ����� ó��
	public PageDTO(int pageNum, int amount, int total) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.total = total;

		// 1. endPage����
		//		 ex) ��ȸ�ϴ� ������ 1 -> ����ȣ 10
		//		 ex) ��ȸ�ϴ� ������ 9 -> ����ȣ 10
		//		 ex) ��ȸ�ϴ� ������ 11 -> ����ȣ 20
		//		 ���� = (int)Math.ceil(��������ȣ / ���������̼ǰ���) * ���������̼ǰ���
		this.endPage = (int)Math.ceil(this.pageNum * 0.1) * 10;

		// 2. startPage����
		// ���� = �������� - ���������̼ǰ��� + 1
		this.startPage = this.endPage - 10 + 1;

		// 3. realEnd(��¥ ����ȣ) ���ؼ� endPage�� ���� �ٽ� ����
		//		 ���� �Խñ��� 52����� -> ��¥ ����ȣ 6
		//		 ���� �Խñ��� 105����� -> ��¥ ����ȣ 11
		//		 ���� = (int)Math.ceil(��ü�Խñۼ� / ȭ�鿡�����������Ͱ���)
		int realEnd = (int)Math.ceil(this.total / (double)this.amount);

		//		 ������������ �������� �� �������� �ϴ� ����ȣ�� �޶����ϴ�.
		//		 ex) 131�� �Խù�
		//		 1�� ������ Ŭ���� -> endPage = 10, realEnd = 14 ( endPage�� ���� )
		//		 11�� ������ Ŭ���� -> endPage = 20, realEnd = 14 ( realEnd�� ���� )
		if(this.endPage > realEnd) {
			this.endPage = realEnd;
		}

		// 4. prev���� ( startPage�� ��ȣ�� 1, 11, 21... )
		this.prev = this.startPage > 1;

		// 5. next����
		//		 ex: 131�� �Խù�
		//		 1~10 Ŭ���� endPage = 10, realEnd = 14 -> ������ư true
		//		 11 Ŭ���� endPage = 14 , realEnd = 14 -> ������ư false
		this.next = this.endPage < realEnd;

		// Ȯ��
		System.out.println("����������:" + this.startPage + ", ��������:" + this.endPage); 
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
