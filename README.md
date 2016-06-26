# Ticket to Ride - UK (Beatles Edition)
This project is a video game recreation of the Days of Wonder board game, Ticket to Ride - UK Edition, using Java and Swing GUIs.

PURPOSE OF PROJECT: To create a functional version of the Ticket to Ride - UK board game with UI

VERSION or DATE: 5/2/16

HOW TO START THIS PROJECT: Run the main(String args[]) method in the TitleScreen class in bluej or other IDE

PLEASE NOTE: The GitHub project page for this project does NOT contain the necessary files (pictures, sound, etc.) to run the full game. If you wish to request these files, please email nm31devi@siena.edu.


USER INSTRUCTIONS:

Upon running the program, you will be greeted by a Title Screen with accompanying music
(of course, this music is a nice rendition of the Beatles’ famous "Ticket to Ride"). This
Title Screen is also the Album Cover of the Ticket to Ride Beatles album. To read the instructions, click on the Instructions button, which will open a window with a scrollable instruction manual. To exit the instruction manual. On the Title Screen, each player will pick (by clicking on) one of the Beatles to play as. Each player picks their Beatle in order (the first to pick is the first player, the second to pick is the second player, and so on). When choosing your Beatle, you may rename your character and pick your train car color (Note that the leftmost train car color is chosen by default). When you are happy with these options, click on the Finish button, which will allow the next player to select their options. Once all players have selected their Beatle and train car color, click the Play Game button to begin gameplay.


On each player’s first turn, 5 destination cards are shown. The player must choose at least 3. Once they are done selecting, they click anywhere on the wooden background to exit (a player may exit the layered window if they have chosen enough destination cards). Optionally, players may drag and drop destination cards onto the right side of the game board to collect them. Then, that player begins normal gameplay. Before they begin their turn, they have the option to convert 4 train cards (or 3 if the player owns a Booster) into a locomotive. This is allowed more than once. They also have the option to buy a technology card (only one per turn) if they have enough locomotives. To view the available technology cards, hover over the deck of technologies and scroll through them using the mouse. Then, click on the card you wish to purchase.

Available technologies with a brief description:

Wales Concession- allows you to claim routes into any of the 5 cities in Wales.
Ireland/France Concession- allows you to claim routes into any of the 10 cities in Ireland and France.
Scotland Concession- allows you to claim routes into any of the 10 cities in Scotland.
Mechanical Stoker- allows you to claim 3 space routes.
Superheated Steam Boiler- allows you to claim 4, 5, and 6 space routes.
Propellers- allows you to claim ferry routes.
Booster- you may now use any 3 cards as locomotives (instead of 4).
Boiler Lagging- you score 1 extra point for each route that you claim after you have this card.
Steam Turbines- you score 2 extra routes for each ferry route that you claim after you have this card.


Advanced technologies with a brief description:

Thermocompressor- claim 2 routes this turn, then return this card. Note that when you purchase this card, it does not appear with your other technology cards, but it is still in effect.
Water Tenders- when drawing train cards, you can decide to draw 3 blind cards instead of the regular 2.
Risky Contracts- at the end of the game, score 20 points if you have the most completed tickets. If not, lose 20 points.
Equalising Beam- at the end of the game, score 15 points if you have the longest route. If not, lose 15 points.
Diesel Power- when claiming a route, you may use 1 less card than required. You must still play at least 1 card and cannot ignore a locomotive on a ferry route.
 

Then, they may 1. choose 2 train cards (or one locomotive), 2. choose at least one out of 3 destination cards, or 3. claim a route (Note: some of these options change depending on which technology cards the player has or does not have).

1) Drawing train cards
	
If a player chooses to draw one of the locomotives that are faced up, then they cannot draw any more train cards. However, they can choose to draw 2 non-locomotive cards that are faced up, draw 2 blind cards, or choose one non-locomotive card that is faced up and one blind card. If they are drawing 2 blind cards and the first happens to be a locomotive, the player is still allowed to draw another card.

2) Choosing destination cards
	
If a player decides to take one or more destination cards for their turn, they must click on the pile of destination cards, where they will draw 3 cards. They are able to look at them all and decide which one(s) they want to keep. Once they take a destination card, they must claim the route between the 2 cities before the game is over, or else they will lose points. When a destination card has been completed, a green check appears over said card.

3) Claiming a route

As long as the player has the appropriate technology cards in their possession and they have the correct number of trains, they can claim a route. If the route is a ferry route, the number of locomotives they must use must be equivalent to the number of ferries needed. If there are 2 players, double routes can only be claimed once. If there are 3 or 4 players, two different players may claim said route. If a player decides to claim a route, they must click on that route and select the train cards they wish to use to claim the route. Train cars are then placed on the route automatically, and the number of train cars in the player’s possession is decreased by the length of the claimed route. Based on the length of the route (and current technology cards in possession), the player’s score is increased.
	
Scoring:

Length 1- 1 point
Length 2- 2 points
Length 3- 4 points
Length 4- 7 points
Length 5- 10 points 
Length 6- 15 points

*Special route from Southampton to New York- 40 points

When the player is done with one of these 3 options, they must click the End Turn button (i.e. they cannot convert train cars to locomotives). Then play proceeds to the next player.

Gameplay continues until one player’s number of trains is equal to 0, 1, or 2. Then, each player after that takes their final turn. Once the game is over, the final scores are added. The player(s) who has/have the Risky Contracts or Equalising Beam technology cards add or subtract points from their total score. Final scores are displayed, as well as each player’s destination cards and a bar chart of how players stack up. Note that the winner’s bar chart is highlighted. Once players are done analyzing the statistics, close the window to end the game. 
