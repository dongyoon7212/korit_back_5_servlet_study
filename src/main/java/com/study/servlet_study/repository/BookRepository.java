package com.study.servlet_study.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;

public class BookRepository {
	private static BookRepository instance;
	private DBConnectionMgr pool;
	
	private BookRepository() {
		pool = DBConnectionMgr.getInstance();
	}
	
	public static BookRepository getInstance() {
		if (instance == null) {
			instance = new BookRepository();
		}
		return instance;
	}
	
	// doPost의 책 추가 로직
	public int saveBook(Book book) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = pool.getConnection();
			String sql = "insert into author_tb values (0, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getAuthor().getAuthorName());
			pstmt.executeUpdate(); // insert, update, delete 실행 후 몇건을 성공했는지 리턴(int)
			ResultSet rs = pstmt.getGeneratedKeys(); // 추가 후 자동 증가된 키값을 불러옴
			if(rs.next()) {
				book.getAuthor().setAuthorId(rs.getInt(1)); // 추가된 현재 주소의 author에 id를 수정
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		try {
			con = pool.getConnection();
			String sql = "insert into publisher_tb values (0, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getPublisher().getPublisherName());
			pstmt.executeUpdate(); 
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				book.getPublisher().setPublisherId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		try {
			con = pool.getConnection();
			String sql = "insert into book_tb values (0, ?, ?, ?)"; // book_name, publisher_id, author_id
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getBookName());
			pstmt.setInt(2, book.getAuthor().getAuthorId());
			pstmt.setInt(3, book.getPublisher().getPublisherId());
			pstmt.executeUpdate(); 
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				book.setBookId(rs.getInt(1));;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		return 1;
	}
	
	// doGet의 책 찾기 로직
	public Book findBookByBookId(int bookId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Book findBook =null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from book_view where book_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bookId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				findBook = Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(Author.builder()
								.authorId(rs.getInt(3))
								.authorName(rs.getString(4))
								.build())
						.publisher(Publisher.builder()
								.publisherId(rs.getInt(5))
								.publisherName(rs.getString(6))
								.build())
						.build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return findBook;
	}
	
	public List<Book> searchBookList(Map<String, String> params) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<>();
		
		Map<String, String> keyData = new HashMap<>();
		keyData.put("bookName", "book_name");
		keyData.put("authorName", "author_name");
		keyData.put("publisherName", "publisher_name");

		
		try {
			con = pool.getConnection();
			String sql = "select * from book_view where ? = 1";
			for (String key : params.keySet()) {
				sql += " or " + keyData.get(key) + " like ?";
			}
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,  params.isEmpty() ? 1 : 0);
			
			int i = 2;
			for(String key : params.keySet()) {
				pstmt.setString(i, "%" + params.get(key) + "%");
				i++;
			}
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Book book = Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(Author.builder()
								.authorId(rs.getInt(3))
								.authorName(rs.getString(4))
								.build())
						.publisher(Publisher.builder()
								.publisherId(rs.getInt(5))
								.publisherName(rs.getString(6))
								.build())
						.build();
				bookList.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return bookList;
	}
	
}
