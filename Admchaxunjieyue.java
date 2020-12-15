import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class Admchaxunjieyue implements ActionListener {
	Font font = new Font("宋体", Font.BOLD, 12);// 添加字体font
	JFrame f = new JFrame("用户查询");
	String[] names;
	int i, j, RowNum, ColNum;
	Object[][] info;
	JTable table;
	TextField edit = new TextField(10);
	JLabel label = new JLabel("输入用户姓名：");
	JButton b = new JButton("查询");
	JButton b1 = new JButton("删除");

	public static void main(String args[]) {
		new Admchaxunjieyue();
	}

	// 响应按钮的事件，完成查询功能
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("查询")) {
			if (edit.getText().length() == 0) {
				try {

					// 装载驱动程序
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

					// 创建连接
					Connection con = DriverManager.getConnection(
							"jdbc:sqlserver://localhost:1433;DatabaseName=nnn",
							"sa", "123456");

					Statement stmt = con.createStatement();
					String name = edit.getText();
					ResultSet rs = stmt
							.executeQuery("select * from borrowinformation");
					// 清除原有行
					DefaultTableModel tableModel = (DefaultTableModel) table
							.getModel();
					tableModel.setRowCount(0);
					// 添加查询到的记录集
					while (rs.next()) {
						String[] arr = new String[5];
						arr[0] = rs.getString("bookid");
						arr[1] = rs.getString("Readerid");
						arr[2] = rs.getString("borrownumber");
						arr[3] = rs.getString("borrowtime");
						arr[4] = rs.getString("returntime");
						tableModel.addRow(arr);
					}

					// 刷新表格，即重新绘制
					table.invalidate();

					rs.close();
					stmt.close();
					con.close();

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			} else {
				try {

					// 装载驱动程序
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

					// 创建连接
					Connection con = DriverManager.getConnection(
							"jdbc:sqlserver://localhost:1433;DatabaseName=nnn",
							"sa", "123456");

					Statement stmt = con.createStatement();
					String name = edit.getText();
					ResultSet rs = stmt
							.executeQuery("select * from borrowinformation where Readerid in "
									+ "(select Readerid from Readerinformation where Readername like '%"
									+ name + "%')");
					// 清除原有行
					DefaultTableModel tableModel = (DefaultTableModel) table
							.getModel();
					tableModel.setRowCount(0);
					// 添加查询到的记录集
					while (rs.next()) {
						String[] arr = new String[5];
						arr[0] = rs.getString("bookid");
						arr[1] = rs.getString("Readerid");
						arr[2] = rs.getString("borrownumber");
						arr[3] = rs.getString("borrowtime");
						arr[4] = rs.getString("returntime");
						tableModel.addRow(arr);
					}

					// 刷新表格，即重新绘制
					table.invalidate();

					rs.close();
					stmt.close();
					con.close();

				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
		if (e.getActionCommand().equals("删除")) {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con = DriverManager.getConnection(
						"jdbc:sqlserver://localhost:1433;DatabaseName=nnn",
						"sa", "123456");

				Statement stmt = con.createStatement();

				// 删除记录
				int i = table.getSelectedRow();
				String id = table.getValueAt(i, 0).toString();
				int result = JOptionPane.showConfirmDialog(f, "你确定要删除编号为："
						+ id + "的图书记录吗？");
				if (result == JOptionPane.OK_OPTION) {
					String sql = "delete Borrowinformation where bookid=" + "'"
							+ id + "'";
					stmt.executeUpdate(sql);
				}

				// 显示删除后数据库记录
				ResultSet rs = stmt
						.executeQuery("select * from Borrowinformation");
				DefaultTableModel tableModel = (DefaultTableModel) table
						.getModel();
				tableModel.setRowCount(0);

				while (rs.next()) {
					String[] arr = new String[5];
					arr[0] = rs.getString("bookid");
					arr[1] = rs.getString("readerid");
					arr[2] = rs.getString("borrownumber");
					arr[3] = rs.getString("borrowtime");
					arr[4] = rs.getString("returntime");
					tableModel.addRow(arr);

				}
				table.invalidate();
				rs.close();
				stmt.close();
				con.close();

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public Admchaxunjieyue() {
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1433;DatabaseName=nnn", "sa",
					"123456");

			Statement stmt = con.createStatement();
			// 构造JTable
			ResultSet rs = stmt
					.executeQuery("select count(*) from borrowinformation");
			rs.next();
			RowNum = rs.getInt(1);

			rs = stmt.executeQuery("select * from borrowinformation");
			ResultSetMetaData rsmd = rs.getMetaData();

			ColNum = rsmd.getColumnCount();

			names = new String[ColNum];
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

			table = new JTable(model);
			table.setBackground(new Color(255, 250, 205));
			table.setFont(font);
			table.setPreferredScrollableViewportSize(new Dimension(400, 50));

			b.addActionListener(this);
			b1.addActionListener(this);
			Panel panel = new Panel();
			Panel panel1 = new Panel();
			panel.add(label);
			panel.add(edit);
			panel1.add(b);
			panel1.add(b1);
			f.add(panel, BorderLayout.NORTH);
			f.add(panel1, BorderLayout.SOUTH);
			f.getContentPane().add(new JScrollPane(table));
			// 响应窗口的关闭事件
			f.addWindowListener(new WindowAdapter() {
				public void windowClosiing(WindowEvent ew) {
					System.exit(0);
				}
			});
			f.setVisible(true);
			f.setSize(700, 400);
			f.setLocation(500, 300);
			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
