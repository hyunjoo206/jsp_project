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
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	private SqlSession sql;
	private String NS = "BoardMapper.";
	
	//dao랑 db연결구문
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}
	
	@Override
	public int insert(BoardVO bvo) {
		log.info(">>> insert DAO in");
		int isOk = sql.insert(NS+"reg", bvo);
		if(isOk > 0) {sql.commit();}
		return isOk;
	}

	@Override
	public List<BoardVO> selectList() {
		log.info(">>>list DAO 진입");
		List<BoardVO> list = new ArrayList<BoardVO>();
		list = sql.selectList(NS+"list");
		return list;
	}


	@Override
	public BoardVO selectOne(int bno) {
		log.info(">>> DAO 진입 성공");
		return sql.selectOne(NS+"sel", bno);
	}

	@Override
	public int update(BoardVO bvo) {
		int isOk = sql.update(NS+"up", bvo);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}
	
	@Override
	public int delete(int bno) {
		int isOk = sql.delete(NS+"del", bno);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
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
		return sql.selectList(NS+"selectList", pgvo);
	}

	@Override
	public String selectRemoveFile(int bno) {
		return sql.selectOne(NS+"removeFile", bno);
	}


	

}