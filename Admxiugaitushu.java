import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class Admxiugaitushu extends MouseAdapter implements ActionListener
{
	Font font= new Font("宋体",Font.BOLD,12);//添加字体font
    JFrame f=new JFrame("修改图书信息");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    
    TextField edit0=new TextField(10);
    TextField edit1=new TextField(10);
    TextField edit2=new TextField(10);
    TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    Label label0=new Label("                                                   图书编号：");
    Label label1=new Label("                                                   图书名称：");
    Label label2=new Label("                                                   图书价格：");
    Label label3=new Label("                                                   图书数量：");
    Label label4=new Label("                                                   图书出版社：");
    
    JButton b=new JButton("修改");
    
	public static void main(String args[]) 
	{
		new Admxiugaitushu();		
	}
	
//JTable响应鼠标单击事件，让用户选择的记录信息填写到上面响应的文本框中。
	public void mouseClicked(MouseEvent e)
	{
		int i=table.getSelectedRow();
		String help=table.getValueAt(i,0).toString();
		edit0.setText(help);
		help=table.getValueAt(i,1).toString();
		edit1.setText(help);
		help=table.getValueAt(i,2).toString();
		edit2.setText(help);
		help=table.getValueAt(i,3).toString();
		edit3.setText(help);
		help=table.getValueAt(i,4).toString();
		edit4.setText(help);
	}

//鼠标响应单击事件，将用户修改后的信息写回数据库。
	public void actionPerformed(ActionEvent e)
	{
          try{			
        	
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       
            Connection con=DriverManager.getConnection
("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");      
		    
//更新数据库记录
            Statement stmt=con.createStatement();
            String id=edit0.getText();
            String name=edit1.getText();
            float price=Float.parseFloat(edit2.getText());
            String press=edit4.getText();
            int number =Integer.parseInt(edit3.getText());
            
            String sql="update bookinformation set bookname='"+name+"'where bookid="+"'"+id+"'";
            stmt.execute(sql);
            sql="update bookinformation set bookprice="+price+"where bookid="+"'"+id+"'";
            stmt.execute(sql);
            sql="update bookinformation set booknumber="+number+"where bookid="+"'"+id+"'";
            stmt.execute(sql);
            sql="update bookinformation set bookpress='"+press+"'where bookid="+"'"+id+"'";
            stmt.execute(sql);

            //将更新后的数据记录显示在JTable中
            ResultSet rs=stmt.executeQuery("select * from bookinformation where bookid="+"'"+id+"'");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0); 
            
            while (rs.next())
            {
                 String[] arr=new String[5];
                 arr[0]=rs.getString("bookid");
                 arr[1]=rs.getString("bookname");
                 arr[2]=rs.getString("bookprice");
                 arr[3]=rs.getString("bookpress");
                 arr[4]=rs.getString("booknumber");
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

	public Admxiugaitushu()
	{
         try{			
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
       
            Connection con=DriverManager.getConnection
("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");      
		
            Statement stmt=con.createStatement();
		   
            ResultSet rs=stmt.executeQuery("select count(*) from bookinformation");
            rs.next();
            RowNum=rs.getInt(1);
                         
            rs=stmt.executeQuery("select * from bookinformation");
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
            table=new JTable(model); 
            table.setBackground(new Color(255,250,205));
            table.setFont(font);
            table.setPreferredScrollableViewportSize(new Dimension(400,50));
            
            edit0.setEditable(false);   //将文本框设置成不允许修改，即学号不允许修改
            b.addActionListener(this);
            Panel panel=new Panel(new GridLayout(5,2));
            panel.add(label0);  panel.add(edit0);
            panel.add(label1);  panel.add(edit1);
            panel.add(label2);  panel.add(edit2);
            panel.add(label3);  panel.add(edit3);
            panel.add(label4);  panel.add(edit4);
            f.add(panel,BorderLayout.NORTH);
            f.add(b,BorderLayout.SOUTH);
            table.addMouseListener(this);
            edit0.setBackground(new Color(255,250,205));
            edit1.setBackground(new Color(255,250,205));
            edit2.setBackground(new Color(255,250,205));
            edit3.setBackground(new Color(255,250,205));
            edit4.setBackground(new Color(255,250,205));
            f.getContentPane().add(new JScrollPane(table));
            f.addWindowListener(new WindowAdapter()
            	{
            		public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            		});
            f.setSize(700,400);
            f.setLocation(500, 200);
            f.setVisible(true);
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
