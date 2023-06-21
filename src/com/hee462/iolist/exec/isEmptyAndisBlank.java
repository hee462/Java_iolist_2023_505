package com.hee462.iolist.exec;

public class isEmptyAndisBlank {
	public static void main(String[] args) {
		String nation1 = "";
		//white space  : 다른 문자열이 없이 space만 있음 
		String nation2 = "    ";
		System.out.println(nation1.equals(""));
		System.out.println(nation1.isEmpty());
		System.out.println(nation1.isBlank());
		
		System.out.println(nation2.equals("    "));
		System.out.println(nation2.isEmpty());
		System.out.println(nation2.isBlank());
		// java11 에선 isBlank를 지원하지만 이전버전에서는 isBlank를 사용할수 없다
		// 이대는 문자열을 trim() method를 한번 통과시켜 white space를 제거한다
		System.out.println(nation2.trim().isEmpty());
		
	}
}
