package model;
import java.awt.*;

import javax.swing.JPanel;
import interfaceGraphique.*;
import maze.*;

import java.util.*;
/**
 * Un hexagone est caractérisé par la coté du cercle dans lequel il est inscrit : cote.
 * L'abscisse et ordonnée du centre : adc et odc.
 * Le numéro de ligne de l'hexagone dans le labyrinthe.
 * Le numéro de colonne de l'hexagone dans le labyrinthe.
 * Sa couleur.
 * Son Label qui est par defaut "E" (pour Empty).
 */
public class Hexagone extends JPanel  {
	public final static double pi = Math.PI;
	private int adc; // Abscisse du centre
	private int odc;//Ordonnée du centre
	private int cote ;//coté de l'hexagone
	private int i ; //numéro de l'hexagone de la maze (abscisse)
	private int j; //numéro de l'hexagone de la maze (ordonnée)
	private Color color; // Couleur de l'hexagone
	private String label = "E"; // De base toutes les cases sont passantes
	private MyWindow mw; // La fenêtre dans la quelle se fera l'affichage
	
	
	public Hexagone(int x,int y,int i,int j,int cote, Color color ) 
	{   this.i = i;
	    this.j = j;
		this.adc = x;
		this.odc = y;
		this.color = color;
		this.cote = cote;
	}
	/**
	 * @return label : label de l'hexagone.
	 */
	public String getLabel() {return label;}
	/**
	 * @return cote : la coté de l'hexagone.
	 */
	public int getCote() {return cote;}
	/**
	 * @return adc : abcisse du centre.
	 */
	public int getX() {return adc;}
	/**
	 * @return odc : ordonné du centre.
	 */
	public int getY() {return odc;}
	/**
	 * @return i : numéro de ligne du labyrinthe dans lequel on a cet hexagone.
	 */
	public int getI() {return i;}
	/**
	 * @return j : numéro de colonne du labyrinthe dans lequel on a cet hexagone.
	 */
	public int getJ() {return j;}
	/**
	 * @return color : couleur de l'hexagone.
	 */
	public Color getColor() {return color;}
	/**
	 * Changer la couleur de l'hexagone.
	 * @param color : la nouvelle couleur de l'hexagone.
	 */
	public void setColor(Color color) 
	{
		this.color = color;
	}
	/**
	 * Changer le label de l'hexagone.
	 * @param ch : le nouveau label de l'hexagone.
	 */
	public void setLabel(String ch ) 
	{
		label = ch;
	}
	/**
	 * Créer la liste des points de l'hexagone.
	 * @return l : la liste des points de l'hexagone.
	 */
	public ArrayList<Point> listeDePoint() 
	{
		ArrayList<Point> l = new ArrayList<Point>();
		for (int k = 1 ; k<7 ; k = k+1 ) 
		{   
			double x_k = adc + cote * Math.sin(2*k*pi / 6); //abcisse du point k
		    double y_k = odc + cote * Math.cos(2*k*pi/6); // ordonnée du point k
		    Point p = new Point((int)x_k , (int)y_k);
		    l.add(p);
		}
		return l;
	}
	/**
	 * Renvoyer l'hexagone à partir de la liste de ses points.
	 * @return p : l'hexagone considéré.
	 */
	public Polygon getHexagon() 
	{
		Polygon p = new Polygon();
		for (Point pt : listeDePoint())
		{
			p.addPoint((int)pt.getX(), (int) pt.getY());
		}
		return p;
	}
	/**
	 * Pour chaque Hexagone , on a un MazeBox qui lui est équivalent , c'est ce que renvoie cette fonction.
	 * Ca va dépendre de la nature de l'hexagone.
	 * @return : MazeBox équivalent à cet Hexagone.
	 */
	public MazeBox getEquivalentMazeBox()
		{
			if (label == "E") 
			{
				MazeBox mb = new EmptyBox(i,j);
				return mb;
			}
			if (label == "W")
			{
				return (MazeBox) new WallBox(i,j);
			}
			if (label == "D")
			{
				return (MazeBox) new DepartureBox(i,j);
			}
			else
			{
				return (MazeBox) new ArrivalBox(i,j);
			}
	}
	
	private final static BasicStroke largeStroke ;
	static { //l'épaisseur des segments
		   largeStroke  = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER) ;
		}
	/**
	 * Tracer l'hexagone.
	 * @param g : Objet de type Graphics qui permet de traçer l'hexagone.
	 */
	public void paintComponent(Graphics g) 
	{ 
	super.paintComponent(g);
	Graphics2D gp = (Graphics2D) g;
	gp.setStroke(largeStroke);
	Polygon p = getHexagon();
	/*
	 * Puisque dans mon modèle un hexagone est vide ( et donc bleu) par défaut , je dois traiter manuellement les autres cas.
	 * Il y a sûrement d'autres manières plus pratiques comme faire des sous_classes d'une classe hexagone mais à ce stade je n'ai pas
	 * envie de modifier le modèle car çela peut induire des problèmes. 
	 */
	if ((((label == "D") || (label == "A")))&&(color != Color.RED))
	{
		gp.setColor(color.GREEN);
	}
	else if ((((label == "D") || (label == "A")))&&(color == Color.RED))
	{
		gp.setColor(color.RED);
	}
	else if ((((label == "W") ))&&(color != Color.RED))
	{
		gp.setColor(color.DARK_GRAY);
	}
	else if (((label == "W") )&&(color == Color.RED))
	{
		gp.setColor(Color.RED);
	}
	else
	{
		gp.setColor(color);
	}
	gp.fillPolygon(p); // Remplir l'hexagone avec sa couleur.
	gp.setColor(Color.BLACK);
	gp.drawPolygon(p); //Tracer les bords.
	gp.setColor(Color.WHITE);
	gp.drawString(label, adc - 3, odc+3);/* Mettre le label sur l'hexagone et le label on 
	le décale de 3 à droite et de 3 à gauche pour qu'il se trace au milieu exactement.*/
	}
	
	

}