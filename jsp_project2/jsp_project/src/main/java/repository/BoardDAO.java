package repository;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardDAO {

	List<BoardVO> selectList();

	int insert(BoardVO bvo);

	BoardVO selectOne(int bno);

	int update(BoardVO bvo3);

	int delete(int bno4);

	int readcount(int bno);

	int page(PagingVO pgvo);

	List<BoardVO> pageList(PagingVO pgvo);

}
