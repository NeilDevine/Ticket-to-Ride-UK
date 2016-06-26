///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.ArrayList;
import javax.sound.sampled.*;

/**
 * This class is a JPanel for the Title Screen for Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class TitleScreen extends JPanel
{
    private Image img; // The background image
    private JFrame frame; // The main window
    private Dimension dim;
    private double scaledX, scaledY;

    // Polygons for each Beatle (for highlighting)
    private Polygon george, paul, john, ringo, highlighted;

    private Font font; // Custom font
    private Color foreground, background; // Custom colors

    // booleans for whether a player clicked a beatle
    private boolean georgeClicked, paulClicked, johnClicked, ringoClicked;

    // Whether the player is currently choosing a beatle
    private boolean choosing; 

    private int numPlayers = 0;

    // The train car colors which have already been taken
    private ArrayList<Color> colorsTaken = new ArrayList<Color>();

    // The names of each player
    private ArrayList<String> names = new ArrayList<String>();

    // The beatle for each player (in order)
    private ArrayList<String> beatles = new ArrayList<String>();

    private RadioButton selected = null;
    private JButton playGame, instructions; // JButtons for the panel
    private JTextField nameField = null; // Name field for each player
    private Clip clip; // Audio clip for ticket to ride :)

    private boolean inInstructions = false; // is instruction window open

    /**
     * Constructor for objects of class TitleScreen
     * @param screen the dimensions of the class TitleScreen
     * @param frame the JFrame object for the title screen
     */
    public TitleScreen(Dimension screen, JFrame frame){
        this.frame = frame;

        //closes the audio clip when the window is closed
        frame.addWindowListener(new WindowAdapter(){
                public void windowClosed(WindowEvent e){
                    clip.close();
                }
            });

        setLayout(null);

        int maxHeight = Math.min(1000, screen.height-70);

        Dimension picDim = new Dimension(screen.width, maxHeight);
        dim = picDim;
        setPreferredSize(dim);

        try{
            img = ImageIO.read(new File("photos/beatles.jpg"));
        }catch (Exception e){
            System.out.println("File Not Found");
        }

        //scales the image
        int x1 = img.getWidth(this);
        int y1 = img.getHeight(this);
        scaledX = (double)dim.width / x1;
        scaledY = (double)dim.height / y1;

        //assigns our color and font scheme to global variables
        background = new Color(102, 51, 51);
        foreground = new Color(255, 204, 51);
        font = new Font("Britannic Bold", Font.PLAIN, 40);

        //creates and scales the instructions button
        instructions = new JButton("How to Play");

        int bx = (int)(677 * scaledX);
        int by = (int)(913 * scaledY);
        int bw = (int)(scaledX * 217);
        int bh = (int)(scaledY * 66);

        //sets the size, color, and font of the button
        instructions.setBounds(bx, by,bw,bh);
        instructions.setBackground(background);
        instructions.setForeground(foreground);
        instructions.setFont(font);

        //creates the JFrame for the Instructions page
        JFrame instructionFrame = 
            new JFrame("Ticket to Ride - UK Instructions Screen");
        instructionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        instructionFrame.addWindowListener(new WindowAdapter(){
                public void windowClosed(WindowEvent e){
                    inInstructions = false;
                }
            });

        //when the instruction button is clicked on
        instructions.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // Show the instructions
                    if(inInstructions) return;

                    Dimension screen = 
                        Toolkit.getDefaultToolkit().getScreenSize();

                    instructionFrame.setSize(screen);
                    instructionFrame.setExtendedState(
                        JFrame.MAXIMIZED_BOTH);

                    Instructions instructionScreen = 
                        new Instructions(dim);

                    instructionFrame.getContentPane().add(
                        instructionScreen);

                    instructionFrame.pack();
                    instructionFrame.setVisible(true);
                    inInstructions = true;

                }
            });

        add(instructions);

        //creates the beatles polygon objects
        george = new Polygon();
        paul = new Polygon();
        john = new Polygon();
        ringo = new Polygon();

        george.addPoint(4,497);
        george.addPoint(63,452);

        george.addPoint(85,421);
        george.addPoint(37,318);
        george.addPoint(68,309);
        george.addPoint(72,262);
        george.addPoint(93,245);
        george.addPoint(167,244);
        george.addPoint(194,259);
        george.addPoint(204,316);
        george.addPoint(227,322);
        george.addPoint(177,423);
        george.addPoint(290,479);
        george.addPoint(418,811);
        george.addPoint(4,811);

        paul.addPoint(285,476);
        paul.addPoint(289,455);
        paul.addPoint(261,395);
        paul.addPoint(253,244);
        paul.addPoint(266, 217);
        paul.addPoint(338,190);
        paul.addPoint(350,171);
        paul.addPoint(318,86);
        paul.addPoint(335,73);
        paul.addPoint(337,41);
        paul.addPoint(351,25);
        paul.addPoint(409,19);
        paul.addPoint(428,28);
        paul.addPoint(438,61);
        paul.addPoint(460,74);
        paul.addPoint(412,182);
        paul.addPoint(418,200);
        paul.addPoint(475,254);
        paul.addPoint(520,417);
        paul.addPoint(482,453);
        paul.addPoint(470,449);
        paul.addPoint(480,496);
        paul.addPoint(480,600);
        paul.addPoint(461,811);
        paul.addPoint(378,811);

        john.addPoint(538,250);
        john.addPoint(598,210);
        john.addPoint(584,167);
        john.addPoint(578,164);
        john.addPoint(569,146);
        john.addPoint(575,68);
        john.addPoint(596,52);
        john.addPoint(618,48);
        john.addPoint(646,49);
        john.addPoint(668,67);
        john.addPoint(671,78);
        john.addPoint(682,88);
        john.addPoint(693,103);
        john.addPoint(694,136);
        john.addPoint(668,187);
        john.addPoint(667,204);
        john.addPoint(688,222);
        john.addPoint(765,245);
        john.addPoint(802,406);
        john.addPoint(759,431);
        john.addPoint(737,553);
        john.addPoint(722,558);
        john.addPoint(740,648);

        john.addPoint(764,706);
        john.addPoint(753,804);

        john.addPoint(577,811);
        john.addPoint(538,486);
        john.addPoint(528,391);

        ringo.addPoint(800,808);
        ringo.addPoint(812,774);
        ringo.addPoint(774,730);
        ringo.addPoint(740,648);
        ringo.addPoint(722,558);
        ringo.addPoint(737,553);
        ringo.addPoint(759,431);
        ringo.addPoint(802,406);

        ringo.addPoint(853,379);
        ringo.addPoint(861,363);
        ringo.addPoint(859,304);
        ringo.addPoint(846,286);
        ringo.addPoint(849,275);
        ringo.addPoint(839,245);
        ringo.addPoint(840,211);
        ringo.addPoint(861,184);
        ringo.addPoint(886,182);
        ringo.addPoint(905,167);
        ringo.addPoint(926,162);
        ringo.addPoint(958,173);
        ringo.addPoint(983,195);
        ringo.addPoint(988,207);
        ringo.addPoint(989,221);
        ringo.addPoint(1003,249);
        ringo.addPoint(997,286);
        ringo.addPoint(976,314);
        ringo.addPoint(950,328);
        ringo.addPoint(945,351);
        ringo.addPoint(969,373);
        ringo.addPoint(1022,394);
        ringo.addPoint(1022,801);

        george = scale(george);
        paul = scale(paul);
        john = scale(john);
        ringo = scale(ringo);

        Adapter ad = new Adapter();

        //add a mouse listener
        addMouseMotionListener(ad);

        addMouseListener(ad);

        //creates and scales the Play Game button
        playGame = new JButton("Play Game");
        bx = (int)(53 * scaledX);
        by = (int)(820 * scaledY);
        bw = (int)(scaledX * 495);
        bh = (int)(scaledY * 190);

        playGame.setBounds(bx, by,bw,bh);
        playGame.setBackground(background);
        playGame.setForeground(foreground);
        playGame.setFont(font);

        //when the Play Game button is clicked
        playGame.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evv){
                    img = null; // Free the image

                    javax.swing.SwingUtilities.invokeLater(new Runnable(){
                            //runs the game
                            public void run() {
                                frame.getContentPane().removeAll();

                                MainPanel mainPanel = new MainPanel(
                                        screen, 
                                        frame,
                                        numPlayers,
                                        colorsTaken,
                                        names, beatles);

                                frame.add(mainPanel);
                                frame.invalidate();
                                frame.validate();
                                frame.pack();

                                //when the window is closed
                                frame.addWindowListener(
                                    new WindowAdapter(){
                                        public void windowClosed(
                                        WindowEvent e){
                                            Images.close();
                                        }
                                    });

                                System.gc();
                                clip.stop();
                                clip.close();

                                Thread t = new Thread(new Runnable(){
                                            public void run(){
                                                //gameplay
                                                mainPanel.play();
                                            }
                                        });
                                System.gc();
                                t.start();
                            }
                        });
                }
            });
        new Thread(new Runnable() {
                public void run() {
                    try{
                        clip = AudioSystem.getClip();
                        AudioInputStream inputStream = 
                            AudioSystem.getAudioInputStream(
                                TitleScreen.class.getResourceAsStream(
                                    "beatles.wav"));

                        clip.open(inputStream);
                        clip.start(); 
                    }catch (Exception e) {
                        System.out.println("Not found");
                    }
                }
            }).start();
    }

    /**
     * Method to scale a Polygon object
     * @param p the Polygon object to be scaled
     * @return the new Polygon object
     */
    public Polygon scale(Polygon p){
        Polygon toRet = new Polygon();
        int[] xArr = scaleArr(p.xpoints, scaledX);
        int[] yArr = scaleArr(p.ypoints, scaledY);
        return new Polygon(xArr,yArr,p.npoints);
    }

    /**
     * Method to scale an array of integers by a given number
     * @param toScale an array of integers to scale
     * @param num the number to scale by
     * @return the new scaled array of integers
     */
    private int[] scaleArr(int[] toScale, double num){
        int[] toRet = Arrays.copyOf(toScale, toScale.length);
        for(int i = 0; i < toScale.length; i++){
            toRet[i] = (int) (toScale[i] * num);
        }
        return toRet;
    }

    /**
     * Paint method for the TitleScreen class
     * @param g the Graphics object
     */
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform save = g2d.getTransform();

        g2d.scale(scaledX, scaledY);
        g2d.drawImage(img, 0, 0, null);
        g2d.setTransform(save);

        //highlights the beatle
        if(highlighted != null){
            g2d.setColor(Color.YELLOW);
            g2d.drawPolygon(highlighted);
        }

    }

    /**
     * Main method for the TitleScreen class
     * @param args an array of command line arguments
     */
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

    /**
     * Creates and shows the GUI
     */
    public static void createAndShowGUI(){
        JFrame frame = new JFrame("Ticket to Ride- UK");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension newScreen = new Dimension(1366,768);

        frame.setLayout(new CardLayout());

        frame.setSize(screen);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        TitleScreen titleScreen = new TitleScreen(screen, frame);
        frame.getContentPane().add(titleScreen);
        frame.pack();
        frame.setVisible(true);
    }

    private class Adapter extends MouseAdapter
    {
        //when the mouse is moved over a beatle
        public void mouseMoved(MouseEvent e){
            if(choosing) return;
            if(george.contains(e.getPoint())){
                highlighted = george;
            }
            else if(paul.contains(e.getPoint())){
                highlighted = paul;
            }
            else if(john.contains(e.getPoint())){
                highlighted = john;
            }
            else if(ringo.contains(e.getPoint())){
                highlighted = ringo;
            }
            else{
                highlighted = null;
            }
            repaint();
        }

        //when the mouse clicks on a beatle
        public void mouseClicked(MouseEvent e){
            if(choosing) return;
            nameField = null;
            int x2, y2;
            x2 = y2 = 0;
            if(highlighted == george && !georgeClicked){
                nameField = new JTextField("George");
                int x = (int)(81 * scaledX);
                int y = (int)(345 * scaledY);
                int w = (int)(110 * scaledX);
                int h = (int)(45 * scaledY);
                nameField.setBounds(x,y,w,h); // Change the bounds

                x2 = (int)(45 * scaledX);
                y2 = (int)(465 * scaledY);

                georgeClicked = true;
                beatles.add("george");
            }
            else if(highlighted == paul && !paulClicked){
                nameField = new JTextField("Paul");
                int x = (int)(358 * scaledX);
                int y = (int)(100 * scaledY);
                int w = (int)(110 * scaledX);
                int h = (int)(45 * scaledY);
                nameField.setBounds(x,y,w,h); // Change the bounds

                x2 = (int)(227 * scaledX);
                y2 = (int)(237 * scaledY);

                paulClicked = true;
                beatles.add("paul");
            }
            else if(highlighted == john && !johnClicked){
                nameField = new JTextField("John");
                int x = (int)(580 * scaledX);
                int y = (int)(130 * scaledY);
                int w = (int)(110 * scaledX);
                int h = (int)(45 * scaledY);
                nameField.setBounds(x,y,w,h); // Change the bounds

                x2 = (int)(523 * scaledX);
                y2 = (int)(264 * scaledY);

                johnClicked = true;
                beatles.add("john");
            }
            else if(highlighted == ringo && !ringoClicked){
                nameField = new JTextField("Ringo");
                int x = (int)(858 * scaledX);
                int y = (int)(253 * scaledY);
                int w = (int)(110 * scaledX);
                int h = (int)(45 * scaledY);
                nameField.setBounds(x,y,w,h); // Change the bounds

                x2 = (int)(736 * scaledX);
                y2 = (int)(420 * scaledY);

                ringoClicked = true;
                beatles.add("ringo");
            }

            if(nameField != null){
                // The user clicked on a beatle
                choosing = true;
                nameField.setForeground(foreground);
                Color newBack = new Color(
                        background.getRed(),
                        background.getBlue(),
                        background.getGreen(),
                        150);
                nameField.setBackground(newBack);
                nameField.setFont(font);
                add(nameField);

                JPanel colorChooser = new JPanel();

                int w = (int)(350 * scaledX);
                int h = (int)(150 * scaledY);;
                colorChooser.setBounds(x2,y2,w,h);
                colorChooser.setOpaque(false);
                colorChooser.setLayout(new BoxLayout(
                        colorChooser, BoxLayout.X_AXIS));

                int imageScaleX = (int)(40 * scaledX);
                int imageScaleY = (int)(40 * scaledY);;

                //choosing the piece color
                Image trainCar = null;
                try{
                    trainCar = ImageIO.read(
                        new File("photos/trainCar.png"));
                    trainCar = trainCar.getScaledInstance(
                        imageScaleX, imageScaleY, Image.SCALE_SMOOTH);
                }catch(Exception ex){
                    System.out.println("File not found");
                }

                ImageIcon icon = new ImageIcon(trainCar);

                RadioButton red = new RadioButton("Red", Color.RED, icon);
                RadioButton blue = new RadioButton(
                        "Blue", Color.BLUE, icon);
                RadioButton green = new RadioButton(
                        "Green", Color.GREEN, icon);
                RadioButton black = new RadioButton(
                        "Black", Color.BLACK, icon);

                Dimension size = new Dimension(50,50);
                blue.setPreferredSize(size);
                green.setPreferredSize(size);
                black.setPreferredSize(size);

                ButtonGroup buttons = new ButtonGroup();
                if(!colorsTaken.contains(Color.RED)){
                    buttons.add(red);
                    if(selected == null){
                        red.setSelected(true);
                        selected = red;
                    }
                }
                if(!colorsTaken.contains(Color.BLUE)){
                    buttons.add(blue);
                    if(selected == null){
                        blue.setSelected(true);
                        selected = blue;
                    }
                }
                if(!colorsTaken.contains(Color.GREEN)){
                    buttons.add(green);
                    if(selected == null){
                        green.setSelected(true);
                        selected = green;
                    }
                }
                if(!colorsTaken.contains(Color.BLACK)){
                    buttons.add(black);
                    if(selected == null){
                        black.setSelected(true);
                        selected = black;
                    }
                }

                if(!colorsTaken.contains(Color.RED)){
                    colorChooser.add(red);
                }
                if(!colorsTaken.contains(Color.BLUE)){
                    colorChooser.add(blue);
                }
                if(!colorsTaken.contains(Color.GREEN))
                    colorChooser.add(green);
                if(!colorsTaken.contains(Color.BLACK))
                    colorChooser.add(black);
                add(colorChooser);

                //creates and scales the Done button
                JButton done = new JButton("Finish");

                int bx = x2 + 30;
                int by = y2 + 150;
                int bw = (int)(scaledX * 160);
                int bh = (int)(scaledY * 50);

                done.setBounds(bx, by,bw,bh);
                done.setBackground(newBack);
                done.setForeground(foreground);
                done.setFont(font);

                //when the done button is clicked
                done.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent ev){
                            choosing = false;
                            colorsTaken.add(selected.color);
                            names.add(nameField.getText());
                            selected = null;
                            numPlayers++;

                            removeAll();
                            if(numPlayers > 1){
                                add(playGame);
                            }
                            add(instructions);
                            invalidate();
                            validate();
                            repaint();
                        }
                    });
                add(done);
            }
            invalidate();
            validate();
            repaint();
        }
    }

    /**
     * Custom Radio Button Class (for picking train colors)
     */
    private class RadioButton extends JRadioButton
    implements ActionListener
    {
        Color color;

        /**
         * Constructor for the RadioButton class
         * @param name the name of the radio button
         * @param c the color of the radio button
         * @param icon the icon of the radio button
         */
        public RadioButton(String name, Color c, ImageIcon icon){
            super(name,icon, false);
            color = c;
            setActionCommand(name);
            addActionListener(this);
        }

        /**
         * Method for when the user clicks on a radio button
         * @param e the ActionEvent object
         */
        public void actionPerformed(ActionEvent e){
            // The user clicked
            setSelected(true);
            selected = this;
        }

        /**
         * The paint method for the RadioButton class
         * @param g the Graphics object
         */
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            Color newColor = new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    (isSelected()) ? 170 : 100);
            g.setColor(newColor);
            g.fillRect(0,0,getWidth(), getHeight());
        }
    }

}
