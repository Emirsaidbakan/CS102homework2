public class Tile implements Comparable {
    
    int value;

    /*
     * creates a tile using the given value. False jokers are not included in this game.
     */
    public Tile(int value) {
        this.value = value;
    }

    /*
     * TODO: should check if the given tile t and this tile have the same value 
     * return true if they are matching, false otherwise
     */
    public boolean matchingTiles(Tile t) {
        return this.value == t.value;
    }

    /*
     * TODO: should compare the order of these two tiles
     * return 1 if given tile has smaller in value
     * return 0 if they have the same value
     * return -1 if the given tile has higher value
     */
    public int compareTo(Object T) {
        Tile tile = (Tile)T;
       if (this.value  < tile.value) {
        return -1;
       }
       else if ( this.value == tile.value){
        return 0;
       }
       else { //this.value > t.value
        return 1;
       }
    }

    /*
     * To determine if this tile and given tile can form a chain together
     * this method checks the difference in values of the two tiles
     * and returns true if the absoulute value of the difference is 1 (they can form a chain)
     * otherwise, it  returns false (they cannot form a chain)
     */
    public boolean canFormChainWith(Tile t) {
        //This returns ture when the abs value if the difference is equal to 1 otherwise reurns flase
        return Math.abs(this.value - t.value)== 1;
    }

    public String toString() {
        return "" + value;
    }

    public int getValue() {
        return value;
    }

}

