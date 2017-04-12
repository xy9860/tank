import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class Main extends JFrame implements ActionListener{//程序入口
	MyPanel panel=new MyPanel();
	StartPanel start=new StartPanel();
	JMenuBar menubar=new JMenuBar();
	JMenu menu=new JMenu("游戏(G)");
	JMenuItem menu_1=new JMenuItem("新游戏(N)");
	JMenuItem menu_2=new JMenuItem("存档(W)");
	JMenuItem menu_3=new JMenuItem("读档(R)");
	JMenuItem menu_4=new JMenuItem("退出(E)");
	Thread th=new Thread(panel);
	Thread th_1=new Thread(start);
	public static void main(String[] args){
		Main tk=new Main();
	}
	Main(){
		menu_1.addActionListener(this);
		menu_1.setActionCommand("new");
		menu_1.setMnemonic('N');
		menu_2.addActionListener(this);
		menu_2.setActionCommand("write");
		menu_3.setMnemonic('W');
		menu_3.addActionListener(this);
		menu_3.setActionCommand("red");
		menu_3.setMnemonic('R');
		menu_4.addActionListener(this);
		menu_4.setActionCommand("exit");
		menu_4.setMnemonic('E');
		menu.setMnemonic('G');
		menu.add(menu_1);
		menu.add(menu_2);
		menu.add(menu_3);
		menu.add(menu_4);
		menubar.add(menu);
		this.addKeyListener(panel);
		this.setJMenuBar(menubar);
		this.add(start);
		this.setSize(800, 600);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setTitle("坦克大战");
		this.setIconImage(new ImageIcon("src/tank.jpg").getImage());
		this.setResizable(false);
		th_1.start();

	}
	public void actionPerformed(ActionEvent e) {//菜单监控
		if(e.getActionCommand().equals("new")){
			this.remove(start);
			this.add(panel);
			th.start();
			this.setVisible(true);
		}else if(e.getActionCommand().equals("write")){

		}else if(e.getActionCommand().equals("red")){

		}else if(e.getActionCommand().equals("exit")){
			System.exit(1);
		}
	}
}
class MyPanel extends JPanel implements KeyListener,Runnable{//自定义面板
	Vector<EnemyTank> etgroup=new Vector<EnemyTank>();
	Vector<Boom> bmgroup=new Vector<Boom>();
	Boom boom=null;
	EnemyTank et_1=null;
	EnemyTank et_2=null;
	EnemyTank et_3=null;
	MyTank mt=null;
	Image im_1=null;
	Image im_2=null;
	Image im_3=null;
	MyPanel(){
		et_1=new EnemyTank(0,0);
		et_2=new EnemyTank(270,0);
		et_3=new EnemyTank(570,0);
		mt=new MyTank(450,300);
		etgroup.add(et_1);
		etgroup.add(et_2);
		etgroup.add(et_3);
		im_1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bzxg1.gif"));
		im_2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bzxg2.gif"));
		im_3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bzxg3.gif"));
		MediaTracker temp=new MediaTracker(this);//预先处理图像,否则第一次显示图像会是失败
		temp.addImage(im_1, 0);
		temp.addImage(im_2, 0);
		temp.addImage(im_3, 0);
		try {
			temp.waitForAll();
		}catch (Exception e) {}
		
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.gray);
		g.fillRect(0, 0, 600, 400);
		this.count(g);
		if(mt.isTankLive()){
			this.drawTank(mt.getX(), mt.getY(), MyTank.mycolor, g, this.mt.getS());
		}
		for(int i=0;i<etgroup.size();i++){//敌方tank
			if(etgroup.get(i).isTankLive()){
				this.drawTank(etgroup.get(i).getX(), etgroup.get(i).getY(), EnemyTank.mycolor, g, etgroup.get(i).getS());
			}
		}
		for(int i=0;i<mt.bugroup.size();i++){//我方子弹
			Bullet b=mt.bugroup.get(i);
			if(!b.isBuLive()){
				mt.bugroup.remove(b);
			}
			if(b!=null&&b.isBuLive()){
				g.setColor(Color.white);
				g.fill3DRect(b.getBullet_x(), b.getBullet_y(), 3, 3,false);
			}
		}
		for(int i=0;i<bmgroup.size();i++){//爆炸效果
			if(bmgroup.get(i).getLife()>6){
				g.drawImage(im_1, bmgroup.get(i).getX(), bmgroup.get(i).getY(), 30, 30, this);
			}else if(bmgroup.get(i).getLife()>3){
				g.drawImage(im_2, bmgroup.get(i).getX(), bmgroup.get(i).getY(), 30, 30, this);
			}else{
				g.drawImage(im_3, bmgroup.get(i).getX(), bmgroup.get(i).getY(), 30, 30, this);
			}
			bmgroup.get(i).lifeTime();
			if(bmgroup.get(i).getLife()==0){
				bmgroup.remove(i);
			}
		}
		for(int i=0;i<etgroup.size();i++){//敌方子弹
			for(int j=0;j<etgroup.get(i).bugroup.size();j++){
				Bullet b=etgroup.get(i).bugroup.get(j);
				if(!b.isBuLive()){
					mt.bugroup.remove(b);
				}
				if(b!=null&&b.isBuLive()){
					g.setColor(Color.white);
					g.fill3DRect(b.getBullet_x(), b.getBullet_y(), 3, 3,false);
				}
			}
		}
	}
	public void drawTank(int x,int y,Color c,Graphics g,String s){//画tank

		if(s.equals("down")||s.equals("up")){
			int lx=x+12;
			int ly=y;
			if(s.equals("down")){
				ly=y+30;
			}
			g.setColor(c);
			g.fill3DRect(x, y, 5, 30, true);
			g.getColor();
			g.fill3DRect(x+5, y+5, 15, 20, false);
			g.getColor();
			g.fill3DRect(x+20, y, 5, 30, true);
			g.getColor();
			g.fillOval(x+7, y+10,10 ,10 );
			g.getColor();
			g.drawLine(x+12, y+15, lx, ly);
		}else if(s.equals("right")||s.equals("left")){
			int lx=x;
			int ly=y+12;
			if(s.equals("right")){
				lx=x+30;
			}
			g.setColor(c);
			g.fill3DRect(x, y, 30, 5, true);
			g.getColor();
			g.fill3DRect(x+5, y+5, 20, 15, false);
			g.getColor();
			g.fill3DRect(x, y+20, 30, 5, true);
			g.getColor();
			g.fillOval(x+10, y+7,10 ,10 );
			g.getColor();
			g.drawLine(x+15, y+12, lx, ly);
		}
	}
	public void count(Graphics g){
		g.setColor(Color.black);
		g.setFont(new Font("黑体",Font.BOLD,20));
		g.drawString("已经击杀的坦克", 620, 100);
		this.drawTank(650, 150, EnemyTank.mycolor, g,"up");
		
		this.drawTank(300, 450, EnemyTank.mycolor, g,"up");
		g.setColor(Color.black);
		g.drawString(Nots.getEnemyLife()+"", 350, 470);
		this.drawTank(100, 450, MyTank.mycolor, g,"up");
		g.setColor(Color.black);
		g.drawString(Nots.getMyLife()+"", 150, 470);
	}
	public void hit(Bullet b,Tank tk){//tank击中判断
		switch(tk.getS()){
		case "up":
		case "down":
			if(b.getBullet_x()>tk.getX()&&b.getBullet_x()<tk.getX()+25&&b.getBullet_y()>tk.getY()&&b.getBullet_y()<tk.getY()+30){
				b.setBuLive(false);
				tk.setTankLive(false);
				if(tk.equals(mt)){
					Nots.hurtMyLife();
				}else{
					Nots.hurtEnemyLife();
				}
				boom=new Boom(tk.getX(),tk.getY());
				bmgroup.add(boom);
			}
			break;
		case "left":
		case "right":
			if(b.getBullet_x()>tk.getX()&&b.getBullet_x()<tk.getX()+30&&b.getBullet_y()>tk.getY()&&b.getBullet_y()<tk.getY()+25){
				b.setBuLive(false);
				tk.setTankLive(false);
				if(tk.equals(mt)){
					Nots.hurtMyLife();
				}else{
					Nots.hurtEnemyLife();
				}
				boom=new Boom(tk.getX(),tk.getY());
				bmgroup.add(boom);
			}
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {//键盘监听
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:this.mt.up();break;
		case KeyEvent.VK_S:this.mt.down();break;
		case KeyEvent.VK_A:this.mt.left();break;
		case KeyEvent.VK_D:this.mt.right();break;
		case KeyEvent.VK_J:
			if(mt.bugroup.size()<2&&mt.isTankLive()){
				this.mt.seedBullet();
			}
			break;
		case KeyEvent.VK_PLUS:break;
		case KeyEvent.VK_MINUS:break;
		}
		this.repaint();
	}
	public void keyReleased(KeyEvent e) {}
	public void run() {//子弹遍历和敌方tank行为
		while(true){
			try {
				Thread.sleep(200); 
			}catch (InterruptedException e) {
				return;
			}
			for(int i=0;i<mt.bugroup.size();i++){
				Bullet b=mt.bugroup.get(i);
				if(b.isBuLive()){
					for(int j=0;j<etgroup.size();j++){
						EnemyTank et=etgroup.get(j);
						if(et.isTankLive()){
							this.hit(b, et);
						}
						this.repaint();
					}
				}
			}
			for(int i=0;i<etgroup.size();i++){
				for(int j=0;j<etgroup.get(i).bugroup.size();j++){
					Bullet b=etgroup.get(i).bugroup.get(j);
					if(b.isBuLive()){
						this.hit(b, mt);
					}
				}		
			}
			switch((int)(Math.random()*19)){
			case 1:et_1.right();this.repaint();break;
			case 2:et_1.up();this.repaint();break;
			case 3:et_1.down();this.repaint();break;
			case 4:et_1.left();this.repaint();break;
			case 5:et_2.right();this.repaint();break;
			case 6:et_2.up();this.repaint();break;
			case 7:et_2.down();this.repaint();break;
			case 8:et_2.left();this.repaint();break;
			case 9:et_3.right();this.repaint();break;
			case 10:et_3.up();this.repaint();break;
			case 11:et_3.down();this.repaint();break;
			case 12:et_3.left();this.repaint();break;
			case 13:
				if(et_1.isTankLive()){
					et_1.seedBullet();
					this.repaint();
				}
				break;
			case 14:
				if(et_2.isTankLive()){
					et_2.seedBullet();
					this.repaint();
				}
				break;
			case 15:
				if(et_3.isTankLive()){
					et_3.seedBullet();
					this.repaint();
				}
			case 16:
				if(et_1.isTankLive()){
					et_1.seedBullet();
					this.repaint();
				}
				break;
			case 17:
				if(et_2.isTankLive()){
					et_2.seedBullet();
					this.repaint();
				}
				break;
			case 18:
				if(et_3.isTankLive()){
					et_3.seedBullet();
					this.repaint();
				}
				break;
			}
			this.repaint();
		}
	}
}
class StartPanel extends JPanel implements Runnable{
	int time=0;
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.gray);
		g.fillRect(0, 0, 600, 400);
		if(time%2==0){
			g.setColor(Color.yellow);
			g.setFont(new Font("伪宋",Font.BOLD,40));
			g.drawString("第一关", 250, 200);
		}
	}
	public void run() {
		while(true){
			try{
				Thread.sleep(600);
			}catch(Exception e){}
			time++;
			this.repaint();
		}
	}
}
