package maze;

import java.awt.Color;
import model.Hexagone;
import model.*;
/**
 * Classe qui définit un sommet vide dans le labyrinthe. Il s'agit d'une sous_classe de classe MazeBox :
 * Créer un 'EmptyBox' défini par ses coordonnés dans le labyrinthe et son label.
 * Renvoyer un booléen pour dire si le box est un "mur" ou pas (méthode getWall()).
 * Obtenir l'hexagone équivalent à ce box (utile pour l'interface graphique plus tard)(méthode getEquivalentHexagone(int x , int y).)
 */
public final class EmptyBox extends MazeBox {
	/**
	 * Les coordonnées dans le labyrinthe ( i = numéro de ligne et j = numéro de colonne).
	 * 
   */
	private int i;
	private int j;
	public EmptyBox(int i , int j) 
	{
		super(i,j,"E");
		this.i = i;
		this.j=j;
	}
	/**
	 * Ce Box n'est pas un Wall
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
       return new Hexagone(x,y,i,j,50,Color.blue);
    }

}
