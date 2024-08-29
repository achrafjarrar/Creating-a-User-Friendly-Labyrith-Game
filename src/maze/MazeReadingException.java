package maze;
/**
 * 
 * Classe qui permet de définir une exception à la lecture d'un fichier pour initialiser un labyrinthe créé.
 *
 */
public class MazeReadingException extends Exception 
{
	/**
	 * 
	 * @param fileName : nom du fichier dans lequel on avait une exception.
	 * @param line : le numéro de ligne dans lequel on avait l'exception. Elle est égale à 0 quand on a 2 points de départs ou d'arrivé.
	 * @param message : le message d'erreur.
	 */
public MazeReadingException (String fileName , int line , String message) 
{
	super("Exception à la ligne "+line + " du fichier " + fileName + ": " + message);
}

}
