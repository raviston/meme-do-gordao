import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player 
{
	public BufferedImage[] playersprite;
	public Spritesheet sheet;
	public int x, y;
	public boolean right, left, shoot;
	public int width, height, speed = 8;
	public int f;
	public boolean delay;
	
	
	public Player(int x, int y)
	{
		sheet = new Spritesheet("/rola.png");
		playersprite = new BufferedImage[2];
		playersprite [0] = sheet.getSprite(0, 0, 16, 16);
		playersprite [1] = sheet.getSprite(16, 0, 16, 16);
		this.x = x;
		this.y = y;
	}
	
	public void tick()
	{
		if (right && x <= 915)  x+= speed;
		else if (left && x >= 0) x-= speed;
		if(shoot && f < 10)
		{
			f++;
			delay = true;
			
		}
		else
		{
			f = 0;
			delay = false;
		}
		
	}
	
	public void render(Graphics g)
	{
		g.drawImage(playersprite[0],x,y,80,80,null);
		
		if(shoot) g.drawImage(playersprite[1],x,y,80,80,null);
			
	}
	
}
