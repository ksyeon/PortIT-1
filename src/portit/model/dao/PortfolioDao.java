package portit.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import portit.model.db.DBConnectionMgr;
import portit.model.dto.Media;
import portit.model.dto.Portfolio;
import portit.model.dto.Profile;
import portit.model.dto.Tag;

/**
 * 포트폴리오 DAO
 * 
 */
public class PortfolioDao {

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs, rs2, rs3, rs4;
	private DBConnectionMgr pool;
	
	private MediaDao mediaDao;
	private ProfileDao profileDao;
	private TagDao tagDao;
	
	public PortfolioDao() {
		try {
			pool = DBConnectionMgr.getInstance();
			conn = pool.getConnection();
			mediaDao = new MediaDao();
			profileDao = new ProfileDao();
			tagDao = new TagDao();
		} catch (Exception e) {
			System.out.println("DB 접속 오류 :");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB 연결
	 */
	private void getConnection() {
		try {
			conn = pool.getConnection();
			if (conn != null) System.out.println("DB 접속");
		} catch (Exception e) {
			System.out.println("DB 접속 오류 :");
			e.printStackTrace();
		}
	}

	/**
	 * DB 접속 해제
	 */
	private void freeConnection() {
		try {
			pool.freeConnection(conn, stmt, rs);
			if (conn != null) {
				System.out.println("DB 접속 해제");
			}
		} catch (Exception e) {
			System.out.println("DB 접속해제 오류 :");
			e.printStackTrace();
		}
	}
	
	/**
	 * 개별 데이터 조회
	 * @param pf_id
	 * @return DTO
	 */
	public Portfolio selectOne(int pf_id) {
		Portfolio portfolio = new Portfolio();
		
		// SELECT문 지정
		String sql = "SELECT * FROM portfolio WHERE pf_id=?";
		
		// DB에 접속해서 작업 실행
		getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				// DB 조회 결과를 DTO에 저장
				portfolio.setPf_id(rs.getInt("pf_id"))
						.setPf_title(rs.getString("pf_title"))
						.setPf_intro(rs.getString("pf_intro"))
						.setPf_regdate(rs.getDate("pf_regdate"))
						.setPf_startdate(rs.getDate("pf_startdate"))
						.setPf_enddate(rs.getDate("pf_enddate"))
						.setPf_url(rs.getString("pf_repository"));
				
				// 작성자 정보를 조회해서 DTO에 저장
				sql = "SELECT prof.prof_name, prof.prof_nick, prof.prof_img FROM profile prof"
						+ " INNER JOIN prof_pf pp "
						+ " ON prof.prof_id=pp.prof_id WHERE pp.prof_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, portfolio.getPf_id());
				rs = stmt.executeQuery();
				while (rs.next()) {
					portfolio.setPf_prof_name(rs.getString(1))
					.setPf_prof_nick(rs.getString(2))
					.setPf_prof_img(rs.getString(3));
				}
				
				// 좋아요 수를 조회해서 DTO에 저장
				sql = "SELECT COUNT(1) FROM pf_like WHERE pf_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, pf_id);
				rs = stmt.executeQuery();
				if (rs.next()) {
					portfolio.setPf_like(rs.getInt(1));
				}
				
				// 태그 사용 데이터를 조회해서 DTO에 저장
				List<Tag> tags_language = new ArrayList<Tag>();
				List<Tag> tags_tool= new ArrayList<Tag>();
				List<Tag> tags_field= new ArrayList<Tag>();
				sql = "SELECT tu.tag_id, t.tag_type, t.tag_name FROM tag_use tu INNER JOIN tag t "
						+ "ON tu.tag_id=t.tag_id "
						+ "WHERE tu.tag_use_type=? AND tu.tag_use_type_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "portfolio");
				stmt.setInt(2, portfolio.getPf_id());
				rs = stmt.executeQuery();
				while (rs.next()) {
					if ("language".equalsIgnoreCase(rs.getString(2))) {
						Tag tag_language = new Tag()
								.setTag_id(rs.getInt(1))
								.setTag_type(rs.getString(2))
								.setTag_name(rs.getString(3));
						tags_language.add(tag_language);
					}
					if ("tool".equalsIgnoreCase(rs.getString(2))) {
						Tag tag_tool = new Tag()
								.setTag_id(rs.getInt(1))
								.setTag_type(rs.getString(2))
								.setTag_name(rs.getString(3));
						tags_tool.add(tag_tool);
					}
					if ("field".equalsIgnoreCase(rs.getString(2))) {
						Tag tag_field = new Tag()
								.setTag_id(rs.getInt(1))
								.setTag_type(rs.getString(2))
								.setTag_name(rs.getString(3));
						tags_field.add(tag_field);
					}
				}
				portfolio.setPf_tags_language(tags_language)
				.setPf_tags_tool(tags_tool)
				.setPf_tags_field(tags_field);
				
				// 공동 작업자를 조회해서 DTO에 저장
				List<Profile> coworkers = new ArrayList<Profile>();
				sql = "SELECT * FROM pf_coworker WHERE pf_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, pf_id);
				rs = stmt.executeQuery();
				profileDao = new ProfileDao();
				while (rs.next()) {
					Profile profile = profileDao.getProfile(rs.getInt("mem_id"));
					coworkers.add(profile);
				}
				portfolio.setPf_coworkers(coworkers);
				
				// 미디어 데이터를 조회해서 DTO에 저장
				List<Media> mediaList = mediaDao.selectList(conn, "portfolio", portfolio.getPf_id());
				portfolio.setPf_mediaList(mediaList);
			}
		} catch (Exception e) {
			System.out.println("DB 조회 오류 :");
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return portfolio;
	}
	
	/**
	 * 전체 데이터 조회
	 * @param sort 정렬할 칼럼
	 * @param keyword 검색어
	 * @param tagList 검색에 사용할 태그
	 * @return DTO 목록
	 */
	public List<Portfolio> selectList(String sort, String keyword, List<Tag> tagList) {
		List<Portfolio> portfolios = new ArrayList<Portfolio>();
		
		// SELECT문 지정
		String sql = "SELECT * FROM portfolio";
		// 검색 조건 추가
		if (!keyword.isEmpty() || tagList != null) {
			sql += " WHERE";
		}
		if (!keyword.isEmpty()) {
			sql += " pf_title LIKE '%" + keyword + "%'"
					+ " OR pf_intro LIKE '%" + keyword + "%'";
		}
		if (!sort.isEmpty()) {
			sql += " ORDER BY " + sort + " DESC";
		}
		
		// DB에 접속해서 작업 실행
		getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				// DB 조회 결과를 DTO에 저장
				Portfolio portfolio = new Portfolio()
						.setPf_id(rs.getInt("pf_id"))
						.setPf_title(rs.getString("pf_title"))
						.setPf_intro(rs.getString("pf_intro"))
						.setPf_regdate(rs.getDate("pf_regdate"))
						.setPf_startdate(rs.getDate("pf_startdate"))
						.setPf_enddate(rs.getDate("rs_enddate"))
						.setPf_url(rs.getString("pf_repository"));
				
				// 작성자 정보를 조회해서 DTO에 저장
				sql = "SELECT prof.prof_name, prof.prof_nick, prof.prof_img FROM profile prof"
						+ " INNER JOIN prof_pf pp "
						+ " ON prof.prof_id=pp.prof_id WHERE pp.pf_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, portfolio.getPf_id());
				rs = stmt.executeQuery();
				while (rs.next()) {
					portfolio.setPf_prof_name(rs.getString(1))
					.setPf_prof_nick(rs.getString(2))
					.setPf_prof_img(rs.getString(3));
				}
				
				// 좋아요 수를 조회해서 DTO에 저장
				sql = "SELECT COUNT(*) FROM pf_like WHERE pf_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, portfolio.getPf_id());
				rs = stmt.executeQuery();
				if (rs.next()) {
					portfolio.setPf_like(rs.getInt(1));
				}
				
				// 태그 사용 데이터를 조회해서 DTO에 저장
				List<Tag> tags_language = new ArrayList<Tag>();
				List<Tag> tags_tool= new ArrayList<Tag>();
				List<Tag> tags_field= new ArrayList<Tag>();
				sql = "SELECT tu.tag_id, t.tag_type, t.tag_name FROM tag_use tu INNER JOIN tag t "
						+ "ON tu.tag_id=t.tag_id "
						+ "WHERE tu.tag_use_type=? AND tu.tag_use_type_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "portfolio");
				stmt.setInt(2, portfolio.getPf_id());
				rs = stmt.executeQuery();
				while (rs.next()) {
					if ("language".equalsIgnoreCase(rs.getString(2))) {
						Tag tag_language = new Tag()
								.setTag_id(rs.getInt(1))
								.setTag_type(rs.getString(2))
								.setTag_name(rs.getString(3));
						tags_language.add(tag_language);
					}
					if ("tool".equalsIgnoreCase(rs.getString(2))) {
						Tag tag_tool = new Tag()
								.setTag_id(rs.getInt(1))
								.setTag_type(rs.getString(2))
								.setTag_name(rs.getString(3));
						tags_tool.add(tag_tool);
					}
					if ("field".equalsIgnoreCase(rs.getString(2))) {
						Tag tag_field = new Tag()
								.setTag_id(rs.getInt(1))
								.setTag_type(rs.getString(2))
								.setTag_name(rs.getString(3));
						tags_field.add(tag_field);
					}
				}
				portfolio.setPf_tags_language(tags_language)
				.setPf_tags_tool(tags_tool)
				.setPf_tags_field(tags_field);
				
				// 공동 작업자를 조회해서 DTO에 저장
				List<Profile> coworkers = new ArrayList<Profile>();
				sql = "SELECT * FROM pf_coworker WHERE pf_id=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, portfolio.getPf_id());
				rs = stmt.executeQuery();
				profileDao = new ProfileDao();
				while (rs.next()) {
					Profile profile = profileDao.getProfile(rs.getInt("mem_id"));
					coworkers.add(profile);
				}
				portfolio.setPf_coworkers(coworkers);
				
				// 미디어 데이터를 조회해서 DTO에 저장
				List<Media> mediaList = mediaDao.selectList(conn, "portfolio", portfolio.getPf_id());
				portfolio.setPf_mediaList(mediaList);
				
				// 데이터 객체를 목록에 저장
				portfolios.add(portfolio);
			}
		} catch (Exception e) {
			System.out.println("DB 조회 오류 :");
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return portfolios;
	}
	
