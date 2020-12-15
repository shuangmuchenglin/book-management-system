import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class wangjimima implements ActionListener
{
    JFrame f=new JFrame("DB Test");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    Font font= new Font("宋体",Font.BOLD,18);//添加字体font
    TextField edit1=new TextField(10);
    TextField edit4=new TextField(10);
    Label label1=new Label("帐号：");
    Label label4=new Label("手机号码：");
    JButton b=new JButton("验证");
    String s3="";
    String s4="";
    
	public static void main(String args[]) 
	{
		new wangjimima();		
	}
	
	public void actionPerformed(ActionEvent e)
	{
          try{
			        	
        	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
              Connection con=DriverManager.getConnection 
              		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");   
	        //增加记录
            Statement stmt=con.createStatement();
            String id=edit1.getText();
            String tel=edit4.getText();
            String sql="select Readertel from Readerinformation where Readerid="+id;
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            while(rs.next())
			{
			s3=rs.getString("Readertel");
			}
            if(s3.equals(tel))
            {
            	String sql1="select Password from Readerinformation where Readerid="+id;
            	ResultSet rs1 =  stmt.executeQuery(sql1);
            	while(rs1.next())
    			{
    			s4=rs1.getString("Password");
    			}
            	JOptionPane.showMessageDialog(f,"你的密码为："+s4);
            }
            else
            {
            	JOptionPane.showMessageDialog(f,"手机号错误！！！");
            }
            stmt.close();
            con.close();
            
		}
		catch(Exception ex)
	    {
	    	System.out.println(ex.getMessage());
	    }
	}

	public wangjimima()
	{
         try{			
        	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con=DriverManager.getConnection 
             		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
		    Statement stmt=con.createStatement();
		   
            ResultSet rs=stmt.executeQuery("select count(*) from Readerinformation");
            rs.next();
            RowNum=rs.getInt(1);
                         
            rs=stmt.executeQuery("select * from Readerinformation");
            ResultSetMetaData rsmd=rs.getMetaData();            
            ColNum=rsmd.getColumnCount();
  
             names=new String[ColNum];     //构造JTable
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
            edit1.setFont(font);//文本框运用字体font1
            edit4.setFont(font);//文本框运用字体font2
            edit1.setBackground(new Color(255,250,205));
            edit4.setBackground(new Color(255,250,205));
            label1.setFont(font);//文本框运用字体font1
            label4.setFont(font);//文本框运用字体font2
            b.addActionListener(this);
            Panel panel1=new Panel();
            Panel panel2=new Panel();
            panel1.add(label1);  panel1.add(edit1);
            panel1.add(label4);  panel1.add(edit4);
            panel2.add(b);
            f.setSize(150,300);
            f.add(panel1,BorderLayout.CENTER);
            f.add(panel2,BorderLayout.SOUTH);
            f.addWindowListener(new WindowAdapter()
            	{
            		public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            		});
            f.setVisible(true);
            f.setLocation(900, 300);
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
