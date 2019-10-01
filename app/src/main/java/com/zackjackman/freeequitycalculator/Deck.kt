package com.zackjackman.freeequitycalculator


import kotlin.collections.ArrayList


class Deck {
    var deck = ArrayList<Card>(52)

    fun newDeck() {
        deck.clear()
        for (suit:Int in 1..4){
            for (rank:Int in 2..14){
                deck.add(Card(rank,suit))
            }
        }
    }

    fun pitchCard(): Card {
        val holder = deck.get(0)
        deck.removeAt(0)
        return holder
    }

    fun addCard(card: Card){
        deck.add(card)
    }

    fun getCard(card: Card):Card{
        for (item in deck){
            if (item.equals(card)){
                return item
            }
        }
        return Card(0,0)
    }

    fun removeCard(card: Card) {
        deck.remove(getCard(card))
    }

    fun shuffle(){
        deck.shuffle()
    }

    override fun toString(): String {
        return "Deck(deck=$deck)"
    }


}