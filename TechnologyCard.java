///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
/**
 * This class represents a Technology Card in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class TechnologyCard extends Card
{
    // How many locomotives a player needs to buy this technology card
    int cost;

    /**
     * TechnologyCard Constructor
     *
     * @param name The name of the technology card.
     * @param d The size dimensions of the technology card.
     * @param cost The cost of the technology card.
     */
    public TechnologyCard(String name, Dimension d, int cost){
        this.name = name;
        this.cost = cost;
        setPreferredSize(d);
        dim = d;
    }
}