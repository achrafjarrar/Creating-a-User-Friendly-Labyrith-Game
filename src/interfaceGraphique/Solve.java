package interfaceGraphique;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
/**
 * Ce bouton , une fois cliqué , demande au modèle de resoudre le problème du plus court chemin dans le labyrinthe.
 */
public class Solve extends JButton implements ActionListener{
	/**
	 * mw est la fenêtre sur laquelle ce bouton agit.C'est la fenêtre principale.
	 */
	private MyWindow mw;
	public Solve(MyWindow mw ) 
	{
		super("Solve");
		 this.setMnemonic(KeyEvent.VK_S);
		this.mw = mw;
		setEnabled(false);//
		addActionListener(this);
	}
	/** La mise à jour est la suivante :
	 * Le bouton est activé quand le labyrinthe est initialisé , A ( Arrival hexagone) est choisi ainsi que
	 * D (Departure hexagone) est choisi.
	 */
	public void notifyForUpdate() 
	{
		setEnabled(mw.getMazeModel().getInit()&&(mw.getMazeModel().getAIsSelected())&&(mw.getMazeModel().getDIsSelected()));
	}
	/**
	 * Quand on clique sur le bouton on demande au modèle de resoudre le labyrinthe.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		mw.getMazeModel().setSolve(true);
	}
}
