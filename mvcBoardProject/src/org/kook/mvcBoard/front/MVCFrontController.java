
package org.kook.mvcBoard.front;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.command.BCommand;
import org.kook.mvcBoard.command.BContentCommand;
import org.kook.mvcBoard.command.BDeleteCommand;
import org.kook.mvcBoard.command.BJoinCommand;
import org.kook.mvcBoard.command.BListCommand;
import org.kook.mvcBoard.command.BLoginCommand;
import org.kook.mvcBoard.command.BModifyCommand;
import org.kook.mvcBoard.command.BReplyCommand;
import org.kook.mvcBoard.command.BReplyViewCommand;
import org.kook.mvcBoard.command.BWriteCommand;

/**
 * Servlet implementation class MVCFrontController
 */
@WebServlet("*.do")
public class MVCFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MVCFrontController() {
       super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		request.setCharacterEncoding("UTF-8"); //한글 처리
		
		actionDo(request, response);
	}
	
	void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		System.out.println("actionDo");
		
		String viewPage = null; //View(보여줄 UI처리 페이지--웹브라우져로 응답 처리해줄 페이지 )
		BCommand command = null; //프론트콘트롤라에서 분류된 요청을 처리할 클래스 객체
		//BCommand는 command를 처리하는 클래스들의 인터페이스
		
		//request URL에서 .do의 성분만 추출하는 과정
		String uri = request.getRequestURI(); // ContextPath(프로젝트명)/xxx.do
		String conPath = request.getContextPath(); // ContextPath(프로젝트명)
		String com = uri.substring(conPath.length()); // xxx.do만 추출
		System.out.println(com);
		if(com.equals("/list.do")) { //전체 게시판 목록 보여주기(게시판의 첫째 화면)
			System.out.println("list.do");
			command = new BListCommand();
			//BCommand인터페이스를 구현한 list 처리용 클래스 BListCommand 객체
			command.execute(request,response);
			//list에 대한 처리 메서드를 재정의
			viewPage = "/Web/list.jsp";
		}
		else if(com.equals("/write_view.do")) {
			viewPage = "/Web/write_view.jsp";			
		}
		else if(com.equals("/write.do")) {
			//데이터베이스에 write_view.jsp의 form에서 입력된 값을 저장
			command = new BWriteCommand();
			command.execute(request, response);
			//저장 완료후에 새로운 리스트 창을 보여줌	
			response.setHeader("Cache-Control", "no-cache");
			//ajax는 cache사용 안함
			//viewPage = "list.do";	
			viewPage = "";
		}
		else if(com.equals("/content_view.do")) {
			//DB를 조회
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "Web/content_view.jsp";			
		}
		else if(com.equals("/modify.do")) {
			//DB를 update
			command = new BModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(com.equals("/delete.do")) {
			//DB를 delete
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		
		else if(com.equals("/reply_view.do")) {
			//DB에서 댓글 작성에 필요한 데이터를 가져옴
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "Web/reply_view.jsp";
		}
		
		else if(com.equals("/reply.do")) {
			//DB에 댓글 내용 저장
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(com.equals("/login.do")) {
			/*
			 * command객체 생성 (command클래스는 BLoginCommand)
			 * BLoginCommand에서 execute메서드 override
			 * BDao에 login처리 메서드를 만듬	
			 * db는 userTable로 만듬(속성 pid(주키),ppw,paddress,pphone,pprofile,phobby ...)		
			 */
			command = new BLoginCommand();
			command.execute(request, response);
			Boolean result = (Boolean)request.getAttribute("result");
			if(result)
				viewPage = "Web/mainFrame.html";
			else
				viewPage = "Web/loginNOK.html";
		}
		
		else if(com.equals("/join.do")) {
			command = new BJoinCommand();
			command.execute(request, response);
			Boolean result = (Boolean)request.getAttribute("result");
			
			if(result == true)
				viewPage = "Web/mainFrame.html";
			else {
				/*
				viewPage ="Web/join.jsp";
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				writer.println("<html lang='ko'><meta charset='UTF-8'><head></head><body>"
				+ "<script>alert('가입실패');</script><a href='Web/join.jsp' target='content'>회원가입</a></body></html>");
				*/
				viewPage = "Web/joinNOK.jsp";
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		//RequestDispatcher클래스는 request요청을 viewpage로 지정된 콤포넌트로 요청을 
		//위임하는 역활을 하는 클래스
		//list.jsp콤포넌트로 브라우져에서 lidt.do로 요청한 내용을 이동(위임)
		if(viewPage != "")			
			dispatcher.forward(request, response);
		
	}

}









