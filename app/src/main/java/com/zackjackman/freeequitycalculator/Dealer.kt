package com.zackjackman.freeequitycalculator

class Dealer(val playerNum:Int) {

    var players = ArrayList<Player>()
    var deck = Deck()
    var board = Board()
    constructor(playerNum: Int, usedDeck: Deck):this(playerNum){
        deck = usedDeck
    }

    fun newDeck(){
        deck.newDeck()
    }

    fun shuffleDeck(){
        deck.shuffle()
    }

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
    }
    fun createPlayers(){
        for (i in 0..this.playerNum-1){
            players.add(Player(i))
        }
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

    fun getPlayerCard(player: Int, postion: Int):Card{
        return players[player].dealtHand[postion]

    }

    fun placeBoardCard(postion:Int, card: Card, stats:Boolean) {
        if(board.getBoardSize()-1 > postion || board.getBoardSize() == 5){
            if(board.board[postion] == card){
            }else {
                val holder = board.replaceCard(postion, card)
                if (!stats){
                deck.addCard(holder)
                }
            }
        }else {
            board.placeCard(postion, card)
            if (!stats){
            deck.removeCard(card)
            }
        }
    }
    fun placeBoardCard(postion: Int,cards: ArrayList<Card>,stats: Boolean){
        for (card in postion..cards.size-1){
            placeBoardCard(card, cards[card], stats)
        }
    }

    fun replacePlayerBoard(){
        for (player in players){
            player.clearBoard()
            player.addCard(board.board)
        }
    }

    fun readHands():String{
        var strings = ""
        strings += ("-----Board-----\n")
        for (card in board.getFullBoard()){
            strings += (card.toString())
        }
        for (player in players){
            strings += ("\n"+ player.toString())
        }
        strings += "Winner: " + readWinner()
        return strings
    }

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