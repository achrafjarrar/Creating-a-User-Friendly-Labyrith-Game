package graph;
/**
 * Modifier et renvoyer la plus petite distance entre un sommet et le sommet de départ
 */
public interface MinDistance {
	public void setMindistance(Vertex v1 , int distance);//Definir la plus petite distance de v1 au sommet de départ
	public int getMindistance(Vertex v1);//Accéder à la plus petite distance de v1 au sommet de départ
	

}
