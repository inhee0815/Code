package project2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BbsServlet extends HttpServlet {
	
	//get 방식으로 요청되었을 때 호출됨
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * String d1 = Integer.toString((int) ((Math.random() * 6) + 1)); String
		 * d2 = Integer.toString((int) ((Math.random() * 6) + 1)); PrintWriter
		 * out = response.getWriter();
		 * 
		 * out.println("<html> < body>" +
		 * "<h1 align=center>HF\'s Chap 2 Dice Roller</h1>" + "<p>" + d1 +
		 * " and " + d2 + " were rolled " + "</body> </html>");
		 */
		super.doGet(request, response);
	}
	//post방식으로 요청되었을 때 호출됨
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter write = response.getWriter();
		String title = request.getParameter("title");
		String writer= request.getParameter("writer");
		String date = request.getParameter("reg_date");		
		addArticle(title, writer, date);
		showArticles(write);
	}
    //데이터 관련 작업 처리 명령은 반드시 예외처리
	private void addArticle(String title, String writer, String date) {
		Connection connection;
		Statement statement;
		String sql;
		String jdbcUrl = "jdbc:mysql://localhost:3306/test";
		String databaseID = "root";
		String databasePW = "";

		try {
			Class.forName("com.mysql.jdbc.Driver"); //jdbc 드라이버 클래스를 동적으로 jvm에 로딩
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found : " + e);

		}
		try {
			sql = "INSERT INTO TEST (title, writer, date) VALUES ('" + title + "','" + writer + "','"  + date + ")";
			connection = DriverManager.getConnection(jdbcUrl, databaseID, databasePW); //애플리케이션 mysql 서버 접속
			statement = connection.createStatement(); //statement 객체를 생성 
			//jdbc를 사용하는 애플리케이션에서 select 뿐만 아니라 모든 sql과 ddl 문장을 실행하는 데 필요한 객체
			//execute(), executeQuery(), executeUpdate()		
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println("SQL Error : " + e);
		} catch (Exception e) {
			System.err.println("Error : " + e);
		}
	}

	private void showArticles(PrintWriter writer) {
		Connection connection;
		Statement statement;
		ResultSet resultset;
		String sql;
		String jdbcUrl = "jdbc:mysql://localhost:3306/test";
		String databaseID = "root";
		String databasePW = "";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found : " + e);
		}
		try {
			sql = "SELECT * FROM TEST";
			connection = DriverManager.getConnection(jdbcUrl, databaseID, databasePW);
			statement = connection.createStatement();
			resultset = statement.executeQuery(sql); // SELECT 쿼리의 결과를 레코드 단위로 하나씩 페치할 수 있는 기능을 제공하는 객체
			while (resultset.next()) { 
				writer.println(resultset.getString("title") + " | "
						+ resultset.getString("writer" + " | " + resultset.getString("date") + "<br>"));
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.err.println("SQL Error : " + e);
		} catch (Exception e) {
			System.err.println("Error : " + e);
		}
	}
}
