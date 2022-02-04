import java.io.File

// tag::standardDice[]
fun standardDice(play1_init: Int, play2_init: Int): Int {
    var play1 = play1_init
    var play2 = play2_init
    var play1Score: Int = 0
    var play2Score: Int = 0
    var dice: Int = 1
    var diceCount = 0

    while (play1Score < 1000 && play2Score < 1000) {
        // three turns player 1
        for (i in 0..2) {
            play1 = ((play1 + dice - 1) % 10) + 1
            dice = (dice) % 100 + 1
        }
        diceCount += 3
        play1Score = play1Score + play1
        if (play1Score >= 1000) {
            return (play2Score * diceCount)
        }
        // three turns player 2
        for (i in 0..2) {
            play2 = (play2 + dice - 1) % 10 + 1
            dice = (dice) % 100 + 1
        }
        diceCount += 3
        play2Score = play2Score + play2
        if (play2Score >= 1000) {
            return (play1Score * diceCount)
        }
    }
    return 1
}
// end::standardDice[]

// list allows direct indexing -> much faster than map
// do not create list seveal times inside function diracDice
val diracDiceCounts = listOf(0, 0, 0, 1, 3, 6, 7, 6, 3, 1)

// tag::diracDice[]
fun diracDice(play1Init: Int, play2Init: Int, dice: List<Int>): Pair<Int, Long> {
    var play1 = play1Init
    var play2 = play2Init
    var play1Score: Int = 0
    var play2Score: Int = 0
    var numOfVars: Long = 1

    var i: Int = 0
    while (true) {
        numOfVars = numOfVars * diracDiceCounts[dice[i]]

        play1 = ((play1 + dice[i] - 1) % 10) + 1
        play1Score = play1Score + play1
        i += 1

        if (play1Score >= 21) {
            return Pair(1, numOfVars)
        } else if (i > dice.size - 1) {
            return Pair(0, 0)
        }

        numOfVars = numOfVars * diracDiceCounts[dice[i]]

        play2 = (play2 + dice[i] - 1) % 10 + 1
        play2Score = play2Score + play2
        i += 1

        if (play2Score >= 21) {
            return Pair(2, numOfVars)
        } else if (i > dice.size - 1) {
            return Pair(0, 0)
        }
    }
}
// end::diracDice[]

//fun main(args: Array<String>) {
var t1 = System.currentTimeMillis()

// tag::read_puzzle_input[]
var startingSpace = mutableListOf<Int>()

File("day2121_puzzle_input.txt").forEachLine {
    startingSpace.add(it.takeLast(1).toString().toInt())
}

var play1 = startingSpace[0]
var play2 = startingSpace[1]
// end::read_puzzle_input[]

// tag::part1[]
var solution1 = standardDice(play1, play2)
// end::part1[]

// tag::part2[]
var play1Wins: Long = 0
var play2Wins: Long = 0

var diceVarOverall = MutableList(0) { mutableListOf<Int>() }
var diceVarOverallNew = MutableList(0) { mutableListOf<Int>() }
var diceVariation = mutableListOf<Int>()

for (ii in 3..9) {
    for (iii in 3..9) {
        diceVariation.add(ii)
        diceVariation.add(iii)
        diceVarOverall.add(diceVariation.toMutableList())
        diceVariation.clear()

        println("-----------------")
        println("-- next turn   --")
        println("-----------------")
        print("   diceVarOverall: ")
        println(diceVarOverall)

        while (diceVarOverall.isNotEmpty()) {

            diceVarOverall.forEach {
                var result = diracDice(play1, play2, it)
                if (result.first == 1) {
                    play1Wins = play1Wins + result.second
                } else if (result.first == 2) {
                    play2Wins = play2Wins + result.second
                } else if (result.first == 0) {
                    for (i in 3..9) {
                        var diceVariationNew = it.toMutableList()
                        diceVariationNew.add(i)
                        diceVarOverallNew.add(diceVariationNew.toMutableList())
                        //	diceVariationNew.clear()
                    }
                }

            }
            diceVarOverall.clear()
            diceVarOverall.addAll(diceVarOverallNew)
            diceVarOverallNew.clear()

        } // end While (diceVarOverall.isNotEmpty())
        println()
    }
}

var solution2 = maxOf(play1Wins, play2Wins)
// end::part2[]

// tag::output[]
// print solution for part 1
println("*******************************")
println("--- Day 21: Dirac Dice ---")
println("*******************************")
println("Solution for part1")
println("   $solution1 you get if you multiply the score of the losing player by the number of times the die was rolled during the game")
println()
// print solution for part 2
println("*******************************")
println("Solution for part2")
println("   $solution2 are the most wins in all universes")
println()
// end::output[]
t1 = System.currentTimeMillis() - t1
println("puzzle solved in ${t1} ms")
//}
