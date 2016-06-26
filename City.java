///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.util.*;
import java.awt.*;
/**
 * This class represents a City (on the game board) in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class City
{
    ArrayList<Road> roads; // Outgoing roads
    private String name; // The name of the city
    char country; // What country this city resides in
    private int x, y; // Center point
    boolean highlighted; // Whether the city is highlighted

    /**
     * Constructor for objects of class City
     * @param name The name of the City
     * @param country The country of this City
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public City(String name, char country, int x, int y){
        this.name = name;
        this.country = country;
        this.x = x;
        this.y = y;
        roads = new ArrayList<Road>();
        // ArrayList of Roads for this City.
    }

    /**
     * This method adds a road to the city's ArrayList of roads that it 
     * contains.
     * 
     * @param   r - The road to be added.
     */
    public void addRoad(Road r){
        roads.add(r);
    }

    /**
     * Method to highlight the city a certain color when it is scrolled
     * over on the board.
     * 
     * @param   g  The graphics object that is painted to.
     * @param   c  The color of the highlight.
     */
    public void highlight(Graphics g, Color c){
        int r = c.getRed();
        int gr = c.getGreen();
        int b = c.getBlue();
        Color newColor = new Color(r, gr, b, 150);
        g.setColor(newColor);
        g.fillOval(x - 8,y - 8,16, 16);
    }

    /**
     * Boolean method that tells us whether or not a given point is
     * located within the City.
     * 
     * @param   p Given point on the map.
     * 
     * @return  True, if the City contains the point. False otherwise.
     */
    public boolean contains(Point p){
        return p.getX() >= x - 8 && p.getX() <= x + 8
        && p.getY() >= y - 8 && p.getY() <= y + 8;
    }

    /**
     * Method that creates and returns a String of the city and its 
     * coordinates.
     * 
     * @return  The created String of the city and location on the map.
     */
    public String toString(){
        return name + " at (" + x + ", " + y + ")";
    }

    /**
     * Method that gives the user the name of the city.
     * 
     * @return  The name of the city.
     */
    public String getName(){
        return name;
    }

    /**
     * Method that gives the user the current x coordinate of the City.
     * 
     * @return  The x-coordinate of the city.
     */
    public int getX(){ return x; }

    /**
     * Method that gives the user the current y coordinate of the City.
     * 
     * @return  The y coordinate of the city.
     */
    public int getY(){ return y; }

    /**
     * Method to set the x coordinate of the City.
     * 
     * @param   x  The new x coordinate of the City.
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Method to set the y coordinate of the City.
     * 
     * @param   y  The new y coordinate of the City.
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Method to paint graphics on to the screen. If highlighted = true,
     * the highlight method is called to highlight that City.
     * 
     * @param   g The graphics object that is painted to.
     */
    public void paint(Graphics g){
        if(highlighted)
            highlight(g, Color.YELLOW);
    }
}
