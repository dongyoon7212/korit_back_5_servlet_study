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
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			String sql = "select * from author_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 실행
			
			List<Author> authorList = new ArrayList<>();
			
			while(rs.next()) { // author 객체에 추가
				authorList.add(Author.builder()
						.authorId(rs.getInt(1))
						.authorName(rs.getString(2))
						.build());
			}
			
			authorList.forEach(author -> System.out.println(author));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
			// 작업이 끝난 후 꼭 연결을 끊어야한다.
		}
	}
	
}
