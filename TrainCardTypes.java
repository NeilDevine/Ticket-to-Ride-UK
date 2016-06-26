import java.awt.*;
/**
 * This is an enum for every train card type in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public enum TrainCardTypes
{
    /**
     * red trains
     */
    red(0, Color.RED),

    /**
     * blue trains
     */
    blue(1, Color.BLUE),

    /**
     * pink trains
     */
    pink(2, Color.PINK),

    /**
     * orange trains
     */
    orange(3, Color.ORANGE),

    /**
     * white trains
     */
    white(4, Color.WHITE),

    /**
     * yellow trains
     */
    yellow(5, Color.YELLOW),

    /**
     * black trains
     */
    black(6, Color.BLACK),

    /**
     * green trains
     */
    green(7, Color.GREEN),

    /**
     * locomotives
     */
    locamotive(8);

    /**
     * The index of the given train card
     */
    int index; 

    /**
     * The color of the given train card
     */
    Color color;
    /**
     * Used to identify locamotive cards by their index.
     * 
     * @param i Number that is used to identify locomotive cards.
     */
    TrainCardTypes(int i){
        index = i;
    }

    /**
     * Used to identify cards by their index and color.
     * 
     * @param i Number used to identify cards.
     * @param c Color that is used to identify cards.
     */
    TrainCardTypes(int i, Color c){
        index = i;
        color = c;
    }
}
