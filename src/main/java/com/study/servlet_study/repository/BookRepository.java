package com.study.servlet_study.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Book;

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
}
