package com.sam.web.jdbc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbutil studentDbutil;
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		
		super.init();
		//create out student db utl and pass in the conn pool/datasource
		try {
			studentDbutil = new StudentDbutil(dataSource);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			
		//read the command parameter
		String theCommand = request.getParameter("command");	
		
		// if command is missing,then give default list of student
		if(theCommand == null) {
			theCommand ="LIST";
		}
		
		//route to appropriate method
			
		switch(theCommand) {
		case "LIST":
			listStudents(request,response);
			break;
			
		case "ADD":
			addStudent(request,response);
			break;
			
		case "LOAD":
			loadStudent(request,response);
			break;
			
		case "UPDATE":
			updateStudent(request,response);
			break;
			
		case "DELETE":
			deleteStudent(request,response);
			break;
			
		default: 
			listStudents(request,response);
		}
			
	} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		//read the student id from form db
		
		String theStudentId =request.getParameter("studentId");
		
		//delete the student from db
		studentDbutil.deleteStudent(theStudentId);
		//send them back to list student page
		listStudents(request,response);
		
		
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		//read student info from the form data
		int id= Integer.parseInt(request.getParameter("studentId"));
		String firstName= request.getParameter("firstName");
		String lastName =request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create new student object
		Student theStudent =new Student(id,firstName,lastName,email);
		//perform update on db
		studentDbutil.updateStudent(theStudent);
		//send them back to list student page
		listStudents(request,response);
		
	}


	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student id from the data
		String theStudentId = request.getParameter("studentId");
		// get student from db (db util)
		Student theStudent = studentDbutil.getStudent(theStudentId);
		//place the student in the request
		request.setAttribute("THE_STUDENT", theStudent);
		//send to the jsp page:update-student-form.jsp
		
		RequestDispatcher dispacter = request.getRequestDispatcher("/update-student-form.jsp");
		
		dispacter.forward(request,response);
	
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student info form data
		String firstName= request.getParameter("firstName");
		String lastName =request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a new student object
		Student theStudent =new Student(firstName,lastName,email);
		//add the student to db
		
		studentDbutil.addStudent(theStudent);
		//send back to main page(student list)
		listStudents(request,response);
		//response.sendRedirect("/list-students.jsp");
	}
	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		//get the student from db util
		List<Student> students =studentDbutil.getStudents();
		
		
		//add student to the request
		request.setAttribute("STUDENT_LIST",students );
		//sent to js page(view)
		RequestDispatcher dispacther = request.getRequestDispatcher("/list-students.jsp");
		dispacther.forward(request, response);
		
	}
	

}
