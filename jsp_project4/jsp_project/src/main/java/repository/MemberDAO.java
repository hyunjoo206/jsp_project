package repository;

import java.util.List;

import domain.MemberVO;

public interface MemberDAO {

	int insert(MemberVO mvo);

	MemberVO selectOne(MemberVO mvo2);

	int lastLogin(String id);

	int update(MemberVO mvo);

	int delete(String delid);

	List<MemberVO> selectList();

}
