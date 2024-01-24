package com.study.servlet_study.service;

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
	
}
