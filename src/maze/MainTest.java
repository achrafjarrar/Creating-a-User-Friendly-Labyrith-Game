package maze;
/**
 * Essayer d'initialiser et de sauvgarder un labyrinthe.
 */
public class MainTest {

	public static void main(String[] args) throws Exception , MazeReadingException
	{
		Maze maze = new Maze(new MazeBox[10][10]);
		maze.initFromTextFile("data/labyrinthe.maze");
		maze.saveToTextFile("data/labyrinthe2.maze");
		
	}

}
