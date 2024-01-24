package com.study.servlet_study.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;
import com.study.servlet_study.service.BookService;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookService bookService;

    public BookServlet() {
        super();
        bookService = BookService.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strBookId = request.getParameter("bookId");
		int bookId = 0;
		
		if(strBookId != null) {
			try {
				bookId = Integer.parseInt(strBookId);
			} catch (NumberFormatException e) {
				response.setContentType("text/plain");
				response.setStatus(400);
				response.getWriter().println("잘못된 형식의 데이터입니다.");
				return;
			}
		}
		
		Book book = bookService.getBook(bookId);
		
		response.setContentType("text/plain");
		response.setStatus(200);
		response.getWriter().println(book);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookName = request.getParameter("bookName");
		String authorName = request.getParameter("authorName");
		String publisherName = request.getParameter("publisherName");
		
		Book book = Book.builder()
				.bookName(bookName)
				.author(Author.builder().authorName(authorName).build())
				.publisher(Publisher.builder().publisherName(publisherName).build())
				.build();
		
		boolean insertStatus = bookService.addBook(book);
		response.setContentType("text/plain");
		response.setStatus(201);
		response.getWriter().println(insertStatus);
	}

}
