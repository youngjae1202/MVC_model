/**
 * DBCP�� �̿��Ͽ� �����ͺ��̽� ó��
 */
package org.kook.mvcBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.kook.mvcBoard.dto.BDto;

public class BDao {
	//DBCPó���� �ʿ��� DataSource ��ü ����
	DataSource dataSource;
	
	//������
	public BDao() {
		//DataSource��ü�� �̿��� oracle ����̹� �ε�
		try {
			Context context = new InitialContext();
			dataSource = 
			(DataSource) context.lookup("java:comp/env/"
					+ "jdbc/Oracle11g");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<BDto> list() {
		//�����ͺ��̽����� �� ���ڵ带 select�ϴ� �޼���
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		//�����ͺ��̽� ó���� ��ü ����
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String query = "select bId, bName, bTitle, bContent, bDate,"
					+ " bHit, bGroup, bStep, bIndent from mvcboard "
					+ "order by bGroup desc, bStep asc";
			// select�� ������ ������ desc�� ��������,asc�� �ø� ����
			//query�� sql������ ���ڿ��� ǥ��
			//sql������ �÷� �̸����� ��ҹ��� ���� ����
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			//select�� ����� �޸𸮿� �����ϴ� ��ü
			//resultSet�� ��� �ִ� ���ڵ带 ArrayList�� ����
			while(resultSet.next()) {
				//next()�� resultSet�� �ִ� ���ڵ尡 ������ �������ڵ� ��ġ�� �̵�
				//record�� �ִ� �Ӽ����� get��������("���̺��� �Ӽ���)���� ��
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				//���ڵ��� �� �Ӽ��� dto��ü ������ ���� ��������� ���� 
				BDto dto = 
				new BDto(bId, bName, bTitle, bContent, bDate, bHit,
						bGroup, bStep, bIndent);
				
				dtos.add(dto);				
			}
			System.out.println("list����");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(resultSet != null) resultSet.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			} catch(Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}		
		return dtos;		
	}
	
	public void write(String bName, String bTitle, String bContent) {
		System.out.println("write.do ó��");
		Connection connection = null;
		PreparedStatement preparedStatement = null;		
		
		//�Խ��� DB�� insert
		String sql ="INSERT INTO MVCBOARD (bId,bName,bTitle,bContent,bHit,bGroup,"
				+ "bStep,bIndent) values(MVCBOARD_SEQ.nextval,?,?,?,0,MVCBOARD_SEQ.currval,0,0)";
		//DB���� �� sql���� �� �ʿ��� ��ü ����
		try {
			connection = dataSource.getConnection(); //DB����
			preparedStatement = connection.prepareStatement(sql); //sql���� ��ü ����
			//sql���� ?���� ����
			preparedStatement.setString(1, bName); 
			//���ڴ� sql���� ?�� ���� ��ȣ,�ι�° ������ �޼��忡�� ���� ���ڷ� �������� ������ �Է� ��
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			//sql�� ����
			int rn = preparedStatement.executeUpdate();	
			//executeUpdate()���� insert,update,delete�� select���� ������ ��쿡 ����ϰ�
			//��ȯ���� sql�� ���� ����� ���� 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
		}		
	}
	
	public BDto contentView(String bId) {
		//������������ get������� ���޵� bId���� �޾Ƽ� �ش��ϴ� ���ڵ带 select��		
		System.out.println("contentView����");
		//hit�� ó�� ***
		upHit(bId);
		
		BDto dto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String sql = "SELECT * FROM MVCBOARD WHERE BID = ? ";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,Integer.parseInt(bId));
			//DB���� BID�� number���̹Ƿ� sql�� ���ô� ���ڿ��� bId������ ���ڷ� ��ȯ�ؼ�
			//setInt()�� ����Ͽ� ����
			resultSet = preparedStatement.executeQuery();
			//resultSet�� �ִ� ���ڵ��� �� �÷����� ������
			if(resultSet.next()) {
				int bid = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				dto = 
				new BDto(bid, bName, bTitle, bContent, bDate, bHit, bGroup, bStep,
						bIndent);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}						
		}		
		return dto;		
	}
	
	public void modify(String bId,String bName,String bTitle,String bContent) {
		System.out.println("modify ����");
		Connection connection = null;
		PreparedStatement preparedStatement = null;	
		try {
			connection = dataSource.getConnection();
			
			String sql = "update mvcboard set bName = ?, bTitle = ?, bContent = ? "
					+ "where bId = ?";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bId));
			
			int rn = preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	void upHit(String bId) {
		//DB�� bHit�÷��� 1���� ��Ŵ(update)
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql = "update mvcboard set bHit = bHit + 1 where bId = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bId));	
			int rn = preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}						
		}
	}
	
	public void delete(String bId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql = "DELETE FROM MVCBOARD WHERE BID = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,Integer.parseInt(bId));
			int rn = preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	public BDto reply_view(String bId) {
		//��ȯ�� BDto ��ü ����
		BDto dto = null;
		//DBó���� �ʿ��� ��ü�� ����
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		//sql SELECT���� ����ϱ� ���ؼ� ResultSet ��ü ����
		ResultSet resultSet = null;
		try {
			String sql = "SELECT * FROM MVCBOARD WHERE bId = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			//���ڷ� ���� bId�� String�̰� DB�� bId�� number�̹Ƿ� parseInt�޼���� ������ ����
			resultSet = preparedStatement.executeQuery();
			//ResultSetŬ������ �޼����� ��ȸ�� ���ڵ尡 �ִ��ĸ� �Ǻ��ϴ� �޼���  next()�� �Ǻ��Ͽ� 
			//true�̸� ���ڵ��� �� �÷�(�Ӽ�)���� BDto��ü�� ��ȯ����
			if(resultSet.next()) {
				int bid = resultSet.getInt("bId"); //��ȣ���� ���ڴ� DB�� �÷� �̸�,�޼����̸���  get��������
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				dto = new BDto(bid, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
			}
			
		}
		catch(Exception e) {
			
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}						
		}
		System.out.println("reply_view����");
		return dto;
	}
	
	public void  reply(String bId, String bName, String bTitle,String bContent, String bGroup,
			String bStep, String bIndent) {
		//�Է� ���� INSERT�ϹǷ� ResultSet�� �ʿ� ���� executeUpdate()�޼��带 ���
		replyShape(bGroup, bStep);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql = "insert into mvcboard (bId, bName, bTitle, bContent, bGroup, bStep,"
					+ "bIndent) values (mvcboard_seq.nextval, ?, ?, ?, ?, ?, ?)";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			//sql�� ?���� ����
			preparedStatement.setString(1, bName);
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			preparedStatement.setInt(4, Integer.parseInt(bGroup));
			preparedStatement.setInt(5, Integer.parseInt(bStep) + 1);
			preparedStatement.setInt(6, Integer.parseInt(bIndent) + 1);
			
			int rn = preparedStatement.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}					
		}		
	}
	
	void replyShape(String bGroup,String  bStep) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql = "UPDATE MVCBOARD SET bStep = bStep + 1 WHERE bGroup = ? and bStep > ?";
			connection = dataSource.getConnection();			
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bGroup));
			preparedStatement.setInt(2, Integer.parseInt(bStep));
			
			int rn = preparedStatement.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}		
		}
	}
	
	public Boolean join(String uid,String upw,String uaddr,String uhobby,String uprofile) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Boolean result = false;
		try {
			String sql = "INSERT INTO USERDB(pid,ppw,paddress,phobby,pprofile) "
					+ "values(?,?,?,?,?)";	
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			preparedStatement.setString(2, upw);
			preparedStatement.setString(3, uaddr);
			preparedStatement.setString(4, uhobby);
			preparedStatement.setString(5, uprofile);
			
			int rn = preparedStatement.executeUpdate();
			if(rn > 0)
				result = true;
			else
				result = false;
		}
		catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null ||
						!connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}		
		}
		return result;
	}
	
	public Boolean login(String uid, String upw) {
		Boolean result = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		
		ResultSet rset = null;
		try {
			String sql = "SELECT PID,PPW FROM USERDB WHERE PID=? AND PPW=?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, uid);
			preparedStatement.setString(2, upw);
			rset = preparedStatement.executeQuery();
			if(rset.next()) {
				result = true;				
			}
			else 
				result = false;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			result = false;
		}
		
		finally {
			try {
				if(preparedStatement != null) 
					preparedStatement.close();
				if(connection != null || !connection.isClosed())
					connection.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}		
		}
		return result;
	}
}


























