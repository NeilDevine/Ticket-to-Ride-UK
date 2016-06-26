///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.*;
/**
 * This class is a JPanel which represents a scrollable instructions 
 * screen.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class Instructions extends JPanel
{
    private Dimension dim;
    private Image image; // The instructions image
    private int yOffset = 0; // How far down the user scrolled
    private JScrollBar scrollBar;
    /**
     * Constructor for objects of class Instructions.
     * @param d the dimension of the Instructions class
     */
    public Instructions(Dimension d)
    {
        dim = d;
        setPreferredSize(dim);
        setLayout(new BorderLayout());
        try{
            image = ImageIO.read(new File("photos/instructions.jpg"));
        }catch (Exception e){
            System.out.println("File Not Found");
        }
        int height = image.getHeight(this);

        //when the mouse is scrolled up or down
        addMouseWheelListener(new MouseWheelListener(){
                public void mouseWheelMoved(MouseWheelEvent e){
                    // Scroll the image in the direction that the
                    // user scrolled
                    // In other words, increment or decrement a y-offset

                    yOffset += e.getWheelRotation();

                    if (yOffset < 0){
                        yOffset = 0;
                    }
                    else if (yOffset*100 > height-dim.height){
                        yOffset = (int)((height-dim.height)/100);
                    }

                    //if the mouse is scrolled, the scroll bar is adjusted
                    scrollBar.setValue(yOffset);
                    repaint();
                    e.consume();
                }
            });

        scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0,15, 0,150);

        //when the scroll bar is moved up or down
        scrollBar.addAdjustmentListener(new AdjustmentListener(){
                public void adjustmentValueChanged(AdjustmentEvent e){
                    yOffset = e.getValue();

                    if (yOffset < 0){
                        yOffset = 0;
                    }
                    else if (yOffset*100 > height-dim.height){
                        yOffset = (int)((height-dim.height)/100);
                    }
                    repaint();

                }
            });
        add(scrollBar, BorderLayout.EAST);

    }

    /**
     * Pain method for the Instructions class
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        // Save the AffineTransform
        AffineTransform save = g2d.getTransform();

        // Scale and paint the image based on the y-offset
        int x = image.getWidth(this);
        double scaledX = (double)dim.width/x;
        g2d.scale(scaledX, 1);
        g2d.drawImage(image, 0, yOffset*(-100), null);

        // Reset the AffineTransform
        g2d.setTransform(save);
    }
}
