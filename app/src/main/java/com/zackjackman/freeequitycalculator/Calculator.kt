package com.zackjackman.freeequitycalculator

import kotlin.math.roundToInt

class Calculator (val player: Int, deck: Deck, val cards :ArrayList<Card>, val accuracy:Int) {
    val dealer = Dealer(player, deck)

    fun dealerSetUp(){
        dealer.createPlayers()
        dealer.placePlayerCard(cards)
    }

    fun randomHands(){
        dealer.runBoard()
    }


    fun calculateOdds(board: ArrayList<Card>):ArrayList<String> {
        val average = Odds(player)
        average.oddsHolders()
        dealer.placeBoardCard(0, board,true)
        for (a in 1..getAccuracy(accuracy)) {
            dealer.shuffleDeck()
            for(b in board.size..4) {
                dealer.placeBoardCard(b, dealer.deck.deck[b], true)
            }
            dealer.replacePlayerBoard()
            val winner = dealer.readWinner()
            if (winner.size > 1) {
                average.addTie(winner)
            } else {
                average.addWin(winner[0])
            }
        }
        val results = ArrayList<String>()
        val total = getAccuracy(accuracy)
        for (odd in 0 until  player){
            var odds = ""
            odds += "Win: " + ((average.getWin(odd) * 1000.0)/total).roundToInt()/10.0 + "%\n" +
                    "Ties: " + (((average.getTie(odd) * 1000.0)/total).roundToInt())/10.0 + "%"
            results.add(odds)
        }
        return results
    }

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