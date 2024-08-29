package interfaceGraphique;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
/**
 * Un panel qui est vert quand on a une solution après avoir cliqué sur le bouton "Solve"
 *  et rouge quand il n y'a pas de solution ou qu'on n'a pas cherché une solution en cliquant sur le bouton"Solve".
 */
public class SolvedPanel extends JPanel 
{  /**
   * mw est la fenêtre principale sur laquelle est affiché ce panel.
   */
	private MyWindow mw;
	public SolvedPanel(MyWindow mw ) 
	{   this.mw = mw;
	    this.setPreferredSize(new Dimension(30,15));
		this.setBackground(Color.RED);// Par défaut on ne demande pas de résoudre le labyrinthe.
		
	}
	/**
	 * La mise-à-jour est la suivante :
	 * Quand il existe une solution , on a dans le modèle un attribut qui s'appelle "green" qui va être égale à "true" quand on 
	 * a cliqué sur le bouton "Solve" et qu'il existe une solution exhibée par des segments entre les deux hexagones de départ et
	 * d'arrivé.
	 *
	 */
	public void notifyForUpdate() 
	{   
		if (mw.getMazeModel().getGreen() == true)
		{
		this.setBackground(Color.GREEN);
		}
	    else 
	    {
		 this.setBackground(Color.RED);
	    }
     }
}

