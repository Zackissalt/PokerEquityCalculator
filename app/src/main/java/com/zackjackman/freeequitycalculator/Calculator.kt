package com.zackjackman.freeequitycalculator

import kotlin.math.roundToInt

class Calculator (val player: Int, deck: Deck, val cards :ArrayList<Card>, val accuracy:Int) {
    val dealer = Dealer(player, deck)
    val average = Odds(player)
    fun dealerSetUp(){
        dealer.createPlayers()
        dealer.placePlayerCard(cards)
    }

    /*
    fun randomHands(){
        dealer.runBoard()
    }
    */

    fun calculateOdds(board: ArrayList<Card>):ArrayList<String> {
        average.oddsHolders()
        dealer.placeBoardCard(0, board, true)
        runBoard(board.size)
        val results = ArrayList<String>()
        for (odd in 0 until  player){
            var odds = ""
            odds += "Win: " + ((average.getWin(odd) * 1000.0)/average.total).roundToInt()/10.0 + "%\n" +
                    "Ties: " + (((average.getTie(odd) * 1000.0)/average.total).roundToInt())/10.0 + "%"
            results.add(odds)
        }
        return results
    }

    fun runBoard(size:Int){
        if (size == 0){
            for (a in 1..getAccuracy(accuracy)) {
                dealer.shuffleDeck()
                    for (b in 0..4) {
                        dealer.placeBoardCard(b, dealer.deck.deck[b], true)
                    }
                    dealer.replacePlayerBoard()
                    recordWinner()

            }
        }else{
            runPartial(size)
        }

    }

    fun runPartial(size: Int){
        val limit = dealer.deck.deck.size
        dealer.deck.deck.sort()
        if (size == 3){
            for (turn in 0..limit-2){
                dealer.placeBoardCard(3, dealer.deck.deck[turn], true)
                for (river in turn+1 until  limit){
                    dealer.placeBoardCard(4, dealer.deck.deck[river], true)
                    dealer.replacePlayerBoard()
                    recordWinner()
                    dealer.removeBoardCard(4)
                }
                dealer.removeBoardCard(3)
            }
        } else{
            for (river in 0 until  limit) {
                dealer.placeBoardCard(4, dealer.deck.deck[river], true)
                dealer.replacePlayerBoard()
                recordWinner()
                dealer.removeBoardCard(4)
            }
        }
    }

    fun recordWinner(){
        val winner = dealer.readWinner()
        if (winner.size > 1) {
            average.addTie(winner)
        } else {
            average.addWin(winner[0])
        }
    }



    // I made accuracy a separate value to later incorporate the choice of how many hands to run into the UI
    fun getAccuracy(int: Int):Int{
        return when(int){
            2-> 4000
            3-> 8000
            4-> 16000
            5-> 50000
            else -> 2000
        }
    }
}