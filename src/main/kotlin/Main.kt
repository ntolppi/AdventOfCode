package net.nzti

fun main() {
    val inputList: MutableList<String> = readInput("day7_input.txt")

    val cards: MutableList<String> = mutableListOf()
    val bids: MutableList<Int> = mutableListOf()
    inputList.forEach { card ->
        val cardAndBid: List<String> = card.split(' ')
        cards.add(cardAndBid[0])
        bids.add(cardAndBid[1].toInt())
    }

    val cardOpts: Map<Char, Int> = mapOf('A' to 14, 'K' to 13, 'Q' to 12, 'J' to 11, 'T' to 10, '9' to 9, '8' to 8, '7' to 7, '6' to 6, '5' to 5, '4' to 4, '3' to 3, '2' to 2)
    val cardComparator = Comparator { card1: String, card2: String ->
        /*
        Compare cards, 0 if equal, positive if greater than, negative if less than
        Card options: A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2
        Hands:
            5 of a kind AAAAA
            4 of a kind KAAAA
            3 of a kind KAAAQ
            2 pair      KKAAQ
            1 pair      KKAQJ
            High Card   AKQJT
         */
        val card1Count: MutableMap<Char, Int> = mutableMapOf()
        val card2Count: MutableMap<Char, Int> = mutableMapOf()
        for (i in card1.indices) {
            card1Count[card1[i]] = card1Count.getOrDefault(card1[i], 0) + 1
            card2Count[card2[i]] = card1Count.getOrDefault(card2[i], 0) + 1
        }

        val card1Sorted: MutableMap<Char, Int> = mutableMapOf()
        card1Count.entries.sortedBy { it.value }.reversed().forEach { card1Sorted[it.key] = it.value }

        val card2Sorted: MutableMap<Char, Int> = mutableMapOf()
        card2Count.entries.sortedBy { it.value }.reversed().forEach { card2Sorted[it.key] = it.value }

        println(card1Sorted)
        println(card2Sorted)

        -1
    }

    println(cards.sortedWith(cardComparator))
}
