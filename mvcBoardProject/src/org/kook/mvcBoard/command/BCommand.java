package org.kook.mvcBoard.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommand {
	//추상메서드
	/*public abstract*/ void execute(HttpServletRequest request, 
			HttpServletResponse response);
}
