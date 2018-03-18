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

	public static boolean prereq_satisfied(ArrayList<String> AList) {
		for(String course : AList) {
			for(Course_details check : CourseList) {
				if(check.getCNo().equals(course)) {
					String prereq =check.getPrereq();
					Course_details checkD = new Course_details(prereq);
					if(prereq.equals("null")) {break;}
					else {
						if(StudentInfo.contains(checkD)!=true) {
							System.out.println("���� ������ �������� �����̽��ϴ�. ");
							System.out.println(check.getCNo()+"�� ���� ���� : "+prereq);
							return false;
						}
					}
				}
			}
		}
		return true;
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

		while(true) {
			for(int i=0;;i++) {
				System.out.print(i+1+" : ");
				String course=sc.next();
				if(!(offered_class(course)==true||course.equals("done"))) {
					System.out.println("���� �ڵ带 �߸� �Է��ϼ̽��ϴ�. �ٽ� Ȯ���Ͻð� �Է��ϼ���. ");
					i--;
					continue;
				}
				for(Course_details cNo: CourseList) {
					if(cNo.getCNo().equals(course)) {
						credit+=cNo.getCredit();
					}
				}
				if(credit>24) {
					System.out.println("��û ������ ����(24����)�� �ʰ��ϼ̽��ϴ�. ");
				}

				if(course.equals("done")){
					if(credit<9) {
						System.out.println("��û ������ �ּ� ������ 9�����Դϴ�. ");
					}
					break;
				}

				else{
					ApplicationList.add(course);
				}

			}
			
			if(prereq_satisfied(ApplicationList)==true) {
				break;
			}
			else {
				System.out.println("�ٽ� ��û�ϼ���. ");
				int a =ApplicationList.size();
				for(int j=0;j<a;j++) {
					ApplicationList.remove(j);
				}
				
			}
		}

		System.out.println("�� "+credit+" ������ ��û�Ͽ����ϴ�. \n");
		System.out.println("________________________________________________\n");	

		// ���� ���� ���� ���� Ȯ��


		System.out.println("________________________________________________\n");	
		System.out.println("������û�� ���������� ó���Ǿ����ϴ�. \n������û ���� : \n");
		for(String cno: ApplicationList) {
			System.out.println(cno);
		}

	}
}
