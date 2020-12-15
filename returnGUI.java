import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class returnGUI implements ActionListener
{
	Font font= new Font("宋体",Font.BOLD,12);//添加字体font
    JFrame f3=new JFrame("借阅信息");
    JButton b=new JButton("确定");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    int kucun1=0;
    String date1="";
    int number1=0;//还书数量
	public static void main(String args[]) 
	{
		new returnGUI();		
	}
	
//响应按钮的事件，完成查询功能
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("确定"))
	    {
			try{
	        	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	              Connection con=DriverManager.getConnection 
	              		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");     
	            Statement stmt=con.createStatement();
	            int i=table.getSelectedRow();
	            String name = table.getValueAt(i,1).toString();
				int result = JOptionPane.showConfirmDialog(f3, "你确定要全部归还名称为："+name+"的图书吗");
	            if(result==JOptionPane.OK_OPTION)   
	            {
	        		try {
	        			con= DriverManager.getConnection
								("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
					    Statement stmt1 = con.createStatement();
	        		    String sql="select Booknumber from bookinformation where bookname='"+name+"'";
	        		    ResultSet rs1 = null;
	        			rs1 = stmt1.executeQuery(sql);
	        			while(rs1.next())
	        			{
	        			kucun1=rs1.getInt(1);	
	        			}
	        			rs1.close();
	        			stmt1.close();
	        			con.close();
	        		} catch (SQLException e1) {
	        			e1.printStackTrace();
	        		}//查询当前库存
	        		
	        		try {
	        			con= DriverManager.getConnection
								("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
					    Statement stmt1 = con.createStatement();
					    String sql="select sum(Borrownumber)" 
					    +" from borrowinformation,Bookinformation"
					    +" where Bookinformation.bookid=borrowinformation.bookid and Bookinformation.bookname='"+name
					    +"' and borrowinformation.Readerid="+denglujiemian.s1+" and returntime is null";
	        		    ResultSet rs1 = null;
	        			rs1 = stmt1.executeQuery(sql);
	        			while(rs1.next())
	        			{
	        			number1=rs1.getInt(1);	
	        			}
	        			rs1.close();
	        			stmt1.close();
	        			con.close();
	        		} catch (SQLException e1) {
	        			e1.printStackTrace();
	        		}//查询还书数量
	        		
	        		int newkucun=kucun1+number1;
	    			try {
	    				con= DriverManager.getConnection
								("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
					    Statement stmt2 = con.createStatement();
	    			    String sql="update bookinformation set Booknumber="+newkucun+" where Bookname='"+name+"'";
	    			    stmt2.executeUpdate(sql);
	    				stmt2.close();
	    				con.close();
	    			} catch (SQLException e1) {
	    				e1.printStackTrace();
	    			}//更新书籍库存
	    			
	    			try {
	    				con= DriverManager.getConnection
								("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
					    Statement stmt3 = con.createStatement();
	    			    String sql="SELECT DATENAME(year,GETDATE())+'-'+ DATENAME(MONTH,GETDATE())+'-'+ DATENAME(DAY,GETDATE())";
	    			    stmt3.executeQuery(sql);
	    			    ResultSet rs3 = null;
	    			    rs3= stmt3.executeQuery(sql);
	    			    while(rs3.next())
	    				{
	    				date1=rs3.getString(1);	
	    				}
	    				stmt3.close();
	    				con.close();
	    			} catch (SQLException e1) {
	    				e1.printStackTrace();
	    			}//获取当前时间
	        		
	    			try {
	    				con= DriverManager.getConnection
								("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
					    Statement stmt4 = con.createStatement();
	    			    String sql="update Borrowinformation set Returntime='"+date1+"' from Bookinformation,borrowinformation"
	    			    		+" where Bookinformation.bookid=borrowinformation.bookid and Bookinformation.bookname='"+name+"'"
	    			    		 +" and borrowinformation.Readerid="+denglujiemian.s1;
	    			    stmt4.executeUpdate(sql);
	    				stmt4.close();
	    				con.close();
	    			} catch (SQLException e1) {
	    				e1.printStackTrace();
	    			}
	    			JOptionPane.showMessageDialog(f3,"还书成功！");
	    			number1=0;//重置还书数量
	    			f3.setVisible(false);
	    			
	            }

			}
			catch(Exception ex)
		    {
		    	System.out.println(ex.getMessage());
		    }
	}
    }

	public returnGUI()
	{
         try{
					
        	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.getConnection             
            		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");      
		
            Statement stmt=con.createStatement();
		    //构造JTable
            ResultSet rs=stmt.executeQuery("select count(*) from borrowinformation");
            rs.next();
            RowNum=rs.getInt(1);
                         
            rs=stmt.executeQuery("select readername as '用户姓名',bookname as '书名',Borrownumber as '数量',Borrowtime as '借阅时间',Returntime as '归还时间'"
            +"from borrowinformation,bookinformation,readerinformation "
            +"where borrowinformation.Readerid=readerinformation.Readerid  and  borrowinformation.Bookid=bookinformation.bookid and borrowinformation.Readerid="+"'"+denglujiemian.s1+"' and borrowinformation.Returntime is null");
            ResultSetMetaData rsmd=rs.getMetaData();              

            
            ColNum=rsmd.getColumnCount();
  
             names=new String[ColNum];     
             for (i=1;i<=ColNum;i++) names[i-1]=rsmd.getColumnName(i);

            info=new Object[RowNum][];
            i=0;
            while (rs.next())
            {
                info[i]=new Object[ColNum];
                for (j=1;j<=ColNum;j++)
                {
                   info[i][j-1]=rs.getObject(j);
                }
                i++;
            }
            
            DefaultTableModel model = new DefaultTableModel(info,names);
           
            table=new JTable(model); 
            table.setBackground(new Color(255,250,205));
            table.setFont(font);
            table.setPreferredScrollableViewportSize(new Dimension(400,50));
 
            b.addActionListener(this);
            Panel panel=new Panel();
            f3.add(panel,BorderLayout.NORTH);
            f3.add(b,BorderLayout.SOUTH);
            f3.getContentPane().add(new JScrollPane(table));
            //响应窗口的关闭事件
            f3.addWindowListener(new WindowAdapter()
            {
            		public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            });
            f3.setSize(700, 400);;
            f3.setLocation(300,200);
            f3.setVisible(true);
            
            rs.close();
            stmt.close();
            con.close();
            
		}
		catch(Exception e)
	    {
	    	System.out.println(e.getMessage());
	    }
        
	}
}
