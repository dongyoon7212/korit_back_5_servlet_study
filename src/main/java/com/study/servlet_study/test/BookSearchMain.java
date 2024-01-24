package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;

public class BookSearchMain {
	
	public static void main(String[] args) {
		String searchName;
		Scanner scanner = new Scanner(System.in);
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 검색할 도서명을 입력하세요. >>> 글
		
		// 도서명 / 저자 / 출판사
		
		System.out.print("검색할 도서명을 입력하세요. >>> ");
		searchName = scanner.nextLine();
		try {
				
			con = pool.getConnection();
				
			String sql = "select\n"
					+ "	bt.book_id,\n"
					+ "	bt.book_name,\n"
					+ "    at.author_name,\n"
					+ "    pt.publisher_name\n"
					+ "from\n"
					+ "	book_tb bt\n"
					+ "    left outer join author_tb at on(at.author_id = bt.author_id)\n"
					+ "    left outer join publisher_tb pt on(pt.publisher_id = bt.publisher_id)\n"
					+ "where\n"
					+ "	book_name like " + "\'%" + searchName + "%\'";
			pstmt = con.prepareStatement(sql);			
			rs = pstmt.executeQuery(); // 쿼리실행
				
				
			
				
			List<Book> bookList = new ArrayList<>();
				
			while(rs.next()) { 
				bookList.add(Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(Author.builder().authorName(rs.getString(3)).build())
						.publisher(Publisher.builder().publisherName(rs.getString(4)).build())
						.build()
						);
			}
	        bookList.forEach(book -> System.out.println(book));
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
	}
	
}
