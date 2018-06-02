package ru.spbau.mit.roguelike.map

import org.junit.Before
import org.junit.Test
import ru.spbau.mit.roguelike.items.Junk

class GameMapTest {
    private val item = Junk("Trump's hair", "Somehow it managed to escape")

    private val cellList = listOf(
            WorldEntrance,
            WallCell,
            FloorCell(emptyList()),
            OpenedDoor,
            ClosedDoor,
            Chest(listOf(item)),
            UnseenCell,
            WorldExit
    )

    private lateinit var map: GameMap

    @Before
    fun initGameMap() {
        map = GameMap(cellList.mapIndexed { index, cell -> (index to 0) to cell }.toMap())
    }

    @Test
    fun testInteraction() {
        assert(map.interact(0 to 0) == NoInteraction)
        assert(map.interact(1 to 0) == NoInteraction)
        assert(map.interact(2 to 0) == NoInteraction)

        assert(map.interact(3 to 0) is ChangesState)
        assert(map[3, 0] === ClosedDoor) {
            "opened door should close"
        }

        assert(map.interact(4 to 0) is ChangesState)
        assert(map[4, 0] === OpenedDoor) {
            "closed door should open"
        }

        val chestInteraction = map.interact(5 to 0)
        assert(
                chestInteraction is CanExchangeItems
                        && chestInteraction.items.size == 1
                        && chestInteraction.items.contains(item)
        ) { "chest should offer items to take" }

        assert(map.interact(6 to 0) == NoInteraction)
        assert(map.interact(7 to 0) == GameFinish)
    }
}