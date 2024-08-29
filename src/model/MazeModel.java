package model;
import java.awt.Color;
import interfaceGraphique.*;
import maze.*;

import java.awt.Graphics;
import java.awt.desktop.SystemEventListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import graph.Dijkstra;
import graph.ShortestPaths;
import graph.Vertex;
/**
 * Le modèle de notre labyrinthe. 
 * Il permet de : 
 * Tracer le labyrinthe.
 * Charger des hexagones d'un fichier. Ce sont les hexagones non vides qu'on va garder,car par défaut , tous les hexagones sont vides.
 * Changer le label d'un hexagone.
 * Enregistrer l'hexagone rouge(sur lequel on a cliqué pour faire la possibilité de changer sa nature).
 * Enregistrer le labyrinthe dans un fichier sur le PC.
 * Activer "Quick mode" ou le désactiver.
 * Faire la résolution de labyrinthe et afficher le résultat par des segements.
 * @author charc
 *
 */
public class MazeModel  {
	private List<ChangeListener> l = new ArrayList<ChangeListener>();//La liste des "Listeners"
	private int nl;//Nombre de lignes.
	private int cl; //Nombre de colonnes.
	private boolean  init = false ; //indique si le labyrinthe est initalisé ou pas.
	private boolean weClickedOnHexagone = false; /* Quand on clique sur un hexagone pour changer son label par exemple,
	on va faire repaint(), mais on doit préserver les changements effectué. Ce booléen nous permet de distinguer entre 
	Créer un nouveau labyrinthe pour la première fois , Faire un repaint() car on a cliqué sur un hexagone pour modifier son label et sa couleur*/
	private Hexagone coloredHexagone; /*Quand on clique sur un hexagone pour changer son label , sa couleur devient rouge, pour des raisons de focalisation,
	cet hexagone est ce qu'on appelle  coloredHexagone*/
	private List<Hexagone> createdHexagone  = new ArrayList<Hexagone>();//Liste des hexagones créés.
	private List<Hexagone> selectedHexagone = new ArrayList<Hexagone>();//Liste des hexagones selectionnés(ceux dont le label n'est pas "E" => Ceux qui ne sont pas des hexagones vides).
	private boolean dIsSelected;// un booléen nous indiquant si on a créé Departure box ou pas
	private boolean aIsSelected;// un booléen nous indiquant si on a créé Arrival box ou pas
	private int iA; // i de A 
	private int jA; // j de A
	private int iD; // i de D
	private int jD; // j de D
	private Maze maze ; // le labyrinthe qui va modéliser la résolution de notre labyrinthe d'hexagones
	private boolean solveIt = false; // Quand solveIt == true , on demande au modèle de resoudre le problème du plus court chemin.
	private boolean green = false; // green == true <=> il y a une solution dans le labyrinthe.
	private String fileName = null; // nom du fichier sur lequel on va faire les enregistrements.
	private String loadFilePath = null; //nom du fichier qu'on veut utiliser pour créer le labyrinthe
	private boolean fileCreated = false; // booléen qui nous indique si on a créé un fichier pour enregistrer le labyrinthe ou pas
	private boolean loading  = false; // booléen qui nous dit si on va utiliser un labyrinthe déjà existant ou pas
	private List<Hexagone> loadedHexagones = new ArrayList<Hexagone>();//Liste des hexagones créés par le bouton "Load"
	private boolean quickMode = false;//booléen qui nous indique si on est en ' Quick Mode' ou pas.
	private char quickModeLabel = '0';//caractère qui nous indique quel label est considéré en 'Quick Mode'('0' est la valeur par défaut).
	/**
	 * Indiquer aux "Listeners" qu'il y a une mise à jour.
	 */
	public void stateChanged() 
	{
		ChangeEvent evt = new ChangeEvent(this);
		for (ChangeListener listener : l) 
		{
			listener.stateChanged(evt);
		}
	}
	/**
	 * Ajouter un nouveau "Listener"
	 * @param listener : le "Listener" qu'on va ajouter à la liste l des "Listeners"
	 */
	public final void addObserver(ChangeListener listener) 
	{
		l.add(listener);
	}
	/**
     * Renvoyer solveIt.
     * @return : solveIt qui est un booléen qui indique si on veut résoudre le problème de plus court chemin ou pas.
     */
	public boolean getSolveIt()
	{
		return solveIt;
		
	}
	/**
	 * Demander au modèle de resoudre le problème de resoudre ou pas le problème de plus court chemin.
	 */
	public void setSolve(boolean b)
	{
		solveIt = b;
		stateChanged();
	}
	/**
	 * Renvoyer le nombre de lignes de notre labyrinthe.
	 */
	public int getLineNumber() 
	{
		return nl;
	}
	/**
	 * Renvoyer le nombre de colonnes de notre labyrinthe.
	 */
	public int getColumnNumber()
	{
		return cl;
	}
	/**
	 * Renvoyer init 
	 * @return init : un booléen qui indique si labyrinthe est initialisé ou pas. 
	 */
	public boolean getInit ()
	{
		return init;
	}
	/**
	 * Modifier le nombre de ligne
	 * @param n : le nouveau nombre de lignes.
	 */
	public void setLineNumber(int n)
	{
		nl = n;
	}
	/**
	 * Modifier le nombre de colonnes.
	 * @param c : le nouveau nombre de colonnes.
	 */
	public void setColumnNumber(int c) 
	{
		cl = c;
	}
	/**
	 * Renvoyer green.
	 * @return green : un booléen qui , quand il est vrai, indique qu'il existe une solution dans labyrinthe.
	 */
	public boolean getGreen()
	{
		return green;
	}
	/**
	 * Changer la valeur de green.
	 * @param b la nouvelle valeur de green.
	 */
	public void setGreen(boolean b)
	{
		green = b;
	}
	/**
	 * Modifier la valeur de loading.
	 * @param b la nouvelle valeur de loading
	 */
	public void setLoading(boolean b)
	{
		loading = b;
		
	}
	/**
	 * Renvoyer loading.
	 * @return loading : un booléen qui indique si on initialise le labyrinthe à l'aide du bouton "Load" ou pas.
	 */
	public boolean getLoading()
	{
		return loading;
	}
	/**
	 * Renvoyer le label d'un hexagone
	 * @param hex : L'hexagone considéré.
	 * @return : Une chaîne de caractère contenant le label de cet hexagone.
	 */
	public String getLabelOf(Hexagone hex)
	{
		return hex.getLabel();
	}
	/**
	 * Changer le chemin du fichier qu'on veut charger.
	 * @param ch : une chaîne de caractère contenant le chemin du fichier dans le PC.
	 */
	public void setloadFilePath(String ch)
	{
		loadFilePath = ch;
	}
	/**
	 * Dans HexagoneMouseListener après avoir cliqué sur un hexagone et après avoir changé son label 
	 * on informe le modèle de ce changement pour qu'il refasse un paintMaze() tout en gardant les hexagones précédemment changés!
	 * C'est pour ça qu'on a besoin de la liste selectedHexagone qui contient les hexagones changés.
	 * Une fois le changement de la nature d'hexagone est fait. On n'a plus d'hexagone sur lequel on veut faire la focalisation(coloredHexagone = null).
	 * Quand on vient de changer la nature d'un hexagone , on demande au modèle de ne pas faire la résolution du labyrinthe avant 
	 * que l'utilisateur ne le redemander et donc d'afficher la couleur rouge( qui indique qu'on n'a pas encore fait la résolution => green = false).
	 */
	public void weClickedOnHexagone() {
		weClickedOnHexagone = true;
		coloredHexagone = null;
		if (solveIt == true)
		{
			solveIt = false;
			green = false;
			stateChanged();
		}
		
	}
	/**
	 * Dire que le labyrinthe est initialisé ou pas.
	 * @param b : booléen qui va déterminer si le labyrinthe est considéré comme initialisé ou pas.
	 */
	public void setInitialised(boolean b) 
	{
		init = b;
		green = false;
		aIsSelected = false; // On remet tout à 0
		dIsSelected = false; // On remet tout à 0
		solveIt = false; // Quand on crée une nouvelle Maze la résolution ne se fait pas automatiquement
		weClickedOnHexagone = false; // On remet tout à 0 
		quickMode = false; //On remet tout à 0
		stateChanged();//L'état a changé
	}
	/**
	 * Changer le label d'un hexagone.
	 * @param hex : Hexagone dont on veut changer le label.
	 * @param ch  : le nouveau label de hex.
	 */
	public void setLabelOf(Hexagone hex , String ch) 
	{
		hex.setLabel(ch);
		stateChanged();
	}
	/**
	 * Ajouter un élément à selectedHexagone.
	 * @param hex : le nouveau hexagone ajouté.
	 */
	public void addSelectedHexagone(Hexagone hex) 
	{
		selectedHexagone.add(hex);
	}
	/**
	 * Vider la liste des hexagones non vides chargés.
	 */
	public void setLoadedHexagones()
	{
		loadedHexagones = new ArrayList<Hexagone>();
	}
	/**
	 * Quand l'hexagone d'arrivé  est sélectionné , aIsSelected == true, sinon aIsSelected == false.
	 * @param b : le booléen qui va déterminer si on a sélectionné ou pas l'hexagone d'arrivé.
	 */
	public void setAIsSelected(boolean b ) 
	{
		aIsSelected = b;
		
	}
	/**
	 * Quand l'hexagone de depart est sélectionné , dIsSelected == true, sinon dIsSelected ==false.
	 * @param b : le booléen qui va déterminer si on a sélectionné ou pas l'hexagone de départ.
	 */
	public void setDIsSelected(boolean b)
	{
		dIsSelected = b;
	}
	/**
	 * Renvoyer la liste des hexagones créés.
	 */
	public List<Hexagone> getCreatedHexagone ()
	{
		return createdHexagone;
	}
	/**
	 * Renvoyer la liste des hexagones sélectionnés , dont la nature a changé.
	 */
	public List<Hexagone> getSelectedHexagone()
	{
		return selectedHexagone;
	}
	/**
	 * @return booléen qui nous permet de savoir si on a sélectionné l'hexagone d'arrivé ou pas.
	 */
	public boolean getAIsSelected()
	{
		return aIsSelected;
	}
	/**
	 * 
	 * @return booléen qui nous permet de savoir si on sélectionné l'hexagone de départ ou pas.
	 */
	public boolean getDIsSelected()
	{
		return dIsSelected;
	}
	/**
	 *  récupérer i de A ( le numéro de ligne de  l'hexagone d'arrivé).
	 *  @return iA : Le numéro de ligne du labyrinthe dans lequel existe l'hexagone d'arrivé.
	 */
	public int getIa() 
	{
		return iA;
	}
	/**
	 * récupérer j de A (le numéro de colonne de l'hexagone d'arrivé).
	 * @return jA : Le numéro de colonne du labyrinthe dans lequel existe l'hexagone d'arrivé.
	 */
	public int getJa()
	{
		return jA;
	}
	/**
	 *  récupérer i de D ( le numéro de ligne de  l'hexagone de départ).
	 *  @return iD : Le numéro de ligne du labyrinthe dans lequel existe l'hexagone de départ.
	 */
	public int getId() 
	{
		return iD;
	}
	/**
	 *  récupérer j de D ( le numéro de colonne de  l'hexagone de départ).
	 *  @return iD : Le numéro de colonne du labyrinthe dans lequel existe l'hexagone de départ.
	 */
	public int getJd()
	{
		return jD;
	}
	/**
	 *  Modifier i de A ( le numéro de ligne de  l'hexagone d'arrivé).
	 */
	public void setIa(int i) 
	{
		iA = i;
	}
	/**
	 *  Modifier j de A ( le numéro de colonne de  l'hexagone d'arrivé).
	 */
	public void setJa(int j)
	{
		jA = j;
	}
	/**
	 *  Modifier i de D ( le numéro de ligne de  l'hexagone de départ).
	 */
	public void setId(int i) 
	{
		iD = i;
	}
	/**
	 *  Modifier j de D ( le numéro de colonne de  l'hexagone de départ).
	 */
	public void setJd(int j)
	{
		jD=j;
	}
	/**
	 * Renvoyer quickMode.
	 * @return quickMode un booléen qui indique qu'on est entrain d'utiliser "Quick Mode" quand il est vrai.
	 */
	public boolean getQuickMode()
	{
		return quickMode;
	}
	/**
	 * Renvoyer quickModeLabel.
	 * @return quickModeLabel : un caractère correspondant au label utilisé lors de "Quick mode".
	 */
	public char getQuickModeLabel()
	{
		return quickModeLabel;
	}
	/**
	 * Déclarer si l'utilisateur utilise "Quick mode" ou pas.
	 * @param b : booléen qui détermine la valeur de quickMode.
	 */
	public void setQuickMode(boolean b)
	{
		quickMode = b;
	}
	/**
	 * Choisir le label(W ou E) qu'on veut associer aux hexagones cliqués.
	 * @param x : le caractère choisi par l'utilisateur.
	 */
	public void setQuickModeLabel(char x)
	{
		quickModeLabel = x;
	}
	/**
	 * Metter une fin à 'Quick mode'.
	 */
	public void endQuickMode()
	{
		quickMode = false;
		stateChanged();
	}
	/**
	 * 
	 * @return loadedHexagones : La liste des hexagones non vides chargés.
	 */
	public List<Hexagone> getLoadedHexagones()
	{
		return loadedHexagones;
	}
	/**
	 * Créer un labyrinthe ayant n lignes et c colonnes
	 * @return  : le labyrinthe ainsi créé.
	 * @param n : nombre de lignes.
	 * @param c : nombre de colonnes.
	 */
	public Maze createNewMaze(int n , int c)
	{
		Maze m = new Maze( new MazeBox[n][c]);
		return m;
	} 
	/**
	 * Renvoyer l'hexagone situé à la ligne i et la colonne j.
	 * @param i : la ligne sur laquelle est situé l'hexagone.
	 * @param j : la colonne sur laquelle est situé l'hexagone.
	 * @return  : l'hexagone situé à la ligne i et à la colonne j ou bien null si ce dernier n'existe pas.
	 */
	public Hexagone getHexagoneIn(int i , int j)
	{
		for (Hexagone hex : createdHexagone)
		{
			if ((hex.getI() == i) && (hex.getJ()==j))
			{
				return hex;
			}
		}
		return null;
	}
	/**
	 * Resoudre le problème du plus court chemin du labyrinthe en faisant une succession de segments entre l'hexagone de départ et celui d'arrivé.
	 */
	public void SolveMaze(Graphics g) throws Exception
	{
		Dijkstra dij = new Dijkstra();
		MazeBox d = maze.getDepartureBox();
		MazeBox a = maze.getArrivalBox();
		ShortestPaths sp = dij.dijkstra(maze, (DepartureBox) d, (ArrivalBox) a);
		ArrayList<Vertex> sol = sp.getShortestPath(a);
		int i1 = a.getAbscisse();//obtenir le numéro de ligne du ArrivalBox
		int j1 = a.getOrdonnee();//obtenir le numéro de colonne du ArrivalBox
		Hexagone ahex = getHexagoneIn(i1,j1);//Obtenir l'hexagone situé au niveau des coordonnées du arrivalBox
		int x1 = ahex.getX();//obtenir l'abcisse de l'hexagone situé au niveau des coordonnées du arrivalBox
		int y1 = ahex.getY();//obtenir l'ordonné del'hexagone situé au niveau des coordonnées du arrivalBox
		int i = 0; // compteur = si il est > 1 alors solution sinon pas de solution . 
		/*
		 * Revenir en arrière pour trouver les sommets qui construisent le plus court chemin, s'il existe au moins deux sommets 
		 * alors on a un plus court chemin (i >= 2 ) sinon pas de plus court chemin (i == 1 , qui correspond à ArrivalBox)
		 */
		for (Vertex v : sol)
		{
			int[] cordonne = maze.coordonneOf((MazeBox) v);
			Hexagone hex = getHexagoneIn(cordonne[0],cordonne[1]);
			int x2 = hex.getX();
			int y2 = hex.getY();
			Segment seg = new Segment(Color.cyan,(float)x1,(float)y1,(float)x2,(float)y2);//on trace le segment de (x1,y1) à (x2,y2)
			seg.paint(g);
			x1 = x2;
			y1 = y2;
			i = i + 1;
	     }
		if (i > 1) 
		{
			green = true; 
			stateChanged();// Rendre le panel SolvedPanel vert
		}
			}
	/**
	 * Cette méthode permet de traçer le labyrinthe après avoir changé la nature d'un hexagone. Il ne s'agit pas d'une initialisation.
	 * Les hexagones créés vont être oublié puis reconstruits à l'aide de la liste selectedHexagone.
	 * Tous les hexagones qui n'existent pas dans selectedHexagone , ont été donc vide et resteront donc vide.
	 * On ne doit oublier la focalisation.
	 * @param g : objet de type graphics qui permet de traçer un hexagone.
	 */
	public void paintAfterChangingLabel(Graphics g)
	{
		{   createdHexagone = new ArrayList<Hexagone>();
		int x= 50; // Abscisse du point de départ
	    int y = 54;//Ordonnee du point de départ
		for (int i = 0 ; i < nl ;i=i+1) 
		{
			for (int j = 0 ;j < cl ; j=j+1 )
			{
				Hexagone hex = new Hexagone(x,y,i,j,50,Color.BLUE);//La couleur par défaut est bleu et la coté on la choisit 50
				for (Hexagone hex1 : selectedHexagone) /* tous ceux sélectionnés auront leur label changé et repaints et ajouté dans createdHexagone avec le nouveau label*/	
				{
					if ((hex1.getI() == i) && (hex1.getJ()== j))
					{
						hex.setLabel(hex1.getLabel());
					}
				}
				if (coloredHexagone == null) // on n'a pas séléctionné un hexagone qui va devenir rouge en cliquant sur lui.
				{
				hex.paintComponent(g);
				maze.setOrdonne(i,j, hex.getEquivalentMazeBox()); //On met le MazeBox équivalent à hex dans maze,
				}
				else if ((coloredHexagone.getI() == i) && (coloredHexagone.getJ() == j) ) //On a cliqué sur un hexagone et ses coordonnés sont (i,j) => on change sa couleur en rouge
				{
					hex.setColor(Color.RED);
					hex.paintComponent(g);
					maze.setOrdonne(i,j, hex.getEquivalentMazeBox());//On met le MazeBox équivalent à hex dans maze.
				}
				else // on a cliqué sur un hexagone et ses coordonnés ne sont pas (i,j).
				{
					hex.paintComponent(g);
					maze.setOrdonne(i,j, hex.getEquivalentMazeBox());//On met le MazeBox équivalent à hex dans maze.
				}
				createdHexagone.add(hex);
				x = x + (int) (2*50*Math.sin(3.14/3));
			}
			if ((i+1)%2 == 0) // Pour le décalage des Hexagones sur une ligne impair
			{
				y = y + 2*(50) - 23; // 2*50 = diamtere donc on descend dans la ligne suivante deux fois la coté
				// le (-23) terme correctif après plusieurs essais
				x = 50 ; //Pas besoin de faire le décalage si la ligne suivante est pair.
			}
			else 
			{
				y = y+ 2*(50) - 23;
				x = 50 + (int) (2*50*Math.sin(3.14/3))-44;//Faire le déclage si la ligne suivante est impaire.
				//le ( - 44 ) est un terme correctif avec plusieurs essais
			}
		}
	}
	}
	/**
	 * Demander au modèle de resoudre le problème du plus court chemin du labyrinthe.
	 * @param g : Objet de type graphics qui va permettre de traçer les segments.
	 */
	public void askForSolve(Graphics g)
	{ 
		try {
				  SolveMaze(g);
			 }
			catch (Exception e) 
			{
				e.printStackTrace();
			}	
	}
	/**
	 * Quand on initialise le labyrinthe avec "Create new maze" et non pas avec le bouton "Load" , weClickedOnHexagone == false et loading == false.
	 * Il s'agit d'oublier tous les hexagones sélectionnés.
	 * Il s'agit ensuite de créer un labyrinthe avec des cases vides qui vont être changé en se basant sur la liste des hexagones chargés.
	 * Cette méthode est utilisée même quand on clique sur "Load" , mais d'abord précédé par la création des listes des hexagones chargés pendant laquelle loading == false.
	 * Après la création de la liste des hexagones chargés, loading devient faux, et la méthode paintMaze est appelé.
	 * Si on clique sur "Create new maze", la liste des hexagones chargés est vidé dans la classe "NewMazeButton".
	 */
	public void paintMaze(Graphics g)
	{   
		maze = createNewMaze(nl,cl);//On crée le labyrinthe théorique équivalente.
		if( (weClickedOnHexagone == false)&&(loading == false)) //Si on n'a pas juste cliqué sur un hexagone, c'est qu'on va initialiser une nouvelle maze
	{   selectedHexagone = new ArrayList<Hexagone>();// Donc tous les hexagones modifiés ( dont le label a changé) vont être oublié.
		int x= 50; // Abscisse du point de départ
	    int y = 54;//Ordonnee du point de départ
		for (int i = 0 ; i < nl ;i=i+1) 
		{
			for (int j = 0 ;j < cl ; j=j+1 )
			{  
				Hexagone hex = new Hexagone(x,y,i,j,50,Color.blue);//La couleur par défaut est bleu et la coté on la choisit 50
				if (coloredHexagone == null) // si on n'a pas cliqué sur un hexagone ,on ne change pas sa couleur en rouge
				{ 
					for(Hexagone hex2 : loadedHexagones)
					{
						if ((hex2.getI() == hex.getI()) && (hex2.getJ() == hex.getJ()))
						hex =hex2;
					}
				hex.paintComponent(g);
				maze.setOrdonne(i,j, hex.getEquivalentMazeBox());//On met le MazeBox équivalent à hex dans maze.
				}
				/* si on a cliqué sur 
				un hexagone et que ses coordonnées sont i et j on change sa couleur en rouge
				*/
				else if ((coloredHexagone.getI() == i) && (coloredHexagone.getJ() == j) ) 
                {  
					for(Hexagone hex2 : loadedHexagones)
					{
						if ((hex2.getI() == hex.getI()) && (hex2.getJ() == hex.getJ()))
						hex =hex2;
					}
					hex.setColor(Color.RED);
					hex.paintComponent(g);
					maze.setOrdonne(i,j, hex.getEquivalentMazeBox());
				}
				else //Si on a cliqué sur un hexagone et que ses coordonnées ne sont pas (i,j) , on ne change pas sa couleur.
				{  
				    for(Hexagone hex2 : loadedHexagones)
					{
						if ((hex2.getI() == hex.getI()) && (hex2.getJ() == hex.getJ()))
						hex =hex2;
					}
					hex.paintComponent(g);
					maze.setOrdonne(i,j, hex.getEquivalentMazeBox());
				}
				createdHexagone.add(hex); // On a à la fin créé un hexagone dont on va ajouter dans la liste createdHexagone
				x = x + (int) (2*(50)*Math.sin(3.14/3));
			}
			if ((i+1)%2 == 0) // Pour le décalage des Hexagones sur une ligne impair
			{
				y = y + 2*(50) - 23; // 2*50 = diamtere donc on descend dans la ligne suivante deux fois la coté
				// le (-23) terme correctif ,après plusieurs essais , pour que les hexagones soient collés.
				x = 50 ; //Pas besoin de faire le décalage si la ligne suivante est pair.
			}
			else 
			{
				y = y+ 2*(50) - 23;
				x = 50 + (int) (2*50*Math.sin(3.14/3))-(44);//Faire le déclage si la ligne suivante est impaire.
				//le ( - 44 ) est un terme correctif, avec plusieurs essais ,pour que les hexagones soient collés.
			}
		}
	}
		/*
		 *Sinon, n utilise l'autre mode. On a juste cliqué sur un hexagone , le label va changer et on va tout re-tracer 
		 * avec uniquement l'hexagone cliqué qui va subir un changement de couleur et de label
		 */
	else if (weClickedOnHexagone) 
	{paintAfterChangingLabel(g);} 
		if (solveIt == true) { askForSolve(g);}; // On demande la résolution du labyrinthe si solveIt == true 
	}
	/**
	 * Demander de créer la liste des hexagones qu'on veut charger pour ensuite les tracer avec paintMaze().
	 * @param g : objet de type Graphics utilisé pour traçer les hexagones.
	 */
	public void paintWithLoad(Graphics g) throws Exception , MazeReadingException
	{
		try
		{
		setLoadedHexagones(); // vider la liste des hexagones chargés précédemment
		maze = createNewMaze(nl,cl);
		maze.initFromTextFile(loadFilePath);
		 selectedHexagone = new ArrayList<Hexagone>();// Tous les hexagones modifiés ( dont le label a changé) vont être oublié.
			int x= 50; // Abscisse du point de départ
		    int y = 54;//Ordonnee du point de départ
			for (int i = 0 ; i < nl ;i=i+1) 
			{
				for (int j = 0 ;j < cl ; j=j+1 )
				{   
					Hexagone hex = maze.get(i, j).getEquivalentHexagone(x, y); // Prendre l'hexagone à la position (i,j) de Maze
					if ((hex.getLabel() == "A")||(hex.getLabel() == "W")||(hex.getLabel()=="D"))
						{
							loadedHexagones.add(hex);
							selectedHexagone.add(hex);
						}
					if(hex.getLabel()=="A")
					{
						this.setAIsSelected(true);
						this.setIa(i);
						this.setJa(j);
						if(this.dIsSelected)//On a donc les hexagones d'arrivé et de départ prets donc possibilité de résolution
						{
							stateChanged();
						}
					}
					if(hex.getLabel()=="D")
					{
						this.setDIsSelected(true);
						this.setId(i);
						this.setJd(j);
						if(this.aIsSelected)//On a donc les hexagones d'arrivé et de départ prets donc possibilité de résolution
						{
							stateChanged();
						}
					}
					x = x + (int) (2*(50)*Math.sin(3.14/3));
				}
				if ((i+1)%2 == 0) // Pour le décalage des Hexagones sur une ligne impair
				{
					y = y + 2*(50) - 23; // 2*50 = diamtere donc on descend dans la ligne suivante deux fois la coté
					// le (-23) terme correctif après plusieurs essais 
					x = 50 ; //Pas besoin de faire le décalage si la ligne suivante est pair.
				}
				else 
				{
					y = y+ 2*(50) - 23;
					x = 50 + (int) (2*50*Math.sin(3.14/3))-(44);//Faire le déclage si la ligne suivante est impaire.
					//le ( - 44 ) est un terme correctif avec plusieurs essais
				}
			}
		}
		catch(MazeReadingException ex) {}//l'exception est traité ailleurs par des messages qui vont surgir sur la fenêtrE.
		catch(Exception ex) {}//l'exception est traité ailleurs mais il faut l'attrapper.
		finally{loading = false;}//Pour ensuite demander à paintMaze(Graphics g) de traçer le labyrinthe.
	}
	/**
	 * Fonction pour déterminer le plus proche hexagone du point cliqué avec la souris sur MazePanel.
	 *      Ceci se fait on prenant l'hexagone ayant son centre le plus proche du point où la souris a cliqué.
	 *      On utilise ensuite un threshhold qui est la coté de l'hexagone. Si le point cliqué par la souris a une distance
	 *      par rapport au centre de l'hexagone le plus proche qui est supérieur à la coté de l'hexagone , on renvoit null.
	 *      @param x : l'abcisse du point cliqué par la souris.
	 *      @parm y : l'ordonné du point cliqué par la souris.
	 *      @return : l'hexagone cliqué ou null si on clique loin.
	 * 
	 */
	public Hexagone getHexagoneClicked(int x , int y) 
	{
		Hexagone foundHexagone = null;
		int n = createdHexagone.size();
		double min = Math.sqrt((double)(x-createdHexagone.get(0).getX())*(x-createdHexagone.get(0).getX()) +
				(y-createdHexagone.get(0).getY())*(y-createdHexagone.get(0).getY()));
		for (int i = 0 ; i< n ; i = i+1 )
		{
			double dx_i = (x-createdHexagone.get(i).getX())*(x-createdHexagone.get(i).getX());
			double dy_i = (y-createdHexagone.get(i).getY())*(y-createdHexagone.get(i).getY());
			if (Math.sqrt(dy_i + dx_i) <= min) 
			{
				min = Math.sqrt(dy_i + dx_i);
				foundHexagone = createdHexagone.get(i);	
			}
		}
		if ((foundHexagone == null) || (min > foundHexagone.getCote())) // si on clique très loin , on n'obtient rien 
		{
			return null;
		}
		return foundHexagone;
	}
	/**
	 * Changer la couleur de Hex en rouge quand on clique sur lui.
	 */
	public void changeColorOf(Hexagone hex)
	{
		coloredHexagone = hex;
		  stateChanged();
	}
	/**
	 * Enregistrer le labyrinthe sur un fichier txt
	 */
	public void saveMaze  (String fileName) throws MazeReadingException,Exception
	{
		maze.saveToTextFile(fileName+".maze");
		this.fileName = fileName;
	}
	/**
	 * Renvoyer le nom du fichier sur lequel on va faire les enregistrements ( Pour le bouton 'Save').
	 * @return fileName : chaîne de caractère contenant  le nom du fichier sur lequel on fait l'enregistement.
	 */
	public String getFileName()
	{
		return fileName;
	}
	/**
	 * Changer le booléen fileCreated en b ( qui est un booléen). Utilisée quand on a créé un nouveau fichier pour enregistrer le labyrinthe.
	 * @param b : booléen qui détermine la valeur de fileCreated.
	 */
	public void setFileCreatedTo(boolean b)
	{
		fileCreated = b;
		stateChanged();
	}
	/**
	 * Renvoyer fileCreated.
	 * @return fileCreated : booléen qui nous indique si on a  créé un fichier pour enregistrer le labyrinthe ou pas.
	 */
	public boolean getFileCreated() 
	{
		return fileCreated;
	}
	
	
	
	

}
