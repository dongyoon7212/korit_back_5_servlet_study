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

public class BookSearchMain2 {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String searchValue = null;
		
		System.out.print("검색할 도서명을 입력하세요 >>> ");
		searchValue = scanner.nextLine();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Book> bookList = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			String sql = "select\n"
					+ "	bt.book_id,\n"				// 1
					+ "	bt.book_name,\n"			// 2
					+ "    bt.author_id,\n"			// 3
					+ "    at.author_name,\n"		// 4
					+ "    bt.publisher_id,\n"		// 5
					+ "    pt.publisher_name\n"		// 6
					+ "from\n"
					+ "	book_tb bt\n"
					+ "    left outer join author_tb at on(at.author_id = bt.author_id)\n"
					+ "    left outer join publisher_tb pt on(pt.publisher_id = bt.publisher_id)\n"
					+ "where\n"
					+ "	bt.book_name like ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchValue + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Author author = Author.builder()
						.authorId(rs.getInt(3))
						.authorName(rs.getString(4))
						.build();
				
				Publisher publisher = Publisher.builder()
						.publisherId(rs.getInt(5))
						.publisherName(rs.getString(6))
						.build();
				
				Book book = Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(author)
						.publisher(publisher)
						.build();
				
				bookList.add(book);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		System.out.println("도서명 / 저자명 / 출판사");
		
		for (Book book : bookList) {
			System.out.println(book.getBookName() + " / " + book.getAuthor().getAuthorName() + " / " + book.getPublisher().getPublisherName());
		}
	}
	
}
