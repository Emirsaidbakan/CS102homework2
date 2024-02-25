import java.util.Arrays;
public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
    }

    /*
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {

        int currentChainLength = 1;
        for (int i = 0; i < numberOfTiles- 1; i++) {
            if ( playerTiles[i].getValue() + 1 == playerTiles[i+1].getValue()) {
                currentChainLength ++;
                if (currentChainLength == 15) {
                    return true;
                }
            }
            else {
                currentChainLength = 1;
            }
        }
        return  false;
    }

    /*
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        int longestChain = 0;
        int similarTilesCounter = 0;        
        // This variable will be used for count the same serial tiles to detect the longest tile serie
        for(int n = 0; n < numberOfTiles - 1; n++){
            if(playerTiles[n] == playerTiles[n + 1]){
                similarTilesCounter++;
            }
            if(similarTilesCounter > longestChain){
                longestChain = similarTilesCounter;
            }
            if(playerTiles[n] != playerTiles[n + 1]){
                similarTilesCounter = 0;
            }
        }
        return longestChain;
    }

    /*
     * TODO: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) {
       if (index >= 0)
        {
            for (int i = index; i + 1 < numberOfTiles; i++) {
                playerTiles[i] = playerTiles[index+1];
            }
            return playerTiles[index];
        }
        else {
            System.out.println("error");
        }
        return null;
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t) {

        for (int i = 0; i + 1 <numberOfTiles ; i++) {
            if (t.getValue() >= playerTiles[i].getValue() && t.getValue() <= playerTiles[i + 1].getValue()) {
                insertTile(t, i + 1);
                return;
            }
        }
    }

    private void insertTile(Tile t, int position){
        for (int i = numberOfTiles- 2; i >= position; i--) {
            playerTiles[i + 1] = playerTiles[i];
        }
        playerTiles[position] = t;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i <playerTiles.length; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
