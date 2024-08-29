package model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Test sur la création d'un hexagone
 */
public class Test {

	public static void main(String[] args) 
	{
		JFrame mw = new JFrame("Hexagone");
		mw.setSize(500,500);
		mw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mw.add(new Hexagone(100,100,10,10,50,Color.BLUE));
		mw.setLocationRelativeTo(null); //Centrer la fenêtre
		mw.setVisible(true);
	}

}
