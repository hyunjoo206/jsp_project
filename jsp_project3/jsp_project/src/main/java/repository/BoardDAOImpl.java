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
	//셋팅, 매퍼, sql 연결
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	private SqlSession sql;
	private String NS = "BoardMapper.";
	
	//dao랑 db연결구문~~!!!!!!
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
		List<BoardVO> list3 = new ArrayList<BoardVO>();
		list3 = sql.selectList(NS+"list"); //sql에 있는 함수(selectList) 써야함
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
		return isOk;
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		return sql.selectOne(NS+"cnt", pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
//		return sql.selectList(NS+"pageList", pgvo);
		return sql.selectList(NS+"selectList", pgvo);
	}


	
	

}