import java.awt.Color;
import java.util.*;


class Tank{//tank类
	private int x=0,y=0;
	private int speed=10;
	private String s="down";
	Bullet bu=null;
	boolean tankLive=true;
	public boolean isTankLive() {
		return tankLive;
	}

	public void setTankLive(boolean tankLive) {
		this.tankLive = tankLive;
	}
	Vector<Bullet> bugroup=new Vector<Bullet>();
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {

		if(x>0){
			this.x = x;
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y>0){
			this.y = y;
		}
	}

	Tank(int x,int y){
		this.y = y;
		this.x = x;
	}
	public void up() {
		s="up";
		if(y>0){
			this.y =y-speed;
		}
	}
	public void down() {
		s="down";
		if(y<370){
			this.y =y+speed;
		}
	}
	public void right() {
		s="right";
		if(x<570){
			this.x =x+speed;
		}
	}
	public void left() {
		s="left";
		if(x>0){
			this.x =x-speed;
		}

	}
	public void seedBullet(){//发射子弹
		switch(this.s){
		case "up":
			bu=new Bullet(x+12,y,s);
			bugroup.add(bu);
			break;
		case "down":
			bu=new Bullet(x+12,y+30,s);
			bugroup.add(bu);
			break;
		case "left":
			bu=new Bullet(x,y+12,s);
			bugroup.add(bu);
			break;
		case "right":
			bu=new Bullet(x+30,y+12,s);
			bugroup.add(bu);
			break;
		}
		Thread th=new Thread(bu);
		th.start();
	}
//	public boolean overlap(){//碰撞检测!!!!!!!!!!!!!!!!!!!!!!!!!!!tank会连在一起______等待解决
//		for(int i=0;i<=MyPanel.etgroup.size();i++){
//			Tank tk=null;
//			if(i==MyPanel.etgroup.size()){
//				tk=MyPanel.mt;
//			}else{
//				tk=MyPanel.etgroup.get(i);
//			}
//			if(!tk.equals(this)){
//				if(tk.getS().equals("up")||tk.getS().equals("down")){
//					if(this.x>=tk.x && this.x<=tk.x+30 && this.y>=tk.y && this.y<=tk.y+30)
//					{
//						return true;
//					}
//					if(this.x+30>=tk.x && this.x+30<=tk.x+30 && this.y>=tk.y && this.y<=tk.y+30)
//					{
//						return true;
//					}
//				}else{
//					if(this.x>=tk.x && this.x<=tk.x+30 && this.y>=tk.y && this.y<=tk.y+30)
//					{
//						return true;
//					}
//					if(this.x+30>=tk.x && this.x+30<=tk.x+30 && this.y>=tk.y && this.y<=tk.y+30)
//					{
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
}
class EnemyTank extends Tank{//敌方tank
	EnemyTank(int x, int y) {
		super(x, y);
	}
	static Color mycolor=Color.green;
}
class MyTank extends Tank{//我方tank
	MyTank(int x, int y) {
		super(x, y);
		super.setS("up");
	}
	static Color mycolor=Color.yellow;

}
class Bullet implements Runnable{//子弹类
	private int bullet_x=0,bullet_y=0;int speed=5;
	private String s="up";
	private boolean buLive=true;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public boolean isBuLive() {
		return buLive;
	}

	public void setBuLive(boolean buLive) {
		this.buLive = buLive;
	}

	public int getBullet_x() {
		return bullet_x;
	}

	public void setBullet_x(int bullet_x) {
		this.bullet_x = bullet_x;
	}

	public int getBullet_y() {
		return bullet_y;
	}

	public void setBullet_y(int bullet_y) {
		this.bullet_y = bullet_y;
	}
	Bullet(int bullet_x,int bullet_y,String s){
		this.bullet_x=bullet_x;
		this.bullet_y=bullet_y;
		this.s=s;
	}
	public void run() {//子弹运动
		while(true){
			try {
				Thread.sleep(50); 
			}catch (InterruptedException e) {

			}
			switch(s){
			case "up":
				if(bullet_y>0){
					this.bullet_y =bullet_y-speed;
				}else{
					buLive=false;
				}
				break;
			case "down":
				if(bullet_y<400){
					this.bullet_y =bullet_y+speed;
				}else{
					buLive=false;
				}
				break;
			case "left":
				if(bullet_x>0){
					this.bullet_x =bullet_x-speed;
				}else{
					buLive=false;
				}
				break;
			case "right":
				if(bullet_x<600){
					this.bullet_x =bullet_x+speed;
				}else{
					buLive=false;
				}
				break;
			}

		}
	}
}
class Boom{//爆炸动画
	private int x,y;
	private int life=9;
	private boolean boomLive=true;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public boolean isBoomLive() {
		return boomLive;
	}
	public void setBoomLive(boolean boomLive) {
		this.boomLive = boomLive;
	}
	public Boom(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void lifeTime(){
		if(life<0){
			this.boomLive=false;
		}else{
			life--;
		}
	}
	
}
class Nots{//记录生命值
	private static int myLife=2;
	private static int enemyLife=10;
	public static int getMyLife() {
		return myLife;
	}
	public static int getEnemyLife() {
		return enemyLife;
	}
	public static void hurtMyLife() {
		Nots.myLife --;
	}
	public static void hurtEnemyLife() {
		Nots.enemyLife --;
	}
}