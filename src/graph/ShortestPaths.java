package graph;
import java.util.ArrayList;
import maze.Maze;
import maze.MazeReadingException;
/**
 * 
 * Interface du plus court chemin pour:
 *  Renvoyer la liste des prédecesseurs définissant le plus court chemin en commençant avec le endVertex avec la méthode getShortestPath(Vertex endVertex )
 *  Modifier le prédécesseur d'un sommet avec la méthode setPrevious(Vertex v1, Vertex v2).
 *  Enregistrer la solution du plus court chemin dans un fichier texte en l'exhibant avec des points entre le sommet de départ et d'arrivé
 *
 */
public interface ShortestPaths {
	public ArrayList<Vertex> getShortestPath(Vertex endVertex );//Renvoie la liste des prédecesseurs définissant le plus court chemin au sommet v
	public void setPrevious(Vertex v1 , Vertex v2 ); // Modifie le prédecesseur du sommet v1 dans un plus court chemin en l'associant v2
	/*Enregistrer la solution du plus court chemin à v dans maze dans fileName à l'aide de sp qu'on va utiliser pour accéder à la liste des sommets de ce chemin
	 * (les exceptions existent car on va utiliser  la méthode de la classe Maze saveToTextFile qui exige ces deux exceptions)
	 */
	public void showShortestPathTo(Vertex v , Maze maze , String fileName , ShortestPaths sp) throws Exception , MazeReadingException;
	
	

}
