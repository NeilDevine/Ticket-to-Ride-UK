///////////////////////////////////////////////////////////////////////////
// For line wrap reference (above is 75)
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.*;
/**
 * This class is a JPanel which displays end of game information in a bar
 * chart style in Ticket to Ride - UK.
 * 
 * @author Neil Devine, AJ Cioffi, Felicia Bassey, Thomas Caron, 
 * Michael Salvador 
 * @version 5/2/16
 */
public class EndChart extends JPanel
{
    private ArrayList<Player> players;
    private Dimension dim;

    /**
     * Constructor for creating an EndChart.
     * @param d The to-be dimension of the panel
     * @param pl The players of the game
     */
    public EndChart(Dimension d, ArrayList<Player> pl){
        setOpaque(false);
        dim = new Dimension(d.width, d.height - 70);
        setPreferredSize(d);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        players = pl;
        Dimension barDim = 
            new Dimension(dim.width/ players.size(), dim.height);

        int maxScore = players.get(0).score;
        Player winner = players.get(0);
        for(Player p : players){
            if(p.score > maxScore){
                maxScore = p.score;
                winner = p;
            }
        }

        for(Player p : players){
            boolean isWinner = p == winner;
            Bar bar = new Bar(barDim, p.name, p.score,
                    p.color, Math.abs(maxScore) + 20, 
                    isWinner, p.destDeck);
            add(bar);
        }
    }

    /**
     * JPanel for displaying a full bar with relevant information
     * in a bar chart.
     */
    private class Bar extends JPanel
    {
        private String name; // Name to display
        private int value; // The value of the bar
        private Color color; // Color of the bar
        private Dimension dim;
        private boolean isWinner; // Whether this bar is the winner

        /**
         * Constructs Bar objects given player information and dimension.
         * @param d The dimension of the bar
         * @param name The name of the player
         * @param score The score for that player
         * @param c The color of that player
         * @param maxScore The maximum score in the game
         * @param isWinner Whether this player is the winner
         * @param deck The player's destination deck to display
         */
        public Bar(Dimension d, String name, int score, Color c,
        int maxScore, boolean isWinner, Deck deck){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            dim = d;
            setPreferredSize(dim);
            this.name = name;
            this.value = score;
            color = c;
            this.isWinner = isWinner;

            add(deck, SwingConstants.CENTER);

            JLabel label = new JLabel(name + ": " + score);

            label.setHorizontalAlignment(SwingConstants.CENTER);
            Color b = new Color(
                    Color.BLUE.getRed(),
                    Color.BLUE.getGreen(),
                    Color.BLUE.getBlue(),
                    100);
            label.setBackground(b);
            label.setForeground(Color.WHITE);

            Dimension labelDim = new Dimension(dim.width, 100);
            label.setPreferredSize(labelDim);
            label.setMinimumSize(labelDim);
            label.setMaximumSize(labelDim);
            label.setOpaque(true);
            label.setFont(new Font(" ", Font.BOLD, 16));

            int width = dim.height / (maxScore) * value;
            if(value < 0)
                width = 0;
            Dimension barDim = 
                new Dimension(dim.width *2,
                    dim.height - 100 - deck.dim.height);

            BarPanel barPanel = new BarPanel(barDim, color, width);

            add(label, SwingConstants.CENTER);
            add(barPanel);

            barPanel.animate();
        }

        /**
         * This method paints all children of the bar.
         * @param g The graphics object to paint to
         */
        protected void paintChildren(Graphics g){
            super.paintChildren(g);
            if(isWinner){
                Color c = new Color(
                        Color.YELLOW.getRed(),
                        Color.YELLOW.getGreen(),
                        Color.YELLOW.getBlue(),
                        100);
                g.setColor(c);
                g.fillRect(0,0,getWidth(), getHeight());
            }
        }

    }

    /**
     * JPanel for displaying an animated bar in a bar chart.
     */
    private class BarPanel extends JPanel
    {
        private Color c;
        private int barHeight;
        Timer timer;
        int frameNum = 1;

        /**
         * Constructs BarPanel objects given certain parameters.
         * @param d The dimension of this bar panel
         * @param c The color of this bar panel
         * @param height the height of the full bar
         */
        public BarPanel(Dimension d, Color c, int height){
            setOpaque(false);
            setPreferredSize(d);
            setMaximumSize(d);
            this.c = c;
            barHeight = height;

            timer = new Timer(7,new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(frameNum == 120){
                            ((Timer)e.getSource()).stop();
                        }
                        else
                            frameNum++;
                        repaint();
                    }
                });
        }

        /**
         * This method paints all children of the bar panel.
         * @param g The graphics object to paint to
         */
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(c);
            RoundRectangle2D roundedRect = 
                new RoundRectangle2D.Float(0,
                    getHeight() - barHeight / 120 * frameNum,
                    getWidth(),
                    barHeight / 120 * frameNum,
                    40, 
                    40);
            g2d.fill(roundedRect);
        }

        /**
         * This method animates the bar.
         */
        public void animate(){
            timer.start();
        }
    }
}
