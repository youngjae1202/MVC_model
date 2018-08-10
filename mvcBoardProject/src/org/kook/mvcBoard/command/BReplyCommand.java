package org.kook.mvcBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;

public class BReplyCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//request객체에 포함된 댓글 작성창에서 post 방식으로 전달된 입력값을 추출
		String bId = request.getParameter("bId"); //파라메터는 입력창의 name속성값
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bGroup = request.getParameter("bGroup");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		
		BDao dao = new BDao(); 
		dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
	}

}
