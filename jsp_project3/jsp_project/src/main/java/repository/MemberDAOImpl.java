package repository;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import orm.DatabaseBuilder;

public class MemberDAOImpl implements MemberDAO {
	//log 찍어주려고
	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	
	//여기 이해 안감
	//SqlSession은 DB라고 생각하면 됨
	private SqlSession sql;
	//NameSpace = SQL이 mapper를 구분하기 위한 이름
	//NameSpace.SQl 구문이름
	private String NS = "MemberMapper.";
	
	//DAO랑 DB랑 연결하는 구문
	public MemberDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}
	
	@Override
	public int register(MemberVO mvo) {
		//isOk로 리턴받아서 확인하기 위함
		//sql에 있는거(SqlSession에서 제공하는 게 insert라서 insert쓰는 거임)
		//sql에 insert로 MemberMapper.reg 로 이어주는거고 mvo로 들고 가니까... 
		int isOk = sql.insert(NS+"reg", mvo);
		//sql.commit을 안하면 db에서 오류가 날 수 있기 때문에 해줘야 함.
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public MemberVO login_auth(MemberVO mvo2) {
		//MemberVO 객체로 리턴받는거
		MemberVO mvo = sql.selectOne(NS+"log", mvo2);
		return mvo;
	}

	@Override
	public int lastlogin(String id2) {
		//isOk로 확인해야 하니까 
		log.info(">>>logout DAO 진입");
		//DB에 있는 last_login값에 넣어주는 거니까
		//sql 구문으로는 update
		int isOk = sql.update(NS+"logout", id2);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int edit(MemberVO mvo3) {
		//isOk로 확인하는 것
		log.info(">>>edit DAO 진입");
		//DB에 있는 내용 수정해주는 거니까 sql구문으로 update
		int isOk = sql.update(NS+"edit", mvo3);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public int remove(String delid) {
		log.info(">>>delete DAO 진입");
		//DB에 있는 내용 삭제하는 거니까 sql 구문 중 delete
		int isOk = sql.delete(NS+"del", delid);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<MemberVO> showlist() {
		log.info(">>>showlist DAO 진입");
		//DB에 있는 내용을 가져와서 jsp에 전달해야 하는거니
		//sql구문 중 select
		//리스트 객체만들어서 받아와야 하니까 만들어줌
		List<MemberVO> list = new ArrayList<MemberVO>();
		//sql에 원래 있는 selectList 불러온거.
		list = sql.selectList(NS+"showlist");
		//리턴받는 값도 list
		return list;
	}

}