package com.zackjackman.freeequitycalculator

class Board {
    var board = ArrayList<Card>(5)

    fun flop(flop1:Card, flop2:Card, flop3:Card){
        board.add(flop1)
        board.add(flop2)
        board.add(flop3)
    }

    fun street(turn:Card){
        board.add(turn)
    }

    fun placeCard(postion: Int, card: Card) {
        board.add(postion, card)
    }

    fun replaceCard(postion: Int, card: Card):Card{
        val holder = board.get(postion)
        board.add(postion, card)
        board.removeAt((postion + 1))
        return holder
    }

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

    fun getBoardSize():Int {
        return board.size
    }





}