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
		String path = "C:/Users/�Ϳ���/Documents/registration-program/src/registration/"+filename;

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

		System.out.println("<2018 1�б� ������û>\n");
		System.out.println("<���������� ����>");

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

		System.out.println("\n������û�� ���� �ڵ带 �Է��Ͻð� ��� ���� �Է��� ������ 'done' �� �Է��Ͽ� ����� Ȯ���Ͻÿ�");
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
				System.out.println("��û ������ ����(24����)�� �ʰ��ϼ̽��ϴ�. ");
			}

			if(course.equals("done")){
				if(totalCredit<9) {
					System.out.println("��û ������ �ּ� ������ 9�����Դϴ�. ");
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
						System.out.println("���� ������ �������� �����̽��ϴ�! ");
						System.out.println("���� ���� : "+ prereq);
					}
				}
			}
		}
		 */

		System.out.println("________________________________________________\n");	
		System.out.println("������û�� ���������� ó���Ǿ����ϴ�. \n������û ���� : \n");
		for(int i=0;ApplicationList[i]!=null;i++) {

			System.out.println(ApplicationList[i]);
		}

	}
}
