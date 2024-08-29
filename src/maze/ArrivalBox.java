package maze;

import java.awt.Color;

import model.Hexagone;
/**
 * Classe qui définit le sommet d'arrivé dans le labyrinthe. Il s'agit d'une sous_classe de classe MazeBox :
 * Créer un 'ArrivalBox' défini par ses coordonnés dans le labyrinthe et son label.
 * Renvoyer un booléen pour dire si le box est un "mur" ou pas.
 * Obtenir l'hexagone équivalent à ce box ( utile pour l'interface graphique plus tard).
 */
public final class ArrivalBox extends MazeBox
{  
	/**
	 * Les coordonnées dans le labyrinthe ( i = numéro de ligne et j = numéro de colonne).
	 * 
   */
	private int i;
	private int j;
	public ArrivalBox (int i , int j ) 
	{
		super(i,j,"A");
		this.j=j;
		this.i=i;
	}
	/**
	 * Ce Box n'est pas un mur.
	 */
	public boolean getWall() 
	{
		return false;
	}
	/**
	 * Renvoyer l'hexagone équivalent avec x et j sont les coordonnées de son centre dans 'MazePanel'.
	 */
	@Override
    public Hexagone getEquivalentHexagone(int x, int y)    	
    {
		Hexagone hex = new Hexagone(x,y,i,j,50,Color.GREEN);
		hex.setLabel("A");
		return hex;
    }
}
