package model;
import java.awt.*;
import java.awt.geom.*;
/**
 * Classe qui modélise un segment.
 */
public class Segment extends Line2D.Float {
	private Color color;
	public Segment(Color color, float x1,float y1 , float x2, float y2)
	{
		super(x1,y1,x2,y2);
		this.color = color;
	}
	private final static BasicStroke largeStroke ;
	static { //l'épaisseur du segment
		   largeStroke  = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER) ;
		}
	/**
	 * Tracer le segment entre deux centres d'hexagones successifs dans le plus court chemin.
	 */
	public final void paint (Graphics g)
	{
		Graphics2D gp = (Graphics2D) g;
		gp.setStroke(largeStroke);
		gp.setColor(color);
		gp.draw(this);
	}
}
