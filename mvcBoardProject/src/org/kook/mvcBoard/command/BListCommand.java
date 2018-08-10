package org.kook.mvcBoard.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;
import org.kook.mvcBoard.dto.BDto;

public class BListCommand implements BCommand {
	//인터페이스 BCommand에 있는 추상메서드를 재정의
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) {
		//게시판 리스트에 사용되는 DB를 select한 레코드 값들을 처리함
		//DB처리는 BDao클래스에 하므로 객체 생성하고 select처리하는
		//메서드를 호출해줌
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		request.setAttribute("list", dtos);	
		//인자로 전달받은 request객체에 멤버변수이름이 list로 하여
		//dtos객체 값을 설정
	}
}
