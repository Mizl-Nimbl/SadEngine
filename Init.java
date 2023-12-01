import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Init 
{
    private boolean windowClosed = false;
    private int mousex, mousey;
    private boolean mouse1clicked;
    private boolean mouse2clicked;
    private boolean mouse3clicked;

    private boolean mouse1held;
    private boolean mouse3held;

    private boolean drawingLine;

    List<Point> mouseCoordinates = new ArrayList<>();

    int Initialize()
    {
        System.out.println("creating frame");
        JFrame frame = new JFrame("SadEngine");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setVisible(true);

        System.out.println("creating panel");
        JPanel panel = new JPanel();
        frame.add(panel);

        System.out.println("creating buffer");
        BufferedImage buffer = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = buffer.getGraphics();

        addlisteners(frame, panel);

        while (!windowClosed) 
        {
            Update(g);
            Render(panel, g, buffer);
        }
        return 0;
    }

    void addlisteners(JFrame frame, JPanel panel)
    {
        frame.addWindowListener((WindowListener) new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                windowClosed = true;
            }
        });

        panel.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e) 
            {
                mousex = e.getX();
                mousey = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) 
            {
                mousex = e.getX();
                mousey = e.getY();
            }

        });

        panel.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                int buttonClicked = e.getButton();
                switch (buttonClicked) {
                    case MouseEvent.BUTTON1:
                        mouse1clicked = true;
                        break;
                    case MouseEvent.BUTTON2:
                        mouse2clicked = true;
                        break;
                    case MouseEvent.BUTTON3:
                        mouse3clicked = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int buttonClicked = e.getButton();
                switch (buttonClicked){
                    case MouseEvent.BUTTON1:
                        mouse1held = true;
                        break;
                    case MouseEvent.BUTTON3:
                        mouse3held = true;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int buttonClicked = e.getButton();
                switch (buttonClicked){
                    case MouseEvent.BUTTON1:
                        mouse1held = false;
                        break;
                    case MouseEvent.BUTTON3:
                        mouse3held = false;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    void Update(Graphics g)
    {
        if (mouse1held)
        {
            drawingLine = true;
            Point currentPoint = new Point(mousex, mousey);
            mouseCoordinates.add(currentPoint);
        }
        else
        {
            drawingLine = false;
            mouseCoordinates.clear();
        }
        if (mouse3held)
        {
            System.out.println("right click held");
        }
        if (mouse1clicked)
        {
            System.out.println("left click clicked");
            mouse1clicked = false;
        }
        if (mouse2clicked)
        {
            System.out.println("middle click clicked");
            mouse2clicked = false;
        }
        if (mouse3clicked)
        {
            System.out.println("right click clicked");
            mouse3clicked = false;
        }
    }

    void DrawLine(Graphics g)
    {
        for (int i = 1; i < mouseCoordinates.size(); i++) {
            Point mousepoint = new Point(mousex, mousey);
            mouseCoordinates.add(mousepoint);
            Point p1 = mouseCoordinates.get(i - 1);
            Point p2 = mouseCoordinates.get(i);
            g.setColor(Color.RED);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    void Render(JPanel panel, Graphics g, BufferedImage buffer)
    {
        if (windowClosed == true)
        {
            g.dispose();
            panel.getGraphics().dispose();
            System.out.println("Graphics processes terminated");
        }
        g.setColor(panel.getBackground());
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        g.setColor(Color.RED);
        g.fillOval(mousex, mousey, 6, 6);
        if (drawingLine)
        {
            DrawLine(g);
        }   
        panel.getGraphics().drawImage(buffer, 0, 0, panel);   
    }
}
