package com.study.servlet_study.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet_study.utils.ParamsConverter;

@WebServlet("/http")
public class HttpStudyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HttpStudyServlet() {
        super(); 
    }
    
    // HTTP 메소드
    // CRUD 4가지
    // POST요청   -> C reate(추가)
    // GET요청    -> R ead(삭제)
    // PUT요청    -> U pdate(수정)
    // DELETE요청 -> D elete(삭제)
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // 클라이언트 -> 서버 -> DB
//		request.setCharacterEncoding("utf-8"); // 요청때도 인코딩을 설정해줘야한다.
		Map<String, String> paramsMap = new HashMap<>();
		
		request.getParameterMap().forEach((k, v) -> { // 모든 데이터는 문자열로 들어온다. 숫자도 문자열로 들어온다.
			StringBuilder builder = new StringBuilder(); // 비어있는 문자열을 만들 수 있는 공간을 만든다.
			
			Arrays.asList(v).forEach(value -> builder.append(value)); // 배열을 리스트로 넣으면 List<S>로 된다.
			// forEach로 builder.append를 통해 value를 넣어준다.
			
			paramsMap.put(k, builder.toString());
		});
		
		System.out.println(paramsMap);
//		System.out.println(request.getParameter("name")); // 이렇게 할 수 있지만 키를 다 알아야한다.
		
		Map<String, String> paramsMap2 = new HashMap<>();
		Iterator<String> ir = request.getParameterNames().asIterator(); // 요청에 들어온 이름들을 Iterator객체로 변환
		while(ir.hasNext()) {
			String key = ir.next();
			paramsMap2.put(key, request.getParameter(key));
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paramsMap = ParamsConverter.convertParamsMapToMap(request.getParameterMap());
				
		System.out.println(paramsMap);
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
