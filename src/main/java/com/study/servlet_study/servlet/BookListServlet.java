package com.study.servlet_study.servlet;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet_study.entity.Book;
import com.study.servlet_study.service.BookService;

@WebServlet("/books")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService;
       
    public BookListServlet() {
        super();
        bookService = BookService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// params가 없으면 싹다 조회
		// bookName -> like조회
		// authorName -> like조회
		// publisherName -> like조회
		
		// bookName 또는 authorName이라면 where조건에 bookName like 조회
		// 여러라면 조건이 다 일치할때 조회
		
		String bookName = request.getParameter("bookName");
		String authorName = request.getParameter("authorName");
		String publisherName = request.getParameter("publisherName");
		
		Map<String, String> params = new HashMap<>();
		if(bookName != null) {
			params.put("bookName", bookName);
		}
		if(authorName != null) {
			params.put("authorName", authorName);
		}
		if(publisherName != null) {
			params.put("publisherName", publisherName);
		}
		List<Book> searchResultBooks = bookService.getSearchBook(params);
		
		response.setContentType("text/plain");
		response.setStatus(201);
		response.getWriter().println(searchResultBooks);
	}

}
