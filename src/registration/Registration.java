package registration;

import java.util.Scanner;

public class Registration {

	public static void main(String[] args) {
		String[] ApplicationList = new String[15] ; 
		String[] el=new String[3];
		Scanner sc = new Scanner(System.in);
		System.out.println("<2018 1�б� ������û>");
		System.out.println("������û�� ������ �Է��Ͻð� ��� ���� �Է��� ������ 'done' �� �Է��Ͽ� ����� Ȯ���Ͻÿ�");
		System.out.println("________________________________________________\n");
		for(int i=0;;i++) {
			String course=sc.next();
			if(course.equals("done")){break;}
			else{
				ApplicationList[i]= course;
			}
		}
		System.out.println("________________________________________________\n");	
		System.out.println("������û�� ���������� ó���Ǿ����ϴ�. ������û ���� : \n");
		for(int i=0;ApplicationList[i]!=null;i++) {

			System.out.println(ApplicationList[i]);
		}
	
	}
}
