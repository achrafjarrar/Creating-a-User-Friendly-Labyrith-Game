package interfaceGraphique;
/**
 * Quand on a deux hexagones de départ dans un fichier à charger, on jette cette exception dans la structure try-catch.
 * Si on capte cette exception , on fait surgir un message d'erreur.
 */
public class DException extends Exception
{
  public DException()
  {
	  super();
  }
}
