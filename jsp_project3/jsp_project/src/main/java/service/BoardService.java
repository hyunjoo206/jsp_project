package service;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardService {

	int register(BoardVO bvo);

	List<BoardVO> list();

	BoardVO detail(int bno);

	int update(BoardVO bvo3);

	int delete(int bno4);

	int getTotal(PagingVO pgvo);

	List<BoardVO> getPageList(PagingVO pgvo);



}
