
package contact;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.sql.*;

public class Contact {
    
    public static void main(String[] args) throws Exception 
    {
       Scanner s= new Scanner(System.in);
       Contact obj=new Contact();
       int no;
       String fname,lname,mnum,email,address;
       int choice,limit=0,maxi,q=0;
       System.out.println("enter the max entries into database");
       maxi=s.nextInt();
       while(q==0)
       {       
       limit=obj.max();
       System.out.println("there are"+limit+"entries. dont exceed 10 entries");
       System.out.println("enter the choice");
       System.out.println("1.add an entry");
       System.out.println("2.remove an entry");
       System.out.println("3.search an entry");
       System.out.println("4.edit an entry");
       System.out.println("5.sort the book");
       System.out.println("6.save into a file from DB");
       System.out.println("7.load from a file to DB");
       System.out.println("8.quit");
       choice=s.nextInt();
       switch(choice)
       {
           case 1:System.out.println("enter the inputs s.no,firstname,lastname,mobile,email,address");
                  if(limit<maxi)
                  {
                  limit++;
                  no=s.nextInt();
                  fname=s.next();
                  lname=s.next();
                  mnum=s.next();
                  email=s.next();
                  address=s.next();
                  obj.add(no,fname,lname,mnum,email,address,limit);
                  }
                   else
                  {
                   System.out.println("no more entries allowed");
                  }
                  break;
           case 2:System.out.println("enter the number to be removed");
                  limit--;
                  no=s.nextInt();
                  obj.remove(no);
                  break;       
           case 3:System.out.println("search based on 1.first name,2.last name,3.mobile num,4.email,5.address");
                  int i=s.nextInt();
                  String ss="select * from list where lname=?";
                  String ss1="";
                  switch(i)
                  {
                      case 1:System.out.println("base on firstname");
                             ss="select * from list where fname=?";
                             System.out.println("enter the first name");
                             ss1=s.next();
                             break;
                      case 2:System.out.println("base on lastname");
                             ss="select * from list where lname=?";
                             System.out.println("enter the last name");
                             ss1=s.next();
                             break;
                      case 3:System.out.println("base on mobile num");
                             ss="select * from list where mnum=?";
                             System.out.println("enter the mobile number");
                             ss1=s.next();
                             break;
                      case 4:System.out.println("base on email id");
                             ss="select * from list where email=?";
                             System.out.println("enter the email id");
                             ss1=s.next();
                             break;
                      case 5:System.out.println("base on address");
                             ss="select * from list where address=?";
                             System.out.println("enter the address");
                             ss1=s.next();
                             break;
                      default :System.out.println("not possible");
                  }
                               
                  obj.search(ss,ss1);
                  break;
           case 4:System.out.println("enter the fname to be edited");
                  fname=s.next();
                  System.out.println("enter the new lname,mobile num, email id , address");
                  lname=s.next();
                  mnum=s.next();
                  email=s.next();
                  address=s.next();
                  obj.edit(fname,lname,mnum,email,address);
                  break;
           case 5:System.out.println("sorting based on 1.first name,2.last name,3.mobile num,4.email,5.address");
                  int i2=s.nextInt();
                  String ss2="select * from list order by no";
                  switch(i2)
                  {                     
                      case 1:System.out.println("base on firstname");
                             ss2="select * from list order by fname ";
                             break;
                      case 2:System.out.println("base on lastname");
                             ss2="select * from list order by lname ";
                             break;
                      case 3:System.out.println("base on mobile num");
                             ss2="select * from list order by mnum ";
                             break;
                      case 4:System.out.println("base on email id");
                             ss2="select * from list order by email";
                             break;
                      case 5:System.out.println("base on address");
                             ss2="select * from list order by address ";
                             break;
                      default :System.out.println("not possible");
                  }
                  obj.sort(ss2);
                  break;
           case 6: System.out.println("enter the file name");
                   String f=s.next();
                   obj.save(f);
                   break;
           case 7: System.out.println("enter the file name");
                   String f1=s.next();
                   if(limit<maxi)
                     limit=obj.load(f1,limit);
                   else
                     System.out.println("no more entries");  
                   break;
           case 8:System.out.println("are you sure ? 1.yes 2.no");
                     q=s.nextInt();
                     if(q==1)
                         System.out.println("thank you");
                     break;
           default: System.out.println("enter a valid choice");
                         
       }   
    }
    }
   
