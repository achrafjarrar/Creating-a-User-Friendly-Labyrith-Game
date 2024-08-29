package model;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import interfaceGraphique.MyWindow;
/**
 * 
 * Quand on clique sur un point dans le MazePanel , on cherche l'hexagone le plus proche et on demande à l'utilisateur de choisir 
 * le label qu'il veut imposer sur l'hexagone cliqué.
 * Si on utilise "Quick mode" , on demande une seule fois la nature de l'hexagone et puis chaque hexagone cliqué par l'utilisateur
 * aura sa nature changé directement.
 *
 */
public class HexagoneMouseListener extends MouseAdapter 

{ /**
  * mw est la fenêtre principale sur laquelle agit ce "Listener".
  */
 private MyWindow mw;
 public HexagoneMouseListener(MyWindow mw )
 {
	 this.mw = mw;
 }
 /**
  * Choisir une seule fois le label et chaque hexagone cliqué ensuite aura sa nature changé en un seul clic.
  * @param clickedHexagone : l'hexagone cliqué.
  */
 public final void quickModeOn(Hexagone clickedHexagone)
 {
	 mw.getMazeModel().addSelectedHexagone(clickedHexagone);
	 for (Hexagone hex : mw.getMazeModel().getLoadedHexagones())
	 {
		 if ((hex.getI() != clickedHexagone.getI())||(hex.getJ()!=clickedHexagone.getJ()))
		 {
			mw.getMazeModel().addSelectedHexagone(hex); 
		 } 
	 }
	 mw.getMazeModel().setLoadedHexagones();
	 mw.getMazeModel().weClickedOnHexagone();
	 if(mw.getMazeModel().getLabelOf(clickedHexagone) == "D")
	 {
		 mw.getMazeModel(). setDIsSelected(false);
	 }
	 if (mw.getMazeModel().getLabelOf(clickedHexagone) == "A" )
	 {
	     mw.getMazeModel(). setAIsSelected(false);
	 }
	 if (mw.getMazeModel().getQuickModeLabel() =='E')
	 {
		 mw.getMazeModel().setLabelOf(clickedHexagone,"E");
	 }
	 else
	 {
		 mw.getMazeModel().setLabelOf(clickedHexagone,"W");
	 }
	  
 }
 /**
  * Quand on clique sur un hexagone , on veut changer son label , en cliquant sur lui , sa couleur devient rouge momentanément.
  * Puis après avoir choisi son label , sa couleur prend la couleur correspondante à son type.
  * On indique au modèle qu'on a cliqué sur un hexagone.
  * Si "Quick mode" est cliqué , on utilise la méthode ci-dessus.
  */
 public final void mouseClicked (MouseEvent e)
 { /*On reçoit l'hexagone cliqué*/
	 Hexagone clickedHexagone = mw.getMazeModel().getHexagoneClicked((int)e.getX(),(int) e.getY());
	 /*S'il est null c'est à dire qu'on a cliqué loin et pas sur un hexagon*/
	 if (clickedHexagone == null )
	 {
		 return ; // si on clique loin on ne séléction aucun Hexagone
	 }
	 /*
	  * Quand on clique sur le bouton 'Quick mode', on utilise la méthode quickModeOn ci-dessus.
	  */
	 if (mw.getMazeModel().getQuickMode())
	 {
		quickModeOn(clickedHexagone);
		return;
	 }
	 mw.getMazeModel().changeColorOf(clickedHexagone); //Changer la couleur de l'hexagone cliqué en rouge.
	 /* Choisir le type de l'hexagone (ou du mazeBox d'une manière équivalente)
	  * Si on clique sur annuler => (selection == null) on fait rien
	  */
		 Object[] possibilites = { "A","D","W","E" };
		 String initialSelection = "A";
		 Object selection = JOptionPane.showInputDialog(null, "Please select Box type",
				"Box type", JOptionPane.QUESTION_MESSAGE,null, possibilites, initialSelection);
		 if (selection == null ) 
		 {
			 return ;
		 }
		 /*
		  * Cet hexagone a subi un changement de label donc on le met dans la liste des hexagones sélectionnés.
		  */
		 mw.getMazeModel().addSelectedHexagone(clickedHexagone);
		 /*
		  * Les hexagones qu'on a initialisé avec le bouton "Load" doivent être pris en compte quand on clique sur un hexagone et 
		  * qu'on va faire un repaint().(Par défaut les hexagones non cliqués sont des hexagones vides ).
		  */
		 for (Hexagone hex : mw.getMazeModel().getLoadedHexagones())
		 {
			 if ((hex.getI() != clickedHexagone.getI())||(hex.getJ()!=clickedHexagone.getJ()))
			 {
				mw.getMazeModel().addSelectedHexagone(hex); 
			 } 
		 }
		 mw.getMazeModel().setLoadedHexagones();
		 /*
		  * Si le label est A on vérifie si on a déjà un hexagone dont le label est "A". Si c'est vrai , on change le label de
		  *  l'ancien hexagone ( il devient "E" ) car on ne peut avoir qu'un seul hexagone d'arrivé.
		  */
		 if (selection == "A")
		 {
			 /*
			  * If getAIsSelected()== true , alors on a déjà un hexagone dont le label est "A" et on doit donc le rendre un hexagone
			  * de label "E" (Empty hexagone)
			  */
			 if(mw.getMazeModel().getAIsSelected()) 
			 {
				 for (Hexagone hex : mw.getMazeModel().getSelectedHexagone())
				 {
					 if ((hex.getI()==mw.getMazeModel().getIa()) && (hex.getJ()==mw.getMazeModel().getJa()))
					 {
						 mw.getMazeModel().setLabelOf(hex,"E");
					 }
				 }
				 mw.getMazeModel().setIa(clickedHexagone.getI());
				 mw.getMazeModel().setJa(clickedHexagone.getJ());
			 }
			 /*
			  * Sinon , on déclare qu'on a sélectionné l'hexagone d'arrivé (ligne 67), et on fixe ses coordonnées dans le labyrinthe
			  */
			 else
			 {
				 mw.getMazeModel().setAIsSelected(true);
				 mw.getMazeModel().setIa(clickedHexagone.getI());
				 mw.getMazeModel().setJa(clickedHexagone.getJ());
			 }
		 }
		 /*
		  * C'est la même chose pour un hexagone de départ.
		  */
		 if (selection == "D")
		 {
			 if(mw.getMazeModel().getDIsSelected()) 
			 {
				 for (Hexagone hex : mw.getMazeModel().getSelectedHexagone())
				 {
					 if ((hex.getI()==mw.getMazeModel().getId()) && (hex.getJ()==mw.getMazeModel().getJd()))
					 {
						 mw.getMazeModel().setLabelOf(hex,"E");
					 }
				 }
				 mw.getMazeModel().setId(clickedHexagone.getI());
				 mw.getMazeModel().setJd(clickedHexagone.getJ());
			 }
			 else
			 {
				 mw.getMazeModel().setDIsSelected(true);
				 mw.getMazeModel().setId(clickedHexagone.getI());
				 mw.getMazeModel().setJd(clickedHexagone.getJ());
			 }
		 }
		 /*
		  * Si on clique sur un hexagone de départ et on change son label alors on n'a plus un hexagone de départ dans
		  * le labyrinthe donc on fait setDIsSelected(false).
		  */
		 if(mw.getMazeModel().getLabelOf(clickedHexagone) == "D" && selection != "D" )
		 {
			 mw.getMazeModel(). setDIsSelected(false);
		 }
		 /*
		  * La même chose pour un hexagone d'arrivé.
		  */
		 if (mw.getMazeModel().getLabelOf(clickedHexagone) == "A" && selection != "A")
		 {
		     mw.getMazeModel(). setAIsSelected(false);
		 }
		 /*
		  * Indiquer au modèle qu'on a cliqué sur un hexagone et qu'on changé son label.
		  */
		 mw.getMazeModel().weClickedOnHexagone();
		 /*
		  * Changer le label de l'hexagone cliqué.
		  */
		 mw.getMazeModel().setLabelOf(clickedHexagone,(String)selection);
 }
}
