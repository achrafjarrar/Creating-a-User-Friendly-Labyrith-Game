package graph;
/**
 * Renvoyer le poids de d'un arc défini par ses extrémités
 */
public interface Distance {
	
    public int getDistance(Vertex v1, Vertex v2);//Accéder au poids de l'arc (v1,v2)
}
