import java.sql.*;
import java.text.DecimalFormat;
import oracle.jdbc.pool.OracleDataSource;
import java.util.Scanner;

public class Main {
	
			static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";;
			static final String uName="";
			static final String uPass="";
			
	public static void main(String[] args) {
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		ResultSet rs=null;
		Statement stmt=null;
		try{	
			
			OracleDataSource ds=new OracleDataSource();
			ds.setURL(host);
			con=ds.getConnection(uName,uPass);
			System.out.println("Connected");
			stmt=con.createStatement();

		    System.out.println("1:Register 2:Login");
		    int in=scan.nextInt();
		    if(in==1){
		    	sql="SELECT MAX(id) AS LastID FROM Membership";
		    	rs=stmt.executeQuery(sql);
		    	rs.next();
		    	int currid=rs.getInt("LastID");
		    	currid++;
		    	System.out.println("Enter new user name:");
		    	String usrname=scan.next();
		    	System.out.println("Enter new password:");
		    	String passwrd=scan.next();
		    	sql="INSERT INTO Membership (id,name,password) VALUES ("+currid+",'"+usrname+"','"+passwrd+"')";
		    	stmt.executeUpdate(sql);
		    	System.out.println("Successfully registered");
		    	
		    }
		    else if(in==2){
		    	boolean logged=false;
		    	System.out.println("Enter new user name:");
		    	String usrname=scan.next();
		    	System.out.println("Enter new password:");
		    	String passwrd=scan.next();
		    	sql="SELECT name,password FROM Membership";
		    	rs=stmt.executeQuery(sql);
		    	while(rs.next()){
		    		String nm=rs.getString("name");
		    		String n[]=nm.split(" ",2);
		    		String ps=rs.getString("password");
		    		String p[]=ps.split(" ",2);
		    		if(n[0].equals(usrname)&&p[0].equals(passwrd)){
		    			System.out.println("You have successfully logged in");
		    			logged=true;
		    			break;
		    		}
		    	}
		    	if(logged==false){
		    		System.out.println("Wrong credentials");
		    	}
		    }
		    else
		    	System.out.println("Wrong command");
		    
		    
			stmt.close();
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}finally{
		      //finally block used to close resources
		      try{
		            stmt.close();
		      }catch(SQLException se){
		      }// do nothing
		      scan.close();
		      try{
		    	  rs.close();
		 	  }catch(SQLException se){
		 	  }// do nothing
		      }
		      try{
		          con.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
	}
}

