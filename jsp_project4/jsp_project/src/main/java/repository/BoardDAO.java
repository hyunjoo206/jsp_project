package repository;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList();

	int page(PagingVO pgvo);

	List<BoardVO> pageList(PagingVO pgvo);

	BoardVO selectOne(int bno);

	int readcount(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	String selectRemoveFile(int bno);

}
