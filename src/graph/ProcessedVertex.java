package graph;
/**
 * Interface pour les vertexs traités 
 */
public interface ProcessedVertex {
	public void addVertex (Vertex v); //Ajouter un nouveau sommet à processedVertex
	public boolean verify(Vertex v); //Vérifie si un sommet existe dans processedVertex

}
