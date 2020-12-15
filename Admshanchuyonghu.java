import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;
import javax.swing.JOptionPane;   //确认窗口

public class Admshanchuyonghu   implements ActionListener
{
	Font font= new Font("宋体",Font.BOLD,12);//添加字体font
    JFrame f=new JFrame("用户删除");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    JButton b=new JButton("删除");
    String id;
    
	public static void main(String args[]) 
	{
		new Admshanchuyonghu();		
	}
	
	public void actionPerformed(ActionEvent e)
	{
          try{
        	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
              Connection con=DriverManager.getConnection 
              		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");     
		
            Statement stmt=con.createStatement();

            //删除记录
            int i=table.getSelectedRow();
            String id=table.getValueAt(i,0).toString();
            int result = JOptionPane.showConfirmDialog(f, "你确定要用户id为："+id+"的用户吗");
    
            if(result==JOptionPane.OK_OPTION)   
            {
            	 String sql="delete readerinformation where Readerid="+id;
                 stmt.executeUpdate(sql);
            }

            //显示删除后数据库记录
            ResultSet rs=stmt.executeQuery("select * from readerinformation");
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0); 
            
            while (rs.next())
            {
            	String[] arr=new String[6];
                arr[0]=rs.getString("Readerid");
                arr[1]=rs.getString("Readername");
                arr[2]=rs.getString("Readerage");
                arr[3]=rs.getString("Readertel");
                arr[4]=rs.getString("Password");
                arr[5]=rs.getString("ReaderTpye");
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

	public Admshanchuyonghu()
	{
         try{
        	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             Connection con=DriverManager.getConnection 
             		("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");       
		
            Statement stmt=con.createStatement();
		   
            ResultSet rs=stmt.executeQuery("select count(*) from readerinformation");
            rs.next();
            RowNum=rs.getInt(1);
                         
            rs=stmt.executeQuery("select * from readerinformation");
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
            b.addActionListener(this);
            f.add(b,BorderLayout.SOUTH);
            //table.addMouseListener(this);
            f.getContentPane().add(new JScrollPane(table));
            f.addWindowListener(new WindowAdapter()
            	{
            		public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            		});
            f.setSize(700,400);;
            f.setLocation(500, 250);
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
