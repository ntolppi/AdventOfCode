package net.nzti.solutions.day7

import net.nzti.readInput

fun main() {
    val inputList: MutableList<String> = readInput("day7_input.txt")

    val cards: MutableList<String> = mutableListOf()
    val cardsMap: MutableMap<String, Int> = mutableMapOf()
    inputList.forEach { card ->
        val cardAndBid: List<String> = card.split(' ')
        cards.add(cardAndBid[0])
        cardsMap[cardAndBid[0]] = cardAndBid[1].toInt()
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
        val card1Category: Int = categorizeCard(card1)
        val card2Category: Int = categorizeCard(card2)

        var compare: Int = -1
        if (card1Category != card2Category) {
            compare = card1Category - card2Category
        } else {
            for (idx in card1.indices) {
                if (card1[idx] != card2[idx]) {
                    val card1Val: Int = cardOpts.getOrDefault(card1[idx], -1)
                    val card2Val: Int = cardOpts.getOrDefault(card2[idx], -1)
                    compare = card1Val - card2Val
                    break
                }
            }
        }
        compare
    }

    val sortedCards: List<String> = cards.sortedWith(cardComparator)

    var winnings: Long = 0
    sortedCards.forEachIndexed { idx, card ->
        winnings += cardsMap.getOrDefault(card, 0) * (idx + 1)
    }
    println(winnings)
}


/**
 * Compare cards, 0 if equal, positive if greater than, negative if less than
 * Card options: A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2
 * Hands:
 * 5k 5 of a kind AAAAA = 6
 * 4k 4 of a kind KAAAA = 5
 * 3F Full House  KKAAA = 4
 * 3k 3 of a kind KAAAQ = 3
 * 2p pair        KKAAQ = 2
 * 1p pair        KKAQJ = 1
 * High Card      AKQJT = 0
 * @param card  Card to categorize
 * @return      Categorization of the card
 */
fun categorizeCard(card: String): Int {
    val cardCount: MutableMap<Char, Int> = mutableMapOf()
    for (i in card.indices) {
        cardCount[card[i]] = cardCount.getOrDefault(card[i], 0) + 1
    }

    val cardSorted: MutableMap<Char, Int> = mutableMapOf()
    cardCount.entries.sortedBy { it.value }.reversed().forEach { cardSorted[it.key] = it.value }

    val topCard: Int = cardSorted.values.toIntArray()[0]

    when(topCard) {
        // 5 of a kind val 6
        5 -> return 6
        // 4 of a kind val 5
        4 -> return 5
        3 -> {
            // Full house val 4
            val secondCard: Int = cardSorted.values.toIntArray()[1]
            if (secondCard == 2) return 4
            // 3 of a kind val 3
            return 3
        }
        2 -> {
            // 2 pair val 2
            val secondCard: Int = cardSorted.values.toIntArray()[1]
            if (secondCard == 2) return 2
            // 1 pair val 1
            return 1
        }
        // High card val 0
        1 -> return 0
        else -> {
            // Uncategorized
            return -1
        }
    }
}
