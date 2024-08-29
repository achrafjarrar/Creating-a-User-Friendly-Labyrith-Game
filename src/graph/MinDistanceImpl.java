package graph;
import java.util.HashMap;
/**
 * Implementation de l'interface MinDistance à l'aide de HashMap.
 */
public class MinDistanceImpl extends HashMap<Vertex,Integer> implements MinDistance
{
  /**
   * Créer HashMap vide qu'on va remplir au fur et à mesure.
   */
  public MinDistanceImpl()
  {
	  super();
  }
  /**
   * Associer la distance "distance"  entre le vertex de départ et le vertex v1 dans le HashMap.
   * @param v1 : le sommet considéré.
   * @param distance : La nouvelle distance entre le sommet v1 et le sommet de départ.
   */
@Override
public void setMindistance(Vertex v1, int distance) {
	
	this.put( v1 ,Integer.valueOf(distance));
}
/**
 * Renvoyer la distance de vertex v1 par rapport au vertex de départ.
 * @param v1 : le sommet considéré.
 */
@Override
public int getMindistance(Vertex v1) {
	
	 return this.get(v1);
}
}
