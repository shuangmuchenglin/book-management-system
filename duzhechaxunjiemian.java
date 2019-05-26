import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;
import javax.swing.table.*;
import javax.swing.JOptionPane;   //确认窗口

public class duzhechaxunjiemian implements ActionListener
{
	Font font= new Font("宋体",Font.BOLD,12);//添加字体font
	public static String id="";
    JFrame f=new JFrame("图书借阅"); 
    Button b0=new Button("确定");
    Button b1=new Button("取消");
    TextField t0=new TextField();
    String[] names;
    String[] ID;
    String[] press;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    TextField edit1=new TextField(10);
    TextField edit2=new TextField(10);
    TextField edit3=new TextField(10);
    Label label1=new Label("输入图书名称：");
    Label label2=new Label("输入图书编号：");
    Label label3=new Label("输入出版社名称：");
    JButton b=new JButton("查询");
    JButton b2=new JButton("借阅");
    JButton b3=new JButton("还书");
    MenuBar br=new MenuBar();
    Menu jm=new Menu("个人用户");
    MenuItem t1=new MenuItem("注销用户");
    MenuItem t2=new MenuItem("借阅查询");
    
	public static void main(String args[]) 
	{
		new duzhechaxunjiemian();		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("借阅"))
		{
			 try{
	        	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	              Connection con=DriverManager.getConnection 
	              		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");     
	            Statement stmt=con.createStatement();
	            int i=table.getSelectedRow();
	            id=table.getValueAt(i,0).toString();
	            int result = JOptionPane.showConfirmDialog(f, "你确定要借阅编号为："+id+"的图书吗");
	    
	            if(result==JOptionPane.OK_OPTION)   
	            {
	    			jieyue jy=new jieyue();
	            }

	            ResultSet rs=stmt.executeQuery("select * from bookinformation");
	            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
	            tableModel.setRowCount(0); 
	            
	            while (rs.next())
	            {
	                 String[] arr=new String[5];
	                 arr[0]=rs.getString("bookid");
	                 arr[1]=rs.getString("bookname");
	                 arr[2]=rs.getString("Bookprice");
	                 arr[3]=rs.getString("Booknumber");
	                 arr[4]=rs.getString("Bookpress");
	                 tableModel.addRow(arr);

	  
	            }
	             table.invalidate();
	            rs.close();
	            stmt.close();
	            con.close();	            
			}

			catch(Exception ex)
		    {
		    	System.out.println(ex.getMessage());
		    }
		}
		
		if(e.getActionCommand().equals("还书"))
		{
			returnGUI returngui=new returnGUI();
		}
		
		if(e.getActionCommand().equals("借阅查询"))
		{
			chaxunjieyue cx1=new chaxunjieyue();
		}
		if(e.getActionCommand().equals("注销用户"))
	    {
			 int result = JOptionPane.showConfirmDialog(f, "你确定要注销吗？");
			    
	            if(result==JOptionPane.OK_OPTION)   
	            {
	            	f.setVisible(false);
	            	denglujiemian DENGLUJIEMIAN=new denglujiemian();
	            }
	    }
		try{
		
			
        	//装载驱动程序
        	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       
            //创建连接
            Connection con=DriverManager.getConnection 
            		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");      
		
            Statement stmt=con.createStatement();
            String name=edit1.getText();
            String ID=edit2.getText();
            String press=edit3.getText();
            
            
            if(name.length()!=0)
            {
            ResultSet rs1=stmt.executeQuery("select * from bookinformation where bookname like"+"'%"+name+"%'");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0); 
            while (rs1.next())
            {
                 String[] arr=new String[5];
                 arr[0]=rs1.getString("Bookid");
                 arr[1]=rs1.getString("bookname");
                 arr[2]=rs1.getString("Bookprice");
                 arr[3]=rs1.getString("Booknumber");
                 arr[4]=rs1.getString("Bookpress");
                 tableModel.addRow(arr);
            }
            table.invalidate();

            rs1.close();
            stmt.close();
            con.close();
            }
            
            if(ID.length()!=0)
            {
            ResultSet rs2=stmt.executeQuery("select * from bookinformation where bookid like"+"'%"+ID+"%'");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0); 
            while (rs2.next())
            {
                 String[] arr=new String[5];
                 arr[0]=rs2.getString("Bookid");
                 arr[1]=rs2.getString("bookname");
                 arr[2]=rs2.getString("Bookprice");
                 arr[3]=rs2.getString("Booknumber");
                 arr[4]=rs2.getString("Bookpress");
                 tableModel.addRow(arr);
            }
            table.invalidate();
            rs2.close();
            stmt.close();
            con.close();
           }
            
            if(press.length()!=0)
            {
            ResultSet rs3=stmt.executeQuery("select * from bookinformation where bookpress like"+"'%"+press+"%'");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0); 
            while (rs3.next())
            {
                 String[] arr=new String[5];
                 arr[0]=rs3.getString("Bookid");
                 arr[1]=rs3.getString("bookname");
                 arr[2]=rs3.getString("Bookprice");
                 arr[3]=rs3.getString("Booknumber");
                 arr[4]=rs3.getString("Bookpress");
                 tableModel.addRow(arr);
            }
            table.invalidate();
            rs3.close();
            stmt.close();
            con.close();
           }
            if(name.length()==0&&ID.length()==0&&press.length()==0)
            {
            	ResultSet rs4=stmt.executeQuery("select * from bookinformation");
                DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
                tableModel.setRowCount(0); 
                while (rs4.next())
                {
                     String[] arr=new String[5];
                     arr[0]=rs4.getString("Bookid");
                     arr[1]=rs4.getString("bookname");
                     arr[2]=rs4.getString("Bookprice");
                     arr[3]=rs4.getString("Booknumber");
                     arr[4]=rs4.getString("Bookpress");
                     tableModel.addRow(arr);
                }
                table.invalidate();
                rs4.close();
                stmt.close();
                con.close();
                }
            
		}
		catch(Exception ex)
	    {
	    	System.out.println(ex.getMessage());
	    }
	}

	public duzhechaxunjiemian()
	{
         try{
					
        	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.getConnection             
            		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");      
		
            Statement stmt=con.createStatement();
		    //构造JTable
            ResultSet rs=stmt.executeQuery("select count(*) from bookinformation");
            rs.next();
            RowNum=rs.getInt(1);
                         
            rs=stmt.executeQuery("select * from bookinformation");
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
            t1.addActionListener(this);
            t2.addActionListener(this);
            b0.addActionListener(this);
            b1.addActionListener(this);
            b2.addActionListener(this);
            b3.addActionListener(this);
            Panel panel=new Panel();
            Panel panel1=new Panel();
            panel.add(label1);
            panel.add(edit1);
            panel.add(label2);
            panel.add(edit2);
            panel.add(label3);
            panel.add(edit3);
            panel1.add(b);
            panel1.add(b2);
            panel1.add(b3);
            f.add(panel,BorderLayout.NORTH);
            f.add(panel1,BorderLayout.SOUTH);
            f.getContentPane().add(new JScrollPane(table));
          //添加菜单
            f.setMenuBar(br);
            br.add(jm);
            jm.add(t1);
            jm.add(t2);
            
           
            //响应窗口的关闭事件
            f.addWindowListener(new WindowAdapter()
            {
            		@SuppressWarnings("unused")
					public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            		});
            
            f.setSize(700, 400);
            f.setVisible(true);
            f.setLocation(300,200);
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
