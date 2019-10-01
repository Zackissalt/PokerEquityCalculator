package com.zackjackman.freeequitycalculator

class Player(val name: Int):Comparable<Player> {
    override fun compareTo(other: Player): Int {
        return getHand().compareTo(other.getHand())
    }

    var hand = ArrayList<Card> (5)
    var dealtHand = ArrayList<Card>(2)

    fun addDealtHand(card:Card){
        dealtHand.add(card)
    }

    fun removeDealtHand(card:Card){
        dealtHand.remove(card)
    }

    fun removeDealtHand(){
        dealtHand.clear()
    }

    fun addCard(cards:ArrayList<Card>){
        for (card in cards){
            hand.add(card)
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