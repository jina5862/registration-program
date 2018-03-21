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

	// courses_offered �� student_info ������ �о� �鿩 ArrayList<Course_details> �� �����ϴ� �޼ҵ�
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
	// ArrayList<Course_details> �� Ư�� �����ڵ� ���� ���� Ȯ���ϴ� �޼ҵ�
	public static boolean contains(ArrayList<Course_details> alist, String cname) {
		for(Course_details s : alist) {
			if(s.getCNo().equals(cname)) {
				return true;
			}
		}
		return false;
	}

	//���������� �����Ͽ����� Ȯ���ϴ� �޼ҵ�
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
						System.out.println("���� ������ �������� �����̽��ϴ�. ");
						System.out.println(check.getCNo()+"�� ���� ���� : "+prereq);
						return false;
					}
				}
			}
		}
		return true;
	}



	// �Է��� ������ ���� ���θ� Ȯ���ϴ� �޼ҵ�
	public static boolean offered_class(String cNo) {
		for(Course_details course : CourseList) {
			if(course.getCNo().equals(cNo)) {return true;}
		}
		return false;
	}

	// ���ǽð��� �ߺ��Ǵ��� Ȯ���ϴ� �޼ҵ�
	public static boolean check_Time(ArrayList<Course_details> applicationList) {

		for(int j=0;j<applicationList.size()-1;j++) {
			for(int k=j+1;k<applicationList.size();k++) {
				if(applicationList.get(j).getCTime().equals(applicationList.get(k).getCTime())){
					System.out.println(applicationList.get(j).getCNo()+" �� "+applicationList.get(k).getCNo()+" �� �ð��� ��Ĩ�ϴ�. ");
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
			credit =0;
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
					System.out.println("�� �Է��Ͻ� ������� ������û �˴ϴ�. ");
					credit-=3;
					break;
				}

				if(course.equals("done")){
					if(credit<9) {
						System.out.println("��û ������ �ּ� ������ 9�����Դϴ�. ");
						i-=1;
						System.out.println("�߰��� �����ڵ带 �Է��Ͻÿ�. ");
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
			
			// ��û�� ���ǽð��� �ð��� �ߺ��Ǵ��� Ȯ���ϴ� �޼ҵ� ȣ��
			if(!check_Time(ApplicationList)) {
				System.out.println("�ٽ� ��û�ϼ���. \n\n");	
				while(ApplicationList.size()>0) {
					ApplicationList.remove(0);
				}
				continue;
			}


			// ���� ���� ���� ���� Ȯ���ϴ� �޼ҵ� ȣ��
			if(prereq_satisfied(ApplicationList)==true) {
				break;
			}
			else {
				System.out.println("�ٽ� ��û�ϼ���. \n\n");			
				//ApplicationList �ʱ�ȭ
				while(ApplicationList.size()>0) {
					ApplicationList.remove(0);
				}	
			}
		}

		System.out.println("________________________________________________\n");	


		System.out.println("������û�� ���������� ó���Ǿ����ϴ�. �� "+credit+" ���� ��û�Ͽ����ϴ�\n\n"+"������û ���� : \n");
		for(Course_details cno: ApplicationList) {
			cno.getAllInfo();
		}

	}
}
