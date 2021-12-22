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
fun diracDice(play1Init: Int, play2Init: Int, diceSeq: List<Int>): Pair<Int, Long> {
    var play1 = play1Init
    var play2 = play2Init
    var play1Score: Int = 0
    var play2Score: Int = 0
    var numOfVars: Long = 1

    var i: Int = 0
    while (true) {
        numOfVars = numOfVars * diracDiceCounts[diceSeq[i]]

        play1 = ((play1 + diceSeq[i] - 1) % 10) + 1
        play1Score = play1Score + play1
        i += 1

        if (play1Score >= 21) {
            return Pair(1, numOfVars)
        } else if (i > diceSeq.size - 1) {
            return Pair(0, 0)
        }

        numOfVars = numOfVars * diracDiceCounts[diceSeq[i]]

        play2 = (play2 + diceSeq[i] - 1) % 10 + 1
        play2Score = play2Score + play2
        i += 1

        if (play2Score >= 21) {
            return Pair(2, numOfVars)
        } else if (i > diceSeq.size - 1) {
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

// double ended queue
// instead of list.forEach() {} simple remove elements at the head of the queue and add new ones at the tail of the
// queue
// this also avoids to maintain a temporary list which is added to the overall list once the original elements are
// consumned
var diceVarOverall = ArrayDeque<List<Int>>()
var totalSeqs = 0

for (dice1 in 3..9) {
    for (dice2 in 3..9) {
        diceVarOverall.addLast(listOf(dice1, dice2))

        println("-----------------")
        println("-- next turn   --")
        println("-----------------")
        print("   diceVarOverall: ")
        println(diceVarOverall)

        while (diceVarOverall.isNotEmpty()) {
            // remove element from the head
            val diceSeq = diceVarOverall.removeFirst()
            totalSeqs += 1

            // destructuring assignment makes code more readable
            val (winner, score) = diracDice(play1, play2, diceSeq)
            if (winner == 1) {
                play1Wins = play1Wins + score
            } else if (winner == 2) {
                play2Wins = play2Wins + score
            } else if (winner == 0) {
                for (dice in 3..9) {
                    var diceSeqUpd = diceSeq.toMutableList()
                    diceSeqUpd.add(dice)
                    // add element at the tail
                    diceVarOverall.addLast(diceSeqUpd)
                }
            }
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
println("   Explored a total of $totalSeqs dice sequences")
println()
// end::output[]
t1 = System.currentTimeMillis() - t1
println("puzzle solved in ${t1} ms")
//}
