package registration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Registration {

	public static void readFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\귀욤디욤\\Documents\\registration-program\\src\\registration\\courses offered"));
		try {
			while(true) {
				String line = br.readLine();
				if (line==null) break;
				System.out.println(line);
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

		System.out.println("<2018 1학기 수강신청>\n");
		System.out.println("<개설교과목 정보>");
		try {
			readFile();
		}catch(Exception e) {
			System.out.println("error occurred");
		}
		
		System.out.println("\n수강신청할 과목을 입력하시고 모든 과목 입력이 끝나면 'done' 를 입력하여 결과를 확인하시오");
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
		System.out.println("수강신청이 정상적으로 처리되었습니다. \n수강신청 과목 : \n");
		for(int i=0;ApplicationList[i]!=null;i++) {

			System.out.println(ApplicationList[i]);
		}

	}
}
