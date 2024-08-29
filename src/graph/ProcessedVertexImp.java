package graph;
import java.util.HashSet;
/**
 * 
 * Classe qui implémente l'interface ProcessedVertex et qui fait l'extension de HashSet<Vertex>
 * Créér un ensemble vide de sommets
 * Ajouter un nouveau sommet à cet ensemble
 * vérifier si un sommet existe dans cet ensemble ou pas
 *
 */
public class ProcessedVertexImp extends HashSet<Vertex> implements ProcessedVertex 
{ 
/**
 * Construire l'ensemble des sommets (initialisé à l'ensemble vide )
 */
public ProcessedVertexImp()
{
	super();
}
/**
 * Ajouter un nouveau sommet.
 * @param v : le nouveau sommet ajouté.
 */
public final void addVertex(Vertex v )
{
	this.add(v);
}
/**
 * Vérifier si un sommet existe ou pas dans l'ensemble.
 * @param v : le sommet à cherché dans l'ensemble.
 */
public final boolean verify(Vertex v ) 
{
	return this.contains(v);
}
}
