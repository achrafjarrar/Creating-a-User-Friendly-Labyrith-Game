package maze;
import graph.Graph;
import graph.Distance;
import graph.Vertex;
import interfaceGraphique.MyWindow;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * Le labyrinthe qui va être codé sous forme d'un tableau de tableaux car c'est plus facile et pratique pour 
 * représenter les matrices de cette manière :
 * Renvoyer la liste des sommets dans ce labyrinthe (Méthode : getAllVertexes() ).
 * Changer un MazeBox dans le labyrinthe( Méthode : setOrdonne(int i, int j , MazeBox m)).
 * Déterminer les coordonnés d'un MazeBox dans le labyrinthe (Méthode : coordonneOf(MazeBox m)).
 * Avoir le MazeBox situé à des coordonnés précisés(Méthode : get(int i , int j)).
 * Changer le label d'un MazeBox situé à des coordonnés précisés(Méthode : setLabel(int i , int j , String newLabel)).
 * Avoir les successeurs d'un sommet dans le labyrinthe (Méthode : getSuccessors(Vertex v )).
 * Avoir la distance entre deux sommets (Méthode : getDistance(Vertex v1 , Vertex v2 )).
 * Initialiser le labyrinthe créé à l'aide d'un fichier texte ( Méthode : initFromTextFile(String fileName) throws Exception , MazeReadingException , FileNotFoundException)).
 * Enregistrer le labyrinthe après la résolution dans un fichier texte ( Méthode : saveToTextFile(String fileName) throws Exception , MazeReadingException).
 * Avoir la case de départ ( Méthode : getDepartureBox() throws Exception ).
 * Avoir la case d'arrivé ( Méthode : getArrivalBox() throws Exception).
 */
