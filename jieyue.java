import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;
public class jieyue  implements ActionListener 
{
	JFrame f = new JFrame("借阅");
	JLabel label = new JLabel("请选择借阅数量：");
	JRadioButton jr1 = new JRadioButton("1");
	JRadioButton jr2 = new JRadioButton("2");
	JRadioButton jr3 = new JRadioButton("3");
	JRadioButton jr4 = new JRadioButton("4");
	JRadioButton jr5 = new JRadioButton("5");
	ButtonGroup group = new ButtonGroup();
	JButton b1 = new JButton("确定");
	JButton b2 = new JButton("取消");
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	String date="";//获取当前时间
	int typenumber=1;
	int number=1;//借阅书籍数量，默认为1，避免出现0
	int kucun=0;//对应书籍库存变量
	int borrow=0;//已借数量
	String type="";//对应读者类型
	int newkucun=0;
	public static void main(String args[]) {
		new jieyue();
	}


	public jieyue() {
		group.add(jr1);
		group.add(jr2);
		group.add(jr3);
		group.add(jr4);
		group.add(jr5);
		jr1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	number=1;
                            }
        });
		jr2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	number=2;
                            }
        });
		jr3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	number=3;
                            }
        });
		jr4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	number=4;
                            }
        });
		jr5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	number=5;
                            }
        });
		b1.addActionListener(this);
		b2.addActionListener(this);
		p1.add(label);
		p3.add(b1);
		p3.add(b2);
		p2.add(jr1);p2.add(jr2);p2.add(jr3);p2.add(jr4);p2.add(jr5);
		f.add(p1, BorderLayout.NORTH);
		f.add(p2, BorderLayout.CENTER);
		f.add(p3, BorderLayout.SOUTH);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosiing(WindowEvent ew) {
				System.exit(0);
			}
		});
		f.setLocation(900,400);
		f.pack();
		f.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("确定"))
	    {
		f.setVisible(false);
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}       
		Connection con = null;
		try {
			con= DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
		    Statement stmt = con.createStatement();
		    String sql="select Booknumber from bookinformation where bookid='"+duzhechaxunjiemian.id+"'";
		    ResultSet rs = null;
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
			kucun=rs.getInt("Booknumber");	
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	//查询库存数量	
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}       

		try {
			con= DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
		    Statement stmt = con.createStatement();
		    String sql1="select Readertpye from Readerinformation where Readerid='"+denglujiemian.s1+"'";
		    ResultSet rs1 = null;
		    rs1= stmt.executeQuery(sql1);
		    while(rs1.next())
			{
			type=rs1.getString("Readertpye");	
			}

			rs1.close();
			stmt.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	//查询用户类型
		
		try {
			con= DriverManager.getConnection
					("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
		    Statement stmt = con.createStatement();
		    String sql="select sum(Borrownumber) from borrowinformation where Readerid="+denglujiemian.s1+" and Returntime is null";
		    ResultSet rs = null;
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
			borrow=rs.getInt(1);	
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//判断最大借书量
		if(type.equals("老师"))
			typenumber=5;
		else
			typenumber=3;
		
		//判断能否借出
		if((number<=kucun)&&((number+borrow)<=typenumber))
		{
			newkucun=kucun-number;
			try {
				con= DriverManager.getConnection
						("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
			    Statement stmt = con.createStatement();
			    String sql="update bookinformation set Booknumber="+newkucun+" where Bookid='"+duzhechaxunjiemian.id+"'";
			    stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}//更新书籍库存
			
			try {
				con= DriverManager.getConnection
						("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
			    Statement stmt = con.createStatement();
			    String sql="SELECT DATENAME(year,GETDATE())+'-'+ DATENAME(MONTH,GETDATE())+'-'+ DATENAME(DAY,GETDATE())+' '+ DATENAME(HOUR,GETDATE())+':'+ DATENAME(MINUTE,GETDATE())+':'+ DATENAME(SECOND,GETDATE())";
			    stmt.executeQuery(sql);
			    ResultSet rs = null;
			    rs= stmt.executeQuery(sql);
			    while(rs.next())
				{
				date=rs.getString(1);	
				}
				stmt.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}//获取当前时间
				
			try {
				con= DriverManager.getConnection
						("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
			    Statement stmt = con.createStatement();
			    String sql="insert into Borrowinformation (Bookid,Readerid,Borrownumber,Borrowtime) values('"+duzhechaxunjiemian.id+"',"+denglujiemian.s1+","+number+",'"+date+"')";
			    stmt.executeUpdate(sql);
				stmt.close();
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(f,"借阅成功！");
			number=1;
			f.setVisible(false);
		}
		else
		{
			JOptionPane.showMessageDialog(f,"借阅失败！");
		}
	    }
		
	}
}
