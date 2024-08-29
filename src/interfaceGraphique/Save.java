package interfaceGraphique;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
/**
 * 
 * Bouton qui permet d'enregistrer le labyrinthe après avoir sélectionné un fichier sur lequel se fait l'enregistrement par 
 * le bouton "Save as".
 *
 */
public class Save extends JButton implements ActionListener
{ /**
  * mw est la fenêtre sur laquelle agit ce bouton. C'est la fenêtre principale.
  */
  MyWindow mw;
  public Save(MyWindow mw)
  {   super("Save");
	  this.mw = mw;
	  this.setMnemonic(KeyEvent.VK_S);
	  this.setEnabled(false);//On ne peut cliquer sur ce bouton que lorsqu'on a sélectionné un fichier sur lequel se fait l'enregistrement.
	  addActionListener(this);
  }
  /**
   * Quand getFileCreated() == true , on a sélectionné un fichier dans lequel on va faire l'enregistrement.
   */
  public void notifyForUpdate() 
  {
	  this.setEnabled(mw.getMazeModel().getFileCreated());
  }
  /**
   * L'action de ce bouton est de demander au labyrinthe d'enregistrer le labyrinthe.
   */
  public void actionPerformed(ActionEvent e) 
  {
	  String ch = mw.getMazeModel().getFileName();
	  try
	  {
		  mw.getMazeModel().saveMaze(ch);
	  }
	  catch(Exception ex)
	  {
		  ex.printStackTrace();
	  }
	  
  }
}
