package com.study.servlet_study.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getMethod()); // 메소드를 출력
		System.out.println(request.getRequestURL()); // 전체 서버주소와 함께 출력
		System.out.println(request.getRequestURI()); // 서버주소를 제외하고 출력
		System.out.println(response.getStatus()); // 응답 상태를 출력
		
		response.setContentType("text/plain");
//		response.setCharacterEncoding("utf-8"); // 한글로 출력하려면 기본적으로 utf-8이어야 한다.
		System.out.println(response.getContentType()); // null이면 응답된 데이터가 없다는 뜻
		
		response.getWriter().println("헬로"); // Hello라는 문자열을 출력 -> 요청을 했을때 응답한 데이터 ( 응답 body )
		
		System.out.println("요청이 들어옴!!");

	}

}

// 클라이언트에서 서버로 요청 -> 톰캣이 요청을 받고 해당 요청 메소드가 있는지 확인 -> 다시 클라이언트로 응답
// 톰캣이 요청과 응답 매개변수를 생성 -> 그리고 필터가 있는지 확인
// 해당 메소드가 없다면 404에러
// 중간에 filter가 있다면 무조건 거쳐서 가게 됨

// filter
// 클라이언트에서 요청 -> 서버로 와서 전처리 -> doGet -> 후처리