import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;

public class ModifyMembers {
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="restrad2";
	static final String uPass="tsukuyomi";

	public ModifyMembers() {

	}
	
	public static void main(String[] args){
		ModifyMembers mm=new ModifyMembers();
		
		while(true){
			mm.displayAllMembers();
		}
	}
	
	public void displayAllMembers(){
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);	
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT * FROM viewAll";
				rs=stmt.executeQuery(sql);
				while(rs.next()){
					int id=rs.getInt("id");
					String name=rs.getString("name");
					int points=rs.getInt("mempoints");
					System.out.println("Member ID:"+id+"     User name:"+name+"     Points:"+points);
				}
			}
			catch(SQLException err){
				System.out.println(err.getMessage());
			}finally{
			      //finally block used to close resources
			      try{
			            stmt.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			    	  rs.close();
			 	  }catch(SQLException se){
			 	  }// do nothing
			      
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try	
			}
	}
	
	//where g is the group wanted
	public void displayGroupMembers(int g){
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT id,name,mempoints FROM Membership, Group"+g+"Members Where id=memid";
				rs=stmt.executeQuery(sql);
				while(rs.next()){
					int id=rs.getInt("id");
					String name=rs.getString("name");
					int points=rs.getInt("mempoints");
					System.out.println("Member ID:"+id+" User name:"+name+" Points:"+points);
				}
			}
			catch(SQLException err){
				System.out.println(err.getMessage());
			}finally{
			      //finally block used to close resources
				  
			      try{
			            stmt.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			    	  rs.close();
			 	  }catch(SQLException se){
			 	  }// do nothing
			      
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try	
			}
	}
	
	public void deleteMember(int id){
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="DELETE FROM Membership WHERE MemID="+id;
				stmt.executeUpdate(sql);
				System.out.println("Successfully removed a Member");
			}
				
			catch(SQLException err){
				System.out.println(err.getMessage());
			}finally{
			      //finally block used to close resources
				  
			      try{
			            stmt.close();
			      }catch(SQLException se){
			      }// do nothing
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try	

			}
	}

}
