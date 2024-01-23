package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;

public class DBConnectionTestMain {
	
	public static void main(String[] args) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
			// getConnection을 사용하기 위해 싱글톤으로 객체 생성
			// pool객체를 하나만 생성하여 관리하기 위함이다.
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // 쿼리 실행 결과를 담는다.
		
		try {
			con = pool.getConnection();
			String name = "dongyoon";
			String sql = "select * from author_tb where author_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name); // 물음표에 들어갈 변수를 삽입, 첫번째 물음표면 1, 두번째면 2
									//자료형은 맞춰줘야함 이것을 표현식이라고 한다.
			
			rs = pstmt.executeQuery(); // 쿼리실행
			
			List<Author> authorList = new ArrayList<>();
			
			while(rs.next()) { // author 객체에 추가, rs.next() -> 컬럼의 다음 값으로 이동한다. 전체 반복
				authorList.add(Author.builder() // rs에 담은 쿼리 결과를 list에 담는다.
						.authorId(rs.getInt(1))
						.authorName(rs.getString(2))
						.build());
			}
			
			authorList.forEach(author -> System.out.println(author)); // forEach를 돌려서 list에 있는 결과를 출력
			
//			for(Author author : authorList) {
//				System.out.println(author);
//			}
//			
//			for (int i = 0; i < authorList.size(); i++) {
//				Author author = authorList.get(i);
//				System.out.println(author);
//			}
			
		} catch (Exception e) { // SQL의 오류를 잡기 위해서
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
			// 작업이 끝난 후 꼭 연결을 끊어야한다.
		}
	}
}
