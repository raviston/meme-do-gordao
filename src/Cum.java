import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cum 
{
	public BufferedImage[] cumsprite;
	public Spritesheet sheet;
    public int x, y;
    public int width, height;
    public boolean morre;
    public double delta;
    public long h;

    public Cum(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.width = 5;
        this.height = 10;
        sheet = new Spritesheet("/rola.png");
		cumsprite = new BufferedImage[1];
		cumsprite [0] = sheet.getSprite(69, 4, 5, 12);
    }

    public void tick()
    {
    	h++;
        if(h <= 150)
        {
            y-= 8;
        }
        if(y <= 80 && y >= 0 && x >= Enemy.x && x <= Enemy.x + 80)
        {
        	Enemy.colidiu = true;
        	y = 99999;
        }
    }


    public void render(Graphics g)
    {
    	h++;
        if(h <= 150)
        {
        	g.drawImage(cumsprite[0],x,y,15,30,null);
        }
    }
}