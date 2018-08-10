package org.kook.mvcBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;

public class BWriteCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {		
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		//getParameter(submit시 클라이언트에서 보낸 name속성값)는 form으로 보낸 데이터 값을 얻어냄
		BDao dao = new BDao();
		dao.write(bName, bTitle, bContent);
		//DB에 insert과정
	}

}
