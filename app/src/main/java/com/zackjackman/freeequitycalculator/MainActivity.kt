package com.zackjackman.freeequitycalculator

import android.graphics.Color
import android.graphics.Color.parseColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
    val cardGroup = ArrayList<Int>()
    val deck = Deck()
    var suitSelect = true
    var playerChoice = false
    var buttonId = 0
    var suit = 0
    var players = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        makeArray()
        findViewById<ConstraintLayout>(R.id.cardPick).visibility = View.INVISIBLE
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.INVISIBLE
        playerVisability()
        suitOptions()
        deck.newDeck()
    }


    fun cardClick(view:View){
        if (buttonId != 0){
            findViewById<ImageButton>(buttonId).setImageResource(R.drawable.card00)
            cardUnselect()
        } else if (playerChoice){
            cardUnselect()
        }
        val cardSelected = view as ImageButton
        buttonId = view.id
        val tag = cardSelected.tag.toString()

        if (tag != "0"){
            putCardBack()
            findViewById<ImageButton>(buttonId).setTag("0")
        }
        cardSelected.setImageResource(R.drawable.card10)
        val layout = findViewById<ConstraintLayout>(R.id.cardPick)
        layout.visibility = View.VISIBLE

    }

    fun cardSelect(view:View) {
        if(playerChoice){
            playerPick(view)
            cardUnselect()
        }else if (suitSelect){
            if(suitPicked(view)){
            rankOptions()
            suitSelect = false
            }else{
                cardUnselect()
            }
        }else{
            val id = rankPick(view)
            if (id != 0){
                findViewById<ImageButton>(buttonId).setImageResource(id)
            }else {
                findViewById<ImageButton>(buttonId).setImageResource(R.drawable.card00)
            }
            cardUnselect()
        }
    }

    fun playerClick(view:View) {
        suitSelect = true
        cardUnselect()
        playerOptions()
        playerChoice = true
    }

    fun calculate(view:View) {
        val bar = findViewById<ProgressBar>(R.id.progressBar)
        bar.visibility = View.VISIBLE
        suitSelect = true
        cardUnselect()
        val board = getBoard()
        Thread(Runnable{ this@MainActivity.runOnUiThread {
            if (checkCardFull() && board.size !in 1..2) {
                val calc = Calculator(players, deck, getHands(), 3)
                calc.dealerSetUp()
                val result = calc.calculateOdds(board)
                placeOdds(result)
            }
            bar.visibility = View.INVISIBLE
            }}).start()




    }

    fun cardUnselect(){
        if(suitSelect && buttonId != 0){
            findViewById<ImageButton>(buttonId).setImageResource(R.drawable.card00)
        } else{
            suitSelect = true
        }
        playerChoice = false
        buttonId = 0
        suit = 0
        val layout = findViewById<ConstraintLayout>(R.id.cardPick)
        layout.visibility = View.INVISIBLE
        suitOptions()

    }

    fun suitPicked(view: View):Boolean{
        val suitSelected = view as ImageButton
        when(suitSelected.id){
            R.id.cardRanks14 -> suit = 1
            R.id.cardRanks2 ->  suit = 2
            R.id.cardRanks3 ->  suit = 3
            R.id.cardRanks4 ->  suit = 4
            else -> return false
        }

        return true

    }

    fun suitOptions(){
        for (id in cardGroup){
            val button = findViewById<ImageButton>(id)
            button.visibility = View.VISIBLE
            when(id){
                R.id.cardRanks14 -> button.setImageResource(R.drawable.card01)
                R.id.cardRanks2 ->  button.setImageResource(R.drawable.card02)
                R.id.cardRanks3 ->  button.setImageResource(R.drawable.card03)
                R.id.cardRanks4 ->  button.setImageResource(R.drawable.card04)
                R.id.cardRanks7 ->  button.setImageResource(R.drawable.card00)
                else -> button.visibility = View.INVISIBLE

            }
        }
    }

    fun rankOptions(){
        for (id in cardGroup){
            val button = findViewById<ImageButton>(id)
            button.visibility = View.VISIBLE
            val tag= (button.tag.toString())
            if (tag != "reset") {
                val card = cardGrab(tag)
                    if (deck.deck.contains(card)){
                        val image = "card" + card.rank + card.suit
                        val drawId = this.getResources().getIdentifier(image, "drawable", this.packageName)
                        button.setImageResource(drawId)
                    } else {
                        button.setImageResource(R.drawable.card00)
                        button.visibility = View.INVISIBLE
                    }
            }
        }
    }

    fun rankPick(view: View):Int{
        val button = view as ImageButton
        val tag= button.tag.toString()
        if (tag != "reset"){
            val card = cardGrab(tag)
            val image = "card" + tag + suit
            val drawId = this.getResources().getIdentifier(image, "drawable", this.packageName)
            deck.removeCard(card)
            var newTag = ""
            newTag += tag
            newTag += suit
            findViewById<ImageButton>(buttonId).setTag(newTag)
            return drawId
        } else {
            return 0
        }
    }

    fun cardGrab(tag: String):Card{
        return deck.getCard(Card(tag.toInt(), suit))
    }

    fun putCardBack(){
        deck.addCard(readCard())
    }

    fun readCard():Card{
        val tag = findViewById<ImageButton>(buttonId).tag.toString()
        var rank = ""
        var suitChar = ""
        for (char in 0 until tag.length){
            if (char != tag.length-1) {
                rank += tag[char]
            } else{
                suitChar += tag[char]
            }
        }
        return Card(rank.toInt(), suitChar.toInt())
    }

    fun playerOptions(){

        for (id in 0..13){
            if (id in 1..7 ) {
                val image = "card" + (id + 1) + 0
                val drawId = this.getResources().getIdentifier(image, "drawable", this.packageName)
                findViewById<ImageButton>(cardGroup[id]).setImageResource(drawId)
                findViewById<ImageButton>(cardGroup[id]).visibility = View.VISIBLE
            } else{
                findViewById<ImageButton>(cardGroup[id]).visibility = View.INVISIBLE
            }
            val layout = findViewById<ConstraintLayout>(R.id.cardPick)
            layout.visibility = View.VISIBLE

        }
    }

    fun playerPick(view: View){
        val button = view as ImageButton
        val tag = button.tag.toString().toInt()
        when (tag){
            2-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players2)
            3-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players3)
            4-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players4)
            5-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players5)
            6-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players6)
            7-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players7)
            8-> findViewById<Button>(R.id.PlayerNumber).setText(R.string.players8)
        }
        players = tag
        playerVisability()
    }

    fun playerVisability(){

        for (count in 3.. 8) {
            val file = "Player0" + count
            val layout = this.getResources().getIdentifier(file, "id", this.packageName)
            var vis = View.VISIBLE
            if (count > players){
                vis = View.INVISIBLE
                clearPlayersHand(count)
            }
            findViewById<ConstraintLayout>(layout).visibility = vis
        }

    }

    fun clearPlayersHand(count:Int){
        for (card in 0..1) {
            val file = "Player" + count + "Card" + card
            val layout = this.getResources().getIdentifier(file, "id", this.packageName)
            buttonId = layout
            if (findViewById<ImageButton>(layout).tag.toString() != "0"){
                putCardBack()
                findViewById<ImageButton>(layout).tag = 0
            }
            findViewById<ImageButton>(layout).setImageResource(R.drawable.card00)
        }
        buttonId = 0

    }

    fun checkCardFull():Boolean{
        for (button in getPlayerCards()){
            if (findViewById<ImageButton>(button).tag.toString() == "0"){
                return false
            }
        }
        return true
    }

    fun getHands():ArrayList<Card>{
        val hands = ArrayList<Card>(16)
        for (button in getPlayerCards()){
            buttonId = button
            hands.add(readCard())
        }
        buttonId = 0
        cardUnselect()
        return hands
    }

    fun getPlayerCards():ArrayList<Int> {
        val buttons = ArrayList<Int>(16)
        for (count in 1.. players) {
            for (card in 0..1) {
                val file = "Player" + count + "Card" + card
                val layout = this.getResources().getIdentifier(file, "id", this.packageName)
                buttons.add(layout)
            }
        }
        return buttons
    }

    fun getBoard():ArrayList<Card>{
        val board = ArrayList<Card>(5)
        for (count in 0.. 4) {
            val file = "board" + count
            val layout = this.getResources().getIdentifier(file, "id", this.packageName)
            if (findViewById<ImageButton>(layout).tag.toString() != "0"){
                buttonId = layout
                board.add(readCard())
            }
        }
        buttonId = 0
        cardUnselect()
        return board
    }

    fun placeOdds(odds:ArrayList<String>){
        for (count in 1..8){
            val file = "Player" + count + "Odds"
            val view = this.getResources().getIdentifier(file, "id", this.packageName)
            if (count <= odds.size){
                findViewById<TextView>(view).text = odds[count-1]
            } else {
                findViewById<TextView>(view).text = "Player $count Odds"
            }
        }
    }

    fun makeArray(){
        cardGroup.add(R.id.cardRanks14)
        cardGroup.add(R.id.cardRanks2)
        cardGroup.add(R.id.cardRanks3)
        cardGroup.add(R.id.cardRanks4)
        cardGroup.add(R.id.cardRanks5)
        cardGroup.add(R.id.cardRanks6)
        cardGroup.add(R.id.cardRanks7)
        cardGroup.add(R.id.cardRanks8)
        cardGroup.add(R.id.cardRanks9)
        cardGroup.add(R.id.cardRanks10)
        cardGroup.add(R.id.cardRanks11)
        cardGroup.add(R.id.cardRanks12)
        cardGroup.add(R.id.cardRanks13)
        cardGroup.add(R.id.cardRanksReset)

    }


}
