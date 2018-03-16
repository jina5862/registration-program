package registration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Registration {
	static String[] CourseList = new String[10];
	
	public static void readFile() throws IOException {
		
		int i=0;
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\�Ϳ���\\Documents\\registration-program\\src\\registration\\courses offered"));
		try {
			while(true) {
				String line = br.readLine();
				if (line==null) break;
				System.out.println(line);
				StringTokenizer tk= new StringTokenizer(line, " ");
				String token;
				token = tk.nextToken();
				CourseList[i]=token;
				i++;
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		finally {
			br.close();}
	}	
	
	public static void main(String[] args) {
		String[] ApplicationList = new String[15] ;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("<2018 1�б� ������û>\n");
		System.out.println("<���������� ����>");
		try {
			readFile();
		}catch(Exception e) {
			System.out.println("error occurred");
		}
		System.out.println(CourseList.length);
		
		System.out.println("\n������û�� ������ �Է��Ͻð� ��� ���� �Է��� ������ 'done' �� �Է��Ͽ� ����� Ȯ���Ͻÿ�");
		System.out.println("_________________________________________________________\n");
		for(int i=0;;i++) {
			System.out.print(i+1+" : ");
			String course=sc.next();
			if(course.equals("done")){break;}
			else{
				ApplicationList[i]= course;
			}
		}
		
		
		System.out.println("________________________________________________\n");	
		System.out.println("������û�� ���������� ó���Ǿ����ϴ�. \n������û ���� : \n");
		for(int i=0;ApplicationList[i]!=null;i++) {

			System.out.println(ApplicationList[i]);
		}

	}
}
