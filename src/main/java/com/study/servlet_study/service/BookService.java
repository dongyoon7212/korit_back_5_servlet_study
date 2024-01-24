package com.study.servlet_study.service;

import java.util.List;
import java.util.Map;

import com.study.servlet_study.entity.Book;
import com.study.servlet_study.repository.BookRepository;

public class BookService {
	private static BookService instance;
	private BookRepository bookRepository;
	
	private BookService() {
		bookRepository = BookRepository.getInstance();
	}
	
	public static BookService getInstance() {
		if (instance == null) {
			instance = new BookService();
		}
		return instance;
	}
	
	public boolean addBook(Book book) {
		return bookRepository.saveBook(book) > 0;
	}
	
	public Book getBook(int bookId) {
		return bookRepository.findBookByBookId(bookId);
	}
	
	public List<Book> getSearchBook(Map<String, String> parmas) {
		return bookRepository.searchBookList(parmas);
	}
	
}
