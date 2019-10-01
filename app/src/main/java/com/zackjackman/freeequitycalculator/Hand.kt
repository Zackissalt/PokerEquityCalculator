package com.zackjackman.freeequitycalculator


class Hand():Comparable<Hand> {
    var hand = ArrayList<Card>(7)

    fun clearHand(){
        hand.clear()
    }

    override fun compareTo(other: Hand): Int {
        if (getRank() != other.getRank()){
            return getRank() - other.getRank()
        } else {
            return forEachCardCompare(other)

        }

    }

    fun forEachCardCompare(other:Hand):Int{
        val handRanked = getRankedHand()
        val otherRanked = other.getRankedHand()
        for (i in 0..4){
            if (handRanked[i].rank != otherRanked [i].rank){
                return handRanked[i].rank - otherRanked[i].rank
            }

        }
        return 0

    }

    fun addCard(card:Card){
        hand.add(card)
    }

    fun addCard(cards:ArrayList<Card>){
        for (card in cards){
            addCard(card)
        }
    }

    fun removeCard(card: Card){
        hand.remove(card)
    }

    // Temp arrays for building and identifying

    fun getDoubles(): ArrayList<Int>{
        val doubles = ArrayList<Int>()
        val holder = ArrayList<Int>()
        for (card in hand){
            if (holder.contains(card.rank)){
                doubles.add(card.rank)
            }
            holder.add(card.rank)

        }
        return doubles

    }

    fun triplesGrab():ArrayList<Int>{
        val doubles = getDoubles()
        val holder = ArrayList<Int>()
        val triple = ArrayList<Int>()
        for (pair in doubles){
            if (holder.contains(pair)){
                triple.add(pair)
            }
            holder.add(pair)
        }
        return triple
    }

    fun flushGrab():ArrayList<Card>{
        return flushGrab(hand, true)
    }

    fun flushGrab(list:ArrayList<Card>, trim:Boolean):ArrayList<Card>{
        val flush = ArrayList<Card>()
        loop@for (i:Int in 1..4){
            if (flush.size > 4){
                break@loop
            }
            for (card:Card in list){
                if (card.suit.equals(i)){
                    flush.add(card)
                }

            }
            if (flush.size > 4){
                continue
            } else{
                flush.clear()
            }

        }
        if (trim){
            flush.sort()
            while (flush.size > 5){
                flush.removeAt(0)
            }
        }
        return flush
    }

    fun straightGrab():ArrayList<Int>{
        return straightGrab(hand)
    }

    fun straightGrab(list:ArrayList<Card>):ArrayList<Int>{

        val straightValues = ArrayList<Int>()
        val values = ArrayList<Int>()
        for (card in list){
            if (!values.contains(card.rank)){
                values.add(card.rank)
            }
        }
        if (values.size < 5){
            return straightValues
        }
        values.sort()
        if (values.contains(2)&&values.contains(14)){
            straightValues.add(2)
            straightValues.add(14)
            values.remove(2)
        }
        var straightBreak = false
        loop@for (i in 0..values.size-2){

            if(values.get(i) + 1 == values.get(i+1)){
                straightValues.add(values.get(i))
                straightBreak = true
            }else if (straightBreak){
                straightValues.add(values.get(i))
                straightBreak = false
                if (straightValues.size > 4) {
                    break@loop
                }
            } else {
                straightValues.clear()
                straightBreak = false
            }
        }
        if (straightValues.size < 5){
            return straightValues
        } else if (straightValues.size > 5 ){
            straightValues.sort()
            if (straightValues.get(0) == 2 && straightValues.get(straightValues.size - 1) == 14){
                if(straightValues.size > 6){
                    straightValues.remove(2)
                }
                straightValues.remove(14)
            }
            while (straightValues.size > 5){
                straightValues.removeAt(0)
            }
        }
        return straightValues
    }

    fun quadGrab():Int{
        val triples = triplesGrab()
        val holder = ArrayList<Int>()
        for (pair in triples){
            if (holder.contains(pair)){
                return pair
            }
            holder.add(pair)
        }
        return 0
    }

    // Boolean checks on every hand ranking

    fun checkPair():Boolean {
        return getDoubles().size == 1
    }

    fun checkTwoPair():Boolean{
        return getDoubles().size > 1
    }

    fun checkTrips():Boolean{
        return triplesGrab().size == 1
    }

    fun checkStraight() :Boolean{
        return straightGrab().size == 5
    }

    fun checkFlush() :Boolean {
        return flushGrab().size == 5
    }

    fun checkBoat():Boolean{
        return triplesGrab().size == 2 || (triplesGrab().size == 1 && getDoubles().size > 2)
    }

    fun checkQuad():Boolean{
        return quadGrab() != 0
    }

    fun checkStrFlush(): Boolean{
        return straightGrab(flushGrab(hand, false)).size == 5
    }



