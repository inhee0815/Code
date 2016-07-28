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
	
	//get ������� ��û�Ǿ��� �� ȣ���
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
	//post������� ��û�Ǿ��� �� ȣ���
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
    //������ ���� �۾� ó�� ����� �ݵ�� ����ó��
	private void addArticle(String title, String writer, String date) {
		Connection connection;
		Statement statement;
		String sql;
		String jdbcUrl = "jdbc:mysql://localhost:3306/test";
		String databaseID = "root";
		String databasePW = "";

		try {
			Class.forName("com.mysql.jdbc.Driver"); //jdbc ����̹� Ŭ������ �������� jvm�� �ε�
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found : " + e);

		}
		try {
			sql = "INSERT INTO TEST (title, writer, date) VALUES ('" + title + "','" + writer + "','"  + date + ")";
			connection = DriverManager.getConnection(jdbcUrl, databaseID, databasePW); //���ø����̼� mysql ���� ����
			statement = connection.createStatement(); //statement ��ü�� ���� 
			//jdbc�� ����ϴ� ���ø����̼ǿ��� select �Ӹ� �ƴ϶� ��� sql�� ddl ������ �����ϴ� �� �ʿ��� ��ü
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
			resultset = statement.executeQuery(sql); // SELECT ������ ����� ���ڵ� ������ �ϳ��� ��ġ�� �� �ִ� ����� �����ϴ� ��ü
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
