import tools.Arrow;
import tools.Text;

public class Launcher {

    public static void main() {

        Window window = new Window(800, 600);

        Text text1 = new Text(window.getViewport());
        window.getScene().addElement(text1);

    }
}
