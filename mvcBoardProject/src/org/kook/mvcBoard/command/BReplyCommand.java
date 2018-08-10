package org.kook.mvcBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;

public class BReplyCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		//request��ü�� ���Ե� ��� �ۼ�â���� post ������� ���޵� �Է°��� ����
		String bId = request.getParameter("bId"); //�Ķ���ʹ� �Է�â�� name�Ӽ���
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
