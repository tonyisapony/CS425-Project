import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;

public class ListRestaurants {
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="restrad2";
	static final String uPass="tsukuyomi";
	static int numToPrint=0;
	public ListRestaurants() {
	}
	public ListRestaurants(int n){
		numToPrint=n;
	}
	public static void main(String[] args) {
		ListRestaurants lr=new ListRestaurants(20);
		lr.List();
		while(true){
			//lr.List();
		}
	}

	public void List(){
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
				if(numToPrint==0)
					sql="SELECT * From GROUP1 ORDER BY ID DESC";
				else
					sql="SELECT * From GROUP1 WHERE ROWNUM<=20 ORDER BY ID DESC";
	    		rs=stmt.executeQuery(sql);
	    		while(rs.next()){
	    			int id=rs.getInt("id");
	    			String name=rs.getString("restname");
	    			int rate=rs.getInt("rating");
	    			
	    			System.out.println("ID:"+id+" Name:"+name+" Rating:"+rate);
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
		}}
}

