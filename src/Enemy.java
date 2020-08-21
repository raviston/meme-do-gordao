import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy
{
    public BufferedImage[] enemysprite;
    public Spritesheet sheet;
    public static int x, y, dx, hp = 40, f, l;
    public long lastTime;
    public double amountOfTicks;
    public double ns;
    public double delta;
    public long  now;
    public static boolean colidiu = false, penes;
    public boolean mortasso;

    public Enemy(int x, int y)
    {
        sheet = new Spritesheet("/rola.png");
        enemysprite = new BufferedImage[2];
        enemysprite [0] = sheet.getSprite(32, 0, 16, 16);
        enemysprite [1] = sheet.getSprite(48, 0, 16, 16);
        this.x = x;
        this.y = y;
        lastTime = System.nanoTime();
        amountOfTicks = 2;
        ns = 1000000000/ amountOfTicks;
        delta = 0;
    }

    public void tick()
    {
        now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        if(delta >= 1) 
        {
            dx = new Random().nextInt(10000);
            delta--;
        }
        if(dx>5000) x += 4;
        else if(dx<=5000) x -= 4;
        if(x >= 915) x=0;
        else if(x <= 0) x=915;
        if(colidiu) morrer();
        if(hp <= 0)
        	{
        	mortasso = true;
        	}
        if(penes) penes();
    }
    public void morrer()
    {
    	colidiu = false;
    	hp--;
    	penes = true;
    }
    
    public void penes()
    {
    	l++;
    		if(l >= 15) 
    		{
    			penes = false;
    			l = 0;
    		}
    }

    public void render(Graphics g)
    {
        if(penes)g.drawImage(enemysprite[1],x,y,80,80,null);
        if(penes == false)g.drawImage(enemysprite[0],x,y,80,80,null);
        g.setFont(new Font("Comic Sans MS",Font.BOLD,20));
        g.setColor(new Color(255,150,150));
        g.drawString("HP: "+hp,0,100);
    }
}
