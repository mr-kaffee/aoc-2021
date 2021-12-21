import java.io.File
import kotlin.math.*

class scanX(field: List<Triple<Int, Int, Int>>, scannerID: String) {
    // this class should represent a single scan with data of puzzle input
    val beacons = field
    val beaconsFlipRot = mutableListOf<Triple<Int, Int, Int>>()
    val scannerID = scannerID

    var scanPosX: Int = 0
    var scanPosY: Int = 0
    var scanPosZ: Int = 0

    // this method is used to flip/rotate the scan
    fun flipRotate(trafo: Int) {
        scanPosX = 0
        scanPosY = 0
        scanPosZ = 0
        when (trafo) {
            (0) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.first, it.second, it.third))
                }
            }
            (1) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.first, -it.second, -it.third))
                }
            }
            (2) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.first, it.third, -it.second))
                }
            }
            (3) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.first, -it.third, it.second))
                }
            }
            (4) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.first, it.second, -it.third))
                }
            }
            (5) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.first, -it.second, it.third))
                }
            }
            (6) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.first, it.third, it.second))
                }
            }
            (7) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.first, -it.third, -it.second))
                }
            }
            (8) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.second, it.first, -it.third))
                }
            }
            (9) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.second, -it.first, it.third))
                }
            }
            (10) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.second, it.third, it.first))
                }
            }
            (11) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.second, -it.third, -it.first))
                }
            }
            (12) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.second, it.first, it.third))
                }
            }
            (13) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.second, -it.first, -it.third))
                }
            }
            (14) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.second, it.third, -it.first))
                }
            }
            (15) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.second, -it.third, it.first))
                }
            }
            (16) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.third, it.first, it.second))
                }
            }
            (17) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.third, -it.first, -it.second))
                }
            }
            (18) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.third, it.second, -it.first))
                }
            }
            (19) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(it.third, -it.second, it.first))
                }
            }
            (20) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.third, it.first, -it.second))
                }
            }
            (21) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.third, -it.first, it.second))
                }
            }
            (22) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.third, it.second, it.first))
                }
            }
            (23) -> {
                beaconsFlipRot.clear()
                beacons.forEach {
                    beaconsFlipRot.add(Triple(-it.third, -it.second, -it.first))
                }
            }
        }
    }

    // this function is used to adapt the offset of the scan
    fun shiftByOffset(x_off: Int, y_off: Int, z_off: Int) {
        for (i in 0..beaconsFlipRot.size - 1) {
            beaconsFlipRot[i] = Triple(
                beaconsFlipRot[i].first + x_off,
                beaconsFlipRot[i].second + y_off,
                beaconsFlipRot[i].third + z_off
            )
        }
        scanPosX += x_off
        scanPosY += y_off
        scanPosZ += z_off
    }

}

//fun main(args: Array<String>) {
var t1 = System.currentTimeMillis()
var solution1: Int = 0
var solution2: Int = 0

// tag::read_puzzle_input[]
// setup for reading puzzle input
var scanInput = mutableListOf<Triple<Int, Int, Int>>()
var allScans = mutableListOf<scanX>()
var scanID: String = ""

// read all scans of puzzle input into scanX classes and add them to a list of unmathced scans (allScans)
File("day2119_puzzle_input.txt").forEachLine {
    if (it.contains("--")) {
        scanInput.clear()
        scanID = it.drop(4).dropLast(4).replace(" ", "-")
    } else if (it.length > 1) {
        var position = it.split(",")
        scanInput.add(
            Triple(
                position[0].toString().toInt(),
                position[1].toString().toInt(),
                position[2].toString().toInt()
            )
        )
    } else {
        //println(scanInput.sortedWith(compareBy({ it.first }, { it.second }, { it.third }))):  not necessary, does not bring any benefit
        allScans.add(scanX(scanInput.toList(), scanID))
    }
}
// end::read_puzzle_input[]

// tag::matchScans[]
// initialize list with matched scans (matchedScans) with first element of allScans list
var matchedScans = mutableListOf<scanX>()
matchedScans.add(allScans[0])
matchedScans[0].flipRotate(0)
matchedScans[0].scanPosX = 0
matchedScans[0].scanPosY = 0
matchedScans[0].scanPosY = 0

allScans.removeAt(0)

// allScans contains the unmatched scans, if a match is detected the object will be moved to matchedScans
// so if allScans is empty, no scan to match is left, job done
var matched_idx: Int = 0
while (allScans.isNotEmpty()) {
    val iter = allScans.iterator()
    while (iter.hasNext()) {
        val scan = iter.next()

        Loop@ for (trafo in 0..23) {
            scan.flipRotate(trafo)

            for (beacon1 in matchedScans[matched_idx].beaconsFlipRot) {
                for (beacon2 in scan.beaconsFlipRot) {
                    var x_off = beacon1.first - beacon2.first
                    var y_off = beacon1.second - beacon2.second
                    var z_off = beacon1.third - beacon2.third
                    scan.shiftByOffset(x_off, y_off, z_off)

                    if (matchedScans[matched_idx].beaconsFlipRot.intersect(scan.beaconsFlipRot).size > 11) {
                        // shift allScan into matchedScan
                        matchedScans.add(scan)
                        iter.remove()
                        break@Loop
                    }
                }
            }
        }
    }

    matched_idx += 1
}
// end::matchScans[]

// tag::part1[]
// for part1 print all beacons in a list without dublicates and count
var beaconsList = mutableListOf<Triple<Int, Int, Int>>()

matchedScans.forEach {
    it.beaconsFlipRot.forEach {
        if (!beaconsList.contains(it)) {
            beaconsList.add(it)
        }
    }
}

solution1 = beaconsList.size
// end::part1[]

// tag::part2[]
// for part2 iterate through absolute coordinates of all Scans and calculate manhatten distance
var manhattenDistMax: Int = 0
var manhattenDist: Int = 0

for (i in 0..matchedScans.size - 1) {
    for (j in 0..matchedScans.size - 1) {
        if (i != j) {
            manhattenDist = abs(matchedScans[i].scanPosX - matchedScans[j].scanPosX)
            manhattenDist = manhattenDist + abs(matchedScans[i].scanPosY - matchedScans[j].scanPosY)
            manhattenDist = manhattenDist + abs(matchedScans[i].scanPosZ - matchedScans[j].scanPosZ)

            if (manhattenDist > manhattenDistMax) {
                manhattenDistMax = manhattenDist
            }
        }
    }
}

solution2 = manhattenDistMax
// end::part2[]

// tag::output[]
// print solution for part 1
println("******************************")
println("--- Day 19: Beacon Scanner ---")
println("******************************")
println("Solution for part1")
println("   $solution1 beacons are there")
println()
// print solution for part 2
println("******************************")
println("Solution for part2")
println("   $solution2 is the largest Manhattan distance between any two scanners")
println()
t1 = System.currentTimeMillis() - t1
println("puzzle solved in ${t1} ms")
// end::output[]
//}