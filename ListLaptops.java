import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;


public class ListLaptops {
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="restrad2";
	static final String uPass="tsukuyomi";
	static int numToPrint=0;
	public ListLaptops() {
	}
	public ListLaptops(int n){
		numToPrint=n;
	}
	public static void main(String[] args) {
		ListLaptops ll=new ListLaptops(20);
		ll.List(1);
		while(true){
			//lr.List();
		}
	}

	public void List(int a){
		Connection con=null;
		String sql=null;
		String sql1=null;
		ResultSet rs1=null;
		Scanner scan=new Scanner(System.in);			
		ResultSet rs=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				if(numToPrint==0)
				{
					sql="SELECT * From LAPTOPCOMMENTS ORDER BY COMMENTID DESC";
					sql1="SELECT * From GROUP2";
				}
					else
					{
					sql="SELECT * From LAPTOPCOMMENTS WHERE ROWNUM<=20 ORDER BY COMMENTID DESC";
					sql1="SELECT * From GROUP2 WHERE ROWNUM<=20";
					}
					
	    		rs=stmt.executeQuery(sql);
	    		rs1=stmt.executeQuery(sql1);
	    		while(rs.next() && rs1.next()){
	    			int id=rs.getInt("lapid");
	    			int id1=rs1.getInt("id");
	    			if (id==id1)
	    			{
	    			String name=rs1.getString("laptopname");
	    			String brand=rs1.getString("brand");
	    			String comment = rs1.getString("comments");
	    			String commenttype=rs1.getString("commenttype");
	    			
	    			System.out.println(name+ ". "+ brand+ ": "+comment+". " + commenttype);
	    			}
	    			else
	    			{
	    				System.out.println("The laptop with LapID: " + id + " Does not exist in the database");
	    			}
	    			
	    		}
	    		System.out.println("You can sort the comments by specific brand or by the type of the comment. \n You can also rate a computer or post a comment on a computer \n 1:Brand  2:Type of comment  3:Rate    4:Post    5:Exit");
    			int input = scan.nextInt();
    			boolean t=false;
    			while(t=false){
    			//HAVING THE COMMENTS SHOWN BY BRANDS
    				if(input==1)
    			{
    			System.out.println("Type in the specific Brand of Computer you want to search by and the 20 most recent comments will be displayed one by one");
    			String b1=scan.next();
    			sql="SELECT * From LAPTOPCOMMENTS WHERE ROWNUM<=20 && LAPID==(SELECT ID FROM GROUP2 WHERE BRAND="+b1+") ORDER BY COMMENT ID DESC";
    			sql1="SELECT * From GROUP2 WHERE ROWNUM<=20";
    			rs1=stmt.executeQuery(sql1);
    			rs=stmt.executeQuery(sql);
    			boolean b = true;
    			while(b==true)
    			{
    				rs.next();
    				rs1.next();
    				String name=rs1.getString("laptopname");
	    			String brand=rs1.getString("brand");
	    			String comment = rs1.getString("comments");
	    			String commenttype=rs1.getString("commenttype");
	    			
	    			System.out.println(name+ ". "+ brand+ ": "+comment+". " + commenttype);
	    			if (commenttype=="buy")
	    			{
	    				System.out.println("You have the option of selling this laptop or showing the next comment. 1:Sell   2:Next Comment");
	    				int option = scan.nextInt();
	    				if (option==1)
	    				{
	    					sell();
	    					b=false;
	    				}
	    				else if(option==2)
	    				{
	    					b=true;
	    				}
	    			}
	    			else if (commenttype=="sell")
	    			{
	    				System.out.println("You have the option of buying this laptop or showing the next comment. 1:Buy   2:Next Comment");
	    				int option = scan.nextInt();
	    				if (option==1)
	    				{
	    					buy();
	    					b=false;
	    				}
	    				else if(option==2)
	    				{
	    					b=true;
	    				}
	    			}
	    			else if (commenttype=="trade")
	    			{
	    				System.out.println("You have the option of trading your laptop with this laptop or showing the next comment. 1:Trade   2:Next Comment");
	    				int option = scan.nextInt();
	    				if (option==1)
	    				{
	    					trade();
	    					b=false;
	    				}
	    				else if(option==2)
	    				{
	    					b=true;
	    				}
	    			}
    				
    			}
    				t=false;
    			}
    			//HAVING COMMENTS SHOWN BY SPECIFIC COMMENT TYPE
    				else if (input==2)
    			{
    				t=false;
    			}
    			//RATE SPECIFIC COMMENTS
    				else if (input == 3)
    			{
    				t=false;
    			}
    			//POST A COMMENT ON A SPECIFIC COMPUTER
    				else if (input == 4)
    			{
    				t=false;
    			}
    			else
    			{
    				t=true;
    			}
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
			      }
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
		}
	public void buy()
	{
		
	}
	
	public void sell()
	{
		
	}
	
	public void trade()
	{
		
	}
}


