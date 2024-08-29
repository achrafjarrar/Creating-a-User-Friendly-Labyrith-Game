package interfaceGraphique;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.*;
/**
 * Le WindowPanel renferme ButtonsPanel et le MazePanel.
 * On a la possibilité d'aller en haut , en bas , à gauche et à droite à l'aide des "ScrollPanes", et ce dans le cas où on a 
 * beaucoup d'hexagones par ligne et/ou par colonne.
 */
public class WindowPanel extends JPanel 
{   private MyWindow mw;
	private ButtonsPanel bp;
	private MazePanel mp;
	/*
	 * Les boucles assurent l'existence des scrollpanes. ajouter des espaces (le string s) qui dépassent horizentalement et 
	 * verticalement la zone initiale permet au scrollPanes d'aller en bas  et d'aller à droite ( ou revenir en haut et d'aller à gauche)
	 */
	public WindowPanel(MyWindow mw) 
	{
		this.setLayout(new BorderLayout());
		mp = new MazePanel(mw);
		bp = new ButtonsPanel(mw);
		this.mw = mw;
		mp.setLayout(new GridLayout(1500,1500));
		String s = "  ";
		for (int j = 0 ; j< 3000; j++) 
		{
			s = s +"  ";
	    }
		for(int i=0;i<1500;i++)
		{
			mp.add(new JLabel(s));
		}
		JScrollPane scrollPane = new JScrollPane(mp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane);
		this.add(bp,BorderLayout.NORTH);
	}
	/**
	 * La mise-à-jour est la suivante :
	 * Demander la mise-à-jour au niveau des boutons et du "MazePanel".
	 */
	public void notifyForUpdate() 
	{   bp.notifyForUpdate();
		mp.notifyForUpdate();
	}
	/**
	 * Renvoyer MazePanel.
	 * @return mp : le panel contenant le labyrinthe.
	 */
	public MazePanel getMazePanel()
	{
		return mp;
	}

}
