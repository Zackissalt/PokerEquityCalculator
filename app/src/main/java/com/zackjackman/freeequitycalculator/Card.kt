package com.zackjackman.freeequitycalculator

 class Card(val rank:Int, val suit:Int) :Comparable<Card> {

     fun rankString():String{
         when(rank){
             11-> return "Jack"
             12 -> return "Queen"
             13 -> return "King"
             14 -> return "Ace"
             else -> return "$rank"
         }
     }

     fun suitString():String{
         when(suit){
             1 -> return "Club"
             2 -> return "Diamond"
             3 -> return "Heart"
             else -> return "Spade"

         }
     }


     fun equals(other: Card): Boolean {
        if (rank == other.rank && suit == other.suit){
            return true
        }
         return false
     }

     override fun compareTo(other: Card): Int {
         return this.rank - other.rank
     }

     override fun toString(): String {
         return "${rankString()} of ${suitString()}\n"
     }


 }