public class Maze implements Graph , Distance 
{ MyWindow mw;
  private boolean b = false; //Un booléen qui indique si on a problème lors de l'initialisation du labyrinthe par texte.
 private MazeBox[][] maze; 
 /**
  * On crée le labyrinthe,initialement vide ,par un tableau de tableau de MazeBox.
  * Le nombre de ligne et de colonne vont être imposés lors de l'initialisation.
  */
 public Maze (MazeBox[][] maze)
 {
	 this.maze = maze;
	 
 }
 /**
  * Renvoyer la liste des Vertex.
  * Dans le cas d'un labyrinthe null on renvoie null.
  */
 public ArrayList<Vertex> getAllVertexes() {
	 if (maze ==null) {return null;}
	 int ligneLength = maze.length;
	 int colonneLength = maze[0].length;
	 ArrayList<Vertex> l = new ArrayList();
	 for (int i = 0  ; i<ligneLength ; i++) 
	 {
		 for (int j = 0 ; j < colonneLength ; j++)
		 {
			 l.add(maze[i][j]);
		 }
	 }
	 return l;
 }
 /**
  * Remplacer le MazeBox sur la ième ligne et jème colonne par m.
  */
 public final void setOrdonne(int i, int j , MazeBox m) 
 {
	 maze[i][j] = m;
 }
 /**
  * Determiner les coordonnées du mazeBox m , s'il n'existe pas dans le labyrinthe on renvoit null.
  * @return un tableau de dimension 2 contenant respectivement l'abcisse et l'ordonnée de m dans le labyrinthe.
  */
 public final int[] coordonneOf(MazeBox m)
 {  
	 int[] coor = new int[2];
	 for (int i = 0 ; i<maze.length ; i=i+1)
	 {
		 for (int j = 0 ; j<maze[0].length;j=j+1)
		 {
			 if (maze[i][j] == m) 
			 {
				 coor[0] = i;
				 coor[1] = j;
				 return coor;
						
			 }
		 }
	 }
	 return null;
 }
 /**
  * Accéder au MazeBox de coordonnées i et j.
  * @param i l'abcisse.
  * @param j l'ordonnée.
  */
 public MazeBox get(int i , int j)
 {
	 return maze[i][j];
 }
 /**
  * Modifier le Label du MazeBox à la ième ligne et jème colonne.
  * @param newLabel le nouveau label.
  */
 public final void setLabel(int i , int j , String newLabel)
 {
	 maze[i][j].setLabel(newLabel);
 }
 /**
  * Avoir les successeur du vertex v.
  * Ca dépend de la parité de la ligne dans laquelle on est.
  * 
  */
 public ArrayList<Vertex> getSuccessors(Vertex v )
 {
	 ArrayList<Vertex> l = new ArrayList();
	 MazeBox box = (MazeBox) v;
	int i = box.getAbscisse();
	int j = box.getOrdonnee();
	/*
	 * Ca va dependre de la parité de la ligne dans laquelle on est (Je me suis basé sur le graphe donné en tp07 en vérifiant les cas)
	 * , mais on doit dans les deux cas traiter les MazeBox 
	 * à droite et à gauche.
	 * La structure de try-catch est utile car dans le cas d'indexOutOfBounds on peut continuer à traiter les autres 
	 * voisins sans arrêter le programme.
	 */
	  try {
		  if (maze[i][j+1].getWall() == false ) //On essaie le voisin à gauche en vérifiant si il est bloquant ou pas
		  {
			  l.add(maze[i][j+1]);
		  }
	  }
		  catch(Exception ex) {} // Si on est dans la colonne la plus à droite on aura une exception ( IndexOutofBounds )
	  try {
		  if (maze[i][j-1].getWall() == false) // On essaie le voisin à droite
		  {
			  l.add(maze[i][j-1]);
		  }
	  }
	  catch (Exception ex) {}
	  /*
	   * Si on est dans une ligne impaire
	   */
	  if (i%2 == 1) 
	  {
	  try {
		  if (maze[i-1][j].getWall() == false ) // On essaie le voisin en haut à gauche
		  {
			  l.add(maze[i-1][j]);
		  }}
		catch (Exception ex) {};
	  try {
		  if (maze[i-1][j+1].getWall() == false ) // On essaie le voisin en haut à droite
		  {
			  l.add(maze[i-1][j+1]);
	      }
          }
	  catch (Exception ex) {};
	  try {
			if(maze[i+1][j].getWall()==false) // On essaie le voisin en bas à gauche
	  {
		  l.add(maze[i+1][j]);
		  
	  }
}
	  catch(Exception ex) {};
	  try {
		  if (maze[i+1][j+1].getWall() == false) // On essaie le voisin en bas à droite
		  {
			  l.add(maze[i+1][j+1]);
		  }
	  }
	  catch(Exception ex) {};  	
}
	  /*
	   * Si la ligne est paire
	   */
	  if (i%2 == 0) {
		  try 
		  {
		  if (maze[i-1][j-1].getWall() == false ) // On essaie le voisin en haut à gauche
		  {
			  l.add(maze[i-1][j-1]);
		  }
		  }
		catch (Exception ex) {};
	  try {
		  if (maze[i-1][j].getWall() == false ) // On essaie le voisin en haut à droite
		  {
			  l.add(maze[i-1][j]);
	      }
          }
	  catch (Exception ex) {};
	  try {
			if(maze[i+1][j].getWall()==false) //On essaie le voisin en bas à droite
	  {
		  l.add(maze[i+1][j]);
		  
	  }
}
	  catch(Exception ex) {};
	  try {
		  if (maze[i+1][j-1].getWall() == false)// On essaie le voisin en bas à gauche
		  {
			  l.add(maze[i+1][j-1]);
		  }
	  }
	  catch(Exception ex) {};
		  
	  }

   return l;
   } 
 /**
  * Avoir la distance de v1 à v2
  * Dans le cas ou il existe un arc de v1 à v2 on renvoit 1 sinon on renvoit +Oo.
  */
public int getDistance(Vertex v1 , Vertex v2 ) 
{
	ArrayList<Vertex> l = getSuccessors(v1);
	for (Vertex v : l ) 
	{
		if (v == v2 )
		{
			return 1;
		}
	}
	return Integer.MAX_VALUE;
	
}
/**
 * Initialiser le labyrinthe à partir d'un fichier text.
 * @param fileName est le nom du fichier à partir duquel on veut faire l'initialisation.
 * Les erreurs qui peuvent surgir sont : 
 * Non existence du fichier.
 * Le nombre de lignes ou de colonnes ne correspond pas à ceux du labyrinthe vide non encore initialisé.
 * Existence de deux sommets d'arrivés ou de départ.
 * Existence d'un caractère non permis qui n'est pas dans la liste suivante { "A","D","E","W"}.
 */
public final void initFromTextFile(String fileName) throws Exception , MazeReadingException , FileNotFoundException
 
{ 
	//boolean b = false;//Un booléen qui sert à nous indiquer s'il y a une erreur dans la lecture du fichier.
	int cptA = 0; // Sert à compter le nombre de "Arrival Box". 
	int cptD =0 ; // Sert à compter le nombre de "Departure Box".
try (FileReader fr = new FileReader(fileName);// L'utilisation de BufferedReader me semble plus simple.
		BufferedReader br = new BufferedReader(fr);
		)
{ 
	int lineNumber = 0 ; //on va comparer cette valeur avec le nombre le lignes de notre Maze.
	while(br.ready() == true ) //Tant qu'on a un caractère à lire
	{
	  String line = br.readLine();
	  lineNumber = lineNumber+1;
	  if ((line.length()) != (maze[0].length))
	  { b= true;
	    throw new MazeReadingException(fileName, lineNumber,"No coherence between the number of columns expected and the number of boxes in this line " + lineNumber) ;
	  }
	  /*
	   * Pour chaque ligne on va parcourir lettre par lettre en allant de gauche à droite et on attend 
	   * à ce qu'on ait A ou D ou W ou E . 
	   * Si on n'est pas dans les 4 cas cités , on a une lettre qui n'est pas cohérente avec le principe du labyrinthe => Exception
	   */
	  for (int k = 0 ; k < line.length() ; k++) 
	  {
		  switch (line.charAt(k)) 
		  {
		  case 'A' : maze[lineNumber-1][k] = new ArrivalBox(lineNumber-1,k);cptA = cptA +1; break;
		  case 'D' : maze[lineNumber-1][k] = new DepartureBox(lineNumber-1,k);cptD = cptD + 1;break;
		  case 'W' : maze[lineNumber-1][k] = new WallBox(lineNumber-1,k);break;
		  case 'E' : maze[lineNumber-1][k] = new EmptyBox(lineNumber-1,k);break;
		  default  : b = true; throw new MazeReadingException(fileName, lineNumber, "The character in line  " + lineNumber +" , in column " + (k+1) +" isn't one of the expected 4 possible characters");
		  }
		  
	  }
	}
	if ((cptA>1)||(cptD>1))
	{   b=true;
		throw new MazeReadingException(fileName,0,"There can't be more than one arrival and departure box!");
	}
	if ((lineNumber) != (maze.length) )
	{
		b=true;
		throw new MazeReadingException(fileName , lineNumber , "Line number is not correct");
	}
}
 catch(FileNotFoundException ex) 
{   ex.printStackTrace();
    b =true;
	 
 }
catch(MazeReadingException ex)
{
	b=true;
ex.printStackTrace();
}
// La fermeture  de br et fr est automatique
}
/**
 * Enregistrer le labyrinthe dans un fichier texte.
 * @param fileName est le nom du fichier dans lequel on veut faire l'enregistrement.
 * Si on a une solution , chaque case vide définissant le plus court chemin
 *  et les cases d'arrivé et de départ vont être remplacés par des points 
 *  sinon on écrit sur le fichier "Pas de solution".
 */
public final void saveToTextFile(String fileName) throws Exception , MazeReadingException
{
	
	PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
	try {
		for (int i = 0 ; i < maze.length ; i++)
	{
		for (int j = 0 ; j < maze[0].length; j++)
		{
			pw.print(maze[i][j].getLabel());//on écrit une ligne à la fin de la 2ème boucle for 
		}
		pw.println();// retour à la ligne
		
		
	}
	
}
catch (Exception ex ) {ex.printStackTrace();}
finally {
	if ( (pw) != (null))
			{
		try {pw.close();} catch(Exception ex) {};
	}
	}
}
/**
 * Renvoyer le case de départ. Ceci se fait grâce au label.
 * Dans le cas d'un labyrinthe vide on renvoit null.
 */
public MazeBox getDepartureBox() throws Exception
{if (maze == null) { return null ;};
	for (int i = 0 ; i<maze.length ; i++) 
	{
		for (int j = 0 ; j < maze[0].length; j++) 
		{  if( maze[i][j] == null) {continue;}
			if (maze[i][j].getLabel() == "D")
				{
				return maze[i][j];
				}
		}
	}
	b=true;
	throw new Exception("Pas de case de départ.");
}
/**
 * Renvoyer la case d'arrivé.Ceci se fait grâce au label
 * Dans le cas d'un labyrinthe null on renvoit null.
 */
public MazeBox getArrivalBox() throws Exception
{   if (maze == null) { return null ;};
	for (int i = 0 ; i<maze.length ; i++) 
	{
		for (int j = 0 ; j < maze[0].length; j++) 
		{   if( maze[i][j] == null) {continue;}
			if (maze[i][j].getLabel() == "A")
				{
				return maze[i][j];
				}
		}
	}
	b=true;
throw new Exception("Pas de case d'arrivé.");
}
/**
 * Renvoyer la valeur du booléen b.
 * @return b : booléen qui indique si on a un problème lors de la création de maze par l'initialisation par texte.
 */
public boolean getError() 
{
	return b;
}
}
