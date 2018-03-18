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

	public static boolean prereq_satisfied(ArrayList<Course_details> student_info,String prereq) {
		Course_details check = new Course_details(prereq);
		if(student_info.contains(check)) {
			return true;
		}
		else return false;
	}
	
	public static boolean offered_class(String cNo) {
		for(Course_details course : CourseList) {
			if(course.getCNo().equals(cNo)) {return true;}
		}
		return false;
	}
	public static void main(String[] args) {
		ArrayList<String> ApplicationList = new ArrayList<String>() ;
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
			}

			if(course.equals("done")){
				if(credit<9) {
					System.out.println("신청 가능한 최소 학점은 9학점입니다. ");
				}
				break;
			}

			else{
				ApplicationList.add(course);
			}
		}
		
		System.out.println("총 "+credit+" 학점을 신청하였습니다. \n");
		System.out.println("________________________________________________\n");	

		// 선수 과목 수강 여부 확인
		for(String course : ApplicationList) {
			for(Course_details check : CourseList) {
				if(check.getCNo().equals(course)) {
					String prereq =check.getPrereq();
					if(prereq.equals("null")) {break;}
					else {
						if(prereq_satisfied(StudentInfo,prereq )==false) {
							System.out.println("선수 과목을 수강하지 않으셨습니다. ");
							System.out.println(check.getCNo()+"의 선수 과목 : "+prereq);
						}
					}
				}
			}
		}

		System.out.println("________________________________________________\n");	
		System.out.println("수강신청이 정상적으로 처리되었습니다. \n수강신청 과목 : \n");
		for(String cno: ApplicationList) {
			System.out.println(cno);
		}

	}
}
