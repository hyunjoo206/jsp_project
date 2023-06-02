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

	int getTotal(PagingVO pgvo);

	List<BoardVO> getPageList(PagingVO pgvo);



}
