package interfaceGraphique;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import maze.*;
/**
 * 
 * Le bouton qui permet d'enregistrer le labyrinthe dans un nouveau fichier dans le PC.
 *
 */
public class SaveAs extends JButton implements ActionListener
{/**
 *mw est la fenêtre sur laquelle agit ce bouton. C'est la fenêtre principale.
 */
private MyWindow mw;
public SaveAs(MyWindow mw) 
{   super("Save as");
    this.setMnemonic(KeyEvent.VK_S);
	this.mw = mw;
	this.setEnabled(false);
	addActionListener(this);
}
/**
 * La mise à jour consister à donner à l'utilisateur la possibilité de cliquer sur ce bouton une fois le labyrinthe est initialisé.
 */
public void notifyForUpdate() 
{
	this.setEnabled(mw.getMazeModel().getInit());
}
/**
 * L'action de ce bouton consiste à permettre à l'utilisateur de choisir le nom et l'emplacement du nouveau fichier dans lequel
 * il veut enregistrer le labyrinthe.
 */
public void actionPerformed(ActionEvent e)
{String ch = null;
FileDialog fd = new FileDialog(mw,"Nouveau",FileDialog.SAVE);
fd.setDirectory("..");
fd.setVisible(true);
ch = fd.getDirectory()+fd.getFile();
	   if (ch!= null)
	   {
		   mw.getMazeModel().setFileCreatedTo(true);
	   }
	   try 
	   {  
	mw.getMazeModel().saveMaze(ch);//Enregistrer le chemin vers le fichier dans le modèle.
	   }
	   catch (MazeReadingException ex)
	   {
		   ex.printStackTrace();
	   }
	   catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
}
}
