import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener
{
	public static Player player;
	public static Enemy enemy;
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 1000, HEIGHT = 600, SCALE = 1;
	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public boolean isRunning;
	public Image background;
	public BufferedImage image;
	static public List<Cum> cum;
	public int mega;
	public int h = 0;
	public BufferedImage[] maesprite, gordomortosprite;
    public Spritesheet sheet;
    private int frames = 0, maxFrames = 10, curAnimation = 0, maxAnimation = 2;
    public JFrame frame;
	public Game()
	{
		frame = new JFrame("Gordao");
		frame.add(this);
		frame.addKeyListener(this);
		frame.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		background = this.getImage("gordo.png");
		player = new Player(500-16, HEIGHT-118);
		int bhj = new Random().nextInt(900);
		enemy = new Enemy(bhj, 0);
		cum = new ArrayList<Cum>();
		sheet = new Spritesheet("/rola.png");
		maesprite = new BufferedImage[3];
		maesprite [0] = sheet.getSprite(96, 0, 48, 48);
		maesprite [1] = sheet.getSprite(96, 48, 48, 48);
		maesprite [2] = sheet.getSprite(80, 0, 16, 16);
		

	}
	
	public static void main(String [] args)
	{
		Game game = new Game();
		game.start();
		new Thread(game).start();
	}
	
	public void start()
	{
		isRunning = true;
		Thread thread = new Thread();
		thread.start();
	}
	
	public void tick()
	{
		player.tick();
		enemy.tick();
		if(hasFocus())
		{
			frame.requestFocus();
		}
		for(int i = 0; i < cum.size(); i++) 
        {
            Cum cumGo = cum.get(i);
            cumGo.tick();
        }
		frames++;
		if(frames>maxFrames)
		{
			frames = 0;
			curAnimation++;
			if(curAnimation >= maxAnimation)
			{
				curAnimation = 0;
			}
		}
		
	}
	
	public void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) 
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g = bs.getDrawGraphics();

    if(enemy.mortasso == false)
    {
        g.drawImage(background,0,-200,WIDTH*SCALE,1000,null);
            for(int i = 0; i < cum.size(); i++) 
            {
                    Cum cumGo = cum.get(i);
                    cumGo.render(g);
            }

            player.render(g);
            enemy.render(g);
    }
    else if(enemy.mortasso)
    {
    	g.setFont(new Font("Comic Sans MS",Font.BOLD,40));
        g.setColor(new Color(255,0,0));
        g.drawString("O GORDÃO TA MORTO",280,300);
        g.drawImage(maesprite[2],770,250,80,80,null);
        g.drawImage(maesprite[curAnimation],50,140,200,200,null);
    }
        g.dispose();
        bs.show();
    }
	
	public Image getImage(String path) 
	{
        URL imageURL = getClass().getResource(path);
        if (imageURL == null) return null;
        return new ImageIcon(imageURL).getImage();
    }
	
	
	
	@Override
	public void keyTyped(KeyEvent e) 
	{

		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_D) 
        {
            player.right = true;
        }
        if(e.getKeyCode()  == KeyEvent.VK_A) 
        {
            player.left = true;
        }
        if(e.getKeyCode()  == KeyEvent.VK_SPACE && player.delay == false) 
        {
            player.shoot = true;
            cum.add(new Cum(player.x + 35,player.y-16));
        }
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_D) 
        {
            player.right = false;
        }
        if(e.getKeyCode()  == KeyEvent.VK_A) 
        {
            player.left = false;
        }
        if(e.getKeyCode()  == KeyEvent.VK_SPACE) 
        {
            player.shoot = false;
            
        }
		
	}

	@Override
	public void run() 
	{
		long lastTime = System.nanoTime();
		double delta = 0;
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1)
			{
			delta--;
			tick();
			render();
			mega++;
			if(mega > 120)
			{
				mega = 0;
			}
			}
		}	
	}

}
