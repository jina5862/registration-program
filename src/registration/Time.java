package registration;

import java.time.LocalTime;
import java.util.StringTokenizer;

public class Time {
	private String day;
	private LocalTime begin;
	private LocalTime end;
	
	Time(){};	
	Time(String day, String time){		// 날짜와 시간을 따로 받아서 날짜, 시작 시간, 끝나는 시간으로 초기화하는 생성자. 
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
	
	public static boolean overlap(Time time1, Time time2){		// 두 객체의 시간이 겹치는지 확인하는 함수	
		if(!time1.day.equals(time2.day)) {		//요일이 다른 경우 시간 중복 확인 필요 없다. 
			return false;
		}		 
		else {				//요일이 같으면 시간이 중복되는지 확인해야 한다.
			if(time1.begin.isBefore(time2.begin)) {	//time1 이 time2 보다 일찍 시작하는데 time2 시작 시간보다 늦게 끝나거나 맞물리는 경우 return false
				if(time2.begin.isAfter(time1.end) || time2.begin.compareTo(time1.end)==0) {
					return false;
				}
			}			
			else {			//time1 이 time2 보다 늦게 시작하는데 time2 가 끝나기 전에 시작하거나 맞물리는 경우 return false
				if(time1.begin.isAfter(time2.end) || time1.begin.compareTo(time2.end)==0) {
					return false;
				}
			}
			return true;
		}
	}
}


