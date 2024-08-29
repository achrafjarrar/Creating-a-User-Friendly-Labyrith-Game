package interfaceGraphique;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import maze.ArrivalBox;
import maze.DepartureBox;
import maze.EmptyBox;
import maze.MazeReadingException;
import maze.WallBox;
/**
 * Ce bouton permet de charger un labyrinthe à partir d'un fichier texte et de faire surgir un message d'erreur si 
 *on envisage un problème de lecture. 
 *
 */
public class Load extends JButton implements ActionListener
{ /**
  *
  * mw est la fenêtre principale dans laquelle se fait l'affichage de ce bouton et sur laquelle ce bouton va agir.
  */
  private MyWindow mw; 
  public Load(MyWindow mw)
  {
  super("Load");
  this.setMnemonic(KeyEvent.VK_L);
  this.mw = mw;
  addActionListener(this);
  this.setEnabled(true);//On peut cliquer sur ce bouton à tout instant.
  }
  /**
   * L'action de ce bouton consiste à afficher tous les dossiers et les fichiers sur le PC et de permettre à l'utilisateur
   * de sélectionner le fichier qu'il veut.
   */
  public void actionPerformed(ActionEvent e) 
  {   
	  String ch = null;
	  FileDialog fd = new FileDialog(mw,"Ouvrir",FileDialog.LOAD);
	  fd.setDirectory("..");
	  fd.setVisible(true);
	  String test = fd.getDirectory();
	  ch = fd.getDirectory()+fd.getFile();
	 if (test == null)
	  { 
		 return;// on arrête le processus quand on clique sur le bouton annuler
	  }
	  try (   
			  FileReader fr = new FileReader(ch);// l'utilisation de BufferedReader me semble plus simple 
				BufferedReader br = new BufferedReader(fr);
				)
		{  int i = 0 ;/*Compteur qui , une fois supérieur ou égale à 1 , on compare la longeur de la ligne 0 et chaque autre ligne.
		si une fois on a s != line.length() , on a un erreur de type 'les lignes n'ont pas un nombre d'hexagones fixe'*/
		   int c = 0;
		   int s = 0;
		   int cptA = 0;
		   int cptD = 0;
			int lineNumber = 0 ; //on va comparer cette valeur avec le nombre le lignes de notre Maze
			while(br.ready() == true ) //Tant qu'on a un caractère à lire
			{ 
			  String line = br.readLine();
			  if (i>=1) 
			  {
				  if(s!= line.length())
				  {
					  throw new Exception();
				  }
			  }
			  if(i==0)
			  {
			  c = line.length();//initialisée une seule fois , pour l'enregistrer et l'utiliser pour change le nombre de colonnes.
			  s = line.length();//contient la longueur de la première ligne et va être comparée à toutes les autres lignes.
			  i = i+1;
			  }
			  
			  for (int k = 0 ; k < line.length() ; k++) 
			  { 
				 if (((line.charAt(k)!='A')&&(line.charAt(k)!='W'))&&((line.charAt(k)!='D')&&(line.charAt(k)!='E')))
				 {
					throw new Exception(); 
				 }
				 if (line.charAt(k) == 'A')
					 {
							 cptA = cptA + 1;
					 }
				 if (line.charAt(k)=='D')
				 {
					 cptD = cptD +1;
				 }
				 if ((cptA > 1))//Si on a plus qu'un seul hexagone d'arrivé => Erreur.
				 {
					 throw new AException();
				 }
				 if (cptD > 1) //Si on a plus qu'un seul hexagone de départ => Erreur.
				 {
					 throw new DException();
				 }  
			  }
			  lineNumber = lineNumber+1;
			}
			if ((lineNumber == 0) && (c == 0)) //Dans le cas d'un fichier vide.
			{
				JOptionPane.showMessageDialog(mw.getWindowPanel(), "Your file is empty.","ERROR",JOptionPane.ERROR_MESSAGE); 
				  return;
			}
			mw.getMazeModel().setLineNumber(lineNumber);//Changer le nombre de lignes du labyrinthe.
			mw.getMazeModel().setColumnNumber(c);//Changer le nombre de colonnes du labyrinthe.
		}
	  catch(FileNotFoundException ex)
	  {
		  JOptionPane.showMessageDialog(mw.getWindowPanel(), "File not found.","ERROR",JOptionPane.ERROR_MESSAGE); 
		  return;
	  }
	  catch(AException ex)
	  {
		  JOptionPane.showMessageDialog(mw.getWindowPanel(), "This file has more than one arrival hexagone.","ERROR",JOptionPane.ERROR_MESSAGE); 
		  return;
	  }
	  catch(DException ex)
	  {
		  JOptionPane.showMessageDialog(mw.getWindowPanel(), "This file has more than one departure hexagone.","ERROR",JOptionPane.ERROR_MESSAGE); 
		  return;
	  }
	  catch (Exception ex)
	  {
		  JOptionPane.showMessageDialog(mw.getWindowPanel(),"Error , please respect the two following  criterias : "
					+ "Each line has the same number of hexagones. The characters allowed are 'E','A','D' and 'W' ","ERROR",JOptionPane.ERROR_MESSAGE);
		  return;
	  }
	  mw.getMazeModel().setloadFilePath(ch);// On change le chemin du fichier à charger.
	  mw.getMazeModel().setLoading(true);//On déclare au modèle qu'on va faire un loading.
	  mw.getMazeModel().setInitialised(true);//On déclare au modèle que le labyrinthe est initialisé. 
  }
  /**
   * Ce bouton ne nécessite aucune mise à jour , j'ai mis cette fonction pour ne pas perdre la généralité de la possibilité de mise à jour
   * dans les éléments de ButtonsPanel.
   */
  public void notifyForUpdate()
  {
	  
  }
}
