package service;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardService {

	int register(BoardVO bvo);

	List<BoardVO> list();

	int getTotal(PagingVO pgvo);

	List<BoardVO> getPageList(PagingVO pgvo);

	BoardVO detail(int bno);

	int readcount(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	String getFileName(int bno);

}