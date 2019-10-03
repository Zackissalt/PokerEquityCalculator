package com.zackjackman.freeequitycalculator

class Player(val name: Int):Comparable<Player> {
    override fun compareTo(other: Player): Int {
        return getHand().compareTo(other.getHand())
    }
    /* In Texas Holdem Poker each person has two "hole cards" that they are dealt that only they have access too
    * while the board gets cards as the round progresses in groups of 3, 1, and 1 all players have access to
    * these cards but must make the best possible hand of 5 out of the 7 (the 5 board and 2 hole)*/
    var hand = ArrayList<Card> (5)
    var dealtHand = ArrayList<Card>(2)

    fun addDealtHand(card:Card){
        dealtHand.add(card)
    }

    /*
    fun removeDealtHand(card:Card){
        dealtHand.remove(card)
    }

    fun removeDealtHand(){
        dealtHand.clear()
    }
    */

    fun addCard(cards:ArrayList<Card>){
        for (card in cards){
            addCard(card)
        }
    }

    fun addCard(card:Card){
        hand.add(card)
    }

    fun getHand():Hand{
        val holder = Hand()
        holder.addCard(dealtHand)
        holder.addCard(hand)
        return holder
    }

    fun clearBoard(){
        hand.clear()
    }

    override fun toString(): String {
        var holder = ""
        for (hand in dealtHand){
            holder += hand.toString()
        }
        return "$name has; \n$holder" + "And a hand ranking of:\n${getHand().printRank()}\n"
    }





}