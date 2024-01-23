package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.study.servlet_study.config.DBConnectionMgr;

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
		
		while (true) {
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
				
				while(rs.next()) {
					System.out.println("책ID : " + rs.getInt(1) + "책 이름 : " + rs.getString(2) + " / 책 저자 : " + rs.getString(3) + " / 책 출판사 : " + rs.getString(4));
				}
			
				
//				List<Book> bookList = new ArrayList<>();
//				
//				while(rs.next()) { 
//					bookList.add(Book.builder()
//							.bookId(rs.getInt(1))
//							.bookName(rs.getString(2))
//							.author()
//							.publisher()
//							.build()
//				}

				
			} catch (Exception e) { // SQL의 오류를 잡기 위해서
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
				// 작업이 끝난 후 꼭 연결을 끊어야한다.
			}
			break;
		};
		
		
		

		
	}
	
}
