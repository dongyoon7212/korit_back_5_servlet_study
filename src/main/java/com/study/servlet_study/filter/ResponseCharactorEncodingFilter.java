package com.study.servlet_study.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*") // 모든 경로를 의미
public class ResponseCharactorEncodingFilter extends HttpFilter implements Filter {
	
	public void destroy() {
		
	}
	
	// httpServletRequest의 하위 -> 업캐스팅 된 상태 ( 다운캐스팅 가능 )
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request; // 다운캐스팅
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// 이 위로는 전처리 과정
		httpResponse.setCharacterEncoding("utf-8"); // 모든 요청에 이 설정을 적용
		
		chain.doFilter(request, response); // 최종 필터를 호출 -> 최종 필터가 서브릿의 doGet호출
		
		// 이 밑으로는 후처리 과정
//		httpResponse.getWriter().println("무조건 후처리함");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
