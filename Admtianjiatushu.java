import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class Admtianjiatushu implements ActionListener
{
	Font font= new Font("宋体",Font.BOLD,12);//添加字体font
    JFrame f=new JFrame("添加图书");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;

    TextField edit1=new TextField(10);
    TextField edit2=new TextField(10);
    TextField edit3=new TextField(10);
    TextField edit4=new TextField(10);
    TextField edit5=new TextField(10);
    Label label1=new Label("图书编号：");
    Label label2=new Label("图书名称：");
    Label label3=new Label("图书价格：");
    Label label4=new Label("图书数量：");
    Label label5=new Label("图书出版社：");
    JButton b=new JButton("增加");
    
	public static void main(String args[]) 
	{
		new Admtianjiatushu();		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(edit1.getText().equals("")||edit2.getText().equals("")||	edit3.getText().equals("")
				||edit4.getText().equals("")||edit5.getText().equals(""))
		{
			JOptionPane.showMessageDialog(f, "请完善书籍信息！");//保证注册信息完善
		}
		else
		{
		try {
			String bookid = edit1.getText();
			String bookname = edit2.getText();
			float bookprice = Float.parseFloat(edit3.getText());
			int booknumber =Integer.parseInt(edit4.getText());
			String Press = edit5.getText();
			String m = "";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con1 = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1433;DatabaseName=nnn", "sa",
					"123456");
			Statement stmt1 = con1.createStatement();
			String sql1 = "select bookname from bookinformation where bookid='"
					+ bookid + "'";
			ResultSet rs = stmt1.executeQuery(sql1);
			while (rs.next()) {
				m = rs.getString("bookname");
			}
			stmt1.close();
			con1.close();
			if (m.equals("")) {
				try {

					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					Connection con = DriverManager.getConnection(
							"jdbc:sqlserver://localhost:1433;DatabaseName=nnn",
							"sa", "123456");
					// 增加记录
					Statement stmt = con.createStatement();
					String sql = "insert into bookinformation values('"
							+ bookid + "','" + bookname + "'," + bookprice
							+ "," + booknumber + ",'" + Press + "')";
					stmt.executeUpdate(sql);

					stmt.close();
					con.close();
					JOptionPane.showMessageDialog(f, "添加成功！");

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(f, "该书籍已存在，添加失败！");
			}
		}
		 catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		}
	}

	public Admtianjiatushu()
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
 
            b.addActionListener(this);
            Panel panel=new Panel();
            panel.add(label1);  panel.add(edit1);
            panel.add(label2);  panel.add(edit2);
            panel.add(label3);  panel.add(edit3);
            panel.add(label4);  panel.add(edit4);
            panel.add(label5);  panel.add(edit5);
            panel.add(b);
            f.add(panel,BorderLayout.NORTH);
            f.getContentPane().add(new JScrollPane(table));
            f.addWindowListener(new WindowAdapter()
            	{
            		public void windowClosiing(WindowEvent ew)
            	    {System.exit(0);}
            		});
            f.setSize(1000,500);
            f.setLocation(500,100);
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
