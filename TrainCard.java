///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;
/**
 * This class represents a Train car Card in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 */
public class TrainCard extends Card
{
    TrainCardTypes type; // The type of train card
    boolean hasNum = false; // Whether the card should display a number
    int num = 0; // The number associated with this card (if any)
    // The number of locomotives which should not be discarded
    // (this is so that the train cards are constant throughout the game)
    int howManyDontCount = 0;

    /**
     * TrainCard Constructor
     *
     * @param d The size dimension
     * @param t The type of the train card.
     */
    public TrainCard(Dimension d,TrainCardTypes t){
        name = t + "_train";
        type = t;
        setPreferredSize(d);
        dim = d;
    }

    /**
     * Method paintComponent This method paints the object.
     *
     * @param g The graphics to paint to.
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(hasNum){
            g.setColor(new Color(0,0,0,150));
            g.fillOval(getWidth()/2 - 10, getHeight()/2 - 17, 20, 20);
            g.setColor(Color.WHITE);
            g.setFont(new Font("stuff", Font.BOLD, 20));
            g.drawString(num + "", getWidth()/2 - 5, getHeight()/2);
        }
    }

    /**
     * Method copy Makes and returns a copy of the card sent to the method.
     *
     * @return A copy of the Traincard that was sent to the method.
     */
    public TrainCard copy(){
        TrainCard c = new TrainCard(getPreferredSize(), type);
        c.num = num;
        c.hasNum = hasNum;
        return c;
    }
}