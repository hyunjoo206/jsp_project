package service;

import java.util.List;

import domain.MemberVO;

public interface MemberService {

	int register(MemberVO mvo);

	MemberVO login_auth(MemberVO mvo2);

	int lastlogin(String id2);

	int edit(MemberVO mvo3);

	int remove(String delid);

	List<MemberVO> showlist();

}
