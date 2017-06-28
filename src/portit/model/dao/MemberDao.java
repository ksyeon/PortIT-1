package portit.model.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import portit.model.db.DBConnectionMgr;
import portit.model.dto.Member;

/**
 * 회원 관련 DAO
 * @author 
 *
 */
public class MemberDao {

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	private DBConnectionMgr pool;
	private String sql = null;
	
	public MemberDao() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			System.out.println("커넥션 풀 오류 - MemberDao()");
			e.printStackTrace();
		}
	}
	
	/**
	 * 회원 가입
	 * @param member
	 * @return
	 */
	public void insert(Member member) {
		try {
			conn = pool.getConnection();
			sql = "INSERT INTO MEMBER(mem_id, mem_email, mem_pw, mem_regdate) VALUES(seq_mem_id.nextVal, ?, ?, sysdate)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMem_email());
			stmt.setString(2, member.getMem_password());
			stmt.executeUpdate();
			System.out.println("등록되엇음");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt);
		}
	}
	
	/**
	 * 회원 조회 - 로그인할 때, 회원가입할 때 사용
	 * @param member
	 * @return
	 */
	public Member search(Member member) {
		int id;
		Member checkMem = new Member();
		try {
			conn = pool.getConnection();
			sql = "SELECT * FROM MEMBER WHERE mem_email = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMem_email());
			rs = stmt.executeQuery();
			
			if(rs.next()) {	
				checkMem.setMem_id(rs.getInt("mem_id"));
				checkMem.setMem_email(rs.getString("mem_email"));
				checkMem.setMem_password(rs.getString("mem_pw"));				
			}			
			else {
				checkMem.setMem_id(0);
//				member.setMem_email("");
//				member.setMem_password("");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt);
		}

		return checkMem;
	}
	
	
	
	/**
	 * 회원 탈퇴
	 */

}