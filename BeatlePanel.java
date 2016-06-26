///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
/**
 * This class is a JPanel that displays a walking Beatle.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class BeatlePanel extends JPanel
{
    private Dimension dim;
    private Timer t; // Timer for animation
    private Timer walkTimer; // Timer for walking animation
    private int frame = 0; // Frame number
    private int walkFrame = 0; // Frame number for walking animation

    // Whether the beatle is walking forward
    private boolean walkingForward = true; 

    // Whether the beatle is walking out of the screen
    private boolean walkingOut = true; 

    // List of all beatles to be displayed
    private ArrayList<String> beatles = new ArrayList<String>();

    /**
     * The constructor for a BeatlePanel.
     * @param d The dimension of the panel
     * @param beatles The names of the beatles in order to display
     */
    public BeatlePanel(Dimension d, ArrayList<String> beatles){
        this.beatles = beatles;
        dim = d;
        setOpaque(false);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setMinimumSize(dim);
        t = new Timer(140, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if(walkingForward){
                        frame++;
                    }
                    else{
                        frame--;
                    }

                    if(frame == 5){
                        frame = 3;
                        walkingForward = false;
                    }
                    else if(frame == -1){
                        frame = 1;
                        walkingForward = true;
                    }
                    repaint();
                }
            });

        walkTimer = new Timer(50, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    walkFrame++;
                    if(walkFrame == 30 && walkingOut){
                        walkFrame = 0;
                        walkingOut = false;
                        Collections.rotate(beatles,-1);
                    }
                    else if(walkFrame == 30 && !walkingOut){
                        walkFrame = 0;
                        walkingOut = true;
                        walkTimer.stop();
                        t.stop();
                    }

                    repaint();
                }
            });
    }

    /**
     * This method paints the actual panel (with beatle)
     * @param g The graphics object to paint to
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform save = g2d.getTransform();
        String frameNum = beatles.get(0) + frame;
        Image toPaint = Images.valueOf(frameNum).image;

        int x1 = toPaint.getWidth(this);
        int y1 = toPaint.getHeight(this);
        double scaledX = (double)dim.width / x1;
        double scaledY = (double)dim.height / y1;

        double translateX = walkingOut ?
                walkFrame * dim.width / 30.0 : 
            (walkFrame - 30) * dim.width / 30.0;

        g2d.translate(translateX ,0);
        g2d.scale(scaledX, scaledY);

        g2d.drawImage(toPaint,0,0,this);
        g2d.setTransform(save);
    }

    /**
     * This method starts the walking beatle
     * transition animation
     */
    public void walk(){
        t.start();
        if(walkTimer.isRunning()){
            walkTimer.stop();
            walkFrame = 0;

            if(walkingOut)
                Collections.rotate(beatles,-1);

            walkTimer.start();
            walkingOut = true;
        }
        else
            walkTimer.start();
    }
}
