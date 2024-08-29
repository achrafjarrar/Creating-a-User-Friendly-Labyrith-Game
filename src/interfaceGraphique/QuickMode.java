package interfaceGraphique;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * Bouton qui permet de saisir des cases vides ou murs d'une manière rapide, juste en choisissant la nature de la case qu'on veut 
 * mettre une fois qu'on clique sur un hexagone.
 * Ceci impose ou bien la case "Wall" ou bien "Empty". les hexagones "Arrival" et "Departure" ne nécessitent pas ce mode car
 * on ne peut mettre qu'un seul pour chacun , donc il suffit de cliquer sur un hexagone d'une manière classique et de choisir le label correspondant.
 * 
 *
 */
public class QuickMode extends JButton implements ActionListener {
	/**
	 * mw est la fenêtre principale sur laquelle ce bouton agit.
	 */
	private MyWindow mw;
	public QuickMode(MyWindow mw)
	{
		super("Quick mode");
		this.mw = mw;
		this.setEnabled(false);
		this.setMnemonic(KeyEvent.VK_Q);
		addActionListener(this);
		
	}
	/**
	 * La mise à jour consiste à , une fois le labyrinthe est initialisé , donner à l'utilisateur
	 *  la possibilité de faire le 'Quick mode'.
	 */
	public void notifyForUpdate()
	{
		this.setEnabled(mw.getMazeModel().getInit());
	}
	/**
	 * Pour le 'Quick mode' , on peut choisir des hexagones "Empty" ou bien "Wall"  : 
	 * Pas besoin de mettre la possibilité de choisir des hexagones "Arrival" ou "Departure" car on ne peut mettre qu'un seul 
	 * il suffit de cliquer sur un hexagone et de choisir son label "A" ou "D".
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		 Object[] possibilites = { "Wall(W)","Empty(E)" };
		 String initialSelection = "Wall(W)";
		 Object selection = JOptionPane.showInputDialog(null, "Please select the label you want to put on the hexagones just by clicking on them",
				"Quick Mode", JOptionPane.QUESTION_MESSAGE,null, possibilites, initialSelection);
		 if (selection == "Wall(W)")
			 {
			   mw.getMazeModel().setQuickModeLabel('W');//les hexagones cliqués auront leurs couleurs changés en noir et leurs labels "W".
			 }
		 else if(selection =="Empty(E)")
		 {
			 mw.getMazeModel().setQuickModeLabel('E');////les hexagones cliqués auront leurs couleurs changés en bleu et leurs labels "E".
		 }
		 else//si on clique sur annuler
		 {
			 return;
		 }
		mw.getMazeModel().setQuickMode(true);//indiquer au modèle qu'on a activé "Quick mode".
	}
	

}
