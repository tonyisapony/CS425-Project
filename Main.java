
import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

import java.util.Scanner;

public class Main {
	
	static final String host="jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl";
	static final String uName="";
	static final String uPass="";
	static Member mem=null;
	static boolean admin=false;
			
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
		    	System.out.println("Enter email address");
		    	String email= scan.next();
		    	sql="INSERT INTO Membership (id,name,password,email) VALUES ("+currid+",'"+usrname+"','"+passwrd+"','"+email+"')";
		    	stmt.executeUpdate(sql);
		    	System.out.println("Successfully registered");
		    	mem=new Member(currid,usrname);
		    	
		    }
		    else if(in==2){
		    	boolean logged=false;
		    	System.out.println("Enter user name:");
		    	String usrname=scan.next();
		    	System.out.println("Enter password:");
		    	String passwrd=scan.next();
		    	sql="SELECT id,name,password FROM Membership";
		    	rs=stmt.executeQuery(sql);
		    	if(usrname.equals("admin")&&passwrd.equals("admin")){
		    		System.out.println("You have logged in as an admin");
		    		logged=true;
		    		admin=true;
		    	}
		    	else
		    	while(rs.next()){
		    		String nm=rs.getString("name");
		    		String n[]=nm.split(" ",2);
		    		String ps=rs.getString("password");
		    		String p[]=ps.split(" ",2);
		    		if(n[0].equals(usrname)&&p[0].equals(passwrd)){
		    			System.out.println("You have successfully logged in");
		    			int currid=rs.getInt("id");
		    			mem=new Member(currid,usrname);
		    			logged=true;
		    			break;
		    		}
		    	}
		    	if(logged==false){
		    		System.out.println("Wrong credentials");
			    	System.exit(0);
		    	}
		    }
		    else{
		    	System.out.println("Wrong command");
		    	System.exit(0);
		    }
		    
		    if(admin){
		    	System.out.println("Please chose what would you like to do. \n1: Add Leader 2: Remove Leader 3: Look at Member list "
		    			+ "4: Look at a Group list 5: Delete Member 6: Logout");
		    	int adin=scan.nextInt();
	    		ModifyMembers mm=new ModifyMembers();
		    	if(adin==1){
		    		System.out.println("Enter which group to add the leader to. 1:Group1 2:Group2");
		    		int g=scan.nextInt();
		    		ModifyLeaders ml=new ModifyLeaders(g);
		    		System.out.println("Enter the ID of the new leader: ");
		    		mm.displayGroupMembers(g);
		    		int add=scan.nextInt();
		    		ml.AddLeader(add, g);
		    	}
		    	else if(adin==2){
		    		System.out.println("Enter which group to remove the leader from. 1:Group1 2:Group2");
		    		int g=scan.nextInt();
		    		ModifyLeaders ml=new ModifyLeaders(g);
		    		System.out.println("Enter the ID of the new leader: ");
		    		mm.displayGroupMembers(g);
		    		int del=scan.nextInt();
		    		ml.AddLeader(del, g);
		    	}
		    	else if(adin==3){
		    		mm.displayAllMembers();
		    	}
		    	else if(adin==4){
		    		System.out.println("Enter which group to view. 1:Group1 2:Group2");
		    		int g=scan.nextInt();
		    		mm.displayGroupMembers(g);
		    	}
		    	else if(adin==5){
		    		System.out.println("Enter the id of the member you want to delete: ");
		    		mm.displayAllMembers();
		    		int del=scan.nextInt();
		    		mm.deleteMember(del);
		    	}
		    	System.out.println("Goodbye!");
		    	System.exit(0);
		    }
		    
			System.out.println("You are now logged in please choose what interest group you would like to access. "
					+ "\n1:Restaurants 2:Laptops 3: Delete Account 4: Logout");
				
	    	int in1=scan.nextInt();
	    	if (in1==1)
	    	{
	    		Restaurants();
	    	}
	    	else if(in1==2)
	    	{
	    		Laptops();
	    	}
	    	else if(in1==3){
	    		sql="DELETE FROM MEMBERSHIP WHERE id="+mem.getID();
	    		stmt.executeUpdate(sql);
	    		System.out.println("Successfully deleted your account, goodbye!");
	    		System.exit(0);
	    	}
	    	else
	    		System.out.println("Logging out...");
	    		System.exit(0);
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
	
	
	//TONY WORKING ON THESE METHODS... RESTAURANT AND LAPTOP COMMENTS
	public static void Restaurants()
	{
		System.out.println("You have now been signed into the Restaurant Interest Group.");
		Connection con=null;
		String sql=null;
		Scanner scan=new Scanner(System.in);			
		ResultSet rs1=null;
		ResultSet rs=null;
		ResultSet rs3=null;
		Statement stmt=null;
			try{	
				OracleDataSource ds=new OracleDataSource();
				ds.setURL(host);
				con=ds.getConnection(uName,uPass);
				stmt=con.createStatement();
				sql="SELECT memID FROM LEADER WHERE GroupNum=1";
				rs1=stmt.executeQuery(sql);
				while(rs1.next()){
					int id=rs1.getInt("memID");

					if(id==mem.getID()){
						System.out.println("\nYou are an admin for this group\n");
						mem.setMemTypeG1(MemType.LEADER);
					}
				}
				System.out.println("Now listing the 20 most recent restaurants: \n");
				ListRestaurants lr=new ListRestaurants(20);
				lr.List();
				System.out.println("What would you like to do? \n 1:Post a new restaurant 2: Review a restaurant");
				int in=scan.nextInt();
				if(in==1){
			    	sql="SELECT MAX(id) AS LastID FROM Group1";
			    	rs=stmt.executeQuery(sql);
			    	rs.next();
			    	int currid=rs.getInt("LastID");
			    	currid++;
			    	System.out.println("Please enter the name of the restaurtant: ");
			    	String name=scan.next();
			    	sql="INSERT INTO GROUP1 (id,restname) VALUES ("+currid+",'"+name+"')";
			    	stmt.executeUpdate(sql);
			    	System.out.println("Successfully added a new restaurant");
					System.exit(0);
				}
				if(in==2){
					System.out.println("Please select restaurant id: ");
					int restid=scan.nextInt();
					System.out.println("Outputting the current reviews...");
					sql="SELECT review FROM REVIEW WHERE RestID="+restid;
					rs=stmt.executeQuery(sql);
					while(rs.next()){
						String rev=rs.getString("review");
						System.out.println("Review: "+rev);
					}
					System.out.println("What would you like to do? 1:Add a review 2:Rate the restaurant");
					int resin=scan.nextInt();
					if(resin==1){
						System.out.println("Enter the review: ");
						String rev=scan.next();
						sql="INSERT INTO REVIEW (RestID,Review) VALUES ("+restid+",'"+rev+"')";
						stmt.executeUpdate(sql);
						System.out.println("Successfully posted review");
						System.exit(0);
					}
					if(resin==2){
						System.out.println("Would you like to rate it up or down? Enter 1 to rate up and -1 to rate down");
						int rate=scan.nextInt();
						sql="SELECT RATING FROM GROUP1 WHERE id="+restid;
						rs=stmt.executeQuery(sql);
						rs.next();
						int rating=rs.getInt("rating");
						rating+=rate;
						sql="UPDATE Group1 SET Rating="+rating+" WHERE id="+restid;
						stmt.executeUpdate(sql);
						System.out.println("You have successfully rated the restaurant");
						System.exit(0);
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
			      scan.close();
			      try{
			    	  rs1.close();
			 	  }catch(SQLException se){
			 	  }// do nothing
			      }
			      try{
			          con.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
	}
	
	public static void Laptops()
	{
		System.out.println("You have now been signed into the Restaurant Interest Group.");
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
		    	sql="SELECT memID FROM LEADER WHERE GroupNum=2";
	    		rs=stmt.executeQuery(sql);
	    		while(rs.next()){
	    			int id=rs.getInt("memID");
	    			if(id==mem.getID()){
	    				System.out.println("You are a leader for this group");
	    				mem.setMemTypeG2(MemType.LEADER);
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

