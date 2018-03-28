package registration;

import java.time.LocalTime;
import java.util.StringTokenizer;

public class Time {
	private String day;
	private LocalTime begin;
	private LocalTime end;
	
	Time(){};	
	Time(String day, String time){		// ��¥�� �ð��� ���� �޾Ƽ� ��¥, ���� �ð�, ������ �ð����� �ʱ�ȭ�ϴ� ������. 
		StringTokenizer tk = new StringTokenizer(time, "-");
		String begin1 = tk.nextToken();
		String end1 = tk.nextToken();
		StringTokenizer tk_1 = new StringTokenizer(begin1, ":");
		LocalTime time_b = LocalTime.of(Integer.valueOf(tk_1.nextToken()), Integer.valueOf(tk_1.nextToken()));
		StringTokenizer tk_2 = new StringTokenizer(end1, ":");
		LocalTime time_e = LocalTime.of(Integer.valueOf(tk_2.nextToken()), Integer.valueOf(tk_2.nextToken()));

		this.day = day;
		this.begin = time_b;
		this.end = time_e;			
	}	
	
	public static boolean overlap(Time time1, Time time2){		// �� ��ü�� �ð��� ��ġ���� Ȯ���ϴ� �Լ�	
		if(!time1.day.equals(time2.day)) {		//������ �ٸ� ��� �ð� �ߺ� Ȯ�� �ʿ� ����. 
			return false;
		}		 
		else {				//������ ������ �ð��� �ߺ��Ǵ��� Ȯ���ؾ� �Ѵ�.
			if(time1.begin.isBefore(time2.begin)) {	//time1 �� time2 ���� ���� �����ϴµ� time2 ���� �ð����� �ʰ� �����ų� �¹����� ��� return false
				if(time2.begin.isAfter(time1.end) || time2.begin.compareTo(time1.end)==0) {
					return false;
				}
			}			
			else {			//time1 �� time2 ���� �ʰ� �����ϴµ� time2 �� ������ ���� �����ϰų� �¹����� ��� return false
				if(time1.begin.isAfter(time2.end) || time1.begin.compareTo(time2.end)==0) {
					return false;
				}
			}
			return true;
		}
	}
}


