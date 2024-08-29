package graph;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import maze.Maze;
import maze.MazeBox;
import maze.MazeReadingException;
/**
 * 
 * Classe qui fait l'extension de HashMap<Vertex,Vertex> et implémente l'interface ShortestPaths:
 * Créer un HashMap vide qu'on va remplir au fur et à mesure.
 * Associer à un vertex donné son prédécesseur en le mettant dans le HashMap.
 * Renovoyer la liste des sommets qui définissent le plus court chemin vers un sommet donné.(Liste de prédecesseurs en commençant par ce sommet).
 * Enregistrer ce plus court chemin dans un fichier texte en remplaçant les 'Empty boxes' par des '.'
 *
 */
public class ShortestPathsImp extends HashMap<Vertex,Vertex> implements ShortestPaths  
{
	/**
	 * Créer HashMap vide.
	 */
public ShortestPathsImp() 
	{
		super();
	}
/**
 * Mettre  (v1,v2) dans le hashmap avec v2 le prédecesseur de v1 (qui est la clé).
 * @param v1 : sommet en entré.
 * @param v2 : prédécesseur de v1.
 */
public final void setPrevious(Vertex v1, Vertex v2) 
{
	this.put(v1, v2);
}
/**
 * Avoir le plus court chemin pour arriver à v en revenant en arrière à partir de v.
 * @param v : le sommet d'arrivé.
 * @return : la liste des sommets de plus court chemin.
 */
public final ArrayList<Vertex> getShortestPath(Vertex v) 
{
	Vertex v1 = v;// v1 va contenir à chaque fois un prédecesseur en commençant par v puis son prédecesseur puis le prédecesseur de ce dernier etc..
	ArrayList<Vertex> l = new ArrayList();// Liste qui va contenir les sommets du plus court chemin
	while(v1 != null )
	{
		l.add(v1);
		v1 = this.get(v1);
		
	}
	return l;
}
/**
 * Méthode pour afficher la solution dans un fichier fileName :
 * S'il existe une soltuion on l'affiche à partir d'un chemin formé par des points '.' entre le sommet d'arrivé et de départ.
 * Sinon , on écrit explicitement sur le fichier fileName "Pas de solution".
 * @param v : Le sommet qui va être considéré comme le sommet d'arrivé.
 * @param m : Le labyrinthe dans laquelle on va chercher le plus court chemin.
 * @param fileName : Le nom du fichier dans lequel on va afficher le plus court chemin s'il existe.
 * @param sp : objet qui permet d'accèder à la liste des sommets formant le plus court chemin.
 */
public final void showShortestPathTo(Vertex v , Maze m, String fileName,ShortestPaths sp)  throws Exception , MazeReadingException
{ // On a deux cas : ou bien on a le plus court chemin ou bien non.
	ArrayList<Vertex> l = sp.getShortestPath(v);//On prend les sommets formant le plus court chemin à l'aide de sp
	if (l.contains((Vertex)m.getDepartureBox())) { //Si on a le sommet de départ à partir de l'arrivé donc il existe une solution.
	for (Vertex m1 : l )
	{
		MazeBox m2 = (MazeBox) m1;
		int i = m2.getAbscisse();
		int j = m2.getOrdonnee();
		m.setLabel(i, j, "."); //On change le label d'un sommet qui appartient au chemin par "."
    }
	m.saveToTextFile(fileName);
	}
	else { // Si on n'arrive pas au sommet de départ en revenant en arrière c'est qu'il n'y a pas de solution.
		FileWriter fos = null;
		PrintWriter pw = null;
		try {
			fos = new FileWriter(fileName);
			pw = new PrintWriter(fos);
			pw.print("Pas de solution");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try{fos.close();} catch (Exception ex) {};
			try{pw.close();} catch (Exception ex) {};
		}
	}
}
}

