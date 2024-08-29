package interfaceGraphique;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import maze.MazeReadingException;
/**
 * MazePanel est le panel qui contiendra le labyrinthe ( l'ensemble des hexagones ) tracé.
 */
public class MazePanel extends JPanel {
	private MyWindow mw;
	/**
	 * On ajoute un HexagoneMouseListener qui permet de sélectionner l'hexagone sur lequel on clique.
	 */
	public MazePanel(MyWindow mw)
	{
		this.setBackground(Color.WHITE);
		this.mw = mw;
		this.addMouseListener(new HexagoneMouseListener(mw));	
	}
	/**
	 * On ajoute un fond d'écran à l'aide d'image dans la structure try-catch.
	 * On demande au modèle de commencer de traçer le labyrinthe.
	 */
	protected void paintComponent(Graphics graphics) 
	{
		super.paintComponent(graphics);
		try {
			
			{
				graphics.drawImage(ImageIO.read(new File("image/images.png")),350,0,mw);
			
			}
			}
			catch (IOException ex)
			{
				
			}
		
			/*
			 * Si on crée le labyrinthe par le bouton "Load" on crée la liste des hexagones chargés avec paintWithLoad puis 
			 * on demande de tout tracer avec paintMaze. Sinon,on trace directement le labyrinthe ( le cas où on clique 
			 * sur le bouton "Create new maze" ou qu'on clique sur un hexagone pour changer son label.
			 * Les éxceptions sont traités par les messages d'erreurs qui se manifestent à l'utilisateur et ceux quand on clique sur 
			 * le bouton "Load" donc pas besoin de les expliciter ici. Par contre la structure try-catch m'était obligatoire.
			 */
		try {
		if(mw.getMazeModel().getLoading() == true)
		{
		mw.getMazeModel().paintWithLoad(graphics);
        mw.getMazeModel().paintMaze(graphics);
		}
		else
		{
			mw.getMazeModel().paintMaze(graphics);
		}
		}
		catch (MazeReadingException fex)
		{   
		  
		}
		catch(Exception ex)
		{
		
		}	
	}
	/**
	 * Dans le cas d'une mise à jour on retrace tout.
	 */
	public void notifyForUpdate() 
	{
		repaint();
	}
	

}
