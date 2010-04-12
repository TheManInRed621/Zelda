package zelda.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import zelda.engine.GObject;
import zelda.engine.Game;
import zelda.engine.Scene;
import zelda.items.GuiHeart;
import zelda.link.Link;

/**
 * A specialised Scene object for the Zelda game.
 *
 * @author maartenhus
 */
public class ZeldaScene extends Scene
{
	protected Link link;   

	
	public ZeldaScene(Game game, String img)
	{
		super(game, img);
		this.game = game;
		sprite.setSprite(new Rectangle(0, 0, game.getWidth(), game.getHeight()));
        for(int i = 0; i < 5; i++)
        {
        GuiHeart heart = new GuiHeart(game, 370+i*12, 50, true);
        gameObjects.add(heart);
        }
	}

	@Override
	public void handleInput()
	{
		super.handleInput();

		// If links walks to the border of the screen it should scroll.
		if (!link.getStateString().equals("SwordState")) //ignore swordstate
		{
			final int currentMaxX = sprite.getX() + sprite.getWidth();
			final int currentMaxY = sprite.getY() + sprite.getHeight();

			final int mod = 1;
			final int box = 200;

			if ((link.getX()) > (currentMaxX - box))
			{
				int newX = sprite.getX() + mod;
				if ((newX + game.getWidth()) <= sprite.getImageWidth())
				{
					link.setX(link.getX() - mod);
					modShapes(-mod, 0);
					sprite.setX(newX);
				}
			}

			if (link.getX() < (sprite.getX() + box))
			{
				int newX = sprite.getX() - mod;

				if (newX > 0)
				{
					link.setX(link.getX() + mod);
					modShapes(mod, 0);
					sprite.setX(newX);
				}
			}

			if (link.getY() > (currentMaxY - box))
			{
				int newY = sprite.getY() + mod;
				if ((newY + game.getHeight()) <= sprite.getImageHeight())
				{
					link.setY(link.getY() - mod);
					modShapes(0, -mod);
					sprite.setY(newY);
				}
			}

			if (link.getY() < (sprite.getY() + box))
			{
				int newY = sprite.getY() - mod;

				if (newY > 0)
				{
					link.setY(link.getY() + mod);
					modShapes(0, mod);
					sprite.setY(newY);
				}
			}
		}
	}

	@Override
	public void initScene()
	{
		link = game.getLink();
	}

	/**
	 * When the screen moves everything else should move in the opposite direction.
	 * otherwise they won't sit still.
	 *
	 * @param modX
	 * @param modY
	 */
	private void modShapes(int modX, int modY)
	{
		for (Polygon poly : solids)
		{
			poly.translate(modX, modY);
		}

		for (GObject obj : gameObjects)
		{
			if (obj.isScreenAdjust()) // should it adjust when screen moves.
			{
				obj.setX(obj.getX() + modX);
				obj.setY(obj.getY() + modY);
			}
		}
	}
    @Override
    public void draw(Graphics2D g2)
	{
		g2.drawImage(sprite.getImage(), 0, 0, game.getWidth(), game.getHeight(), null);
        g2.setColor(Color.white);
        Font f = new Font ("Serif", Font.BOLD, 12);
        g2.setFont (f);
        g2.drawString("-- LIFE --", game.getWidth() - 122, game.getHeight() / 9);
	}
}

    

