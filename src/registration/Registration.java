package registration;

import java.util.Scanner;

public class Registration {

	public static void main(String[] args) {
		String[] ApplicationList = new String[15] ; 
		String[] el=new String[3];
		Scanner sc = new Scanner(System.in);
		System.out.println("<2018 1학기 수강신청>");
		System.out.println("수강신청할 과목을 입력하시고 모든 과목 입력이 끝나면 'done' 를 입력하여 결과를 확인하시오");
		System.out.println("________________________________________________\n");
		for(int i=0;;i++) {
			String course=sc.next();
			if(course.equals("done")){break;}
			else{
				ApplicationList[i]= course;
			}
		}
		System.out.println("________________________________________________\n");	
		System.out.println("수강신청이 정상적으로 처리되었습니다. 수강신청 과목 : \n");
		for(int i=0;ApplicationList[i]!=null;i++) {

			System.out.println(ApplicationList[i]);
		}
	
	}
}
