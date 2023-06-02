package handler;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHandler {
	private static Logger log = LoggerFactory.getLogger(FileHandler.class);
	
	//이미지랑 path 주고 잘 됐으면 1 리턴할 예정
	public int deleteFile(String imageFileName, String savePath) {
		boolean isDel = false;
		//파일을 삭제하는 메서드의 리턴타입이 boolean 
		log.info(">>> deleteFile method접근");
		log.info(">>> 이미지 파일 이름 : "+ imageFileName);
		
		File fileDir = new File(savePath);
										//경로 분리하는 seperator /
		File removeFile = new File(fileDir+File.separator+imageFileName);
		File removeThumbFile = new File(fileDir+File.separator+"th_"+imageFileName);
		
		//해당하는 파일이 없다면?
		//지워야 하는 파일이 있는지(존재하는지) 확인 => 삭제
		if(removeFile.exists() || removeThumbFile.exists()) {
			isDel = removeFile.delete(); //리턴 형태는 boolean
			log.info(">>> remove File > "+(isDel ? "성공":"실패"));
			if(isDel) { //메인 파일이 지워졌다면 썸네일 파일도 삭제
				isDel = removeThumbFile.delete();
				log.info(">>> removeThumbFile > "+(isDel ? "성공":"실패"));
			}
		}
		log.info(">>> FileHandler remove OK");
		return isDel? 1 : 0;
	}
}
