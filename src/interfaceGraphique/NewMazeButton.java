package interfaceGraphique;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
/**
 * Le bouton pour créer un nouveau labyrinthe.
 * De base , le labyrinthe contient que des cases vides (Empty boxes) qu'il peut ensuite changer en cliquant sur chaque case
 * ou en utilisant "Quick mode".
 * On demande à l'utilisateur le nombre de lignes et de colonnes désiré.
 * Il faut qu'il mette des numéros et non pas des caractères spéciaux ou littéraires ce qui induit des messages d'erreur.
 * Une fois les deux nombres saisis , on informe le modèle d'initialiser le labyrinthe et de demander une mise à jour.
 */
public class NewMazeButton extends JButton implements ActionListener
{   /**
    *
    * mw est la fenêtre principale sur laquelle le bouton va agir.
    */
	private MyWindow mw ; 
	/* On ajoute un listener pour le bouton */
	public NewMazeButton (MyWindow mw ) 
	    {   
		super("Create new maze");
		this.setMnemonic(KeyEvent.VK_C);
		this.mw = mw;
		addActionListener(this);
		}
	/**
	 * Quand on clique sur le bouton on demande à l'utilisateur de saisir le nombre de lignes et de colonnes.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{  mw.getMazeModel().setLoading(false); // Pour dire qu'on ne va initialiser à partir d'un fichier existant
	   JOptionPane jop = new JOptionPane();
	   String n = jop.showInputDialog(null,"Set the number of lines",
			   "Maze's line number", JOptionPane.QUESTION_MESSAGE);
	   /*
	    * Si l'utilisateur clique directement sur Ok sans rien mettre , rien ne se passe
	    */
	   if (n==null){ return ; };
	   /*
	    * Si le nombre de ligne < 1 , on affiche un message d'erreur et on demande encore une fois de resaisir le nombre de ligne.
	    * Une fois bien saisi , si on a un caractère qui n'est pas un nombre , on catch une exception 
	    * et on affiche un erreur(ligne 50 , 51 et 52).
	    * Pareil pour le nombre de colonnes.
	    * Puis on modifie le modèle et on déclare le fait qu'il est intialisé.
	    */
	   try {
	   while((Integer.parseInt(n) < 1))
	   {   JOptionPane.showMessageDialog(mw.getWindowPanel(), "Line number must be higher than 1 ","ERROR",JOptionPane.ERROR_MESSAGE);
		   n = jop.showInputDialog(null,"Set the number of lines",
				   "Maze's line number", JOptionPane.QUESTION_MESSAGE);
		   if (n==null){ return ; };//On arrête la demande quand l'utilisateur clique sur "annuler".
	   }
	   int ligne = Integer.parseInt(n);
	   JOptionPane jop2 = new JOptionPane();
	   String c = jop2.showInputDialog(null,"Set the number of columns",
			   "Maze's column number",JOptionPane.QUESTION_MESSAGE);
	   /*
	    * Si l'utilisateur clique directement sur Ok rien ne se passe
	    */
	   if (c == null) { return ;}
	   while ( Integer.parseInt(c) < 1)
	   {  JOptionPane.showMessageDialog(mw.getWindowPanel(), "Column number must be higher than 1 ","ERROR",JOptionPane.ERROR_MESSAGE);
	    c = jop2.showInputDialog(null,"Set the number of columns","Maze's column number",JOptionPane.QUESTION_MESSAGE);
	    if (c == null) { return ;}//On arrête la demande quand l'utilisateur clique sur "annuler".
	   }
	   int colonne = Integer.parseInt(c);
	   mw.getMazeModel().setLineNumber(ligne);//Changer le nombre de lignes.
	   mw.getMazeModel().setColumnNumber(colonne);//Changer le nombre de colonnes.
	   mw.getMazeModel().setLoading(false); //On informe le modèle qu'on fait une création de labyrinthe sans cliquer sur le bouton "Load".
	   mw.getMazeModel().setLoadedHexagones();//On vide la liste des hexagones chargés , si précédemment il a chargé un labyrinthe.
	   mw.getMazeModel().setInitialised(true);// Prevenir le modele que le labyrinthe est initialisé
	   }
	   catch (Exception ex)
	   {
		   JOptionPane.showMessageDialog(mw.getWindowPanel(), "Line number and column number must be NUMBERS ! ","ERROR",JOptionPane.ERROR_MESSAGE);
	   }
	}
	/**
	   * Ce bouton ne nécessite aucune mise à jour , j'ai mis cette fonction pour ne pas perdre la généralité de la possibilité de mise à jour
	   * dans les éléments de ButtonsPanel.
	   */
	public void notifyForUpdate() 
	{
		
	}
	
	

}
