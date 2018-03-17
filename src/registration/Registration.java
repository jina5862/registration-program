package registration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Registration {
	static Course_details[] CourseList = new Course_details[10];
	static Course_details[] StudentInfo = new Course_details[20];
	static int totalCredit = 0;

	public static void readFile(Course_details[] courses, String filename) throws IOException {
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
					courses[i]=course;
				}
			}
			else if(filename.equals("student_info")) {
				for(int i=0;;i++) {
					String line=br.readLine();
					if(line ==null)break;

					StringTokenizer tk= new StringTokenizer(line, "  ");
					String code = tk.nextToken();
					String name = tk.nextToken();

					Course_details course = new Course_details(code, name);
					courses[i]=course;

				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		finally {
			br.close();}
	}	

	public static boolean prereq_satisfied(Course_details[] student_info,String prereq) {
		for(Course_details courses: student_info) {
			if(courses.getCNo().equals(prereq)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String[] ApplicationList = new String[15] ;
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

			for(int j=0;j<10;j++) {
				if(CourseList[j].getCNo().equals(course)) {
					totalCredit+=CourseList[j].getCredit();
				}
			}
			if(totalCredit>24) {
				System.out.println("신청 가능한 학점(24학점)을 초과하셨습니다. ");
			}

			if(course.equals("done")){
				if(totalCredit<9) {
					System.out.println("신청 가능한 최소 학점은 9학점입니다. ");
				}
				break;
			}

			else{
				ApplicationList[i]= course;
			}
		}
		/*
		for(String course : ApplicationList) {
			for(int i=0;i<10;i++) {
				if(CourseList[i].getCNo().equals(course)) {
					String prereq = CourseList[i].getPrereq();
					if(prereq_satisfied(StudentInfo, prereq)) {
						continue;
					}
					else {
						System.out.println("선수 과목을 수강하지 않으셨습니다! ");
						System.out.println("선수 과목 : "+ prereq);
					}
				}
			}
		}
		 */

		System.out.println("________________________________________________\n");	
		System.out.println("수강신청이 정상적으로 처리되었습니다. \n수강신청 과목 : \n");
		for(int i=0;ApplicationList[i]!=null;i++) {

			System.out.println(ApplicationList[i]);
		}

	}
}
