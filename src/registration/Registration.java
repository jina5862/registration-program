package registration;

import java.io.BufferedReader;
import java.io.FileReader;
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
			if(filename.equals("courses offered")) {
				for(int i=0;;i++) {
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
				for(int i=0;;i++) {
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



	// 입력한 과목의 개설 여부를 확인하는 메소드
	public static boolean offered_class(String cNo) {
		for(Course_details course : CourseList) {
			if(course.getCNo().equals(cNo)) {return true;}
		}
		return false;
	}

	// 강의시간이 중복되는지 확인하는 메소드
	public static boolean check_Time(ArrayList<Course_details> applicationList) {

		for(int j=0;j<applicationList.size()-1;j++) {
			for(int k=j+1;k<applicationList.size();k++) {
				if(applicationList.get(j).getCTime().equals(applicationList.get(k).getCTime())){
					System.out.println(applicationList.get(j).getCNo()+" 와 "+applicationList.get(k).getCNo()+" 의 시간이 겹칩니다. ");
					return false;
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
				String course=sc.next();
				if(!(offered_class(course)==true||course.equals("done"))) {
					System.out.println("과목 코드를 잘못 입력하셨습니다. 다시 확인하시고 입력하세요. ");
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
			
			// 신청한 강의시간의 시간이 중복되는지 확인하는 메소드 호출
			if(!check_Time(ApplicationList)) {
				System.out.println("다시 신청하세요. \n\n");	
				while(ApplicationList.size()>0) {
					ApplicationList.remove(0);
				}
				continue;
			}


			// 선수 과목 수강 여부 확인하는 메소드 호출
			if(prereq_satisfied(ApplicationList)==true) {
				break;
			}
			else {
				System.out.println("다시 신청하세요. \n\n");			
				//ApplicationList 초기화
				while(ApplicationList.size()>0) {
					ApplicationList.remove(0);
				}	
			}
		}

		System.out.println("________________________________________________\n");	


		System.out.println("수강신청이 정상적으로 처리되었습니다. 총 "+credit+" 학점 신청하였습니다\n\n"+"수강신청 과목 : \n");
		for(Course_details cno: ApplicationList) {
			cno.getAllInfo();
		}

	}
}
