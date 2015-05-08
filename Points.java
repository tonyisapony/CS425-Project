import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

public class Points {
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="restrad2";
	static final String uPass="tsukuyomi";
	static int group;
	static int rest;
	static int rate;
	static int rev;
	static int currpoints;
	
	public Points(int g) {
		group=g;
	}
	
	public void showContributionValue(){
		Connection con=null;
		String sql=null;			
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				if(group==1){
					sql="SELECT * FROM GROUP1POINTS";
					rs=stmt.executeQuery(sql);
					rs.next();
					rest=rs.getInt(2);
					rs.next();
					rate=rs.getInt(2);
					rs.next();
					rev=rs.getInt(2);
					System.out.println("The contribution value for adding a restaurant is: "+rest);
					System.out.println("The contribution value for rating a restaurant is: "+rate);
					System.out.println("The contribution value for reviewing a restaurant is: "+rev);
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
	
	public void loadContributionValues(){
		Connection con=null;
		String sql=null;			
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				if(group==1){
					sql="SELECT * FROM GROUP1POINTS";
					rs=stmt.executeQuery(sql);
					rs.next();
					rest=rs.getInt(2);
					rs.next();
					rate=rs.getInt(2);
					rs.next();
					rev=rs.getInt(2);
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
	
	public void UpdateMemPoints(int id, int p){
		Connection con=null;
		String sql=null;		
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT MEMPOINTS FROM Membership WHERE id="+id;
				rs=stmt.executeQuery(sql);
				rs.next();
				currpoints=rs.getInt("mempoints");
				currpoints+=p;
				sql="UPDATE MEMBERSHIP SET MEMPOINTS="+currpoints+" WHERE id="+id;
				stmt.executeUpdate(sql);
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
	
	public int getRest(){
		return rest;
	}
	public int getRate(){
		return rate;
	}
	public int getRev(){
		return rev;
	}
	public int getCurrPoints(){
		return currpoints;
	}
}
