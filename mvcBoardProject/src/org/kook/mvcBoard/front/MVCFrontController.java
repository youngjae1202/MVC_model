
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
		request.setCharacterEncoding("UTF-8"); //�ѱ� ó��
		
		actionDo(request, response);
	}
	
	void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		System.out.println("actionDo");
		
		String viewPage = null; //View(������ UIó�� ������--���������� ���� ó������ ������ )
		BCommand command = null; //����Ʈ��Ʈ�Ѷ󿡼� �з��� ��û�� ó���� Ŭ���� ��ü
		//BCommand�� command�� ó���ϴ� Ŭ�������� �������̽�
		
		//request URL���� .do�� ���и� �����ϴ� ����
		String uri = request.getRequestURI(); // ContextPath(������Ʈ��)/xxx.do
		String conPath = request.getContextPath(); // ContextPath(������Ʈ��)
		String com = uri.substring(conPath.length()); // xxx.do�� ����
		System.out.println(com);
		if(com.equals("/list.do")) { //��ü �Խ��� ��� �����ֱ�(�Խ����� ù° ȭ��)
			System.out.println("list.do");
			command = new BListCommand();
			//BCommand�������̽��� ������ list ó���� Ŭ���� BListCommand ��ü
			command.execute(request,response);
			//list�� ���� ó�� �޼��带 ������
			viewPage = "/Web/list.jsp";
		}
		else if(com.equals("/write_view.do")) {
			viewPage = "/Web/write_view.jsp";			
		}
		else if(com.equals("/write.do")) {
			//�����ͺ��̽��� write_view.jsp�� form���� �Էµ� ���� ����
			command = new BWriteCommand();
			command.execute(request, response);
			//���� �Ϸ��Ŀ� ���ο� ����Ʈ â�� ������	
			response.setHeader("Cache-Control", "no-cache");
			//ajax�� cache��� ����
			//viewPage = "list.do";	
			viewPage = "";
		}
		else if(com.equals("/content_view.do")) {
			//DB�� ��ȸ
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "Web/content_view.jsp";			
		}
		else if(com.equals("/modify.do")) {
			//DB�� update
			command = new BModifyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(com.equals("/delete.do")) {
			//DB�� delete
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		
		else if(com.equals("/reply_view.do")) {
			//DB���� ��� �ۼ��� �ʿ��� �����͸� ������
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "Web/reply_view.jsp";
		}
		
		else if(com.equals("/reply.do")) {
			//DB�� ��� ���� ����
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.do";
		}
		else if(com.equals("/login.do")) {
			/*
			 * command��ü ���� (commandŬ������ BLoginCommand)
			 * BLoginCommand���� execute�޼��� override
			 * BDao�� loginó�� �޼��带 ����	
			 * db�� userTable�� ����(�Ӽ� pid(��Ű),ppw,paddress,pphone,pprofile,phobby ...)		
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
				+ "<script>alert('���Խ���');</script><a href='Web/join.jsp' target='content'>ȸ������</a></body></html>");
				*/
				viewPage = "Web/joinNOK.jsp";
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		//RequestDispatcherŬ������ request��û�� viewpage�� ������ ������Ʈ�� ��û�� 
		//�����ϴ� ��Ȱ�� �ϴ� Ŭ����
		//list.jsp������Ʈ�� ���������� lidt.do�� ��û�� ������ �̵�(����)
		if(viewPage != "")			
			dispatcher.forward(request, response);
		
	}

}









