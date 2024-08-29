package graph;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
/**
 * Renvoyer le plus court chemin d'un graphe .
 * Le codage initial avec les interfaces est dans le commentaire.
 */
public class Dijkstra {
	public Dijkstra()
	{
		
	}
	//Codage avec interfaces dans le javadoc suivant:
	/*public ArrayList<Vertex> dijkstra(Graph graph , ShortestPaths shortestPaths , MinDistance minDistance , ProcessedVertex processedVertexes , Distance distance , Vertex startVertex , Vertex endVertex) {
		processedVertexes.addVertex(startVertex);
		Vertex pivotVertex = startVertex;
		minDistance.setMindistance(pivotVertex , 0);
		for (Vertex v : graph.getAllVertexes()) 
		{
			if (v != pivotVertex) 
			{minDistance.setMindistance(v, Integer.MAX_VALUE);
			}
		}
		while(processedVertexes.verify(endVertex) == false ) 
		{
		  for (Vertex v : graph.getSuccessors(pivotVertex)) 
		  {
			  if (minDistance.getMindistance(v) > distance.getDistance(pivotVertex,v) + minDistance.getMindistance(pivotVertex)) 
			  {
			  minDistance.setMindistance(v, distance.getDistance(pivotVertex, v) + minDistance.getMindistance(pivotVertex));
			  shortestPaths.setPrevious(v,pivotVertex);
			  
			  }
		  }
		  int min = Integer.MAX_VALUE;
		  Vertex newPivot = startVertex;
		  for(Vertex v : graph.getAllVertexes())
		  { if ((processedVertexes.verify(v) == false) && (minDistance.getMindistance(v) < min)) 
		  {
			  min = minDistance.getMindistance(v);
			  newPivot = v;
		  }  
		  }
		  processedVertexes.addVertex(newPivot);
		  pivotVertex = newPivot;
		 
		}
		return shortestPaths.shortestPath(endVertex);
	}*/
	/**
	 * Implémentation de la méthode du recherche de plus court chemin.
	 * @param graph : le graphe qui contient les sommets.
	 * @param startVertex : le sommet de départ.
	 * @param endVertex : le sommet d'arrivé.
	 * @return sPI : Objet qui permet d'accéder aux sommets définissants le plus court chemin.
	 */
	public  ShortestPaths dijkstra(Graph graph , Vertex startVertex, Vertex endVertex)
	{
		ProcessedVertexImp pVI = new ProcessedVertexImp();
		MinDistanceImpl mDI = new MinDistanceImpl();
		ShortestPathsImp sPI = new ShortestPathsImp();
		pVI.addVertex(startVertex);
		Vertex pivotVertex = startVertex;
		mDI.setMindistance(pivotVertex, 0);
		if(graph.getAllVertexes() == null ) {return null;}; // si on a graphe null , on ne procède pas à la recherche du chemin.
		for (Vertex v : graph.getAllVertexes()) 
		{
			if (v != pivotVertex) 
			{mDI.setMindistance(v, Integer.MAX_VALUE);
			}
		}
		int i = 0; /*si i = n on sort de la boucle while et on sait que le plus court
		chemin n'existe pas*/
		int nbreDeSommets = graph.getAllVertexes().size();
		while ((pVI.verify(endVertex) == false ) && (i< nbreDeSommets - 1))
		{ i = i+1;
		  for (Vertex v : graph.getSuccessors(pivotVertex)) 
		  {
			  if (mDI.getMindistance(v) > graph.getDistance(pivotVertex,v) + mDI.getMindistance(pivotVertex)) 
			  {
			  mDI.setMindistance(v, graph.getDistance(pivotVertex, v) + mDI.getMindistance(pivotVertex));
			  sPI.setPrevious(v,pivotVertex);
			  
			  }
	       }
		  int min = Integer.MAX_VALUE;
		  Vertex newPivot = startVertex;
		  for(Vertex v : graph.getAllVertexes())
		  { if ((pVI.verify(v) == false) && (mDI.getMindistance(v) < min)) 
		  {
			  min = mDI.getMindistance(v);
			  newPivot = v;
		  }  
		  }
		  
		  pVI.addVertex(newPivot);
		  pivotVertex = newPivot;
	}
		  return sPI;	
}}

