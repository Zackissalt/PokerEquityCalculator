package com.zackjackman.freeequitycalculator

class Dealer(val playerNum:Int) {

    var players = ArrayList<Player>()
    var deck = Deck()
    var board = Board()
    constructor(playerNum: Int, usedDeck: Deck):this(playerNum){
        deck = usedDeck
    }

    fun shuffleDeck(){
        deck.shuffle()
    }

    fun createPlayers(){
        for (i in 0..this.playerNum-1){
            players.add(Player(i))
        }
    }

    /* Functions used for testing when developing class may make use of later

    fun runBoard(){
        clearAll()
        newDeck()
        shuffleDeck()
        createPlayers()
        pitchCards()
        bringFlop()
        bringTurn()
        bringRiver()
    }


    fun clearAll(){
        players.clear()
        board.board.clear()


    fun newDeck(){
        deck.newDeck()
    }

    fun pitchCards(){
        for (i in 1..2) {
            for (player in players) {
                player.addDealtHand(this.deck.pitchCard())
            }
        }
    }

    fun bringFlop(){
        board.flop(deck.pitchCard(),deck.pitchCard(),deck.pitchCard())
        for (player in players){
            player.addCard(board.getFlop())
        }
    }

    fun bringTurn(){
        board.street(deck.pitchCard())
        for (player in players){
            player.addCard(board.getTurn())
        }
    }

    fun bringRiver(){
        board.street(deck.pitchCard())
        for (player in players){
            player.addCard(board.getRiver())
        }
    }
    */

    // Functions for custom placement of cards while maitainting deck integrity
    fun placePlayerCard(player:Int, card: Card){
        players[ player ].addDealtHand(card)
        deck.removeCard(card)
    }

    fun placePlayerCard(cards: ArrayList<Card>){
        var count = 0
        for (player in 0 until players.size){
            placePlayerCard(player, cards[count])
            count++
            placePlayerCard(player, cards[count])
            count++
        }
    }

    /*
    fun removePlayerCard(player: Int, card: Card){
        players[ player ].removeDealtHand(card)
        deck.addCard(card)
    }

    fun removePlayerCardAll(){
        for (player in players){
            for (card in player.dealtHand){
                deck.addCard(card)
            }
            player.removeDealtHand()
        }

    }

    fun getPlayerCard(player: Int, position: Int):Card{
        return players[player].dealtHand[position]

    }
    */

    fun placeBoardCard(position:Int, card: Card, stats:Boolean) {
        if(board.getBoardSize()-1 > position || board.getBoardSize() == 5){
            if(board.board[position] == card){
            }else {
                val holder = board.replaceCard(position, card)
                if (!stats){
                deck.addCard(holder)
                }
            }
        }else {
            board.placeCard(position, card)
            if (!stats){
            deck.removeCard(card)
            }
        }
    }

    fun placeBoardCard(position: Int, cards: ArrayList<Card>, stats: Boolean){
        for (card in position until cards.size){
            placeBoardCard(card, cards[card], stats)
        }
    }

    fun removeBoardCard(position: Int){
        board.removeCard(position)
    }

    fun replacePlayerBoard(){
        for (player in players){
            player.clearBoard()
            player.addCard(board.board)
        }
    }



    // returns all highest value ranking hands
    fun readWinner():ArrayList<Int>{

        val highestRank = ArrayList<Int>()
        for (player in players){
            if (players.max()!!.compareTo(player) == 0){
                highestRank.add(players.indexOf(player))
            }
        }
        return highestRank
    }


}