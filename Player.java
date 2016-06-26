///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.imageio.ImageIO;
/**
 * This Class represents a player in Ticket To Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class Player extends JPanel
{
    String name; // The name of the Player
    JPanel handPanel; // Panel for a Player's hand of train cards
    TrainPanel tp; // Panel for train cars
    JPanel scorePanel; // Panel for displaying the score

    ArrayList<TrainCard> trainCards;

    // The Player's Technology Deck
    Deck techDeck;

    // The Player's Destination Deck
    Deck destDeck;

    Color color; // The Player's color
    Dimension dim;

    // Whether the Player has the Booster tech card
    boolean hasBooster;

    // Whether the Player has the Boiler Lagging tech card
    boolean hasLagging;

    // Whether the Player has the Steam Turbines tech card
    boolean hasTurbines;

    // Whether the Player has the Double Heading tech card
    boolean hasHeading;

    // Whether the Player has the Right of Way tech card
    boolean hasWay;

    // Whether the Player has the Thermocompressor tech card
    boolean usingThermo;

    // Whether the Player has the Risky Contracts tech card
    boolean hasContracts;

    // Whether the Player has the Equalising Beam tech card
    boolean hasBeam;

    int numTrains; // The number of trains

    int score; // The Player's current score
    JLabel scoreLabel; // A JLabel for the score

    Image trainCar; // An image for a train car
    JButton convertButton; // A button for converting locomotives

    /**
     * The constructor for Player panels. 
     * The player panel is on the right
     * side of the screen in the main panel in the main game.
     * @param d The to-be dimension of the Player Panel
     * @param name The name of this player
     */
    public Player(Dimension d, String name){
        this.name = name;
        numTrains = 35; // Set to the maximum number of trains
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setMinimumSize(d);
        int width = (int)(d.height * .284);
        dim = new Dimension(width, d.height);

        int cardWidth = dim.width / 2 - 7;
        int cardHeight = (int)(cardWidth * .66);

        Dimension cardDim = new Dimension(cardWidth,cardHeight);
        Dimension deckDim =
            new Dimension((int)(cardWidth * 1.2), 
                (int)(cardHeight * 1.2));
        Dimension deckDim2 = 
            new Dimension(deckDim.height, 
                deckDim.width);

        handPanel = new JPanel();
        handPanel.setOpaque(false);
        handPanel.setPreferredSize(
            new Dimension(dim.width, cardHeight * 6));
        handPanel.setMaximumSize( handPanel.getPreferredSize() );

        trainCards = new ArrayList<TrainCard>();
        for(int i = 0; i < TrainCardTypes.values().length; i++){
            TrainCard card = new TrainCard(cardDim, 
                    TrainCardTypes.values()[i]);
            card.hasNum = true;
            handPanel.add(card);
            trainCards.add(card);
        }
        trainCards.get(8).num = 1;

        destDeck = new Deck("destination", deckDim2);
        techDeck = new Deck("technology", deckDim);

        destDeck.cards.clear();
        destDeck.removeAll();
        techDeck.cards.clear();
        techDeck.removeAll();

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setPreferredSize(
            new Dimension(dim.width, 
                destDeck.getPreferredSize().height + 10));
        p2.add(destDeck);
        p2.add(techDeck);

        Dimension trainPanelSize = 
            new Dimension(d.width,
                (d.height - 
                    handPanel.getPreferredSize().height
                    - p2.getPreferredSize().height)/2);
        tp = new TrainPanel(trainPanelSize);
        tp.setMaximumSize( tp.getPreferredSize() );

        scorePanel = new JPanel();
        scorePanel.setPreferredSize(trainPanelSize);
        scorePanel.setMaximumSize(trainPanelSize);
        scorePanel.setOpaque(false);

        scoreLabel = new JLabel(name + " Score: " + score);
        scoreLabel.setFont(new Font("", 1, 20));
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel);

        add(handPanel);
        add(p2);
        add(tp);
        add(scorePanel);

        Image convertLogo = null;
        try{
            trainCar =
            ImageIO.read(new File("photos/trainCar.png"));
            convertLogo =
            ImageIO.read(new File("photos/vintage-train-image.png"));
            convertLogo = 
            convertLogo.getScaledInstance(
                cardDim.width, cardDim.height, Image.SCALE_SMOOTH);
        }catch(Exception e){
            System.out.println("File not found");
        }

        ImageIcon convertLogoIcon = new ImageIcon(convertLogo);
        convertButton = new JButton("Convert", convertLogoIcon);
        convertButton.setMaximumSize(cardDim);
        convertButton.setPreferredSize(cardDim);
        convertButton.setOpaque(false);
        convertButton.setContentAreaFilled(false);

        handPanel.add(convertButton);
    }

    /**
     * This method determines if the player may purchase 
     * a given technology card 'c'.
     * @param c The technology card in question
     * @return Whether this player may purchase 'c'
     */
    public boolean canPurchase(TechnologyCard c){
        return trainCards.get(8).num >= c.cost &&
        !techDeck.contains(Images.valueOf(c.name));
    }

    /**
     * This method allows the user to buy a technology card 'c'.
     * @param c The technology card to purchase
     * @param discard The discard pile to discard to
     */
    public void purchase(TechnologyCard c, Deck discard){
        if(c.name.equals("booster")){
            hasBooster = true;
        }
        else if(c.name.equals("boiler_lagging")){
            hasLagging = true;
        }
        else if(c.name.equals("steam_turbines")){
            hasTurbines = true;
        }
        else if(c.name.equals("double_heading")){
            hasHeading = true;
        }
        else if(c.name.equals("risky_contracts")){
            hasContracts = true;
        }
        else if(c.name.equals("equalising_beam")){
            hasBeam = true;
        }
        else if(c.name.equals("right_of_way")){
            hasWay = true;
            discardLocos(c.cost, discard);
            return;
        }
        else if(c.name.equals("thermo_compressor")){
            usingThermo = true;
            discardLocos(c.cost, discard);
            return;
        }
        techDeck.addCard(c);
        TrainCard loco = trainCards.get(8);

        discardLocos(c.cost, discard);
    }

    /**
     * This method determines if the player may conquer 'r'.
     * @param The road in question
     * @return Whether this player may conquer 'r'
     */
    public boolean canConquer(Road r){
        City a = r.cityA;
        City b = r.cityB;
        boolean can = true;
        if(a.country == 'w' || b.country == 'w'){
            can = can &&
            techDeck.contains(Images.wales_concession);
        }
        if(a.country == 'I' ||
        b.country == 'I' || a.country == 'F' || b.country == 'F'){
            can = can &&
            techDeck.contains(Images.ireland_france_concession);
        }
        if(a.country == 's' || b.country == 's'){
            can = can && 
            techDeck.contains(Images.scotland_concession);
        }
        if(r.length() == 3){
            can = can &&
            techDeck.contains(Images.mechanical_stoker);
        }
        if(r.length() > 3 && r.length() < 7){
            can = can && 
            techDeck.contains(Images.superheated_steam_boiler);
        }
        if(r.locosRequired > 0){
            can = can && techDeck.contains(Images.propellers);
        }
        if(r.occupying.size() > 0 && !r.occupying.contains(this)){
            can = can && hasWay;
        }
        if(r.occupying.contains(this)){
            return false;
        }
        if(r.length() > numTrains){
            return false;
        }

        return can;
    }

    /**
     * This method allows the player to conquer a road 'r'
     * given which cards were used to conquer it.
     * @param r The road to conquer or claim
     * @param cardsUsed Which train cards were used to conquer 'r'
     * @param discard The discard deck to discard to
     */
    public void conquer(Road r, int[] cardsUsed, Deck discard){
        ArrayList<Card> used = new ArrayList<Card>();
        TrainCardTypes[] values = TrainCardTypes.values();
        int cardsRequired = r.length();
        if(techDeck.contains(
            Images.diesel_power) && cardsRequired > 1){
            cardsRequired--;
        }

        if(r.color != Color.GRAY){
            for(int i = 0; i < cardsUsed.length; i++){
                if(r.color == values[i].color){
                    trainCards.get(i).num -= cardsUsed[i];
                    cardsRequired -= cardsUsed[i];
                    for(int j = 0; j < cardsUsed[i]; j++){
                        discard.addCard(
                            new TrainCard(null,
                                trainCards.get(i).type));
                    }
                }
            }
            TrainCard loco = trainCards.get(8);
            discardLocos(cardsRequired, discard);
        }
        if(r.color == Color.GRAY){
            int maxIndex = maxArr(cardsUsed, 0, 8);
            int nonLocosNeeded = cardsRequired - r.locosRequired;
            int nonLocosUsed = 
                Math.min(cardsUsed[maxIndex], nonLocosNeeded);
            int locosUsed = cardsRequired - nonLocosUsed;

            trainCards.get(maxIndex).num -= nonLocosUsed;
            for(int j = 0; j < nonLocosUsed; j++){
                discard.addCard(new TrainCard(
                        null, trainCards.get(maxIndex).type));
            }
            discardLocos(locosUsed , discard);
        }
        r.claim(this);

        updateScore(r);
        tp.removeTrains(r.length());
    }

    /**
     * This method discards into a discard pile the correct
     * number of locomotives given a certain number of them.
     * @param howMany How many locomotives were used
     * @param discard The discard pile to discard to
     */
    private void discardLocos(int howMany, Deck discard){
        TrainCard loco = trainCards.get(8);
        for(int j = 0; j < howMany; j++){
            if(loco.howManyDontCount > 0){
                loco.num--;
                loco.howManyDontCount--;
            }
            else{
                loco.num--;
                discard.addCard(
                    new TrainCard(null, TrainCardTypes.locamotive));
            }
        }
    }

    /**
     * This method is merely a helper method for determining
     * the max value in a certain range
     * @param a The integer array in question
     * @param start The start index of the range
     * @param end The end index of the range
     */
    private int maxArr(int[] a, int start, int end){
        int max = Integer.MIN_VALUE;
        int maxIndex = 0;
        for(int i = start; i < end; i++){
            if(a[i] > max){
                max = a[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    /**
     * This method updates the score based on the length
     * of a claimed road.
     */
    private void updateScore(Road r){
        switch(r.length()){
            case 1: case 2:
            score += r.length();
            break;
            case 3:
            score += 4;
            break;
            case 4:
            score += 7;
            break;
            case 5:
            score += 10;
            break;
            case 6:
            score += 15;
            break;
            case 10:
            score += 40;
            break;
        }
        if(hasLagging)
            score++;
        if(hasTurbines && r.locosRequired > 0)
            score += 2;
        updateScore();
    }

    /**
     * This method updates the score panel and 
     * label based on the current score.
     */
    public void updateScore(){
        scorePanel.remove(scoreLabel);
        scoreLabel = new JLabel(name + " Score: " + score);
        scoreLabel.setFont(new Font("", 1, 20));
        scoreLabel.setForeground(Color.WHITE);
        scorePanel.add(scoreLabel);
    }

    /**
     * This method sets the score of this player
     * @parm s The new score
     */
    public void setScore(int s){
        score = s;
        updateScore();
    }

    /**
     * This method adds a given train 
     * or destination card to the player.
     * @param c The card to add
     */
    public void addCard(Card c){
        for(MouseListener listener : c.getMouseListeners()){
            if(!(listener instanceof MainPanel.destHover))
                c.removeMouseListener(listener);
        }

        for(MouseMotionListener listener :
        c.getMouseMotionListeners()){
            if(!(listener instanceof MainPanel.destHover))
                c.removeMouseMotionListener(listener);
        }

        if(c instanceof TrainCard){
            TrainCardTypes type = ((TrainCard)c).type;
            trainCards.get(type.index).num++;
        }
        else if(c instanceof DestinationCard){

            destDeck.addCard(c);
        }
    }

    /**
     * This method adds a destination or train card to this player.
     * @param c The card to add
     * @param counts Whether this card should 
     * count towards the discard pile
     */
    public void addCard(Card c, boolean counts){
        if(c instanceof TrainCard){
            TrainCardTypes type = ((TrainCard)c).type;

            trainCards.get(type.index).num++;
            if(!counts && type.index == 8)
                trainCards.get(type.index).howManyDontCount++;
        }
        else if(c instanceof DestinationCard){
            destDeck.addCard(c);
        }
    }

    /**
     * This method determines if the player may convert
     * a given set of train cards into a locomotive
     * @param cardsUsed Array of train cards to convert
     * @return Whether this player may
     * convert 'cardsUsed' to a locomotive
     */
    public boolean canConvert(int[] cardsUsed){
        if(cardsUsed.length != 8) return false;
        int cardsNeeded = hasBooster ? 3 : 4;
        for(int i = 0; i < 8; i++){
            cardsNeeded -= cardsUsed[i];
            if(cardsNeeded == 0) return true;
        }
        return false;
    }

    /**
     * This method allows users to convert a given set of 
     * train cards into a locomotive
     * @param cardsUsed Array of train cards to convert
     * @param discard The discard deck for train cards
     */
    public void convert(int[] cardsUsed, Deck discard){
        // Converts normal train cards to a locamotive
        if(cardsUsed.length != 8) return;
        for(int i = 0; i < 8; i++){
            trainCards.get(i).num -= cardsUsed[i];
            for(int j = 0; j < cardsUsed[i]; j++){
                discard.addCard(new TrainCard(null, 
                        trainCards.get(i).type));
            }
        }
        trainCards.get(8).num++;
        trainCards.get(8).howManyDontCount++;
    }

    /**
     * This method determines if the Component 'comp'
     * resides within the player panel.
     * @param comp The component in question
     * @return Whether 'comp' descends from this
     */
    public boolean contains(Component comp){
        return SwingUtilities.isDescendingFrom(comp,this);
    }

    /**
     * This class is a JPanel which displays a player's
     * current train cars
     */
    private class TrainPanel extends JPanel
    {
        Stack<Train> trains;

        /**
         * Constructs the Train panel for displaying train cars.
         * @param d The dimension of this panel
         */
        public TrainPanel(Dimension d){
            this.setPreferredSize(d);
            setOpaque(false);
            trains = new Stack<Train>();
            for(int i = 0; i < numTrains; i++){
                Train t = new Train(d);
                trains.push(t);
                add(t);
            }
        }

        /**
         * This method removes 'numTimes' trains.
         * @param numTimes how many trains to remove
         */
        public void removeTrains(int numTimes){
            numTrains-=numTimes;
            for(int i = 0; i < numTimes; i++)
                remove(trains.pop());
        }

        /**
         * This class is a JComponent for 
         * each train in a train panel
         */
        private class Train extends JComponent
        {
            Dimension dim;

            /**
             * This is the constructor for each train.
             * @param d The dimension of the train
             */
            public Train(Dimension d){
                dim = new Dimension(30, 15);
                setPreferredSize(dim);
            }

            /**
             * This method paints the actual train.
             * @param g The graphics object to paint to
             */
            protected void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g;
                AffineTransform save = g2d.getTransform();

                int x1 = trainCar.getWidth(this);
                int y1 = trainCar.getHeight(this);
                double scaledX = (double)dim.width / x1;
                double scaledY = (double)dim.height / y1;

                g2d.setColor(new Color(
                        color.getRed(), color.getGreen(),
                        color.getBlue(), 100));
                g2d.fillRect(0,0,dim.width,dim.height);
                g2d.scale(scaledX, scaledY);

                g2d.drawImage(trainCar,0,0,this);
                g2d.setTransform(save);
            }
        }
    }
}
