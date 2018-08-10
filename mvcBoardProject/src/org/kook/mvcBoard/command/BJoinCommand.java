package org.kook.mvcBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;

public class BJoinCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("uid");
		String pw = request.getParameter("upw");
		String address = request.getParameter("uaddress");
		String hobby = request.getParameter("uhobby");
		String profile = request.getParameter("uprofile");
		BDao dao = new BDao();
		boolean result = dao.join(id,pw,address,hobby,profile);
		request.setAttribute("result", result);
	}

}
