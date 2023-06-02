package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {
	private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
	private BoardDAO bdao;
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int register(BoardVO bvo) {
		log.info(">>> register service 진입");
		return bdao.insert(bvo); //insert는 내가 이름 정하는 거
	}

	@Override
	public List<BoardVO> list() {
		log.info(">>> list service 진입");
		return bdao.selectList();
	}
	
	
	
	

	@Override
	public int getTotal(PagingVO pgvo) {
		log.info(">>>getTotal service 진입");		
		return bdao.page(pgvo);
	}

	@Override
	public List<BoardVO> getPageList(PagingVO pgvo) {
		log.info(">>> getPageList service 진입");		
		return bdao.pageList(pgvo);
	}

	@Override
	public BoardVO detail(int bno) {
		log.info(">>> detail service 진입");
		return bdao.selectOne(bno);
	}

	@Override
	public int readcount(int bno) {
		log.info(">>> readcount service 진입");		
		return bdao.readcount(bno);
	}

	@Override
	public int update(BoardVO bvo) {
		log.info(">>> detail service 진입");		
		return bdao.update(bvo);
	}

	@Override
	public int delete(int bno) {
		log.info(">>> delete service 진입");		
		return bdao.delete(bno);
	}

	@Override
	public String getFileName(int bno) {
		return bdao.selectRemoveFile(bno);
	}
	
}
