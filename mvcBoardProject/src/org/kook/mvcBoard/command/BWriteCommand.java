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
		//getParameter(submit�� Ŭ���̾�Ʈ���� ���� name�Ӽ���)�� form���� ���� ������ ���� ��
		BDao dao = new BDao();
		dao.write(bName, bTitle, bContent);
		//DB�� insert����
	}

}
