///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.sound.sampled.*;
/**
 * Abstract class Card - contains the methods that all card sub-classes
 * need to override.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public abstract class Card extends JComponent
{
    /**
     * Dimension of the card
     */
    Dimension dim;

    /**
     * Scaled X / width value
     */
    double scaledX = 1;

    /**
     * Scaled Y / height value
     */
    double scaledY = 1;

    /**
     * True for face up, false for face down
     */
    boolean faceUp = true; 

    /**
     * The name of the card
     */
    String name;
    private Timer t; // Timer for flip animation
    private int frameNum = 1; // Frame number for animations

    // Number of revolutions for flip animation
    private int revolutions = 0; 
    /**
     * Method paintComponent This method paints the object.
     *
     * @param g The graphics to paint to.
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform save = g2d.getTransform();

        setPreferredSize(dim);
        Image toPaint = null;
        toPaint = Images.valueOf(name).image;
        if(!faceUp){
            if(this instanceof TrainCard){
                toPaint = Images.train_card_back.image;
            }
            else if(this instanceof DestinationCard){
                toPaint = Images.dest_card.image;
            }
        }

        int x1 = toPaint.getWidth(this);
        int y1 = toPaint.getHeight(this);
        scaledX = (double)dim.width / x1;
        scaledY = (double)dim.height / y1;
        g2d.scale(scaledX, scaledY);

        if(t != null){
            double scaleX = faceUp ? frameNum / 30.0 : 1.0 - frameNum/30.0;

            double scaleWidth = (scaleX * toPaint.getWidth(this));
            double toTranslate = (toPaint.getWidth(this) - scaleWidth)/2.0;

            g2d.translate(toTranslate,0);
            g2d.scale(scaleX,1);

            g2d.drawImage(toPaint,0, 0, this);
        }
        else{
            g2d.drawImage(toPaint, 0, 0, this);
        }
        g2d.setTransform(save);
    }

    /**
     * Method equals This method determines if two cards are equal.
     *
     * @param c A card object whose equality will be checked.
     * @return True if they are equal and False otherwise
     */
    public boolean equals(Object c){
        if(c instanceof Card){
            return name.equals(((Card)c).name);
        }
        else if(c instanceof Images){
            Images card = (Images) c;
            return name.equals(c+ "");
        }
        return false;
    }

    /**
     * Method toString returns the name of the card.
     *
     * @return The name of a card
     */
    public String toString(){
        return name;
    }

    /**
     * Method flip this method does the sound and animation for flipping a
     * card
     *
     * @param d Takes a parameter of type Deck
     */
    public void flip(Deck d){
        new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream =
                            AudioSystem.getAudioInputStream(
                                Card.class.getResourceAsStream(
                                    "page-flip-16.wav"));
                        clip.open(inputStream);
                        clip.start(); 
                    } catch (Exception e) {
                        System.out.println("Not found");
                        System.err.println(e.getMessage());
                    }
                }
            }).start();

        faceUp = false;
        t = new Timer(10, new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    if(frameNum == 30){
                        frameNum = 1;

                        check(d);
                        if(revolutions != 2)
                            faceUp = true;
                    }
                    frameNum++;
                    repaint();
                }
            });
        t.start();
    }

    /**
     * Method check This method checks how many times a card has been
     * flipped so that it keep flipping a card.
     *
     * @param d  Takes a parameter of type Deck
     */
    public void check(Deck d){
        revolutions++;
        if(revolutions == 2){
            revolutions = 0;
            t.stop();
            t = null;
            frameNum = 1;
            d.remove(this);
            if(d.cards.size() == 0 && d.discardPile != null){
                d.useDiscardPile();
            }
            if(d.cards.size() > 0)
                d.add(d.cards.peek());
            d.newPaint();
        }
    }
}