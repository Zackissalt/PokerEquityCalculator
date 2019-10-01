package com.zackjackman.freeequitycalculator

class Odds(val players:Int) {
    val playersOdds = ArrayList<Array<Int>>()
    var total = 0
    fun oddsHolders(){
        for (player in 0 until players){
            playersOdds.add(makeOdds())
        }
    }

    fun makeOdds():Array<Int>{
        return arrayOf(0,0)
    }

    fun addTie(ties: ArrayList<Int>){
        for (tie in 0 until ties.size) {
            playersOdds[tie][1]++
        }
        total++
    }

    fun addWin(player: Int){
        playersOdds[player][0]++
        total++
    }

    fun getWin(player: Int):Int{
        return playersOdds[player][0]
    }

    fun getTie(player: Int):Int{
        return playersOdds[player][1]
    }

}