import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.table.*;

public class Admchaxunyonghu implements ActionListener
{
    Font font= new Font("宋体",Font.BOLD,12);//添加字体font
    JFrame f=new JFrame("用户查询");
    String[] names;
    int i,j,RowNum,ColNum;
    Object[][] info;
    JTable table;
    TextField edit=new TextField(10);
    JLabel label=new JLabel("输入用户姓名：");
    JButton b=new JButton("查询");

    public static void main(String args[])
    {
        new Admchaxunyonghu();
    }

    //响应按钮的事件，完成查询功能
    public void actionPerformed(ActionEvent e)
    {
        try{


            //装载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            //创建连接
            Connection con=DriverManager.getConnection
                    ("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");

            Statement stmt=con.createStatement();
            String name=edit.getText();
            ResultSet rs=stmt.executeQuery("select * from readerinformation where Readername="+"'"+name+"'");
            // 清除原有行
            DefaultTableModel tableModel= (DefaultTableModel)table.getModel();
            tableModel.setRowCount(0);
            //添加查询到的记录集
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

            //刷新表格，即重新绘制
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

    public Admchaxunyonghu()
    {
        try{

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.getConnection
                    ("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");

            Statement stmt=con.createStatement();
            //构造JTable
            ResultSet rs=stmt.executeQuery("select count(*) from readerinformation");
            rs.next();
            RowNum=rs.getInt(1);

            rs=stmt.executeQuery("select * from readerinformation");
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
            panel.add(label);
            panel.add(edit);
            f.add(panel,BorderLayout.NORTH);
            f.add(b,BorderLayout.SOUTH);
            f.getContentPane().add(new JScrollPane(table));
            //响应窗口的关闭事件
            f.addWindowListener(new WindowAdapter()
            {
                public void windowClosiing(WindowEvent ew)
                {System.exit(0);}
            });
            f.setVisible(true);
            f.setSize(700,400);
            f.setLocation(500,300);
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
