package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import repository.MemberDAO;
import repository.MemberDAOImpl;

public class MemberServiceImpl implements MemberService {
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	private MemberDAO mdao;
	
	//service와 DAO를 이어주기 위해 작성
	//
	public MemberServiceImpl() {
		mdao = new MemberDAOImpl();
	}
	
	@Override
	//여기서 int가 리턴값 => isOk를 하기 위해서
	public int register(MemberVO mvo) {
		//log로 service 잘 진입했는지 찍어주기
		log.info(">>> register service 진입완료");
		//db에 갖다주고 돌아오는 값은 객체여야 하니까
		
		//return을 하는 이유는 isOk로 다시 갖다 주려고 하는것
		return mdao.register(mvo);
	}

	@Override
	public MemberVO login_auth(MemberVO mvo2) {
		//log로 찍어보기
		log.info(">>> login_auth service 진입완료");
		return mdao.login_auth(mvo2);
	}

	@Override
	public int lastlogin(String id2) {
		log.info(">>> lastlogin service 진입완료");
		return mdao.lastlogin(id2);
	}

	@Override
	public int edit(MemberVO mvo3) {
		log.info(">>> edit service 진입완료");
		return mdao.edit(mvo3);
	}

	@Override
	public int remove(String delid) {
		log.info(">>> delete service 진입완료");
		return mdao.remove(delid);
	}

	@Override
	public List<MemberVO> showlist() {
		log.info(">>> showlist service 진입완료");
		return mdao.showlist();
	}
	
}
