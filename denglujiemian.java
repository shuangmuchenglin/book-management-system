import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class denglujiemian implements ActionListener  //计算器类实现动作事件监听接口
{       Font font= new Font("宋体",Font.BOLD,18);//添加字体font
        JFrame f=new JFrame("图书馆登录窗口：");
		JButton b=new JButton("登录");
		JButton b1=new JButton("注册新帐号");
		JButton b2=new JButton("忘记密码？");
		JTextField t1 = new JTextField(20);//添加文本框组件
		JPasswordField t2 = new JPasswordField(20);//添加文本框组件
		JLabel m1=new JLabel("帐号：");//添加标签组件
		JLabel m2=new JLabel("密码：");//添加标签组件
	    JPanel p1=new JPanel();//添加面板组件p1
	    JPanel p2=new JPanel();//添加面板组件p2
	    JRadioButton jr1=new JRadioButton("管理员");//添加两个单选按钮
	    JRadioButton jr2=new JRadioButton("用户");
	    ButtonGroup group=new ButtonGroup();
	    public static String s1="";//保存用户名
	    int turn=0;//控制管理员或用户登录的开关变量
	public denglujiemian() //构造函数
    { 
			  f.setResizable(false);//设置窗体大小不可改变
			  group.add(jr1);
			  group.add(jr2);
			  b.addActionListener(this);
			  b1.addActionListener(this);
			  b2.addActionListener(this);
		      t1.addActionListener(this);      
		      t2.addActionListener(this);
		      jr1.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	turn=1;
		                            }
		        });
		      jr2.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	turn=2;
		                            }
		        });
		      f.addWindowListener(new monitor());//给窗体f注册窗口事件监听器
		      p1.setLayout(new FlowLayout(1));//面板p1网格布局
		      p1.add(m1);p1.add(t1);
		      p1.add(m2);p1.add(t2);
		      p1.add(jr1);p1.add(jr2);
		      p2.add(b2);p2.add(b);p2.add(b1);
		      b.setBackground(new Color(255,250,205));
		      b1.setBackground(new Color(255,250,205));
		      b2.setBackground(new Color(255,250,205));
		      b.addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent e) {
						s1=t1.getText();
						String s2=t2.getText();
						if(s1.equals("")||s2.equals(""))
						{
							JOptionPane.showMessageDialog(f,"请输入帐号或密码！");
						}
						else if(turn==0)
						{
							JOptionPane.showMessageDialog(f,"请选择登录类型！");	
						}
						else
						{
							if(turn==2)//用户登录
							{
						    String s3="";
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
						    String sql="select Password from Readerinformation where Readerid="+s1;
						    ResultSet rs = null;
							rs = stmt.executeQuery(sql);
							while(rs.next())
							{
							s3=rs.getString("Password");
							}
							rs.close();
							stmt.close();
							con.close();
						    } catch (SQLException e1) {
							e1.printStackTrace();
						    }
						    if(s2.equals(s3))
						    {
						    JOptionPane.showMessageDialog(f,"登录成功");
						    duzhechaxunjiemian DUZHECHAXUNJIEMIAN=new duzhechaxunjiemian();//创建读者查询界面对象
						    turn=0;//重置为零
						    f.setVisible(false);
						    }
						    else
						    JOptionPane.showMessageDialog(f,"密码错误！登录失败");
					        }
							
							if(turn==1)//管理员登陆
							{
						    String s3="";
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
						    String sql="select admpwd from adminformation where admid="+s1;
						    ResultSet rs = null;
							rs = stmt.executeQuery(sql);
							while(rs.next())
							{
							s3=rs.getString("admpwd");
							}
							rs.close();
							stmt.close();
							con.close();
						    } catch (SQLException e1) {
							e1.printStackTrace();
						    }
						    if(s2.equals(s3))
						    {
						    JOptionPane.showMessageDialog(f,"登录成功");
						    AdmGui admgui=new AdmGui();
						    turn=0;//重置为零
						    f.setVisible(false);
						    }
						    else
						    JOptionPane.showMessageDialog(f,"密码错误！登录失败");
					        }
						}
					}
					public void mouseEntered(MouseEvent e) {
						// 处理鼠标移入
					}
					public void mouseExited(MouseEvent e) {
						// 处理鼠标离开
					}
					public void mousePressed(MouseEvent e) {
						// 处理鼠标按下
					}
					public void mouseReleased(MouseEvent e) {
						// 处理鼠标释放
					}
				});//注册鼠标事件监听器并实现鼠标实现鼠标事件接口
		      b1.addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent e) {
						try {
							Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}       

						Connection con = null;
						try {
							con= DriverManager.getConnection
									("jdbc:sqlserver://localhost:1433;DatabaseName=nnn","sa","123456");  
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						duzhezhuce DUZHEZHUCE=new duzhezhuce();//改成读者信息
					}
					public void mouseEntered(MouseEvent e) {
						// 处理鼠标移入
					}
					public void mouseExited(MouseEvent e) {
						// 处理鼠标离开
					}
					public void mousePressed(MouseEvent e) {
						// 处理鼠标按下
					}
					public void mouseReleased(MouseEvent e) {
						// 处理鼠标释放
					}
				});//注册鼠标事件监听器并实现鼠标实现鼠标事件接口
		      b2.addMouseListener(new MouseListener(){
					public void mouseClicked(MouseEvent e) {
						  JOptionPane.showMessageDialog(f,"请输入帐号和手机号进行验证");
						wangjimima WANGJIMIMA=new wangjimima();
					}
					public void mouseEntered(MouseEvent e) {
						// 处理鼠标移入
					}
					public void mouseExited(MouseEvent e) {
						// 处理鼠标离开
					}
					public void mousePressed(MouseEvent e) {
						// 处理鼠标按下
					}
					public void mouseReleased(MouseEvent e) {
						// 处理鼠标释放
					}
				});//注册鼠标事件监听器并实现鼠标实现鼠标事件接口
	
		      f.add(p1,"Center"); //面板p1使用边界布局    
		      f.add(p2,"South"); //面板p2使用边界布局    
		      f.setLocation(800, 400);//窗体f初始坐标  
		      f.setSize(300, 200);//窗体f初始大小
		      f.setVisible(true);//窗体f可见
		      t1.setFont(font);//文本框运用字体font1
		      t2.setFont(font);//文本框运用字体font2
		      t1.setBackground(new Color(240,240,200));//设置文本框背景颜色
		      t2.setBackground(new Color(240,240,200));//设置文本框背景颜色
		    }

	public void actionPerformed(ActionEvent e)//实现动作事件接口
	{  	
	} 
 		
		public static void main(String args[])
		{
		denglujiemian DLJM=new denglujiemian();//通过denglujiemian类显示窗体
	    }
}

class monitor extends WindowAdapter
{ public void windowClosing(WindowEvent e)
     {System.exit(0);}
}//关闭窗体
