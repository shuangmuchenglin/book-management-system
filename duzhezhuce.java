import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class duzhezhuce implements ActionListener 
{
	JFrame f = new JFrame("DB Test");
	String[] names;
	int i, j, RowNum, ColNum;
	Object[][] info;
	TextField edit1 = new TextField("",10);
	TextField edit2 = new TextField("",10);
	TextField edit3 = new TextField("",10);
	TextField edit4 = new TextField("",10);
	TextField edit5 = new TextField("",10);
	TextField edit6 = new TextField("",10);
	JLabel label1 = new JLabel("帐号：        ");
	JLabel label2 = new JLabel("姓名：        ");
	JLabel label3 = new JLabel("年龄：        ");
	JLabel label4 = new JLabel("手机号码：");
	JLabel label5 = new JLabel("密码：        ");
	JLabel label6 = new JLabel("职业：        ");
	JButton b = new JButton("增加");

	public static void main(String args[]) {
		new duzhezhuce();
	}

	public void actionPerformed(ActionEvent e) {
		if(edit1.getText().equals("")||edit2.getText().equals("")||	edit3.getText().equals("")
				||edit4.getText().equals("")||edit5.getText().equals("")||edit6.getText().equals(""))
		{
			JOptionPane.showMessageDialog(f, "请完善注册信息！");//保证注册信息完善
		}
		else
		{
		try {
			String Readerid = edit1.getText();
			String Readername = edit2.getText();
			int Readerage = Integer.parseInt(edit3.getText());
			String Readertel = edit4.getText();
			String Password = edit5.getText();
			String Readertype = edit6.getText();
			String m = "";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con1 = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1433;DatabaseName=nnn", "sa",
					"123456");
			Statement stmt1 = con1.createStatement();
			String sql1 = "select Readername from Readerinformation where Readerid='"
					+ Readerid + "'";
			ResultSet rs = stmt1.executeQuery(sql1);
			while (rs.next()) {
				m = rs.getString("Readername");
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
					String sql = "insert into Readerinformation values("
							+ Readerid + ",'" + Readername + "'," + Readerage
							+ "," + Readertel + ",'" + Password + "','"
							+ Readertype + "')";
					stmt.executeUpdate(sql);

					stmt.close();
					con.close();
					JOptionPane.showMessageDialog(f, "注册成功！");

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(f, "该用户已存在，注册失败！");
			}
		}
		 catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		}
	}

	public duzhezhuce() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1433;DatabaseName=nnn", "sa",
					"123456");
			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("select count(*) from Readerinformation");
			rs.next();
			RowNum = rs.getInt(1);

			rs = stmt.executeQuery("select * from Readerinformation");
			ResultSetMetaData rsmd = rs.getMetaData();
			ColNum = rsmd.getColumnCount();

			names = new String[ColNum]; // 构造JTable
			for (i = 1; i <= ColNum; i++)
				names[i - 1] = rsmd.getColumnName(i);

			info = new Object[RowNum][];
			i = 0;
			while (rs.next()) {
				info[i] = new Object[ColNum];
				for (j = 1; j <= ColNum; j++) {
					info[i][j - 1] = rs.getObject(j);
				}
				i++;
			}

			DefaultTableModel model = new DefaultTableModel(info, names);

			b.addActionListener(this);
			Panel panel1 = new Panel();
			Panel panel2 = new Panel();
			panel1.add(label1);
			panel1.add(edit1);
			panel1.add(label2);
			panel1.add(edit2);
			panel1.add(label3);
			panel1.add(edit3);
			panel1.add(label4);
			panel1.add(edit4);
			panel1.add(label5);
			panel1.add(edit5);
			panel1.add(label6);
			panel1.add(edit6);
			panel2.add(b);
			edit1.setBackground(new Color(255,250,205));
			edit2.setBackground(new Color(255,250,205));
			edit3.setBackground(new Color(255,250,205));
			edit4.setBackground(new Color(255,250,205));
			edit5.setBackground(new Color(255,250,205));
			edit6.setBackground(new Color(255,250,205));
			f.setSize(200, 300);
			f.add(panel1, BorderLayout.CENTER);
			f.add(panel2, BorderLayout.SOUTH);
			f.addWindowListener(new WindowAdapter() {
				public void windowClosiing(WindowEvent ew) {
					System.exit(0);
				}
			});
			f.setVisible(true);
			f.setLocation(900, 100);
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
