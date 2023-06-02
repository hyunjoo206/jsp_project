package service;

import java.util.List;

import domain.MemberVO;

public interface MemberService {

	int register(MemberVO mvo);

	MemberVO login(MemberVO mvo2);

	int lastLogin(String id2);

	int modify(MemberVO mvo3);

	int remove(String delid);

	List<MemberVO> list();

	
}