	public List<Portfolio> selectListByMemId(int mem_id) {
		List<Portfolio> portfolioList = new ArrayList<Portfolio>();
		getConnection();
		try {
			String sql = "SELECT pf_id FROM prof_pf WHERE mem_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_id);
			rs = stmt.executeQuery();			
			while (rs.next()) {
				portfolioList.add(selectOne(rs.getInt("pf_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			freeConnection();
		}		
		return portfolioList;
	}

	public List<Portfolio> selectListByNick(String prof_nick) {
		List<Portfolio> portfolioList = new ArrayList<Portfolio>();
		getConnection();
		try {
			String sql = "SELECT pf_id FROM prof_pf WHERE prof_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, usernameToId(prof_nick));
			rs = stmt.executeQuery();			
			while (rs.next()) {
				portfolioList.add(selectOne(rs.getInt("pf_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			freeConnection();
		}		
		return portfolioList;
	}
	
	/**
	 * 데이터 추가
	 * @param pf_id
	 * @return 추가된 데이터 개수
	 */
	public int insert(Portfolio portfolio) {
		int rows = 0;
		try {
			// INSERT문 지정
			String sql = "INSERT INTO portfolio("
					+ "pf_id, pf_title, pf_intro, pf_regdate, pf_like, "
					+ "pf_startdate, pf_enddate, pf_numofperson, pf_repository"
					+ ") VALUES(seq_pf_id.nextVal,?,?,SYSDATE,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, portfolio.getPf_title());
			stmt.setString(2, portfolio.getPf_intro());
			stmt.setInt(3, 0);
			stmt.setDate(4, new Date(portfolio.getPf_startdate().getTime()));
			stmt.setDate(5, new Date(portfolio.getPf_enddate().getTime()));
			stmt.setInt(6, portfolio.getPf_numofperson());
			stmt.setString(7, portfolio.getPf_url());
			rows += stmt.executeUpdate();
			
			// 작성된 게시물 번호 얻기
			sql = "SELECT seq_pf_id.currVal FROM DUAL";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			int pf_id = 0;
			if (rs.next()) {
				pf_id = rs.getInt(1);
			}

			// 태그 추가
			List<Tag> pf_tags_language = portfolio.getPf_tags_language();
			List<Tag> pf_tags_tool = portfolio.getPf_tags_tool();
			List<Tag> pf_tags_field = portfolio.getPf_tags_field();
			if (pf_tags_language != null) {
				for (int i = 0; i < pf_tags_language.size(); i++) {
					tagDao.insertTag(conn, pf_tags_language.get(i));
				}
			}
			if (pf_tags_tool != null) {
				for (int i = 0; i < pf_tags_tool.size(); i++) {
					tagDao.insertTag(conn, pf_tags_tool.get(i));
				}
			}
			if (pf_tags_field != null) {
				for (int i = 0; i < pf_tags_field.size(); i++) {
					tagDao.insertTag(conn, pf_tags_field.get(i));
				}
			}
			// 태그 사용 추가			
			if (pf_tags_language != null) {
				for (int i = 0; i < pf_tags_language.size(); i++) {
					tagDao.insertTagUse(conn, "portfolio", pf_id, pf_tags_language.get(i));
				}
			}
			if (pf_tags_tool != null) {
				for (int i = 0; i < pf_tags_tool.size(); i++) {
					tagDao.insertTagUse(conn, "portfolio", pf_id, pf_tags_tool.get(i));
				}
			}
			if (pf_tags_field != null) {
				for (int i = 0; i < pf_tags_field.size(); i++) {
					tagDao.insertTagUse(conn, "portfolio", pf_id, pf_tags_field.get(i));
				}
			}
			
			// 공동 작업자 추가
			List<Profile> coworkers = portfolio.getPf_coworkers();
			sql = "INSERT INTO pf_coworker(pf_co_id, pf_id, mem_id)"
					+ " VALUES(seq_pf_co_id.nextVal,seq_pf_id.currVal,?)";
			if (coworkers != null) {
				for (int i = 0; i < coworkers.size(); i++) {
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, usernameToId(coworkers.get(i).getProf_nick()));
					stmt.executeUpdate();
				}
			}
			
			// 미디어 라이브러리 추가
			List<Media> mediaList = portfolio.getPf_mediaList();
			if (mediaList != null) {
				for (int i = 0; i < mediaList.size(); i++) {
					mediaList.get(i).setMl_type("portfolio").setMl_type_id(pf_id);
					mediaDao.insert(conn, mediaList.get(i));
				}
			}
			
			// 프로필과 포트폴리오 연결
			sql = "INSERT INTO prof_pf(prof_pf_id, prof_id, pf_id)"
					+ " VALUES(seq_prof_pf_id.nextVal,?,seq_pf_id.currVal)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, usernameToId(portfolio.getPf_prof_name()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return rows;
	}
	
	/**
	 * 데이터 수정
	 * @param pf_id
	 * @return 수정된 데이터 개수
	 */
	public int update(Portfolio portfolio) {
		int rows = 0;
		try {
			// UPDATE문 지정
			String sql = "UPDATE portfolio"
					+ " SET pf_title=?, pf_intro=?, pf_startdate=?,"
					+ " pf_enddate=?, pf_numofperson=?, pf_repository=?"
					+ " WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, portfolio.getPf_title());
			stmt.setString(2, portfolio.getPf_intro());
			stmt.setDate(3, new Date(portfolio.getPf_startdate().getTime()));
			stmt.setDate(4, new Date(portfolio.getPf_enddate().getTime()));
			stmt.setInt(5, portfolio.getPf_numofperson());
			stmt.setString(6, portfolio.getPf_url());
			stmt.setInt(7, portfolio.getPf_id());
			rows += stmt.executeUpdate();

			// 등록되지 않은 태그 추가
			List<Tag> pf_tags_language = portfolio.getPf_tags_language();
			List<Tag> pf_tags_tool = portfolio.getPf_tags_tool();
			List<Tag> pf_tags_field = portfolio.getPf_tags_field();
			if (pf_tags_language != null) {
				for (int i = 0; i < pf_tags_language.size(); i++) {
					tagDao.insertTag(conn, pf_tags_language.get(i));
				}
			}
			if (pf_tags_tool != null) {
				for (int i = 0; i < pf_tags_tool.size(); i++) {
					tagDao.insertTag(conn, pf_tags_tool.get(i));
				}
			}
			if (pf_tags_field != null) {
				for (int i = 0; i < pf_tags_field.size(); i++) {
					tagDao.insertTag(conn, pf_tags_field.get(i));
				}
			}
			
			// 태그 사용 수정
			for (int i = 0; i < pf_tags_language.size(); i++) {
				tagDao.updateTagUse(conn, "portfolio", portfolio.getPf_id(), pf_tags_language.get(i));
			}
			for (int i = 0; i < pf_tags_tool.size(); i++) {
				tagDao.updateTagUse(conn, "portfolio", portfolio.getPf_id(), pf_tags_tool.get(i));
			}
			for (int i = 0; i < pf_tags_field.size(); i++) {
				tagDao.updateTagUse(conn, "portfolio", portfolio.getPf_id(), pf_tags_field.get(i));
			}
			
			// 공동 작업자 수정
			List<Profile> coworkers = portfolio.getPf_coworkers();
			for (int i = 0; i < coworkers.size(); i++) {
				coworkers.get(i).setMem_id(usernameToId(coworkers.get(i).getProf_nick()));
			}
			for (int i = 0; i < coworkers.size(); i++) {
				sql = "MERGE INTO pf_coworker pfc "
						+ "USING (SELECT * FROM pf_coworker WHERE pf_id=?) pfcs "
						+ "ON pfc.pf_id=pfcs.pf_id "
						+ "WHEN MATCHED THEN "
						+ "UPDATE SET mem_id=? WHERE mem_id!=? AND pf_id=? "
						+ "WHEN NOT MATCHED THEN INSERT ("
						+ "pfc.pf_co_id, pfc.pf_id, pfc.mem_id"
						+ ") VALUES (seq_pf_co_id.nextVal,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, portfolio.getPf_id());
				stmt.setInt(2, coworkers.get(i).getMem_id());
				stmt.setInt(3, coworkers.get(i).getMem_id());
				stmt.setInt(4, portfolio.getPf_id());
				stmt.setInt(5, portfolio.getPf_id());
				stmt.setInt(6, coworkers.get(i).getMem_id());
				stmt.executeUpdate();
			}
			
			
			// 미디어 라이브러리 수정
			List<Media> mediaList = portfolio.getPf_mediaList();
			mediaDao.delete(conn, "portfolio", portfolio.getPf_id());
			for (int i = 0; i < mediaList.size(); i++) {
				mediaDao.insert(conn, mediaList.get(i));
			}
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return rows;
	}
	
	/**
	 * 데이터 삭제
	 * @param prof_id
	 * @return 삭제된 데이터 개수
	 */
	public int delete(int pf_id) {
		int rows = 0;
		getConnection();
		try {
			// 미디어 데이터 삭제
			mediaDao.delete(conn, "portfolio", pf_id);
			
			// 포트폴리오의 좋아요 데이터 삭제
			String sql = "DELETE FROM pf_like WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			stmt.executeUpdate();
			
			// 공동 작업자 삭제
			sql = "DELETE FROM pf_coworker WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			stmt.executeUpdate();
			
			// 태그 사용 삭제
			sql = "DELETE FROM tag_use WHERE tag_use_type=? AND tag_use_type_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "portfolio");
			stmt.setInt(2, pf_id);
			stmt.executeUpdate();
			
			// 내가 등록한 포트폴리오 데이터 삭제
			sql = "DELETE FROM prof_pf WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			stmt.executeUpdate();
			
			// 포트폴리오 데이터 삭제
			sql = "DELETE FROM portfolio WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			rows += stmt.executeUpdate();
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return rows;
	}

	/**
	 * 포트폴리오의 좋아요 데이터를 추가하고 좋아요 숫자를 반환
	 * @param mem_id
	 * @param pf_id
	 * @return
	 */
	public int addLike(int mem_id, int pf_id) {
		int likes = 0;
		getConnection();
		try {
			String sql = "INSERT INTO pf_like(pf_lk_id, mem_id, pf_id, pf_lk_regdate)"
					+ " VALUES(seq_pf_lk_id.nextVal,?,?,SYSDATE)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_id);
			stmt.setInt(2, pf_id);
			stmt.executeUpdate();
			
			sql = "SELECT COUNT(1) FROM pf_like WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				likes = rs.getInt(1);
			}
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return likes;
	}
	
	/**
	 * 포트폴리오의 좋아요 데이터를 삭제하고 좋아요 숫자를 반환
	 * @param mem_id
	 * @param pf_id
	 * @return
	 */
	public int minusLike(int mem_id, int pf_id) {
		int likes = 0;
		getConnection();
		try {
			String sql = "DELETE pf_like WHERE mem_id=? AND pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_id);
			stmt.setInt(2, pf_id);
			stmt.executeUpdate();
			
			sql = "SELECT COUNT(1) FROM pf_like WHERE pf_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				likes = rs.getInt(1);
			}
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return likes;
	}
	

	/**
	 * 회원번호로 프로필 번호를 얻기
	 * @param mem_id
	 * @return
	 */
	private int memToProf(int mem_id) {
		int prof_id = 0;
		getConnection();
		try {
			String sql = "SELECT prof_id FROM profile WHERE mem_id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, mem_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				prof_id = rs.getInt("prof_id");
			}
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return prof_id;
	}
	
	/**
	 * 프로필 닉네임으로 회원번호 얻기
	 * @param username
	 * @return
	 */
	private int usernameToId(String username) {
		int mem_id = 0;
		getConnection();
		try {
			String sql = "SELECT mem_id FROM profile WHERE prof_nick=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			if (rs.next()) {
				mem_id = rs.getInt("mem_id");
			}
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return mem_id;
	}
	
	/**
	 * 태그 이름으로 번호 얻기
	 * @param tag_name
	 * @return
	 */
	private int tagNameToId(String tag_name) {
		int tag_id = 0;
		getConnection();
		try {
			String sql = "SELECT tag_id FROM tag WHERE tag_name=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, tag_name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				tag_id = rs.getInt("tag_id");
			}
		} catch (Exception e) {
			System.out.println("");
			e.printStackTrace();
		} finally {
			freeConnection();
		}
		return tag_id;
	}

	

	
	

	/**
	 * 내가 작성한 포트폴리오 조회하기 
	 * 
	 * @author hyang
	 */
	
	public List myPortfolio(int prof_id) {
		ArrayList portlist = new ArrayList();
		String sql = "";
		
		try {
			conn = pool.getConnection();
			sql = "SELECT distinct PORT.* , PORT.PF_TITLE, PORT.PF_LIKE, P.PROF_NICK, MEDIA.ML_PATH"
					+ " FROM PORTFOLIO PORT INNER JOIN PROF_PF PROF ON PORT.PF_ID = PROF.PF_ID"
					+ " JOIN MEDIA_LIBRARY MEDIA ON PORT.PF_ID = MEDIA.ML_TYPE_ID"
					+ " JOIN PROFILE P ON P.PROF_ID = PROF.PROF_ID"
		            + " WHERE PROF.PROF_ID = ?";
		        
					
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, prof_id);
			rs2 = stmt.executeQuery();

			
			while (rs2.next()) {
				Portfolio port = new Portfolio();
				//port.setProf_pf_id(rs2.getInt("prof_pf_id"));
				//port.setPf_prof_img(rs2.getString("ml_path"));
				port.setPf_id(rs2.getInt("pf_id"));
				port.setPf_regdate(rs2.getDate("pf_regdate"));
				//port.setMl_path(rs2.getString("ml_path"));
				port.setProf_nick(rs2.getString("prof_nick"));
				port.setPf_title(rs2.getString("pf_title"));
				port.setPf_like(rs2.getInt("pf_like"));
				
				int pf_id = rs2.getInt("pf_id");
				
				port.setMl_path2(myportfolio_media(pf_id));		
				port.setTags(getTag(port, pf_id));
				portlist.add(port);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(conn, stmt, rs2);
		}
		return portlist;
	}
	
	/**
	 * 내가 작성한 포트폴리오의 태그 부분 가져오기 
	 * 
	 * @param pf_id
	 */
	
	
	public List<String> getTag(Portfolio port, int pf_id) {
		String sql = "";
		
		try {
			sql = "SELECT tag_name FROM (SELECT * FROM tag t, tag_use tu "
					+ " WHERE t.tag_id = tu.tag_id AND tu.tag_use_type = 'pf' "
					+ " AND tu.tag_use_type_id = ? "
					+ " ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum < 4 "; // 랜덤하게
																		// 3개

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			rs3 = stmt.executeQuery();

			List<String> tags = new ArrayList<>();
			while (rs3.next()) {
				tags.add(rs3.getString("tag_name"));
				
			}
			return tags; 
		} catch (Exception e) {
			System.out.println("TAG() : 여기 에러나지마라 " + e);
		}
		return null;
	}
	
	
	
	/**
	 * myPortfolio 페이지에서 x버튼 눌러서 삭제
	 * 
	 * @param prof_pf_id
	 */
	public void deleteMyport(int pf_id) {
		String sql = "delete from PROF_PF where prof_pf_id =" + pf_id + "";

		try {
			conn = pool.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (Exception err) {
			System.out.println("DBCP  : " + err);
		}
	}
	/**
	 * 포트폴리오에서 하나의 사진만 가져오기 
	 * @param pf_id
	 * @return
	 */
	public List myportfolio_media(int pf_id) {
		try {
			String sql = "SELECT ml_path FROM (SELECT * FROM portfolio pf, media_library ml "
					+ " WHERE pf.pf_id = ml.ml_type_id AND ml.ml_type = 'pf' AND ml.ml_type_id = ? ) "
					+ " WHERE rownum < 1 ";
			
			ArrayList list = new ArrayList();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pf_id);
			rs4 = stmt.executeQuery();
			
			List<String> media_path = new ArrayList<>();
			while (rs4.next()) {
				media_path.add(rs4.getString("ml_path"));
			}
			return media_path;
		} 
		catch (Exception e) {
			System.out.println("ml_path(proj) 오류" + e);
		}
		return null;
	}
}