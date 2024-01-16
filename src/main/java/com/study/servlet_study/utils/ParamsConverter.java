package com.study.servlet_study.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParamsConverter {
	
	public static Map<String, String> convertParamsMapToMap(Map<String, String[]> paramsMap) {
		Map<String, String> map = new HashMap<>();
		
		paramsMap.forEach((k, v) -> { // 모든 데이터는 문자열로 들어온다. 숫자도 문자열로 들어온다.
			StringBuilder builder = new StringBuilder(); // 비어있는 문자열을 만들 수 있는 공간을 만든다.
					
			Arrays.asList(v).forEach(value -> builder.append(value)); // 배열을 리스트로 넣으면 List<S>로 된다.
					// forEach로 builder.append를 통해 value를 넣어준다.
					
			map.put(k, builder.toString());
		});
		
		return map;
	}
}
