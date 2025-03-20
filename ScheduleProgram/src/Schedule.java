import java.util.GregorianCalendar;
//import java.io.Serializable;

public class Schedule implements java.io.Serializable  {
	//1. 필드
	private int serialNumber; //개별 일정 구분을 위한 일련번호
	private String lable; //일정 이름
	private char[] categories = { 's', 'f', 'w', 'p'}; //카테고리
	private char category;
	private GregorianCalendar start, end;
	private int importance;//중요도 1~5
	private boolean isCompleted = false; //완료 여부
	private String memo;//메모
	
	private static final long serialVersionUID = 1L; //역직렬화
	
	//2. 생성자(라벨, 타입, 시작, 끝, 메모, 중요도)
	public Schedule(String lable, char category, GregorianCalendar start, 
			GregorianCalendar end, String memo, int importance) {
		this.lable = lable;
		this.category = category;
		this.start = start;
		this.end = end;
		this.memo = memo;
		this.importance = importance;
	}
	public Schedule() {
		
	}

	//3. 메서드
	//3-1. get&set 메서드
 	public int getSerialNumber() {
		return serialNumber;
	}
	
 	public void setSerialNumber (int serialNumber) {
		this.serialNumber = serialNumber;
 	}
 	
	public String getLabel() {
		return lable;
	}
	
	public void setLable(String lable) {
		this.lable = lable;
	}
	
	public char[] getCategories() {
		return categories;
	}
	
	public char getCategoryElement(int index) { //필요?
		return categories[index];
	}
	
	public void setCategoryArr(int index, char category) {
		categories[index] = category;
	}
	
	public char getCategory() {
		return category;
	}
	
	public void setCategory(char category) {
		this.category = category;
	}
	
	public GregorianCalendar getStart() {
		return start;
	}
	
	public void setStart(GregorianCalendar start) {
		this.start = start;
	}
	
	public GregorianCalendar getEnd() {
		return end;
	}
	
	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}
	
	public boolean getIsCompleted() {
		return isCompleted;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public int getImportance() {
		return importance;
	}
	
	void setImportance(int importance) {
		this.importance = importance;
	}

	//3-2. 기타 메서드
	String showImportance() { //중요도를 별로 가시적 표현
		String star = "";
		for(int i = 0; i < importance; i++) {
			star += "★";
		}
		for (int i = 0; i < 5 - importance; i++) {
			star += "☆";
		}
		return star;
	}
	
	char showIsCompleted() {
		char box = '□';
		if(isCompleted) {
			box = '■';
		}
		return box;
	}
	
	boolean equals(Schedule s) {
		if(s.lable.contains(this.lable) ) {
			if( this.memo != null && s.lable.contains(this.memo) ) 
				return true;
			else
				return true;
		}
		return false;
	}
	
	/*
    public String toString() {
        return "Schedule{" +
                "serialNumber=" + serialNumber +
                ", label='" + getLabel() + '\'' +
                ", category=" + category +
                ", start=" + start.getTime() +
                ", end=" + end.getTime() +
                ", importance=" + importance +
                ", isCompleted=" + isCompleted +
                ", memo='" + memo + '\'' +
                '}';
    }
    */
}

