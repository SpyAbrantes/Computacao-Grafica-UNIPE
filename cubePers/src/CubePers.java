
import java.awt.*;
import java.awt.event.*;

public class CubePers extends Frame {
    public static void main(String[] args) {
        new CubePers();
    }

    CubePers() {
        super("Um cubo em perspectiva");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setLayout(new BorderLayout());
        add("Center", new CvCubePers());
        Dimension dim = getToolkit().getScreenSize();
        setSize(dim.width / 2, dim.height / 2);
        setLocation(dim.width / 4, dim.height / 4);
        show();
    }
}



