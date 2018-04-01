package registration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Registration {
	static ArrayList<Course_details> CourseList = new ArrayList<Course_details>();
	static ArrayList<Course_details> StudentInfo = new ArrayList<Course_details>();

	// courses_offered 및 student_info 파일을 읽어 들여 ArrayList<Course_details> 로 저장하는 메소드
	public static void readFile(ArrayList<Course_details> courses, String filename) throws IOException {
		String path = "C:/Users/귀욤디욤/Documents/registration-program/src/registration/"+filename;

		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			br.readLine();
			if(filename.equals("courses offered")||filename.equals("student_registration")) {
				while(true) {
					String line=br.readLine();
					if(line ==null) break;

					System.out.println(line);
					StringTokenizer tk= new StringTokenizer(line, "  ");
					String code=tk.nextToken();
					String name=tk.nextToken();
					String time=tk.nextToken();
					String prof=tk.nextToken();
					String room=tk.nextToken();
					String prereq=tk.nextToken();
					int credit= Integer.valueOf(tk.nextToken());

					Course_details course = new Course_details(code, name, time, prof, room, prereq, credit);
					courses.add(course);
				}
			}
			else if(filename.equals("student_info")) {
				while(true){
					String line=br.readLine();
					if(line ==null)break;

					StringTokenizer tk= new StringTokenizer(line, "  ");
					String code = tk.nextToken();

					Course_details course = new Course_details(code);
					courses.add(course);

				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		finally {
			br.close();}
	}	
	// ArrayList<Course_details> 에 특정 과목코드 포함 여부 확인하는 메소드
	public static boolean contains(ArrayList<Course_details> alist, String cname) {
		for(Course_details s : alist) {
			if(s.getCNo().equals(cname)) {
				return true;
			}
		}
		return false;
	}

	//최종 수강신청 정보를 저장하는 메소드
	public static void writeFile(ArrayList<Course_details> courses, String filename) {
		String path = "C:/Users/귀욤디욤/Documents/registration-program/src/registration/"+filename;
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			bw.write("\n");
			for(Course_details cd:courses) {
				bw.write(cd.getCNo()+"  ");
				bw.write(cd.getCTitle()+"  ");
				bw.write(cd.getProf()+"  ");
				bw.write(cd.getCTime()+"  ");
				bw.write(cd.getClassroom()+"  ");
				bw.write(cd.getPrereq()+"  ");
				bw.write(cd.getCredit()+"\n");
			}
			bw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}	
	}

	//선수과목을 수강하였는지 확인하는 메소드
	public static boolean prereq_satisfied(ArrayList<Course_details> AList) {
		for(Course_details course : AList) {
			for(Course_details check : CourseList) {
				if(check.equals(course)) {
					String prereq =check.getPrereq();
					if(prereq.equals("null")) {break;}
					else {
						if(contains(StudentInfo, prereq)) {
							break;
						}
						System.out.println("선수 과목을 수강하지 않으셨습니다. ");
						System.out.println(check.getCNo()+"의 선수 과목 : "+prereq);
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean offered_class(String cNo) {	// 입력한 과목의 개설 여부를 확인하는 메소드
		for(Course_details course : CourseList) {
			if(course.getCNo().equals(cNo)) {return true;}
		}
		return false;
	}


	public static ArrayList<Time> CourseTime(String cTime){	// 강의시간을 String 으로 받아 Time(요일, 시작, 끝) 의 arraylist 로 반환하는 함수 
		int length = cTime.length();
		String time1, time2;			
		String day1, day2;
		StringTokenizer tp= new StringTokenizer(cTime, "/");
		String c= tp.nextToken();

		if(c.length()>length/2) {		// 해당 수업의 시간이 이틀 모두 같다.
			day1 = Character.toString(c.charAt(0));
			time1 =c.substring(2,c.length());
			day2 = Character.toString(c.charAt(1));
			time2 = c.substring(2,c.length());
		}
		else {		// 수업의 시간이 요일별로다르다. 
			day1 = Character.toString(c.charAt(0));
			time1 = c.substring(1,c.length());
			String c2 = tp.nextToken();
			day2 = Character.toString(c2.charAt(0));
			time2 = c2.substring(1, c2.length());
		}

		ArrayList<Time> timee = new ArrayList<Time>();
		Time day1111 = new Time(day1, time1);
		Time day2222 = new Time(day2, time2);
		timee.add(day1111);
		timee.add(day2222);

		return timee;
	}
	// 과목 혹은 강의 시간이 중복되는지 확인하는 메소드
	public static boolean check_Time(ArrayList<Course_details> applicationList) {
		for(int j=0;j<applicationList.size()-1;j++) {

			ArrayList<Time> t1 = CourseTime(applicationList.get(j).getCTime());
			StringTokenizer tk= new StringTokenizer(applicationList.get(j).getCNo(), "-");
			String ctitle= tk.nextToken();

			for(int k=j+1;k<applicationList.size();k++) {
				ArrayList<Time> t2 = CourseTime(applicationList.get(k).getCTime());
				StringTokenizer tk1= new StringTokenizer(applicationList.get(k).getCNo(), "-");
				String ctitle_1=tk1.nextToken();

				if(ctitle.equals(ctitle_1)){		// 동일한 과목인 경우 
					System.out.println(ctitle+" 를 중복 신청하셨습니다. ");
					return false;
				}

				for(Time s : t1) {
					for(Time t : t2) {
						if(Time.overlap(s,t)) {
							System.out.println(applicationList.get(j).getCNo()+"와"+applicationList.get(k).getCNo()+"의 시간이 겹칩니다 ");
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		ArrayList<Course_details> ApplicationList = new ArrayList<Course_details>() ;
		int credit =0;
		Scanner sc = new Scanner(System.in);

		System.out.println("<2018 1학기 수강신청>\n");
		System.out.println("<개설교과목 정보>");

		try {
			readFile(CourseList, "courses offered");
		}catch(Exception e) {
			System.out.println("error occurred");
		}

		try {
			readFile(StudentInfo, "student_info");
		}catch(Exception e) {
			System.out.println("error occurred");
		}

		System.out.println("\n수강신청할 과목 코드를 입력하시고 모든 과목 입력이 끝나면 'done' 를 입력하여 결과를 확인하시오");
		System.out.println("_________________________________________________________\n");

		while(true) {
			credit =0;
			for(int i=0;;i++) {

				System.out.print(i+1+" : ");
				String course=sc.nextLine();
				if(!(offered_class(course)==true||course.equals("done"))) {
					System.out.println("과목 코드를 잘못 입력하셨습니다. 다시 확인하고 입력하세요. ");
					i--;
					continue;
				}
				for(Course_details cNo: CourseList) {
					if(cNo.getCNo().equals(course)) {
						credit+=cNo.getCredit();
					}
				}

				if(credit>24) {
					System.out.println("신청 가능한 학점(24학점)을 초과하셨습니다. ");
					System.out.println("전 입력하신 과목까지 수강신청 됩니다. ");
					credit-=3;
					break;
				}

				if(course.equals("done")){
					if(credit<9) {
						System.out.println("신청 가능한 최소 학점은 9학점입니다. ");
						i-=1;
						System.out.println("추가할 과목코드를 입력하시오. ");
						continue;
					}
					break;
				}

				else{
					for(Course_details s: CourseList) {
						if(s.getCNo().equals(course)) {
							ApplicationList.add(s);
						}
					}
				}
			}

			if(!check_Time(ApplicationList)) {		// 신청한 강의시간의 시간이 중복되는지 확인하는 메소드 호출
				System.out.println("다시 신청하세요. \n\n");	
				while(ApplicationList.size()>0) {
					ApplicationList.remove(0);
				}
				continue;
			}

			if(prereq_satisfied(ApplicationList)==true) {		// 선수 과목 수강 여부 확인하는 메소드 호출
				break;
			}
			else {
				System.out.println("다시 신청하세요. \n\n");			
				while(ApplicationList.size()>0) {				//ApplicationList 초기화
					ApplicationList.remove(0);
				}	
			}
		}
		try {
			writeFile(ApplicationList, "student_registration");		
		} catch(Exception e) {
			System.out.println("error occurred");
		}

		System.out.println("________________________________________________\n");	
		System.out.println("수강신청이 정상적으로 처리되었습니다. 총 "+credit+" 학점 신청하였습니다\n\n"+"수강신청 과목 : \n");
		
		ArrayList<Course_details> RegistrationList = new ArrayList<Course_details>();
		try{
			readFile(RegistrationList, "student_registration");
		} catch(Exception e) {
			System.out.println("exception occurred");
		}

	}
}
