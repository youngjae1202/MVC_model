package org.kook.mvcBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;

public class BLoginCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("uid");
		String pw = request.getParameter("upw");
		BDao dao = new BDao();
		Boolean result = dao.login(id,pw);
		request.setAttribute("result", result);
	}

}