    fun printRank():String {
        hand.sort()
        return when(getRank()){
            9-> "Straight Flush\n" + arrayString(getStrFlush())
            8-> "Four of a Kind\n" + arrayString(getQuad())
            7-> "Full House\n" + arrayString(getFullHouse())
            6-> "Flush\n" + arrayString(getFlush())
            5-> "Straight\n" + arrayString(getStraight())
            4-> "Three of a Kind\n" + arrayString(getTrips())
            3-> "Two Pair\n" + arrayString(getTwoPair())
            2-> "Pair\n" + arrayString(getPair())
            else -> "High Card\n" + arrayString(getHighCard())
        }
    }

    fun getRankedHand(): ArrayList<Card>{
            hand.sort()
            return when(getRank()){
                9-> getStrFlush()
                8-> getQuad()
                7-> getFullHouse()
                6-> getFlush()
                5-> getStraight()
                4-> getTrips()
                3-> getTwoPair()
                2-> getPair()
                else -> getHighCard()
            }

    }

    fun getRank(): Int {
        return when {
            checkStrFlush() -> 9
            checkQuad() -> 8
            checkBoat() -> 7
            checkFlush() -> 6
            checkStraight() -> 5
            checkTrips() -> 4
            checkTwoPair() -> 3
            checkPair() -> 2
            else -> 1
        }
    }
    // Area for functions getting the ranked hand

    fun getHighCard():ArrayList<Card>{
        val highCard = hand
        highCard.sort()
        highCard.removeAt(0)
        highCard.removeAt(0)
        return highCard
    }
    fun getPair():ArrayList<Card>{
        val pair = ArrayList<Card>()
        for (card in hand){
            if (card.rank == getDoubles()[0]){
                pair.add(card)
                if (pair.size == 2){
                        break
                }
            }
        }
        hand.sortDescending()
        for (card in hand){
            if (!pair.contains(card)){
                pair.add(card)
                if (pair.size == 5){
                    break
                }
            }
        }
        return pair
    }
    fun getTwoPair():ArrayList<Card>{
        val twoPair = ArrayList<Card>()
        hand.sortDescending()
        loop@ for (card in hand){

            for (count in getDoubles()){
                if (twoPair.size == 4){
                    break@loop
                }
                if (card.rank == count){
                    twoPair.add(card)
                }
            }
        }
        for (card in hand){
            if (!twoPair.contains(card)){
                twoPair.add(card)
                if (twoPair.size == 5){
                    break
                }
            }
        }
        return twoPair
    }

    fun getTrips():ArrayList<Card>{
        val trips = ArrayList<Card>()
        for (card in hand){
            if (card.rank == triplesGrab()[0]){
                trips.add(card)
            }
        }
        hand.sortDescending()
        for (card in hand){
            if (card.rank != triplesGrab()[0]){
                trips.add(card)
                if (trips.size == 5){
                    break
                }
            }
        }
        return trips
    }

    fun getStraight():ArrayList<Card>{
        return getStraight(false, hand)
    }
    fun getStraight(getFlush:Boolean, list:ArrayList<Card>):ArrayList<Card>{
        val straight = ArrayList<Card>()
        for (rank in straightGrab(list)){
            loop@ for (card in list){
                if (card.rank == rank && !straight.contains(card)){
                    straight.add(card)
                    if (!getFlush) {
                        break@loop
                    }
                }
            }
        }
        straight.sortDescending()
        return straight
    }

    fun getFlush():ArrayList<Card>{
        val flush = flushGrab()
        flush.sortDescending()
        return flush
    }

    fun getFullHouse():ArrayList<Card>{
        val fullHouse = ArrayList<Card>()
        val trips = triplesGrab()
        val doubles = getDoubles()
        trips.sort()
        doubles.sortDescending()
        while (doubles[0] == trips[0]){
            doubles.removeAt(0)
        }
        if (trips.size == 2){
            for (card in hand){
                if (card.rank == trips[1]||card.rank == trips[0]){
                    fullHouse.add(card)
                }
            }
            fullHouse.sort()
            fullHouse.removeAt(0)
        } else {
            for (card in hand){
                if (card.rank == trips[0]){
                    fullHouse.add(card)
                }else if (card.rank == doubles[0]){
                        fullHouse.add(card)
                    }
                }
            }
        return fullHouse
    }

    fun getQuad():ArrayList<Card>{
        val quads = ArrayList<Card>()
        val quad = quadGrab()
        for (count in 1..4){
            quads.add(Card(quad, count))
        }
        hand.sortDescending()
        for (card in hand){
            if (quads[0].rank != card.rank){
                quads.add(card)
                break
            }
        }
        return quads
    }

    fun getStrFlush():ArrayList<Card>{
        return getStraight(true, flushGrab(hand, false))
    }


    override fun toString(): String {
        hand.sort()
        return "Hand(hand=$hand)"
    }

    fun arrayString(list:ArrayList<Card>):String{
        var holder = ""
        for (card in list){
            holder += card
        }
        return holder
    }
}
