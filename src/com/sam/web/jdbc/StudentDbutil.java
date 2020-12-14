package com.sam.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
//it helper class /DAO layer 
public class StudentDbutil {
	private DataSource dataSource;
	public StudentDbutil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	public List<Student> getStudents() throws SQLException
	{
		List<Student> students = new ArrayList<Student>();
		
		Connection conn =null;
		Statement stmt =null;
		ResultSet myRs =null;
		
		try {
			
		
		// get connection
		conn= dataSource.getConnection();
		//create sql statement
		String sql ="select * from student order by last_name";
		stmt = conn.createStatement();
		//execute query
		myRs=stmt.executeQuery(sql);
		
		//processs result set
		while(myRs.next()) {
			//retrive the data from result set row
			int id =myRs.getInt("id");
			String firstName= myRs.getString("first_name");
			String lastName =myRs.getString("last_name");
			String email = myRs.getString("email");
			
			// create new student object
			Student tempStudent = new Student(id,firstName,lastName,email);
			
			//add it to the list of student
			students.add(tempStudent);
		}
		
		
		return students;
		
		}finally {
			//close jdbc object
			//conn.close();
			close(conn,stmt,myRs);
		}
		
	}
	private void close(Connection conn, Statement stmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs!= null) {
				myRs.close();
			}
			if(stmt !=null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void addStudent(Student theStudent) throws Exception {
		
		//do jdbc work
		Connection conn=null;
		PreparedStatement stmt=null;
		try {
			//get the conneciton
			conn= dataSource.getConnection();
		
		//create sql for insert
		String sql = "insert into student"
				+"(first_name,last_name,email)"
				+"values(?,?,?)";
		stmt=conn.prepareStatement(sql);
		
		//set the param value for the student 
		
		stmt.setString(1,theStudent.getFirstName());
		stmt.setString(2,theStudent.getLastName());
		stmt.setString(3,theStudent.getEmail());
		
		//execute sql insert 
		stmt.execute();
		
		//clean up kdbc objet
		
	}finally {
		close(conn,stmt,null);
		
	}
	}
	public Student getStudent(String theStudentId) throws Exception{
		Student theStudent =null;
		
		Connection conn =null;
		Statement stmt =null;
		ResultSet myRs =null;
		int studentId;
		try {
		//convert student id int int 
		 studentId = Integer.parseInt(theStudentId);
		
		// get connection
		conn= dataSource.getConnection();
		//create sql statement
		String sql ="select * from student where id=?";
		stmt = conn.createStatement();
		//create prepared statement
		stmt= conn.prepareStatement(sql);
		//set parameter
		((PreparedStatement) stmt).setInt(1, studentId);
		
		//execute query
		myRs=stmt.executeQuery(sql);
		
		//processs result set
		if(myRs.next()) {
			//retrive the data from result set row
			//int id =myRs.getInt("id");
			String firstName= myRs.getString("first_name");
			String lastName =myRs.getString("last_name");
			String email = myRs.getString("email");
			
			// use the studentId during the consturctor
			//Student tempStudent = new Student(id,firstName,lastName,email);
			theStudent =new Student(studentId,firstName,lastName,email);
		}
		else {
			throw new Exception ("could not find the student id"+studentId);
		}
		
		return theStudent;
		
		}finally {
			//close jdbc object
			//conn.close();
			close(conn,stmt,myRs);
		}
		
		//return theStudent;
	}
	public void updateStudent(Student theStudent) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			
			// create SQL update statement
			String sql = "update student set first_name=?,last_name=?,email=? where id=? ";
			
			// prepare statement
			myStmt = myConn.prepareStatement(sql);
			
			// set params
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			// execute SQL statement
			myStmt.execute();
		}
		finally {
			// clean up JDBC objects
			close(myConn, myStmt, null);
		}
	}

	public void deleteStudent(String theStudentId) throws SQLException {
		
		Connection conn=null;
		PreparedStatement stmt=null;
		try {
			//convert student id to int
			int studentId =Integer.parseInt(theStudentId);
			//get the conneciton
			conn=dataSource.getConnection();
			
			//create sql to delete the student
			String sql ="delete from student where id=?";
			//prepate statement
			stmt=conn.prepareStatement(sql);
			//set param
			stmt.setInt(1, studentId);
			//execute sql statement
			stmt.execute();
		}finally {
			close(conn,stmt,null);
			
		}
		
	}
}


