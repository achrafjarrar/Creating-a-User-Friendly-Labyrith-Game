package interfaceGraphique;
import java.awt.Dimension;
import java.awt.FlowLayout;
/**
 * L'ensemble des boutons et des panels qui sont : 
 * Solve sv => permet de resoudre le problème du plus court chemin.
 * NewMazeButton nwb => permet de créer une nouvelle labyrinthe en précisante le nombre de lignes et de colonnes.
 * SolvedPanel sp => Quand il est vert alors il existe une solution sinon s'il est rouge alors il n'en existe pas.
 * Unsolve us => Quand on a une solution et on veut garder les mêmes hexagones juste en enlevant le chemin.
 * ClickOnHexagone => Message pour rappeler l'utilisateur de cliquer sur un hexagone pour changer le label.
 * Save => bouton pour enregistrer le labyrinthe après l'avoir mis sur un fichier.
 * Save As => bouton pour enregistrer le labyrinthe dans un fichier.
 * Load l => bouton pour charger un labyrinthe.
 * QuickMode qm => bouton pour mettre des hexagones 'Wall' ou 'Empty' rapidement en en seul click au lieu de choisir un par un.
 * EndQuickMode eqm => bouton pour désactiver Quick mode
 */
import javax.swing.*;
public class ButtonsPanel extends JPanel {
	private Solve sv;
	private NewMazeButton nwb;
	private SolvedPanel sp;
	private ClickOnHexagone coh;
	private Unsolve us;
	private SaveAs sa;
	private Save s;
	private Load l;
	private QuickMode qm;
	private EndQuickMode eqm;
	public ButtonsPanel(MyWindow mw) 
	{   this.setLayout(new FlowLayout(FlowLayout.CENTER));
	    this.sp = new SolvedPanel(mw);
		this.sv = new Solve(mw);
		this.nwb = new NewMazeButton(mw);
		this.us = new Unsolve(mw);
		this.sa = new SaveAs(mw);
		this.s = new Save(mw);
		this.l = new Load(mw);
		this.coh = new ClickOnHexagone(mw);
		this.qm = new QuickMode(mw);
		this.eqm = new EndQuickMode(mw);
		this.add(sa);
		this.add(s);
		this.add(l);
		this.add(us);
		this.add(sv);
		this.add(qm);
		this.add(eqm);
		this.add(nwb);
		this.add(sp);
		this.add(coh);
		
	}
	/**
	 * Dans le cas d'une mise à jour , on notifie tous les boutons.
	 */
	public void notifyForUpdate() 
	{   qm.notifyForUpdate();
	    eqm.notifyForUpdate();
		sv.notifyForUpdate();
		nwb.notifyForUpdate();
		sp.notifyForUpdate();
		us.notifyForUpdate();
		sa.notifyForUpdate();
		s.notifyForUpdate();
		l.notifyForUpdate();
		coh.notifyForUpdate();
	}
	

}
