import java.util.*;
import java.lang.*;
import java.io.*;
import java.sql.*;

public class JDBC3
{
	public static void main(String args[])
	{
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		Scanner in=new Scanner(System.in);
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loaded");
			con=DriverManager.getConnection("jdbc:oracle:thin:@DESKTOP-2NUH9IF:1521:XE","system","oracle");
			System.out.println("Connection Established");
			st=con.createStatement();
			do
			{
				System.out.print("\nEnter any query : ");
				String sqlQuery=in.nextLine();
				boolean x=st.execute(sqlQuery);
				if(x==true)
				{
					rs=st.getResultSet();
					ResultSetMetaData rsmd=rs.getMetaData();
					int n=rsmd.getColumnCount();
					for(int i=1;i<=n;i++)
						System.out.print(rsmd.getColumnName(i)+"\t");
					System.out.println("\n-----------------------------------------------------\n");
					while(rs.next()==true)
					{
						for(int i=1;i<=n;i++)
						{
							int dataType=rsmd.getColumnType(i);
							
							if(dataType==Types.VARCHAR || dataType==Types.CHAR)
								System.out.print(rs.getString(i)+"\t");
							else if(dataType==Types.DATE)
								System.out.print(rs.getDate(i)+"\t");
							else if(dataType==Types.TIMESTAMP)
								System.out.print(rs.getString(i)+"\t");
							else if(dataType==Types.NUMERIC)
								System.out.print(rs.getInt(i)+"\t");
						}
						System.out.println();
					}
				}
				else
				{
					int n=st.getUpdateCount();
					System.out.println(n+" row effected");
				}
			}
			while(true);
		}
		catch(ClassNotFoundException e)
		{
			System.out.print(e);
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
		}
	}
}
