package org.kook.mvcBoard.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kook.mvcBoard.dao.BDao;
import org.kook.mvcBoard.dto.BDto;

public class BListCommand implements BCommand {
	//�������̽� BCommand�� �ִ� �߻�޼��带 ������
	@Override
	public void execute(HttpServletRequest request, 
			HttpServletResponse response) {
		//�Խ��� ����Ʈ�� ���Ǵ� DB�� select�� ���ڵ� ������ ó����
		//DBó���� BDaoŬ������ �ϹǷ� ��ü �����ϰ� selectó���ϴ�
		//�޼��带 ȣ������
		BDao dao = new BDao();
		ArrayList<BDto> dtos = dao.list();
		request.setAttribute("list", dtos);	
		//���ڷ� ���޹��� request��ü�� ��������̸��� list�� �Ͽ�
		//dtos��ü ���� ����
	}
}
