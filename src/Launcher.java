import tools.Text;

public class Launcher {

    public static void main() {

        Window window = new Window(800, 600);

        Text text = new Text();
        
        window.getScene().addElement(text);
    }
}
