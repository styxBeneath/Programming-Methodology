import acm.graphics.GCompound;
import acm.graphics.GObject;
import java.awt.*;
import java.awt.Color;

import acm.graphics.*;

public class GHeart extends GCompound {

	private GPolygon pg = new GPolygon();
	private GLabel lifeNum = new GLabel("");
	private int n;

	// constructor
	public GHeart(int numTries) {
		n = numTries; // number of tries

		// points of heart shaped polygon
		pg.addVertex(0, 0);
		pg.addVertex(2.5, -5);
		pg.addVertex(7.5, -5);
		pg.addVertex(10, 0);
		pg.addVertex(12.5, -5);
		pg.addVertex(17.5, -5);
		pg.addVertex(20, 0);
		pg.addVertex(10, 10);
		pg.setFilled(true);
		pg.setFillColor(Color.RED);

		add(pg);

		// label,which indicates the number of tries left
		lifeNum = new GLabel(" " + n);
		lifeNum.setFont(new Font("Serif", Font.BOLD, 20));
		lifeNum.setColor(Color.RED);
		add(lifeNum, 21, 10);
	}

}
