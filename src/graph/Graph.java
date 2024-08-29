package graph;
import java.util.ArrayList;
/**
 * Interface du graph qui permet de renvoyer la liste de tous les sommets , des successeurs d'un vertex
 * et de la distance entre deux vertexs quelconques.
 */
public interface Graph {
	public ArrayList<Vertex> getAllVertexes();//renvoyer la liste de tous les sommets
	public ArrayList<Vertex> getSuccessors(Vertex v );//renvoyer la liste des successeurs de v
	public int getDistance(Vertex v1, Vertex v2);// renvoyer le poids de deux arrêtes
	

}
