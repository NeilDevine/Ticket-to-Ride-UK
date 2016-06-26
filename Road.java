///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
/**
 * This class represents a road or route in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class Road
{
    /**
     * First connected city
     */
    City cityA;

    /**
     * Second connected city
     */
    City cityB;

    /**
     * Every polygon of this road
     */
    ArrayList<Polygon> rects; 

    /**
     * The color of the road
     */
    Color color; 

    /**
     * The amount of locomotives required to claim the road
     */
    int locosRequired; 

    /**
     * The players currently occupying this road
     */
    ArrayList<Player> occupying = new ArrayList<Player>();

    /**
     * Boolean for Adjacency List algorithms
     */ 
    boolean visited = false;

    /**
     * Constructor of objects for class Road.
     * @param a The first City
     * @param b The second City
     * @param c The color of the road
     */
    public Road(City a, City b, Color c){
        cityA = a;
        cityB = b;
        color = c;
        rects = new ArrayList<Polygon>();
        locosRequired = 0;
    }

    /**
     * 2nd constructor of objects for class Road.
     * @param a The first City
     * @param b The second City
     * @param c The color of the road
     * @param locos The number of locomotives required
     */
    public Road(City a, City b, Color c, int locos){
        this(a,b,c);
        locosRequired = locos;
    }

    /**
     * Boolean method that determines whether or not two roads between
     * cityA and cityB are the exact same road or not.
     * @param road the other road in question
     * @return  True if the two roads are the same road, false otherwise.
     */
    public boolean equals(Road road){
        return (cityA == road.cityA && cityB == road.cityB)
        || (cityB == road.cityA && cityA == road.cityB);
    }

    /**
     * Method to claim this Road for a certain player.
     * 
     * @param   p The player that is claiming this road.
     */
    public void claim(Player p){
        occupying.add(p);
    }

    /**
     * Method that adds a rectangle to the Road.
     * 
     * @param   x1 X coordinate of the 1st corner of the rectangle.
     * @param   y1 Y coordinate of the 1st corner of the rectangle.
     * @param   x2 X coordinate of the 2nd corner of the rectangle.
     * @param   y2 Y coordinate of the 2nd corner of the rectangle.
     * @param   x3 X coordinate of the 3rd corner of the rectangle.
     * @param   y3 Y coordinate of the 3rd corner of the rectangle.
     * @param   x4 X coordinate of the 4th corner of the rectangle.
     * @param   y4 Y coordinate of the 4th corner of the rectangle.
     */
    public void addRect(int x1, int y1, int x2, int y2, int x3, int y3,
    int x4, int y4){
        int[] x = {x1, x2, x3, x4};
        int[] y = {y1, y2, y3, y4};
        Polygon p = new Polygon(x,y, 4);
        p = new Polygon();
        p.addPoint(x1,y1);
        p.addPoint(x2,y2);
        p.addPoint(x3,y3);
        p.addPoint(x4,y4);
        rects.add(p);
    }

    /**
     * Method to highlight the Road when it is scrolled over on the board,
     *  it will be highlighted a more transparent version of its original
     *  color.
     *  
     *  @param  g The Graphics object to be painted to.
     */
    public void highlight(Graphics g){
        int r = color.getRed();
        int gr = color.getGreen();
        int b = color.getBlue();
        Color c = new Color(r,gr,b, 100);
        g.setColor(c);
        for(Polygon p : rects){
            g.fillPolygon(p);
        }
    }

    /**
     * Method to highlight the road the color of the player who claimed it
     * when scrolled over.
     * 
     * @param  g The Graphics object to be painted to.
     */
    public void highlightClaimed(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform save = g2d.getTransform();
        for(Player player : occupying){
            int r = player.color.getRed();
            int gr = player.color.getGreen();
            int b = player.color.getBlue();
            Color c = new Color(r,gr,b);
            g2d.setColor(c);
            for(Polygon p : rects){
                g2d.fillPolygon(p);
            }
            g2d.translate(1,1);
        }
        g2d.setTransform(save);
    }

    /**
     * Method that says whether or not a certain point is located anywhere
     * within the Road.
     * 
     * @param   point - The Point that is being looked for.
     * 
     * @return  True if the Point is contained, false otherwise.
     */
    public boolean contains(Point point){
        for(Polygon p : rects){
            if(p.contains(point)) return true;
        }
        return false;
    }

    /**
     * Method that gives the length of the Road.
     * 
     * @return  The number of rectangles in the Road.
     */
    public int length(){
        return rects.size();
    }

    /**
     * Method that creates a String that reads "(starting city) to
     * (destination city)".
     * 
     * @return  The string that tells us the starting and destonation
     * cities.
     */
    public String toString(){
        return cityA + " to " +  cityB;
    }
}
