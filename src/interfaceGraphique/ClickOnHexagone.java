package interfaceGraphique;
import java.awt.Color;
import javax.swing.JLabel;
/**
 * 
 * Afficher un message écrit pour l'utilisateur indiquant qu'il faut cliquer sur un hexagone pour changer son label.
 *
 */
public class ClickOnHexagone extends JLabel{
	/**
	 * @param mw est la fenêtre dans laquelle se fait l'affichage de ce bouton. C'est la fenêtre principale.
	 */
	MyWindow mw;
	public ClickOnHexagone(MyWindow mw)
	{   
		super("Click on an hexagone to change its label !");
		this.setEnabled(false);
		this.mw = mw;
	}
	/**
	 * La mise à jour de cet élément consiste à devenir plus clair , une fois l'utilisateur crée le labyrinthe.
	 */
	public void notifyForUpdate()
	{
		this.setEnabled(mw.getMazeModel().getInit());
	}
	

}
