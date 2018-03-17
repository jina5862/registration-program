package registration;

public class Course_details {
	private String cNo;
	private String cTitle;
	private String cTime;
	private String prof;
	private String classroom;
	private String prerequisites;
	private int credit;
	
	Course_details(){};
	Course_details(String cNo, String cTitle){
		this.cNo = cNo;
		this.cTitle=cTitle;
	}
	Course_details(String cNo, String cTitle, String cTime, String prof, 
			String classroom, String prerequisites, int credit) {
		this.cNo= cNo;
		this.cTitle=cTitle;
		this.cTime=cTime;
		this.prof=prof;
		this.classroom=classroom;
		this.prerequisites=prerequisites;
		this.credit=credit;
		
	}
	public String getCNo() {
		return this.cNo;
	}
	public String getPrereq() {
		return this.prerequisites;
	}
	public int getCredit() {
		return this.credit;
	}

}
