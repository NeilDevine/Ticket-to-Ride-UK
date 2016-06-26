///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
/**
 * This class represents the main jpanel for in-game activities 
 * in Ticket to Ride - UK. The main panel is split into 
 * three sections: the game panel, board, and Player panel.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class MainPanel extends JPanel
{
    private JFrame frame; // The main window

    private Board board; // Game board object
    private Dimension dim;

    // Panel for the current player (right side)
    private JPanel playerPanel = new JPanel(); 

    // Panel for the game board
    private JPanel boardPanel = new JPanel(); 

    // Panel for displaying game-related information and buttons
    private GamePanel gamePanel; 

    // LayeredPane for displaying layers
    private JLayeredPane layeredPane; 

    // JPanel for a raised layer
    private JPanel layeredPanel = new JPanel(); 

    // Each player object
    private ArrayList<Player> players = new ArrayList<Player>();

    private Player currentPlayer; // The current Player object
    private int playerNum; // The index of the current player

    // List of every player's beatle in order
    private ArrayList<String> beatles = new ArrayList<String>();

    private volatile int phase; // The current 'phase' of the turn
    private Image background;
    private Image cloud;
    private ImageIcon convertLogo;

    // The number of train cards a player drew on a turn
    private int trainCardsDrawn; 

    private ArrayList<TrainCard> handCopy=new ArrayList<TrainCard>();

    // Array for train cards used in claiming roads
    private int[] cardsUsed = new int[9]; 

    // Whether the layer is currently open
    private boolean inLayer = false; 

    // Whether the player may exit the layer
    private boolean canExit = true; 

    // Whether it is the first turn for the current player
    private boolean firstTurn; 

    // Whether it is the last turn for the current player
    private  boolean lastTurn; 
    private boolean enabled = true; // Whether input is enabled

    // Whether the player is taking destination cards (on first turn)
    private boolean takingDest = false;

    // Whether the player is taking destination cards (in game)
    private boolean takingDest2 = false; 

    // Whether the Player is holding a card (dnd)
    private boolean holding = false; 
    // The card a player is holding (dnd)
    private Card cardHolding = null; 

    private DragAndDrop dnd; // Drag and Drop input adapter
    private Point mouseAt = new Point(); // For drag and drop

    // For dnd support
    private boolean firstDest;

    // Whether the game is finished
    private boolean finished = false; 
    /**
     * The constructor for main panels in Ticket to Ride.
     * 
     * @param screen The dimension of the screen (for scaling)
     * @param frame The main window for the application
     * @param numPlayers The number of players who will be playing
     * @param colorsTaken An ordered list of colors for each player
     * @param names An ordered list of names for each player
     * @param beatles An ordered list of beatles for each player
     */
    public MainPanel(Dimension screen, JFrame frame, int numPlayers, 
    ArrayList<Color> colorsTaken, ArrayList<String> names,
    ArrayList<String> beatles){
        dnd = new DragAndDrop(this);
        this.frame = frame;
        dim = screen;
        this.beatles = beatles;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        layeredPane = frame.getLayeredPane();

        int maxHeight = Math.min(1000, screen.height - 70);
        int boardWidth = (int)(maxHeight * (double)2/3);
        int sideWidth = (screen.width - boardWidth) / 2;

        Dimension boardDim = new Dimension(boardWidth, maxHeight);
        Dimension sideDim = new Dimension(sideWidth, maxHeight);

        for(int i = 0; i < numPlayers; i++){
            Player p = new Player(sideDim, names.get(i));
            p.setPreferredSize(sideDim);

            p.convertButton.addActionListener(new ConvertLocos());
            players.add(p);
        }
        currentPlayer = players.get(0);
        playerNum = 0;

        boardPanel.setPreferredSize(boardDim);
        boardPanel.setOpaque(false);
        boolean doubleRoutes = numPlayers >= 3;
        System.gc();
        board = new Board(boardDim, doubleRoutes);
        board.setPreferredSize(boardDim);

        boardPanel.add(board);

        gamePanel = new GamePanel(sideDim);
        gamePanel.setPreferredSize(sideDim);

        add(gamePanel);
        add(boardPanel);
        add(currentPlayer);

        // setBorder(BorderFactory.createLineBorder(Color.WHITE));

        try{
            background = 
            ImageIO.read(new File("photos/background.jpg"));
            cloud = ImageIO.read(new File("photos/cloud.png"));
        }catch(Exception e){
            System.out.println("File not found");
        }
        background = 
        background.getScaledInstance(screen.width,
            screen.height, Image.SCALE_SMOOTH);

        for(int i = 0; i < colorsTaken.size(); i++){
            players.get(i).color = colorsTaken.get(i);
        }

        addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    if(finished) return;

                    Component component = 
                        getComponentAt(e.getPoint());
                    if(canExit){
                        if(takingDest){
                            for(Component comp : 
                            layeredPanel.getComponents()){
                                if(comp instanceof DestinationCard){
                                    Card c = (Card)comp;
                                    Deck d = gamePanel.destDeck;
                                    d.pushToBottom(c);
                                }
                            }
                        }
                        if(takingDest2){
                            phase = 2;
                            takingDest2 = false;
                        }
                        takingDest = false;
                        if(phase != 2)
                            enableInput();
                        outOfLayer();
                        board.roadClicked = null;
                        layeredPane.remove(layeredPanel);
                        for(Component comp :
                        layeredPane.getComponentsInLayer(
                            JLayeredPane.PALETTE_LAYER)){
                            layeredPane.remove(comp);
                        }
                    }
                    repaint();
                    e.consume();
                }
            });

        addMouseListener(dnd);
        addMouseMotionListener(dnd);
    }

    /**
     * This method begins the main play loop in Ticket to Ride.
     * That is: first turns for each player, turns for each player,
     * final turns for each player, and end game results.
     */
    public void play(){
        int turnNum = 1;
        firstTurn = true;
        playTurn();
        do{
            switchPlayer();
            if(turnNum < players.size()){
                turnNum++;
            }
            else{
                firstTurn = false;
            }
            playTurn();
        }while(!endOfGame());

        // Last x turns : x = players.size()

        System.out.println("Last turns...");
        lastTurn = true;
        switchPlayer();
        for(int i = 0; i < players.size(); i++){
            playTurn();
            if(i != players.size()-1)
                switchPlayer();
        }

        // Game over, add up points

        Player playerWithMaxTickets = players.get(0);
        int maxTickets = 0;

        for(Player p : players){
            int score = p.score;
            // For Destination Cards
            int ticketsCompleted = 0;
            Iterator it = p.destDeck.cards.iterator();
            while(it.hasNext()){
                DestinationCard card = (DestinationCard)it.next();
                City a = board.getCity(card.cityA);
                City b = board.getCity(card.cityB);
                boolean connected = board.isConnected(a,b,p);
                if(b.getName().equals("France")){
                    connected = connected || 
                    board.isConnected(a,board.getSecondFrance(), p);
                }
                score += connected ? 
                    card.pointValue : 0 - card.pointValue;
                if(p.hasHeading && connected){
                    score += 2;
                }
                if(connected)
                    ticketsCompleted++;

            }
            if(ticketsCompleted > maxTickets){
                maxTickets = ticketsCompleted;
                playerWithMaxTickets = p;
            }

            p.setScore(score);
        }

        for(Player p : players){
            if(p.hasContracts){
                boolean risk = playerWithMaxTickets == p;
                int toAdd = risk ? 20 : -20;
                playerWithMaxTickets.score += toAdd;
                playerWithMaxTickets.updateScore();
            }
        }

        for(Player p : players){
            if(p.hasBeam){
                Player playerWithLongestRoute = players.get(0);
                int longestRoute = 0;
                for(Player pl : players){
                    int longRoute = board.longestPath(pl);
                    if(longRoute > longestRoute){
                        longestRoute = longRoute;
                        playerWithLongestRoute = p;
                    }
                }
                if(p == playerWithLongestRoute){
                    p.score += 15;
                }
                else{
                    p.score -= 15;
                }
                p.updateScore();
            }
        }

        EndChart end = new EndChart(
                new Dimension(dim.width/2,dim.height), players);
        remove(currentPlayer);
        remove(boardPanel);
        remove(gamePanel);
        board.enabled = false;
        add(boardPanel);
        add(end);
        frameRepaint(frame);

        finished = true;
        System.gc();
    }

    /**
     * This method determines if the last four turns have started.
     * @return Whether the end of the game has been reached
     */
    private boolean endOfGame(){
        return currentPlayer.numTrains < 3;
    }

    /**
     * Begins logic for the current player's turn
     */
    private void playTurn(){
        if(firstTurn){
            // Make the player choose 3 of 5 Destination Cards

            layeredPanel = 
            new LayeredPanel(new Dimension(400,400),350,300);

            for(int i = 0; i < 5; i++){
                Card c = gamePanel.destDeck.drawCard(true);
                c.addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e){
                            board.cityLineA = null;
                            board.cityLineB = null;
                            board.cityLineC = null;

                            Card clicked = (Card)e.getSource();

                            currentPlayer.addCard(clicked);
                            layeredPanel.remove(clicked);
                            if(layeredPanel.getComponents().length
                            <= 2){
                                canExit = true;
                                if(
                                layeredPanel.getComponents().length
                                == 0){
                                    outOfLayer();
                                    layeredPane.remove(layeredPanel);
                                    enableInput();
                                    takingDest = false;
                                    firstDest = false;
                                }
                            }

                            layeredPanel.invalidate();
                            layeredPanel.validate();
                            layeredPanel.repaint();
                            frameRepaint(frame);
                        }

                        public void mousePressed(MouseEvent ev){
                            holding = true;
                            cardHolding = (Card)ev.getSource();
                        }
                    });
                c.addMouseMotionListener(new destHover());
                c.addMouseListener(new destHover());
                c.addMouseListener(dnd);
                c.addMouseMotionListener(dnd);

                layeredPanel.add(c);
            }
            firstDest = true;
            layeredPane.add(layeredPanel,
                JLayeredPane.PALETTE_LAYER);

            inLayer();
            canExit = false;
            takingDest = true;
            disableInput();

            // Give the player 4 Train Cards

            for(int i = 0; i < 4; i++){
                currentPlayer.addCard(
                    gamePanel.trainDeck.drawCard(false));
            }
            repaint();
        }
        phase = 0;

        while(phase == 0){
            checkRoads();
            try{
                Thread.sleep(33); // Wait a bit
            }catch(InterruptedException e){
                System.out.println("Interrupted");
            }
        }
        // phase now equals 1, purchased technology card

        while(phase == 1){
            checkRoads();
            try{
                Thread.sleep(33); // Wait a bit
            }catch(InterruptedException e){
                System.out.println("Interrupted");
            }
        }
        // phase now equals 2, turn is done, 
        // wait for input to switch players

        disableInput();
        while(phase == 2){
            try{
                Thread.sleep(33); // Wait a bit
            }catch(InterruptedException e){
                System.out.println("Interrupted");
            }
        }
        enableInput();
    }

    // Checks if the user wants to capture a road
    /**
     * This method checks whether the user is 
     * attempting to claim a road.
     */
    private void checkRoads(){
        if(board.roadClicked != null && trainCardsDrawn == 0){
            boolean inDoubleRoute = false;
            Road otherRoute = board.doubleRoute(board.roadClicked);
            if(otherRoute != null){
                if(otherRoute.occupying.contains(currentPlayer))
                    inDoubleRoute = true;
            }

            if(currentPlayer.canConquer(board.roadClicked)
            && !inLayer && !inDoubleRoute){
                // Allow user to choose which ones to put in

                layeredPanel = 
                new LayeredPanel(new Dimension(400,400),450,400);

                cardsUsed = new int[9];
                for(int i = 0; i <
                currentPlayer.trainCards.size(); i++){
                    TrainCard c =
                        currentPlayer.trainCards.get(i).copy();
                    c.addMouseListener(new MouseAdapter(){
                            public void mouseClicked(MouseEvent e){
                                if(finished) return;

                                // Get the card that was clicked
                                TrainCard card = 
                                    (TrainCard)e.getSource();

                                if(card.num > 0){
                                    // Take away the card they clicked
                                    card.num--;

                                    // Update cardsUsed
                                    cardsUsed[card.type.index]++;

                                    // Check if the list
                                    // of cards ('cardsUsed') works
                                    if(playerCanCapture(
                                        board.roadClicked)){
                                        currentPlayer.conquer(
                                            board.roadClicked, 
                                            cardsUsed,
                                            gamePanel.discardPile);

                                        City sF = 
                                            board.getSecondFrance();
                                        for(Card c :
                                        currentPlayer.destDeck.cards){
                                            DestinationCard dest = 
                                                (DestinationCard)c;
                                            City a =
                                                board.getCity(
                                                    dest.cityA);
                                            City b = 
                                                board.getCity(
                                                    dest.cityB);
                                            boolean connected = 
                                                board.isConnected(
                                                    a,
                                                    b,
                                                    currentPlayer);

                                            if(b.getName().equals(
                                                "France")){

                                                connected = 
                                                connected || 
                                                board.isConnected(
                                                    a,
                                                    sF,
                                                    currentPlayer);
                                            }

                                            if(connected){
                                                dest.checked = true;
                                            }
                                        }
                                        outOfLayer();
                                        enableInput();
                                        board.roadClicked = null;
                                        cardsUsed = new int[9];
                                        layeredPane.remove(
                                            layeredPanel);

                                        if(currentPlayer.usingThermo){
                                            enabled = false;
                                            currentPlayer.usingThermo
                                            = false;
                                        }
                                        else{
                                            enableInput();
                                            phase = 2;
                                        }
                                    }
                                }
                                card.repaint();
                            }
                        });
                    handCopy.add(c);
                    layeredPanel.add(c);
                }

                layeredPane.add(layeredPanel, 
                    JLayeredPane.PALETTE_LAYER);
                canExit = true;
            }
            else{
                if(inLayer){
                }
                else{
                    board.roadClicked = null;
                }
            }
        }
    }

    /**
     * This method determines if the current
     * player can claim the road 'r'.
     * @param r The road which the current 
     * player is attempting to claim
     * @return Whether the current player may claim 'r'
     */
    private boolean playerCanCapture(Road r){
        // Determines if the 'currentPlayer' can
        // capture the Road, 'r', given the train
        // cards in 'cardsUsed'
        if(cardsUsed[8] < r.locosRequired){
            return false;
        }

        int cardsRequired = r.length();
        if(currentPlayer.techDeck.contains(Images.diesel_power) 
        && cardsRequired > 1){
            cardsRequired--;
        }

        for(int i = 0; i < 8; i++){
            TrainCardTypes t = TrainCardTypes.values()[i];
            if(r.color == t.color || r.color == Color.GRAY){
                if(cardsUsed[i] + cardsUsed[8] >= cardsRequired){
                    return true;
                }
            }
        }

        if(cardsUsed[8] >= cardsRequired){
            return true;
        }
        return false;
    }

    /**
     * This method merely switches to the next player.
     */
    private void switchPlayer(){
        remove(currentPlayer);
        playerNum++;
        if(playerNum == players.size())
            playerNum = 0;

        currentPlayer = players.get(playerNum);
        add(currentPlayer);

        // Reset global variables
        phase = 0;
        trainCardsDrawn = 0;
        currentPlayer.hasWay = false;
        currentPlayer.usingThermo = false;
        tendersCanWork = true;
        board.roadClicked = null;

        gamePanel.beatlePanel.walk();

        frame.getContentPane().invalidate();
        frame.getContentPane().validate();
        frame.getContentPane().repaint();
    }

    /**
     * This method paints the main panel component.
     * @param g The graphics object to paint to
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(background, 0, 0, this);
        if(inLayer){
            g.setColor(new Color( 100, 100, 100, 100));
            g.fillRect(0,0, getWidth(), getHeight());
        }
    }  

    /**
     * This method paints all children of main panel.
     * @param g The graphics object to paint to
     */
    public void paintChildren(Graphics g){
        super.paintChildren(g);
        Graphics2D g2d = (Graphics2D) g;
        if(inLayer){
            g.setColor(new Color( 100, 100, 100, 100));
            g.fillRect(0,0, getWidth(), getHeight());
        }
        if(cardHolding != null){
            AffineTransform save = g2d.getTransform();
            Point p = SwingUtilities.convertPoint(
                    cardHolding,mouseAt,this);
            g2d.translate(p.getX(), p.getY());
            cardHolding.paintComponent(g2d);
            g2d.setTransform(save);
        }
    }

    /**
     * This method fully repaints and revalidates the window.
     * @param f The JFrame to repaint and revalidate
     */
    private void frameRepaint(JFrame f){
        f.getContentPane().invalidate();
        f.getContentPane().validate();
        f.getContentPane().repaint();
        repaint();
    }

    /**
     * This method enables input to the player
     */
    private void enableInput(){
        enabled = true;
        board.enabled = true;
    }

    /**
     * This method disables input to the player.
     */
    private void disableInput(){
        enabled = false;
        board.enabled = false;
    }

    /**
     * This method creates a layered popup which may be edited.
     */
    private void inLayer(){
        inLayer = true;
        frameRepaint(frame);
    }

    /**
     * This method destroys the layered popup if existing.
     */
    private void outOfLayer(){
        inLayer = false;
        frameRepaint(frame);
    }

    /**
     * This is an inner class for game panels in ticket to ride.
     * The game panel is the leftmost side during gameplay.
     * It displays relevant information for all players.
     */
    private class GamePanel extends JPanel 
    {
        Dimension dim;

        Deck techDeck;
        Deck trainDeck;
        Deck destDeck;
        Deck discardPile;

        JPanel trainPanel = new JPanel();
        JPanel techPanel = new JPanel();;
        JPanel destPanel = new JPanel();
        BeatlePanel beatlePanel;

        TrainCard[] cards;
        /**
         * This is the constructor for the game panel.
         * @param d The dimension of the panel
         */
        public GamePanel(Dimension d){
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            trainPanel.setOpaque(false);
            techPanel.setOpaque(false);
            destPanel.setOpaque(false);
            setMinimumSize(d);

            int width = (int)(d.height * .284);
            dim = new Dimension(width, d.height);

            int cardWidth = width / 2 - 7;
            int cardHeight = (int)(cardWidth * .63);
            Dimension cardDim = new Dimension(cardWidth, cardHeight);

            Dimension tDim = new Dimension(dim.width, cardHeight*4);
            trainPanel.setMinimumSize(tDim);
            trainPanel.setMaximumSize(tDim);

            discardPile = new Deck("train", tDim);
            discardPile.cards.clear();

            trainDeck = 
            new Deck(cardDim, discardPile, players.size());
            trainDeck.faceDown();

            MouseAdapter adapter = new Input();

            trainPanel.add(trainDeck);
            cards = new TrainCard[5];
            for(int i = 0; i < cards.length; i++){
                cards[i] = (TrainCard)trainDeck.drawCard(false);
                cards[i].addMouseListener(adapter);
                trainPanel.add(cards[i]);
            }

            trainDeck.addMouseListener(adapter);

            add(trainPanel);

            Dimension techDim = new Dimension(d.width,
                    (int)(cardHeight*1.4 + 20));
            techPanel.setPreferredSize(techDim);
            techPanel.setMinimumSize(techDim);
            techPanel.setMaximumSize(techDim);

            techDeck = new Deck("technology", 
                new Dimension((int)(cardDim.width * 1.4),
                    (int)(cardDim.height * 1.4)));
            techDeck.addMouseListener(new MouseAdapter(){
                    /**
                     * This method triggers upon clicking the deck
                     * of purchaseable technology cards (the user is
                     * attempting to purchase).
                     * @param e The mouseEvent triggered
                     */
                    public void mouseClicked(MouseEvent e){
                        // bought technology card
                        if(phase != 0 || 
                        trainCardsDrawn > 0 || !enabled) return;

                        TechnologyCard c = 
                            (TechnologyCard)techDeck.cards.peek();
                        if(currentPlayer.canPurchase(c)){
                            // Case for risky contracts
                            // and equalising beam
                            if(c.name.equals("risky_contracts")
                            || c.name.equals("equalising_beam")){
                                if(trainDeck.reshuffled){
                                    return;
                                }
                            }

                            // Otherwise 
                            currentPlayer.purchase(c, discardPile);
                            if(!c.name.equals("right_of_way")
                            && !c.name.equals("thermo_compressor")){
                                techDeck.drawCard(false);
                            }
                            phase = 1;
                        }

                        frame.getContentPane().repaint();
                    }
                });
            techPanel.add(techDeck);

            add(techPanel);

            Dimension destDeckDim = 
                new Dimension((int)(cardDim.height*1.5), 
                    (int)(cardDim.width*1.5));

            Dimension destDim = 
                new Dimension(d.width, destDeckDim.height + 20);
            destPanel.setPreferredSize(destDim);
            destPanel.setMinimumSize(destDim);
            destPanel.setMaximumSize(destDim);

            destDeck = new Deck("destination", destDeckDim);
            destDeck.faceDown();
            destDeck.addMouseListener(adapter);
            destPanel.add(destDeck);

            Image endLogo = null;
            Dimension logoDim = new Dimension(116,105);
            try{
                endLogo = 
                ImageIO.read(new File("photos/end-turn.png"));
                endLogo =
                endLogo.getScaledInstance(logoDim.width,
                    logoDim.height, Image.SCALE_SMOOTH);
            }catch(Exception e){
                System.out.println("File not found");
            }

            ImageIcon endLogoIcon = new ImageIcon(endLogo);

            JButton endButton = 
                new JButton("End Turn", endLogoIcon);
            endButton.setMaximumSize(logoDim);
            endButton.setPreferredSize(logoDim);
            endButton.setOpaque(false);
            endButton.setContentAreaFilled(false);

            endButton.addActionListener(new ActionListener(){
                    // This method triggers the end of the turn
                    public void actionPerformed(ActionEvent e){
                        if(phase == 2)
                            phase = 3;
                    }
                });
            destPanel.add(endButton);
            add(destPanel);

            int h1 = 
                d.height-tDim.height-techDim.height-destDim.height;	
            Dimension beatleDim =  
                new Dimension(d.width, 
                    h1);
            beatlePanel = new BeatlePanel(beatleDim, beatles);
            add(beatlePanel);

        }

        /**
         * This method resets the train cards
         * in the train panel
         */
        public void setCards(){
            // Resetting the cards in the game panel
            trainPanel.removeAll();
            trainPanel.add(trainDeck);
            for(int i = 0; i < cards.length; i++){
                if(cards[i] != null)
                    trainPanel.add(cards[i]);
            }
            trainPanel.repaint();
        }

        /**
         * This method determines if the train panel
         * is filled with locomotives
         * @return Whether the train panel is entirely locomotives
         */
        public boolean allLocos(){
            for(int i = 0; i < cards.length; i++){
                if(cards[i] != null || 
                cards[i].type != TrainCardTypes.locamotive){
                    return false;
                }
            }
            return true;
        }
    }

    // Whether the Water Tenders tech card may be applied
    private boolean tendersCanWork = true;

    /**
     * This class reads input main gameplay components,
     * i.e. decks and train cards.
     */
    private class Input extends MouseAdapter
    {
        int clicked = 0;

        ArrayList<Card> cardsClicked = new ArrayList<Card>();
        ArrayList<Card> cardsNotClicked = new ArrayList<Card>();

        /**
         * Function which triggers upon clicking game
         * changing components (decks or cards).
         * @param e The MouseEvent
         */
        public void mouseClicked(MouseEvent e){
            if(!enabled) return;
            if(e.getSource() instanceof Deck){
                Deck d = (Deck)e.getSource();
                if(d.type.equals("destination")
                && trainCardsDrawn == 0){
                    // drew 3 destination cards
                    if(gamePanel.destDeck.cardsSize() == 0) return;
                    layeredPanel =
                    new LayeredPanel(
                        new Dimension(400,400),350,300);

                    for(int i = 0; i < 3; i++){
                        Card c = gamePanel.destDeck.drawCard(true);
                        if(c == null) break;
                        c.addMouseListener(new MouseAdapter(){
                                public void mouseClicked(
                                MouseEvent e){
                                    board.cityLineA = null;
                                    board.cityLineB = null;
                                    board.cityLineC = null;

                                    Card clicked = 
                                        (Card)e.getSource();
                                    currentPlayer.addCard(clicked);
                                    layeredPanel.remove(clicked);
                                    Component[] comps = 
                                        layeredPanel.getComponents();
                                    if(comps.length <= 2){
                                        canExit = true;
                                        if(comps.length == 0){
                                            outOfLayer();
                                            layeredPane.remove(
                                                layeredPanel);
                                            takingDest = false;
                                            takingDest2 = false;
                                            phase = 2;
                                        }
                                    }

                                    layeredPanel.invalidate();
                                    layeredPanel.validate();
                                    layeredPanel.repaint();
                                    frameRepaint(frame);
                                }

                                public void mousePressed(
                                MouseEvent ev){
                                    holding = true;
                                    cardHolding =
                                    (Card)ev.getSource();
                                }
                            });
                        c.addMouseListener(new destHover());
                        c.addMouseMotionListener(new destHover());
                        c.addMouseListener(dnd);
                        c.addMouseMotionListener(dnd);
                        layeredPanel.add(c);
                    }
                    layeredPane.add(layeredPanel, 
                        JLayeredPane.PALETTE_LAYER);
                    inLayer();
                    canExit = false;
                    disableInput();
                    takingDest = true;
                    takingDest2 = true;

                }
                else if(d.type.equals("train")){
                    // If the user clicked the train deck
                    if(trainCardsDrawn < 2){
                        Card c = d.drawCard(true, false);
                        if(c == null) return;
                        currentPlayer.addCard(c);
                        trainCardsDrawn++;
                        if(trainCardsDrawn == 2 && 
                        !currentPlayer.techDeck.contains(
                            Images.water_tenders)){
                            phase = 2;
                        }

                        if(trainCardsDrawn == 2 && 
                        !tendersCanWork){
                            phase = 2;
                            tendersCanWork = true;
                        }
                    }
                    else if(phase != 2 && trainCardsDrawn == 2
                    && currentPlayer.techDeck.contains(
                        Images.water_tenders)){
                        Card c = d.drawCard(true, false);
                        if(c == null) return;
                        currentPlayer.addCard(c);
                        trainCardsDrawn++;
                        phase = 2;
                        tendersCanWork = true;
                    }
                }
            }
            else if(e.getSource() instanceof TrainCard){
                // If the user clicked an individual train card
                TrainCard c = (TrainCard)e.getSource();
                for(int i = 0; i < gamePanel.cards.length; i++){
                    Card card = gamePanel.cards[i];
                    if(c == card){
                        if(trainCardsDrawn < 2){
                            if(c.type == TrainCardTypes.locamotive){
                                if(trainCardsDrawn == 0){
                                    // The user can only take
                                    // a locamotive on the first turn
                                    // then the turn is over
                                    currentPlayer.addCard(c);
                                    TrainCard toReplace = 
                                        (TrainCard)
                                        gamePanel.trainDeck.drawCard(
                                            true);
                                    trainCardsDrawn++;
                                    if(toReplace == null){ 
                                        gamePanel.cards[i] = null;
                                        gamePanel.setCards();
                                        phase = 2;
                                        return;
                                    }
                                    toReplace.addMouseListener(this);
                                    gamePanel.cards[i] = toReplace;
                                    gamePanel.setCards();
                                    phase = 2;
                                    break;
                                }
                            }
                            else{
                                // The user may take the card
                                tendersCanWork = false;
                                currentPlayer.addCard(c);
                                TrainCard toReplace = 
                                    (TrainCard)
                                    gamePanel.trainDeck.drawCard(
                                        true);
                                trainCardsDrawn++;
                                if(toReplace == null){
                                    // Nothing left in the deck
                                    gamePanel.cards[i] = null;
                                    gamePanel.setCards();
                                    if(trainCardsDrawn == 2)
                                        phase = 2;
                                    return;
                                }
                                toReplace.addMouseListener(this);
                                gamePanel.cards[i] = toReplace;
                                gamePanel.setCards();
                                if(trainCardsDrawn == 2 || 
                                (gamePanel.allLocos() &&
                                    gamePanel.trainDeck.cardsSize()
                                    == 0))
                                    phase = 2;
                                break;
                            }
                        }
                    }
                }
            }

            if(gamePanel.trainDeck.cardsSize() == 0 && 
            isNullArray(gamePanel.cards)){
                phase = 2;
            }

            frameRepaint(frame);
        }

        /**
         * This method determines if an array is
         * filled with null values.
         * @param arr The array in question
         * @return Whether arr is a null array
         */
        public boolean isNullArray(Object[] arr){
            for(int i = 0; i < arr.length; i++){
                if(arr != null) return false;
            }
            return true;
        }
    }

    // Point for moving around layered panels
    private Point prev = new Point();

    /**
     * Mouse adapter for layered components
     */
    private class LayeredAdapter extends MouseAdapter
    {
        /**
         * Method for dragging the layered panel across the screen.
         * @param e The MouseEvent object
         */
        public void mouseDragged(MouseEvent e){
            int xDiff = e.getXOnScreen() - (int)prev.getX();
            int yDiff = e.getYOnScreen() - (int)prev.getY();
            if(prev.getX() == 0) xDiff = 0;
            if(prev.getY() == 0) yDiff = 0;

            int x = layeredPanel.getBounds().x + (xDiff);

            int y = layeredPanel.getBounds().y + (yDiff);

            layeredPanel.setBounds(x,y, 
                layeredPanel.getBounds().width, 
                layeredPanel.getBounds().height);
            prev = e.getLocationOnScreen();
            frameRepaint(frame);
        }

        /**
         * Triggers upon releasing the mouse on a layer
         * @param e The MouseEvent object
         */
        public void mouseReleased(MouseEvent e){
            prev = new Point();
        }
    }

    /**
     * Action Listener for converting train cards into locomotives.
     */
    private class ConvertLocos implements ActionListener
    {
        int[] cardsUsed2 = new int[8];

        /**
         * This method triggers upon clicking the 'convert' button.
         * @param e The ActionEvent object
         */
        public void actionPerformed(ActionEvent e){
            if(!enabled) return;
            layeredPanel =
            new LayeredPanel(new Dimension(450,400), 400, 400);

            canExit = true;
            cardsUsed2 = new int[8];

            for(int i = 0; i < 
            currentPlayer.trainCards.size()-1; i++){
                TrainCard c =
                    currentPlayer.trainCards.get(i).copy();
                c.addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e){
                            // Get the card that was clicked
                            TrainCard card = 
                                (TrainCard)e.getSource();

                            if(card.num > 0){
                                // Take away the card they clicked
                                card.num--;

                                // Update cardsUsed
                                cardsUsed2[card.type.index]++;

                                // Check if the list 
                                // of cards ('cardsUsed') works
                                if(currentPlayer.canConvert(
                                    cardsUsed2)){
                                    currentPlayer.convert(
                                        cardsUsed2,
                                        gamePanel.discardPile);

                                    outOfLayer();
                                    enableInput();
                                    cardsUsed2 = new int[8];
                                    layeredPane.remove(layeredPanel);
                                }
                            }
                            card.repaint();
                        }
                    });
                layeredPanel.add(c, new GridBagConstraints());
            }

            layeredPane.add(layeredPanel,
                JLayeredPane.PALETTE_LAYER);
            layeredPane.invalidate();
            layeredPane.validate();
            frameRepaint(frame);
        }
    }

    /**
     * Mouse adapter for reading input for destination cards
     * (particularly for the hovering effect).
     */
    class destHover extends MouseAdapter
    {
        /**
         * This method triggers upon hovering the mouse
         * on a destination card
         * @param e The MouseEvent Object
         */
        public void mouseMoved(MouseEvent e){
            Graphics g = board.getGraphics();
            if(e.getSource() instanceof DestinationCard){
                DestinationCard card =
                    (DestinationCard)e.getSource();
                City a = board.getCity(card.cityA);
                City b = board.getCity(card.cityB);
                City c = null;
                if(b.getName().equals("France"))
                    c = board.getSecondFrance();

                board.cityLineA = a;
                board.cityLineB = b;
                board.cityLineC = c;
            }  
            board.repaint();
        }

        /**
         * This method triggers upon exiting the mouse
         * on a destination card
         * @param e The MouseEvent Object
         */
        public void mouseExited(MouseEvent e){
            board.cityLineA = null;
            board.cityLineB = null;
            board.cityLineC = null;
            board.repaint();
        }
    }

    /**
     * Inner class for creating layered jpanels for
     * displaying in layer information.
     */
    private class LayeredPanel extends JPanel
    {
        Dimension dim;

        /**
         * This method constructs layered jpanels
         * @param d The dimension of the panel
         * @param x The x coordinate value
         * @param y The y coordinate value
         */
        public LayeredPanel(Dimension d, int x, int y){
            dim = d;
            setPreferredSize(new Dimension(500, 500));
            setOpaque(false);

            addMouseMotionListener(new LayeredAdapter());

            addMouseListener(new LayeredAdapter());
            setBounds( x, y,  d.width, d.height );
            disableInput();

            inLayer();
        }

        /**
         * This method paints the layered panel 
         * (painting necessary images too).
         * @param g The graphics object to paint to
         */
        public void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D)g;
            int x1 = cloud.getWidth(this);
            int y1 = cloud.getHeight(this);
            double scaledX = (double)dim.width / x1;
            double scaledY = (double)dim.height / y1;
            g2d.scale(scaledX, scaledY);
            g2d.drawImage(cloud,0,0,this);
            g2d.scale(1/scaledX, 1/scaledY);
        }
    }

    /**
     * Mouse adapter for input relevant to
     * dragging and dropping destination cards.
     */
    private class DragAndDrop extends MouseAdapter
    {
        Component parent;

        /**
         * Constructs DragAndDrop adapter objects
         * @param The parent of the component to add to
         */
        public DragAndDrop(Component parent){
            this.parent = parent;
        }

        /**
         * Triggers upon release of the mouse for
         * drag and droppable components
         * @param ev The MouseEvent object
         */
        public void mouseReleased(MouseEvent ev){
            Point p = 
                SwingUtilities.convertPoint(
                    ev.getComponent(), ev.getPoint(), parent);
            Component comp = 
                SwingUtilities.getDeepestComponentAt(parent,
                    (int)p.getX(), (int)p.getY());

            if(comp == null || !currentPlayer.contains(comp)){
                cardHolding = null;
                holding = false;
                frameRepaint(frame);
                return;
            }

            if(holding && cardHolding != null){
                board.cityLineA = null;
                board.cityLineB = null;
                board.cityLineC = null;

                currentPlayer.addCard(cardHolding);
                layeredPanel.remove(cardHolding);

                if(layeredPanel.getComponents().length <= 2){
                    canExit = true;
                    if(layeredPanel.getComponents().length == 0){
                        outOfLayer();
                        layeredPane.remove(layeredPanel);
                        enableInput();

                        if(takingDest2){
                            phase = 2;
                            takingDest2 = false;
                        }
                    }
                }

                layeredPanel.invalidate();
                layeredPanel.validate();
                layeredPanel.repaint();
                frameRepaint(frame);
                currentPlayer.repaint();
            }
            holding = false;
            cardHolding = null;

            frameRepaint(frame);
        }

        /**
         * Triggers upon pressing a drag and drop enabled
         * component
         * @param ev The MouseEvent object
         */
        public void mousePressed(MouseEvent ev){
            Component comp = 
                SwingUtilities.getDeepestComponentAt(
                    parent,ev.getX(), ev.getY());
            if(!(comp instanceof DestinationCard)){
                return;
            }

            holding = true;
            cardHolding = (DestinationCard)comp;
        }

        /**
         * Triggers upon dragging a drag and drop enabled
         * component
         * @param e The MouseEvent object
         */
        public void mouseDragged(MouseEvent e){
            if(cardHolding != null){
                mouseAt = e.getPoint();
                repaint();
            }
        }
    }
}
