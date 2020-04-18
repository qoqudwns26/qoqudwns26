package bbj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbjDAO {

	private Connection conn;
	private ResultSet rs;
	
	public BbjDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/BBJ?characterEncoding=UTF-8&serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}


public String getDate() {
	String SQL = "Select NOW()";
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getString(1);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return ""; // 데이터베이스 오류
	}


public int getNext() {
	String SQL = "select bbjID FROM bbj order by bbjID desc";
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1) + 1;
		}
		return 1; // 첫 번째 게시물인 경우
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -1; // 데이터베이스 오류
	}

	public int write(String bbjTitle, String userID, String bbjContent) {
		String SQL = "INSERT INTO BBJ VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  getNext());
			pstmt.setString(2,  bbjTitle);
			pstmt.setString(3,  userID);
			pstmt.setString(4,  getDate());
			pstmt.setString(5,  bbjContent);
			pstmt.setInt(6,  1);
				return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
		
}
	public ArrayList<Bbj> getList(int pageNumber) {
		String SQL = "SELECT * FROM BBJ WHERE BbjID < ? AND BbjAvailable = 1 ORDER BY BbjID DESC LIMIT 10";
		ArrayList<Bbj> list = new ArrayList<Bbj>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbj bbj = new Bbj();
				bbj.setBbjID(rs.getInt(1));
				bbj.setBbjTitle(rs.getString(2));
				bbj.setUserID(rs.getString(3));
				bbj.setBbjDate(rs.getString(4));
				bbj.setBbjContent(rs.getString(5));
				bbj.setBbjAvailable(rs.getInt(6));
				list.add(bbj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM BBJ WHERE bbjID < ? AND bbjAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbj getBbj(int bbjID) {
		String SQL = "SELECT * FROM BBJ WHERE bbjID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbjID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Bbj bbj = new Bbj();
				bbj.setBbjID(rs.getInt(1));;
				bbj.setBbjTitle(rs.getString(2));
				bbj.setUserID(rs.getString(3));;
				bbj.setBbjDate(rs.getString(4));
				bbj.setBbjContent(rs.getString(5));
				bbj.setBbjAvailable(rs.getInt(6));
				return bbj;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public int update(int bbjID, String bbjTitle, String bbjContent) {
		String SQL = "UPDATE BBJ SET bbjTitle = ?, bbjContent = ? WHERE bbjID = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  bbjTitle);
			pstmt.setString(2,  bbjContent);
			pstmt.setInt(3,  bbjID);
				return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
	
	}
	
	public int delete(int bbjID) {
		String SQL = "UPDATE BBJ SET bbjAvailable = 0 WHERE bbjID = ? ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  bbjID);
				return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 데이터베이스 오류
		
	}
}