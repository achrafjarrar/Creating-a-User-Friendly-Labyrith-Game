package interfaceGraphique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
/**
 * 
 * Ce bouton est couplé à un autre bouton "Quick mode". Quand on clique sur "End quick mode" , L'effet de "Quick mode" s'arrête.
 *
 */
public class EndQuickMode extends JButton implements ActionListener {
	/**
	 * @param mw : la fenêtre principale dans laquelle se fait l'affichage.
	 */
	MyWindow mw;
	public EndQuickMode(MyWindow mw)
    {
	  super("End quick mode");
	  this.setEnabled(false);
	  this.mw = mw;
	  this.setMnemonic(KeyEvent.VK_E);
	  addActionListener(this);
	  
    }
	/**
	 * Quand on clique sur ce bouton on met un terme à "Quick mode".
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		mw.getMazeModel().endQuickMode();
	}
	/**
	 * La mise à jour consiste à donner à l'utilisateur la possibilité de cliquer sur ce bouton uniquement 
	 * quand on a déjà cliqué sur le bouton "Quick mode".
	 * Naturellement , on ne peut cliquer sur ce bouton que lorsqu'on a déjà sélectionné "Quick mode".
	 */
	public void notifyForUpdate()
	{
		this.setEnabled(mw.getMazeModel().getQuickMode());
	}

}
