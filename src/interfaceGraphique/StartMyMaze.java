package interfaceGraphique;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import model.*;
/**
 * Tester de créer une fenêtre avec le modèle fait =
 */
public class StartMyMaze {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new MyWindow();

	}

}
