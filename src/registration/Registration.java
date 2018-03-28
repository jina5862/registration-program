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

	public static boolean offered_class(String cNo) {	// �Է��� ������ ���� ���θ� Ȯ���ϴ� �޼ҵ�
		for(Course_details course : CourseList) {
			if(course.getCNo().equals(cNo)) {return true;}
		}
		return false;
	}


	public static ArrayList<Time> CourseTime(String cTime){	// ���ǽð��� String ���� �޾� Time(����, ����, ��) �� arraylist �� ��ȯ�ϴ� �Լ� 
		int length = cTime.length();
		String time1, time2;			
		String day1, day2;
		StringTokenizer tp= new StringTokenizer(cTime, "/");
		String c= tp.nextToken();

		if(c.length()>length/2) {		// �ش� ������ �ð��� ��Ʋ ��� ����.
			day1 = Character.toString(c.charAt(0));
			time1 =c.substring(2,c.length());
			day2 = Character.toString(c.charAt(1));
			time2 = c.substring(2,c.length());
		}
		else {		// ������ �ð��� ���Ϻ��δٸ���. 
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

	public static boolean check_Time(ArrayList<Course_details> applicationList) {
		for(int j=0;j<applicationList.size()-1;j++) {

			ArrayList<Time> t1 = CourseTime(applicationList.get(j).getCTime());
			StringTokenizer tk= new StringTokenizer(applicationList.get(j).getCNo(), "-");
			String ctitle= tk.nextToken();

			for(int k=j+1;k<applicationList.size();k++) {
				ArrayList<Time> t2 = CourseTime(applicationList.get(k).getCTime());
				StringTokenizer tk1= new StringTokenizer(applicationList.get(k).getCNo(), "-");
				String ctitle_1=tk1.nextToken();

				if(ctitle.equals(ctitle_1)){		// ������ ������ ��� 
					System.out.println(ctitle+" �� �ߺ� ��û�ϼ̽��ϴ�. ");
					return false;
				}
				
				for(Time s : t1) {
					for(Time t : t2) {
						if(Time.overlap(s,t)) {
							System.out.println(applicationList.get(j).getCNo()+"��"+applicationList.get(k).getCNo()+"�� �ð��� ��Ĩ�ϴ� ");
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
					System.out.println("���� �ڵ带 �߸� �Է��ϼ̽��ϴ�. �ٽ� Ȯ���ϰ� �Է��ϼ���. ");
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

			if(!check_Time(ApplicationList)) {		// ��û�� ���ǽð��� �ð��� �ߺ��Ǵ��� Ȯ���ϴ� �޼ҵ� ȣ��
				System.out.println("�ٽ� ��û�ϼ���. \n\n");	
				while(ApplicationList.size()>0) {
					ApplicationList.remove(0);
				}
				continue;
			}

			if(prereq_satisfied(ApplicationList)==true) {		// ���� ���� ���� ���� Ȯ���ϴ� �޼ҵ� ȣ��
				break;
			}
			else {
				System.out.println("�ٽ� ��û�ϼ���. \n\n");			
				while(ApplicationList.size()>0) {				//ApplicationList �ʱ�ȭ
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
