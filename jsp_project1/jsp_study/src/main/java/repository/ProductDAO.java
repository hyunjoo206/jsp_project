package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

import domain.ProductVO;
import orm.DatabaseConnector;

public class ProductDAO implements DAO {
	//DB Connector 
	private Connection conn;
	//DB 연결 후 sql 전송 객체
	private PreparedStatement pst;
	//query문 작성 변수
	private String query="";
	
	public ProductDAO() {
		DatabaseConnector dbc = DatabaseConnector.getInstance(); // new로 생성불가. 싱글톤 객체생성 방식
		conn = dbc.getConnection();
		
	}
	
	@Override
	public int insert(ProductVO pvo) {
		System.out.println(">>> DAO 진입성공");
		//쿼리문을 query에 넣기 - ?이용해서 매개변수 처리
		query="insert into product(pname, price, madeby) values(?,?,?)";
		try {
			//여기선 펼쳐서 가져오기 되지만, mybatis에서는 무조건 한 객체로만 가능함. 
			pst = conn.prepareStatement(query);
			pst.setString(1, pvo.getPname());
			pst.setInt(2, pvo.getPrice());
			pst.setString(3, pvo.getMadeby());	
			//executeUpdate : insert, update, delete 
			//executeQuery : select에서 사용
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<ProductVO> selectList() {
		System.out.println(">>> DAO 진입성공");
		query = "select * from product order by pno desc";
		List<ProductVO> list = new ArrayList<ProductVO>();
		try {
			pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				list.add(new ProductVO(rs.getInt("pno"),
						rs.getString("pname"),
						rs.getString("regdate")));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ProductVO selectOne(int pno) {
		System.out.println(">>> DAO 진입성공");
		query="select * from product where pno=?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, pno);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				return new ProductVO(rs.getInt("pno"), rs.getString("pname"), 
						rs.getInt("price"), rs.getString("regdate"), rs.getString("madeby"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(ProductVO pvo2) {
		System.out.println(">>> DAO 접근완료");
		query="update product set pname=?, price=?, madeby=?, regdate=now() where pno=?";
		
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, pvo2.getPname());
			pst.setInt(2, pvo2.getPrice());
			pst.setString(3, pvo2.getMadeby());
			pst.setInt(4, pvo2.getPno());
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
		}		
		return 0;
	}

	@Override
	public int delete(int pno) {
		System.out.println(">>> DAO 진입 성공");
		query="delete from product where pno = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setInt(1, pno);
			return pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
} 
