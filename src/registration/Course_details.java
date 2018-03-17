package registration;

public class Course_details {
	String cNo;
	String cTitle;
	String cTime;
	String prof;
	String classroom;
	String prerequisites;
	
	Course_details(){};
	Course_details(String cNo, String cTitle, String cTime, String prof, 
			String classroom, String prerequisites) {
		this.cNo= cNo;
		this.cTitle=cTitle;
		this.cTime=cTime;
		this.prof=prof;
		this.classroom=classroom;
		this.prerequisites=prerequisites;
		
	}

}
