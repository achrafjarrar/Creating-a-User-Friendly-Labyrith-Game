package maze;
import graph.Vertex;
import model.*;
/**
 * 
 * Classe mère pour définir ensuite les sous_classes correpondants au différents types des "MazeBox" implémentant l'interface Vertex:
 * Definir un 'Box' avec son label  et coordonnées.
 * Renvoyer l'abcisse de ce Box dans le labyrinthe.
 * Renvoyer l'ordonnée de ce Box dans le labyrinthe.
 * Renvoyer le label de ce 'Box'.
 * Changer le label du sommet à nouveaux label(Pour effectuer l'affichage sur les fichiers textes).
 * 
 * 
 */
public abstract class MazeBox implements Vertex {
	/**
	 * @param i est l'abscisse de ce MazeBox dans le graphe ( ou dans le labyrinthe d'une manière équivalente).
	 * @param j est l'ordonnée de ce MazeBox dans le graphe ( ou dans le labyrinthe d'une manière équivalente).
	 * @param label peut être "D" pour "Departure" , "A" pour "Arrival" , "E" pour "Empty" , "W" pour "Wall".
	 */
	//private boolean wall;// Oui si la case est bloquante et non sinon
	private int i;//l'abscisse dans le graphe
	private int j;//l'ordonnée dans le graphe
	private String label ;//son label ("D" pour départ , "A" pour arrivé .. )
	public MazeBox(int i , int j , String label) {
		this.i = i;
		this.j = j;
		this.label = label ;
	}
	/**
	 * 
	 * Dire si cette case est bloquante ou non.
	 */
	public abstract boolean getWall();
	/**
	 * 
	 * Renvoyer l'abcisse du MazeBox dans le labyrinthe.
	 */
	public final int getAbscisse () 
	{
		return i;
	}
	/**
	 * Renvoyer l'ordonnée de ce 'MazeBox' dans le labyrinthe.
	 * 
	 */
	public final int getOrdonnee()
	{
		return j;
	}
	/**
	 * Renvoyer le label de ce 'MazeBox'.
	 */
	public final String getLabel()
	{
		return label;
	}
	/**
	 *  Changer le Labels du sommet(Ou du 'MazeBox' d'une manière équivalente).
	 *  Particulièrement pour les sommets  de plus court chemin
	 *  qui vont devenir des points dans maze pour pouvoir effectuer l'affichage.
	 *  @param newLabel est une chaîne de caractère qui définit le nouveau label.
    */
	public final void setLabel(String newLabel) 
	{
		this.label = newLabel;
	}
	/**
	 * Renvoie l'hexagone équivalent de ce 'MazeBox'
	 * x est un entier qui définit l'abcisse de l'hexagone dans le labyrinthe.
	 * y est un entier qui définit l'ordonnée de l'hexagone dans le labyrinthe.
	 */
	public abstract Hexagone getEquivalentHexagone(int x,int y);
}
