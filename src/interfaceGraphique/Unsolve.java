package interfaceGraphique;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
/**
 * Ce bouton permet d'enlever le chemin de la solution sans changer la nature des hexagones.
 */
public class Unsolve extends JButton implements ActionListener
{  private MyWindow mw;
   /*
    * Tant qu'on n'a pas resolu le labyrinthe  , on n'a pas besoin d'activer le bouton unsolve (c'est à dire qu'on n'a pas 
    * la possiblité de cliquer dessus , à priori)
    */
   public Unsolve(MyWindow mw) 
   {
	   super("Unsolve");
	   this.setMnemonic(KeyEvent.VK_U);
	   this.setEnabled(false);
	   this.mw = mw;
	   addActionListener(this);
   }
   /**
    * La mise-à-jour est la suivante :
    * Si on a le "SolvedPanel" vert , on a donc une solution , on peut donc enlever la solution.
    */
   public void notifyForUpdate()
   { 
	  setEnabled(mw.getMazeModel().getGreen());
   }
   /**
    * Quand on clique sur le bouton , On remet le SolvedPanel rouge (car pas de solution) et on demande au modèle d'enlever la
    * solution existante (Enlever les segments).
    */
@Override
public void actionPerformed(ActionEvent e)
{   mw.getMazeModel().setGreen(false);
	mw.getMazeModel().setSolve(false);
}
}
