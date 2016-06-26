///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
/**
 * This class represents a Destination Card in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class DestinationCard extends Card
{
    int pointValue; // Point value for the destination card
    String cityA; // First city
    String cityB; // Second city
    boolean checked = false; // Whether this card should display a check
    /**
     * DestinationCard Constructor This constructor makes the
     * destination cards
     * 
     * @param d A The dimensiion of the card
     * @param cityA The name of the first city on the card
     * @param cityB The name of the second city on the card
     * @param pv The point value of the card.
     */
    public DestinationCard(Dimension d, String cityA, String cityB,
    int pv){
        this.cityA = cityA;
        this.cityB = cityB;
        name = cityA + "_" + cityB;
        pointValue = pv;
        setPreferredSize(d);
        dim = d;
    }

    /**
     * Method paintComponent This method paints the object.
     *
     * @param g The graphics to paint to.
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(checked){
            Image toPaint = null;
            toPaint = Images.valueOf("check").image;
            int x1 = toPaint.getWidth(this);
            int y1 = toPaint.getHeight(this);
            scaledX = (double)dim.width / x1;
            scaledY = (double)dim.height / y1;
            g2d.scale(scaledX, scaledY);
            g2d.drawImage(toPaint, 0, 0, this);
        }
    }
}