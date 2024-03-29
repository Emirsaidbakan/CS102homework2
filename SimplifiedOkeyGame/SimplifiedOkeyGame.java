import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        int playerOkeyIndex = 0;
        int distrubutedCardIndex = 0;
        /* while (playerOkeyIndex < 15) {
            players[0].playerTiles[playerOkeyIndex] = tiles[distrubutedCardIndex];
            distrubutedCardIndex++;
            playerOkeyIndex++;
            getAndRemoveTile(distrubutedCardIndex);
        } */
        /* for (int i = 0; i < 15; i++) {
            players[0].playerTiles[i] = tiles[distrubutedCardIndex];
            distrubutedCardIndex++;
            getAndRemoveTile(distrubutedCardIndex);
        }
        for (int i = 1; i < players.length; i++){
            for (int k = 0; k < 14; k++) {
                players[i].playerTiles[k] = tiles [distrubutedCardIndex];
                distrubutedCardIndex++;
                getAndRemoveTile(distrubutedCardIndex);
            }
        }
        for (Player player : players) {
            sortArray(player.getTiles());
        } */

        int distributedCardIndex = 0;

        for (int i = 0; i < players[0].getTiles().length; i++) {
            players[0].playerTiles[i] = new Tile(tiles[distributedCardIndex].getValue());
            distributedCardIndex++;
        }

    }

    public Tile getAndRemoveTile(int index) {
        if (index >= 0)
        {
             Tile temp;
             for (int i = index; i + 1 < tiles.length; i++) {
                 temp = tiles[i];
                 tiles[i] = tiles[index+1];
                 tiles[i+1] = temp;
             }
             return tiles[index];
        }
        else {
             System.out.println("error");
        }
        return null;
     }

    /*
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        return lastDiscardedTile.toString();
    }

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {

        //Check if there are any tiles left
        if (tileCount > 0) {
            //Find the top tile
            Tile currentTopTile = tiles[tileCount-1];
            String tileName = currentTopTile.toString();

            //Update the tile count
            tileCount--;
            
            //Remove tile from array
            currentTopTile = null;

            return  tileName;
        } 
        //If there are no tiles left, return an error message saying so
        else {
            String errorMessage = "No more tiles left!";
            return errorMessage;
        }
    }

    /*
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
    /*  we  will shuffle and change the cards positions 52 times (104 / 2) 
     *  this amount is usually enough to a fair shuffle */
/*         Random random = new Random();
        Tile randomTile;
        int randomTileIndex;
        int temp; // this temp is for to change the cards position with each other
        for(int n = 0; n < 52; n++){
            temp = tiles[n].value;
            randomTileIndex = random.nextInt(104);
            tiles[n].value = tiles[randomTileIndex].value;
            tiles[randomTileIndex].value = temp;
               
        } */

        Collections.shuffle(Arrays.asList(tiles));
    }

    /*
     * TODO: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {
        if ( players[currentPlayerIndex].checkWinning() == true || tileCount == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /* TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners = new Player[players.length];
        int max = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].findLongestChain() > max) {
                max = players[i].findLongestChain();
            }
        }

        for (int i = 0; i < players.length; i++) {
            if (players[i].findLongestChain() == max) {
                winners[i] = players[i];
            }
        }
        return winners;
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*
     * pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     */
    public void pickTileForComputer() {
        
        int previousLongestChain = getCurrentPlayer().findLongestChain(); //Before adding the last discarded tile
        getCurrentPlayer().addTile(lastDiscardedTile);
        int newLongestChain = getCurrentPlayer().findLongestChain(); //After adding the last discarded tile

        //Checks whether the longest chain has increased in size after adding the into the player's hand.
        //If not, it removes the tile and gets the top tile       
        if (previousLongestChain <= newLongestChain) { 
            getCurrentPlayer().getAndRemoveTile(getCurrentPlayer().findPositionOfTile(lastDiscardedTile));
            getTopTile();
        } 
    }

    /*
     * Current computer player will discard the least useful tile.
     */
    public void discardTileForComputer() {
        //Checks whether there are duplicate tiles, if there are then that tile is discarded and breaks
        for (int i = 0; i< getCurrentPlayer().getTiles().length; i++) {            
            if (getCurrentPlayer().getTiles()[i + 1] != null) {
                if (getCurrentPlayer().getTiles()[i].getValue() == getCurrentPlayer().getTiles()[i + 1].getValue()) {
                    discardTile(i);
                    return;
                }
            }   
        }

        //If there are no duplicate tiles, looks for the shortest chain and discards the last tile on the chain
        int shortestChain = 100;
        int temp = 0;
        int index = 0;
        for (int i = 0; i < getCurrentPlayer().getTiles().length; i++) {
            if (getCurrentPlayer().getTiles()[i + 1] != null) {
                if(getCurrentPlayer().getTiles()[i + 1].getValue() - getCurrentPlayer().getTiles()[i].getValue() == 1) {
                    temp++;
                }
                if (temp < shortestChain) {
                    shortestChain = temp;
                    index = i;
                }
            }
        }
        discardTile(index + shortestChain - 1);
    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
        lastDiscardedTile = getCurrentPlayer().getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        getCurrentPlayer().displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public String getCurrentPlayerName() {
        return getCurrentPlayer().getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

    public Player getCurrentPlayer() {
         return players[currentPlayerIndex];
    }

    /**
     * This method aims to sort the tile array in ascending or 
     * 
     * @param list the tile that will be sorted
     * @author Elif Su Temirel due to the errors in her gitKraken I uploaded this method
     */
    public static void sortArray(Tile[] list){
        boolean test;
        int temp;
        do{
            test = true;
            for(int n = 0; n < list.length - 1; n++){
                if(list[n+1] != null){
                  if(list[n].value > list[n+1].value){
                    temp = list[n].value;
                    list[n] = list[n + 1];
                    list[n + 1].value = temp;
                    }
                }
            }
            for(int a = 0; a < list.length - 1; a++){
                if(list[a+1] != null){
                        if(list[a].value > list[a+1].value){
                        test = false;
                        }
                    }            
                }
        }
        while ( test == false);
    }
}
//test
