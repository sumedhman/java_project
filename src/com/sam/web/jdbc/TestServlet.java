package com.sam.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//define datasource /connection pool for resources  injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//step 1 set up the printWrite
	PrintWriter out =response.getWriter();
	response.setContentType("text/html");
	//step 2 get the connection to db
	Connection conn=null;
	Statement stmt = null;
	ResultSet myRs = null;
	
	try {
		
	conn=dataSource.getConnection();
		
	//step3 create a sql statement
	String sql= "select * from student";
	stmt = conn.createStatement();
	//step 4 execute sql query
	myRs= stmt.executeQuery(sql);
		
	//step process the result set
	while(myRs.next()) {
		String email= myRs.getString("email");
		out.println(email);
		out.println();
	}
	
	}catch(Exception e) {
		e.printStackTrace();
	}
	
	}

}
