package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {

	//선언해주기
	private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
	private BoardDAO bdao;
	
	// 연결해주기 -> interface 생성 후 service로 생성
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}
	
	@Override
	public int register(BoardVO bvo) {
		log.info("register service 진입");
		return bdao.insert(bvo);
	}

	@Override
	public List<BoardVO> list() {
		log.info(">>> list service 진입");
		return bdao.selectList();
	}

	@Override
	public BoardVO detail(int bno) {
		log.info(">>> detail service 진입");
		return bdao.selectOne(bno);
	}

	@Override
	public int update(BoardVO bvo3) {
		log.info(">>> update service 진입");
		return bdao.update(bvo3);
	}

	@Override
	public int delete(int bno4) {
		log.info(">>> delete service 진입");
		return bdao.delete(bno4);
	}

	@Override
	public int getTotal(PagingVO pgvo) {
		log.info(">>> getTotal service 진입");
		return bdao.getTotal(pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
		log.info(">>> getPageList service 진입");
		return bdao.getPageList(pgvo);
	}



}