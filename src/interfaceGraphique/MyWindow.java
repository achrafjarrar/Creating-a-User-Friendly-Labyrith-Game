package interfaceGraphique;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.*;
/**
 * Création de la fenêtre en lui ajoutant le panel principal : WindowPanel wp
 * Dès que la fenêtre apparaît , on demande à l'utilisateur s'il veut un petit tutoriel sur l'utilisation de l'application.
 * Ceci se fait à travers une demande s'il le veut ou pas.
 * S'il le veut on fait une succession de messages indiquant comment utiliser l'application.
 * Sinon on lui demande juste de cliquer sur fullscreen et de ne pas dépasser 50 lignes et 50 colonnes.
 * 
 */
public class MyWindow extends JFrame implements ChangeListener {
	private WindowPanel wp;
	private MazeModel mm = new MazeModel();
	public MyWindow() {
		super("Welcome to the Maze ");
		this.setSize(1400,770);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	   this.wp = new WindowPanel(this);
		this.setContentPane(wp);
		 Object[] possibilites = { "NO","YES" };
		 String initialSelection = "NO";
		 Object selection = JOptionPane.showInputDialog(null, "Do you want quick notes on how to use the application ? It is highly recommended to read these notes if it's the first time you use it.",
				"Please select YES or NO ", JOptionPane.QUESTION_MESSAGE,null, possibilites, initialSelection);
		
		if(selection == "YES")
			{JOptionPane.showMessageDialog(this, "I will explain briefly how you can use this Maze : First click on 'Create new Maze' or on 'Load'  ");
		JOptionPane.showMessageDialog(this, "If you clicked on 'Create new Maze', you can choose as many hexagones as you want , but bear in mind if you put way too "
				+ "many , the application would be laggy.");
		JOptionPane.showMessageDialog(this, "It is recommended that you don't surpass 50 lines and 50 columns.");
				
		JOptionPane.showMessageDialog(this, "After creating the maze , the hexagones are all empty(Blue) . To turn one into Wall(Dark Grey) ,"
				+ " Arrival(Green) or Departure(Green) Hexagone , "
				+ "click on it and choose your label ! ");
		JOptionPane.showMessageDialog(this, "Or you can click on 'Quick mode' to change the hexagones label faster.");
		JOptionPane.showMessageDialog(this, "Quick mode allows you to choose 'Wall' or 'Empty' label. Once you choose it, just click on the hexagones that you want to change their label to the chosen one!");
		JOptionPane.showMessageDialog(this, "Arrival and Departure hexagones can be chosen simply by clicking on an hexagone and choosing the label 'A' or 'D'.There can't be more than one for each of them ,that's why you don't need 'Quick mode' for them.");
		JOptionPane.showMessageDialog(this, "When you finish editing with 'Quick mode' , click on 'End quick mode' to get back to normal mode!");
		JOptionPane.showMessageDialog(this, "If you clicked on 'Load', choose your file. Don't forget that we don't accept "
				+ " a maze with two or more arrival or departure hexagones, and that each line must have the same number of hexagones! ");

		JOptionPane.showMessageDialog(this, "Once you have your start and arrival Hexagone , you click on the 'Solve' button. ");
		JOptionPane.showMessageDialog(this, "If the right rectangle turns to green it means there is a solution and it is displayed with a path from the start Hexagone to the Arrival Hexagone . If it's still red it means there is no solution.");
		JOptionPane.showMessageDialog(this, "You also have a 'Unsolve' button to take off the path if you ever want to preserve the same maze and only change labels!");
		JOptionPane.showMessageDialog(this, "To save your maze in a new file click on 'Save as' , and choose where to put your file");
		JOptionPane.showMessageDialog(this, "Finally, please put full screen! Enjoy ! ");
			}
		if (selection == null) {return ;}
		if ( selection == "NO")
		{   JOptionPane.showMessageDialog(this, "Remmember , if you put way too many Hexagones, the application will eventually become laggy."
				+ " Please try not to surpass 50 lines and 50 columns.");
		JOptionPane.showMessageDialog(this, "Finally, please put full screen! Enjoy ! ");
					
		}
		mm.addObserver(this);
		this.setVisible(true);
	}
	/**
	 * 
	 * @return renvoyer le modèle de notre labyrinthe.
	 */
	public MazeModel getMazeModel() 
	{
		return mm;
	}
	/**
	 * 
	 * @return renvoyer le "WindowPanel".
	 */
	public WindowPanel getWindowPanel()
	{
		return wp;
	}
	/**
	 * Si le modèle informe la fenêtre d'une modification , on informe le windowPanel qu'il y a une mise à jour.
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		wp.notifyForUpdate();
	}
}
