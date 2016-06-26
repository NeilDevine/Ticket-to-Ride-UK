///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * Handles all of the operations related to decks of cards in our version 
 * of Ticket to Ride.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class Deck extends JComponent implements
MouseWheelListener
{
    Stack<Card> cards;
    boolean faceUp = true; // Whether the deck is facing up 
    //(cards are visible)
    Dimension dim;
    String type; // The type of deck
    Deck discardPile; // Discard pile for train card decks

    // Whether the deck has been reshuffled
    boolean reshuffled = false; 

    /**
     * Constructor for a desk object.
     * 
     * @param type Represents which type of deck is being created.
     * @param d Dimensions of the deck on the board.
     * 
     */
    public Deck(String type, Dimension d){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.type = type;
        dim = d;
        cards = new Stack<Card>();
        setPreferredSize(d);
        switch(type){
            case "destination":
            // Make a deck of destination cards
            cards.push(new DestinationCard(d,"aberdeen","glasgow",5));
            cards.push(new DestinationCard(d,"galway","barrow",12));
            cards.push(new DestinationCard(d,"belfast","manchester",9));
            cards.push(new DestinationCard(d,"edinburgh","birmingham",12));
            cards.push(new DestinationCard(d,"penzance","london",10));
            cards.push(new DestinationCard(d,"ullapool","dundee",4));
            cards.push(new DestinationCard(d,"londonderry","dublin",6));
            cards.push(new DestinationCard(d,"aberystwyth","cardiff",2));
            cards.push(new DestinationCard(d,"nottingham","ipswich",3));
            cards.push(new DestinationCard(d,"manchester","london",6));
            cards.push(new DestinationCard(d,"stornoway","glasgow",7));
            cards.push(new DestinationCard(d,"limerick","cardiff",12));
            cards.push(new DestinationCard(d,"glasgow","manchester",11));
            cards.push(new DestinationCard(d,"wick","dundee",4));
            cards.push(new DestinationCard(d,"london","france",7));
            cards.push(new DestinationCard(d,"inverness","leeds",13));
            cards.push(new DestinationCard(d,"holyhead","cardiff",4));
            cards.push(new DestinationCard(d,"edinburgh","london",15));
            cards.push(new DestinationCard(d,"glasgow","france",19));
            cards.push(new DestinationCard(d,"liverpool","hull",3));
            cards.push(new DestinationCard(d,"birmingham","cambridge",2));
            cards.push(new DestinationCard(d,"southampton","london",4));
            cards.push(new DestinationCard(d,"leeds","london",6));
            cards.push(new DestinationCard(d,"liverpool","southampton",6));
            cards.push(new DestinationCard(d,"birmingham","london",4));
            cards.push(new DestinationCard(d,"cambridge","london",3));
            cards.push(new DestinationCard(d,"cardiff","london",8));
            cards.push(new DestinationCard(d,"manchester","norwich",6));
            cards.push(new DestinationCard(d,"londonderry","stranraer",4));
            cards.push(new DestinationCard(d,"cork","leeds",13));
            cards.push(
                new DestinationCard(d,"fortwilliam","edinburgh",3));
            cards.push(
                new DestinationCard(d,"liverpool","llandrindodWells",6));
            cards.push(new DestinationCard(d,"wick","edinburgh",5));
            cards.push(new DestinationCard(d,"rosslare","aberystwyth",4));
            cards.push(new DestinationCard(d,"rosslare","carmarthen",6));
            cards.push(new DestinationCard(d,"galway","dublin",5));
            cards.push(new DestinationCard(d,"stranraer","tullamore",6));
            cards.push(new DestinationCard(d,"belfast","dublin",4));
            cards.push(new DestinationCard(d,"sligo","holyhead",9));
            cards.push(new DestinationCard(d,"manchester","plymouth",8));
            cards.push(new DestinationCard(d,"newcastle","southampton",7));
            cards.push(new DestinationCard(d,"dublin","london",15));
            cards.push(new DestinationCard(d,"cardiff","reading",4));
            cards.push(
                new DestinationCard(d,"londonderry","birmingham",15));
            cards.push(new DestinationCard(d,"glasgow","dublin",9));
            cards.push(new DestinationCard(d,"dundalk","carlisle",7));
            cards.push(new DestinationCard(d,"inverness","belfast",10));
            cards.push(new DestinationCard(d,"stornoway","aberdeen",5));
            cards.push(new DestinationCard(d,"bristol","southampton",2));
            cards.push(new DestinationCard(d,"norwich","ipswich",1));
            cards.push(new DestinationCard(d,"plymouth","reading",5));
            cards.push(new DestinationCard(d,"dublin","cork",6));
            cards.push(new DestinationCard(d,"london","brighton",3));
            cards.push(new DestinationCard(d,"northampton","dover",3));
            cards.push(new DestinationCard(d,"leeds","france",10));
            cards.push(new DestinationCard(d,"leeds","manchester",1));
            cards.push(new DestinationCard(d,"newcastle","hull",3));
            shuffle();

            break;

            case "technology":
            // Make a deck of Technology cards
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("wales_concession",d, 1));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("ireland_france_concession",
                        d, 1));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(
                    new TechnologyCard("scotland_concession",d, 1));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("mechanical_stoker",d, 1));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("superheated_steam_boiler",
                        d, 2));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("propellers",d, 2));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("booster",d, 2));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("boiler_lagging",d, 2));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("steam_turbine",d, 2));  
            }
            for (int i = 0; i < 4; i++){
                cards.push(new TechnologyCard("double_heading",d, 4));  
            }
            cards.push(new TechnologyCard("right_of_way",d, 4));  
            cards.push(new TechnologyCard("thermo_compressor",d, 1));  
            for(int i = 0; i < 2; i++){
                cards.push(new TechnologyCard("water_tenders", d, 2));
            }
            cards.push(new TechnologyCard("risky_contracts",d, 2));  
            cards.push(new TechnologyCard("equalising_beam",d, 2)); 
            cards.push(new TechnologyCard("diesel_power",d, 3));  
            break;

            case "train":
            // Make a deck of Train cards
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.pink));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.white));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.blue));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.yellow));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.orange));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.black));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.red));  
            }
            for (int i = 0; i < 12; i++){
                cards.push(new TrainCard(d, TrainCardTypes.green));  
            }
            for (int i = 0; i < 20; i++){
                cards.push(new TrainCard(d, TrainCardTypes.locamotive));  
            }
            shuffle();
            break;
        }

        add(cards.peek());
        newPaint();

        addMouseWheelListener(this);
    }

    /**
     * Deck Constructor
     *
     * @param d Dimensions of the deck for how it appears on the board.
     * @param discard Creates a separate deck of discarded cards.
     * @param numPlayers Used to identify the number of players that are 
     * in the game.
     */
    public Deck(Dimension d, Deck discard, int numPlayers){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        discardPile = discard;
        type = "train";
        dim = d;
        cards = new Stack<Card>();
        setPreferredSize(d);
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.pink));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.white));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.blue));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.yellow));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.orange));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.black));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.red));  
        }
        for (int i = 0; i < 12; i++){
            cards.push(new TrainCard(d, TrainCardTypes.green));  
        }
        for (int i = 0; i < 20 - numPlayers; i++){
            cards.push(new TrainCard(d, TrainCardTypes.locamotive));  
        }
        shuffle();
        add(cards.peek());
        newPaint();
    }

    /**
     * Rearranges cards in a deck.
     */
    public void shuffle(){
        remove(cards.peek());
        Collections.shuffle(cards);
        add(cards.peek());
    }

    /**
     * Withdraws a card from a deck.
     *
     * @param animate Used to determine whether or not a card movement 
     * needs to be animated or not.
     * @return The card that was drawn from the deck.
     */
    public Card drawCard(boolean animate){
        if(cards.size() == 0 && discardPile != null){
            useDiscardPile();
        }
        if(cards.size() > 0){

            Card c = cards.peek();
            //remove(c);
            if(animate){
                c.flip(this);
                //remove(c);
            }
            else{
                c.faceUp = true;
                remove(c);
            }

            cards.pop();
            //c.flip();
            //System.out.println("Cards left in deck: " + cards.size());
            if(cards.size() == 0 && discardPile != null){
                useDiscardPile();
            }
            if(cards.size() > 0)
                add(cards.peek());
            newPaint();
            return c;
        }
        newPaint();
        return null;
    }

    /**
     * Withdraws a card from a deck.
     *
     * @param animate Used to determine whether or not a card movement 
     * needs to be animated or not.
     * @param inDeck Used to determine whether or not card was in the 
     * deck. 
     * @return The card that was drawn from the deck.
     */
    public Card drawCard(boolean animate, boolean inDeck){
        if(cards.size() == 0 && discardPile != null){
            useDiscardPile();
        }
        if(cards.size() > 0){

            Card c = cards.peek();
            if(animate){
                c.flip(this);
            }
            else{
                c.faceUp = true;
                remove(c);
            }

            cards.pop();
            if(cards.size() == 0 && discardPile != null){
                useDiscardPile();
            }
            if(cards.size() > 0 && inDeck)
                add(cards.peek());
            newPaint();
            return c;
        }
        return null;
    }

    /**
     * Makes deck of discarded cards into a usable deck.
     */
    public void useDiscardPile(){
        reshuffled = true;
        System.out.println("Out of cards, using discard pile...");
        cards = copyOf(discardPile.cards);
        faceDown();
        discardPile.cards.clear();
        if(cards.size() > 0)
            add(cards.peek());
        newPaint();
        shuffle();
    }

    /**
     * Handles what happens if the mouse wheel scrolls on top of cards.
     *
     * @param e Used to represent scrolling of the mouse wheel.
     */
    public void mouseWheelMoved(MouseWheelEvent e){
        if(cards.size() > 0 && faceUp){
            remove(cards.peek());
            int direction = 0;
            if(e.getWheelRotation() > 0){
                direction = 1;
            }else if(e.getWheelRotation() < 0){
                direction = -1;
            }
            Collections.rotate(cards, direction);
            if(cards.size() > 0)
                add(cards.peek());
        }
        newPaint();
        e.consume();
    }

    /**
     * Can make all of the cards in a deck face down.
     */
    public void faceDown(){
        faceUp = false;
        Iterator it = cards.iterator();
        while(it.hasNext()){
            Card c = (Card)it.next();
            c.faceUp = false;
            c.dim = dim;
        }
        newPaint();
    }

    /**
     * Changes the dimensions of a deck of cards on the board.
     *
     * @param d Represents the dimensions of the deck of cards on the 
     * screen.
     */
    public void reSize(Dimension d){
        dim = d;
        setPreferredSize(dim);
        Iterator it = cards.iterator();
        while(it.hasNext()){
            Card c = (Card)it.next();
            c.dim = dim;
        }
    }

    /**
     * Adds a card to the deck.
     *
     * @param c Represents the card to be added.
     */
    public void addCard(Card c){
        if(cards.size() > 0)
            remove(cards.peek());
        c.dim = dim;
        cards.push(c);
        removeAll();
        if(cards.size() > 0)
            add(cards.peek());
        newPaint();
    }

    /**
     * Checks to see whether or not the deck contains a certain card.
     *
     * @param c The image of the card that you are checking the deck
     * for.
     * @return The boolean value as to whether the deck contains a 
     * certain card.
     */
    public boolean contains(Images c){
        return cards.contains(new TechnologyCard(c + "",null, 0));
    }

    /**
     * Puts a card at the bottom of the deck.
     *
     * @param c Specific card that is going to the bottom of the deck. 
     */
    public void pushToBottom(Card c){
        // This method puts a Card (specifically a destination card) at 
        // the bottom of 'cards'
        if(c instanceof DestinationCard){
            Stack<Card> newCards = new Stack<Card>();
            newCards.push(c);
            newCards.addAll(cards);
            cards = newCards;
            faceDown();
        }
    }

    /**
     * Makes a copy of a deck of cards.
     * 
     * @param toCopy Deck of cards to make a copy of.
     * @return The copy of the deck of cards.
     */
    public Stack<Card> copyOf(Stack<Card> toCopy){
        Stack<Card> toReturn = new Stack<Card>();
        Iterator it = toCopy.iterator();
        while(it.hasNext()){
            toReturn.push((Card)it.next());
        }
        return toReturn;
    }

    /**
     * Returns the amount of cards in the deck.
     *
     * @return The amount of cards in the deck.
     */
    public int cardsSize(){
        return cards.size();
    }

    /**
     * Repaints the deck of cards.
     */
    public void newPaint(){
        invalidate();
        validate();
        repaint();
    }
}