    public void add(int i1,String s1,String s2,String s3,String s4,String s5,int lim)
    {
        
      
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
            PreparedStatement ps=con.prepareStatement("insert into list values(?,?,?,?,?,?)");
            ps.setInt(1,i1);
            ps.setString(2,s1);
            ps.setString(3,s2);
            ps.setString(4,s3);
            ps.setString(5,s4);
            ps.setString(6,s5);
            ps.executeUpdate();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
       
       
    }
    public void remove(int i1)
    {
        
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
            PreparedStatement ps=con.prepareStatement("delete from list where no=?");
            ps.setInt(1,i1);
            ps.executeUpdate();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void search(String s1,String s2)
    {
         try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
            PreparedStatement ps=con.prepareStatement(s1);
            ps.setString(1, s2);
            ResultSet rs;
            rs=ps.executeQuery();
            while(rs.next())
            {
                int i1=rs.getInt("no");
                String ss1,ss2,ss3,ss4,ss5;
                ss1=rs.getString("fname");
                ss2=rs.getString("lname");
                ss3=rs.getString("mnum");
                ss4=rs.getString("email");
                ss5=rs.getString("address");
                System.out.println(i1+" "+ss1+" "+ss2+" "+ss3+" "+ss4+" "+ss5);
            }
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void edit(String s1,String s2,String s3,String s4,String s5)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
            PreparedStatement ps=con.prepareStatement("update list set lname=?,mnum=?,email=?,address=? where fname=?");
            ps.setString(1,s2);
            ps.setString(2,s3);
            ps.setString(3,s4);
            ps.setString(4,s5);
            ps.setString(5,s1);
            ps.executeUpdate();
            con.close();
            System.out.println("update complete");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void sort(String s1)
    {
          try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
            PreparedStatement ps=con.prepareStatement(s1);
           // ps.setString(1,s1);
            ResultSet rs;
            rs=ps.executeQuery();
            while(rs.next())
            {
                int i1=rs.getInt("no");
                String ss1,ss2,ss3,ss4,ss5;
                ss1=rs.getString("fname");
                ss2=rs.getString("lname");
                ss3=rs.getString("mnum");
                ss4=rs.getString("email");
                ss5=rs.getString("address");
                System.out.println(i1+" "+ss1+" "+ss2+" "+ss3+" "+ss4+" "+ss5);
            }
            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    public void save(String s) throws Exception
    {   
        File file=new File(s);
        if(!file.exists())
            file.createNewFile();
        PrintWriter pw=new PrintWriter(new FileWriter(file,true));
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
        PreparedStatement ps=con.prepareStatement("select * from list");
        ResultSet rs;
        rs=ps.executeQuery();
            while(rs.next())
            {
                int i1=rs.getInt("no");
                String ss1,ss2,ss3,ss4,ss5;
                ss1=rs.getString("fname");
                ss2=rs.getString("lname");
                ss3=rs.getString("mnum");
                ss4=rs.getString("email");
                ss5=rs.getString("address");
                System.out.println(i1+" "+ss1+" "+ss2+" "+ss3+" "+ss4+" "+ss5);
                pw.println(i1+" "+ss1+" "+ss2+" "+ss3+" "+ss4+" "+ss5);
            }
            con.close(); 
            pw.close();
    }
    public int load(String s,int lim)throws Exception
    {
        String str[];
        int i1=0;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
        PreparedStatement ps=con.prepareStatement("insert into list values(?,?,?,?,?,?)");
        
        Scanner in=new Scanner(new File(s));
        while(in.hasNext()&& lim<10)//limit can be changed
        {
          int k=1;
          lim++;
          str=in.nextLine().split(" ");
          i1=Integer.parseInt(str[0]);
          ps.setInt(1, i1);
          for(int i=2;i<=6;i++)
          {
              ps.setString(i, str[k]);
              k++;
          }
            ps.executeUpdate();
            
        }
        con.close();
        return lim;
    }
    public int max() throws Exception// to find num of entries in contact 
    {
            int lim=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/phonebook","root","");
            PreparedStatement ps=con.prepareStatement("select * from list");
            ResultSet rs;
            rs=ps.executeQuery();
            while(rs.next())
            {
                lim++;
            }
            con.close();
            return lim;
    }
            
}

