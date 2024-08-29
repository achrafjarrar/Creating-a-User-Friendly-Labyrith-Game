package maze;
import java.io.FileNotFoundException;

import graph.Dijkstra;
import graph.Graph;
import graph.ShortestPaths;
import graph.ShortestPathsImp;
import graph.Vertex;
/**
 * Tester  la  création et l'intitialisation d'un labyrinthe.
 * Ensuit tester la résolution du problème de plus court chemin et l'affichage de ce dernier dans un fichier texte.
 */
public class MainTestWithFiles {

	public static void main(String[] args) throws MazeReadingException,Exception
	{
	/*
	*Il faut à chaque fois modifier le nombre de ligne et de colonne avant d'utiliser le fichier. (Ligne 17)
	*/
		Maze m = new Maze(new MazeBox[10][10]); //Ici on saisit la taille de la maze qui est à modifier à chaque fois qu'on change le fichier.
		m.initFromTextFile("data/labyrinthe.maze");/*J'ai essayé avec un labyrinthe correctement écrit*/
		if (m.getError() ==  false )//Si on peut faire la résolution ( Donc pas de problème d'initialisation)
		{
		Dijkstra dij = new Dijkstra();
		MazeBox d = m.getDepartureBox();
		MazeBox a = m.getArrivalBox();
		ShortestPaths sp = dij.dijkstra(m, (DepartureBox) d, (ArrivalBox) a);
		if (sp != null) {sp.showShortestPathTo((Vertex)a,m,"data/solution_de_labyrinthe.maze",sp);};
		System.out.println("Succes"); // Si on réussit à trouver une solution ou pas ( on n'est pas dans une boucle infinie).
	}}
	
}
