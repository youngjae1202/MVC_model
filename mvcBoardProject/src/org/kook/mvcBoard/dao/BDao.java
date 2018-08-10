/**
 * DBCP를 이용하여 데이터베이스 처리
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
	//DBCP처리에 필요한 DataSource 객체 선언
	DataSource dataSource;
	
	//생성자
	public BDao() {
		//DataSource객체를 이용한 oracle 드라이버 로딩
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
		//데이터베이스에서 각 레코드를 select하는 메서드
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		//데이터베이스 처리용 객체 선언
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			String query = "select bId, bName, bTitle, bContent, bDate,"
					+ " bHit, bGroup, bStep, bIndent from mvcboard "
					+ "order by bGroup desc, bStep asc";
			// select한 데이터 정렬은 desc는 내림차순,asc는 올림 차순
			//query는 sql문법을 문자열로 표시
			//sql에서는 컬럼 이름들은 대소문자 구분 안함
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			//select한 결과를 메모리에 저장하는 객체
			//resultSet에 들어 있는 레코드를 ArrayList에 저장
			while(resultSet.next()) {
				//next()는 resultSet에 있는 레코드가 있으면 다음레코드 위치로 이동
				//record에 있는 속성값은 get데이터형("테이블의 속성명)으로 얻어냄
				int bId = resultSet.getInt("bId");
				String bName = resultSet.getString("bName");
				String bTitle = resultSet.getString("bTitle");
				String bContent = resultSet.getString("bContent");
				Timestamp bDate = resultSet.getTimestamp("bDate");
				int bHit = resultSet.getInt("bHit");
				int bGroup = resultSet.getInt("bGroup");
				int bStep = resultSet.getInt("bStep");
				int bIndent = resultSet.getInt("bIndent");
				
				//레코드의 각 속성을 dto객체 생성을 통해 멤버변수로 저장 
				BDto dto = 
				new BDto(bId, bName, bTitle, bContent, bDate, bHit,
						bGroup, bStep, bIndent);
				
				dtos.add(dto);				
			}
			System.out.println("list성공");
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
		System.out.println("write.do 처리");
		Connection connection = null;
		PreparedStatement preparedStatement = null;		
		
		//게시판 DB에 insert
		String sql ="INSERT INTO MVCBOARD (bId,bName,bTitle,bContent,bHit,bGroup,"
				+ "bStep,bIndent) values(MVCBOARD_SEQ.nextval,?,?,?,0,MVCBOARD_SEQ.currval,0,0)";
		//DB접속 및 sql실행 에 필요한 객체 생성
		try {
			connection = dataSource.getConnection(); //DB연결
			preparedStatement = connection.prepareStatement(sql); //sql실행 객체 생성
			//sql문에 ?값을 설정
			preparedStatement.setString(1, bName); 
			//숫자는 sql에서 ?의 순서 번호,두번째 변수는 메서드에서 받은 인자로 웹페이지 폼에서 입력 값
			preparedStatement.setString(2, bTitle);
			preparedStatement.setString(3, bContent);
			//sql문 실행
			int rn = preparedStatement.executeUpdate();	
			//executeUpdate()문은 insert,update,delete등 select문을 제외한 경우에 사용하고
			//반환값은 sql문 성공 결과의 갯수 
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
		//웹페이지에서 get방식으로 전달된 bId값을 받아서 해당하는 레코드를 select함		
		System.out.println("contentView성공");
		//hit수 처리 ***
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
			//DB에는 BID가 number형이므로 sql로 사용시는 문자열인 bId변수를 숫자로 변환해서
			//setInt()를 사용하여 설정
			resultSet = preparedStatement.executeQuery();
			//resultSet에 있는 레코드의 각 컬럼값을 가져옴
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
		System.out.println("modify 성공");
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
		//DB의 bHit컬럼을 1증가 시킴(update)
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
		//반환할 BDto 객체 선언
		BDto dto = null;
		//DB처리에 필요한 객체들 선언
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		//sql SELECT문을 사용하기 위해서 ResultSet 객체 선언
		ResultSet resultSet = null;
		try {
			String sql = "SELECT * FROM MVCBOARD WHERE bId = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(bId));
			//인자로 받은 bId는 String이고 DB의 bId는 number이므로 parseInt메서드로 정수로 변경
			resultSet = preparedStatement.executeQuery();
			//ResultSet클래스의 메서드중 조회한 레코드가 있느냐를 판별하는 메서드  next()로 판별하여 
			//true이면 레코드의 각 컬럼(속성)값을 BDto객체로 변환해줌
			if(resultSet.next()) {
				int bid = resultSet.getInt("bId"); //괄호안의 인자는 DB의 컬럼 이름,메서드이름은  get데이터형
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
		System.out.println("reply_view성공");
		return dto;
	}
	
	public void  reply(String bId, String bName, String bTitle,String bContent, String bGroup,
			String bStep, String bIndent) {
		//입력 값을 INSERT하므로 ResultSet은 필요 없고 executeUpdate()메서드를 사용
		replyShape(bGroup, bStep);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String sql = "insert into mvcboard (bId, bName, bTitle, bContent, bGroup, bStep,"
					+ "bIndent) values (mvcboard_seq.nextval, ?, ?, ?, ?, ?, ?)";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			//sql의 ?값을 설정
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


























