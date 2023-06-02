package repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import orm.DatabaseBuilder;

public class BoardDAOImpl implements BoardDAO {
	//로그 띄우려고 (성공했는지 보려고)
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	private SqlSession sql;
	private String NS = "BoardMapper.";
	
	//dao랑 db연결구문
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}
	
	public int insert(BoardVO bvo) {
		log.info(">>> insert DAO in");
		int isOk = sql.insert(NS+"reg", bvo);
		if(isOk > 0) {sql.commit();}
		return isOk;
	}

	@Override
	public List<BoardVO> selectList() {
		log.info(">>>list DAO 진입");
		List<BoardVO> list3 = new ArrayList<BoardVO>();
		list3 = sql.selectList(NS+"list");
		return list3;
	}

	@Override
	public BoardVO selectOne(int bno) {
		log.info(">>> DAO 진입 성공");
		return sql.selectOne(NS+"sel", bno);
	}

	@Override
	public int update(BoardVO bvo3) {
		int isOk = sql.update(NS+"up", bvo3);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int delete(int bno4) {
		int isOk = sql.delete(NS+"del", bno4);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk; //컨트롤러에서 isOk로 잘 받아왔는지 확인하려고
	}

	@Override
	public int readcount(int bno) {
		int isOk = sql.update(NS+"read", bno);
		if(isOk >0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int page(PagingVO pgvo) {
		return sql.selectOne(NS+"cnt", pgvo);
	}

	@Override
	public List<BoardVO> pageList(PagingVO pgvo) {
//		return sql.selectList(NS+"pageList", pgvo);
		//+++++++++++검색할때 여기 바꿔주기~~~~~
		return sql.selectList(NS+"selectList", pgvo);
	}


}
