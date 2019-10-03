package com.zackjackman.freeequitycalculator

class Board {
    var board = ArrayList<Card>(5)

    /* Flop and Street are poker terms flop is the first three cards that come to the board at the same time
    while street refers to the the "Next Street" which could mean the turn or river also know as the fourth and fifth cards respectively
    fun flop(flop1:Card, flop2:Card, flop3:Card){
        board.add(flop1)
        board.add(flop2)
        board.add(flop3)
    }

    fun street(turn:Card){
        board.add(turn)
    }
    */

    fun placeCard(position: Int, card: Card) {
        board.add(position, card)
    }

    fun replaceCard(position: Int, card: Card):Card{
        val holder = board.get(position)
        board.add(position, card)
        board.removeAt((position + 1))
        return holder
    }

    fun removeCard(position: Int){
        board.removeAt(position)
    }

    /*
    fun getFlop():ArrayList<Card>{
        val holder = ArrayList<Card>(3)
        for (i in 0..2) {
            holder.add(board.get(i))
        }
        return holder
    }

    fun getTurn():Card{
        return board.get(3)
    }

    fun getRiver():Card{
        return board.get(4)
    }

    fun getFullBoard():ArrayList<Card> {
        return board
    }
    */

    fun getBoardSize():Int {
        return board.size
    }





}