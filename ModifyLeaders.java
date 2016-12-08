import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;


public class ModifyLeaders {
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="";
	static final String uPass="";
	static int group;
	static int leaders;
	public ModifyLeaders(int g) {
		group=g;
	}
	
	public void CheckLeaders(){
		Connection con=null;
		String sql=null;		
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT COUNT(MEMID) AS LeaNum FROM LEADER WHERE GroupNum="+group;
				rs=stmt.executeQuery(sql);
				rs.next();
				leaders=rs.getInt("LeaNum");
				
				
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

	
	public void AddLeader(int id, int g){
			Connection con=null;
			String sql=null;			
			Statement stmt=null;
				try{	
					OracleDataSource ds=new OracleDataSource();
					ds.setURL(host);
					con=ds.getConnection(uName,uPass);
					stmt=con.createStatement();
					ModifyLeaders ml=new ModifyLeaders(group);
					ml.CheckLeaders();
					if(leaders>=2){
						System.out.println("There are already too many leaders");
						System.exit(0);
					}
					else{
						sql="INSERT INTO LEADER (MemID,GroupNum) VALUES ("+id+","+g+")";
						stmt.executeUpdate(sql);
						System.out.println("Successfully added a new Leader");
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
				          con.close();
				      }catch(SQLException se){
				         se.printStackTrace();
				      }//end finally try	
	
				}
	}
	
	public void removeLeader(int id, int g){
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="DELETE FROM LEADER WHERE MemID="+id+" AND GroupNum="+g;
				stmt.executeUpdate(sql);
				System.out.println("Successfully removed a Leader");
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
	
	public void ShowLeaders(){
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
				sql="SELECT id,name FROM Membership,LEADER WHERE id=memid AND groupnum="+group;
				rs=stmt.executeQuery(sql);
				while(rs.next()){
					int i=rs.getInt("id");
					String n=rs.getString("name");
					System.out.println("Member ID:"+i+"     User name:"+n);
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

}
