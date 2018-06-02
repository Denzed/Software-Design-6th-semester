package ru.spbau.mit.roguelike.creatures

import org.junit.Before
import org.junit.Test
import ru.spbau.mit.roguelike.map.plus
import ru.spbau.mit.roguelike.runner.EmptyMapGenerator
import ru.spbau.mit.roguelike.runner.GameSettings

class CreatureManagerTest {
    private val mapDimensions = 10 to 10

    private val map = EmptyMapGenerator.generateMap(GameSettings(
            mapDimensions,
            GameSettings.Difficulty.EASY
    ))

    private val hero = TestHero()
    private val monster = Monster("test", 1f, 1f)

    private lateinit var creatureManager: CreatureManager

    @Before
    fun initCreatureManager() {
        creatureManager = CreatureManager(hero, mapOf(map.entrance to setOf(monster)), map)
    }

    @Test
    fun testCreatureDeath() {
        assert(creatureManager[map.entrance].size == 2) {
            "Creatures should have been on the map"
        }

        assert(creatureManager.processAttack(hero, map.entrance, Direction.CURRENT, monster)) {
            "Monster should have died"
        }

        val creaturesOnEntrance = creatureManager[map.entrance]

        assert(creaturesOnEntrance.size == 1 && creaturesOnEntrance.contains(hero)) {
            "Killed monster should have been removed from the map"
        }
    }

    @Test
    fun testCreatureMove() {
        assert(creatureManager[map.entrance].size == 2) {
            "Creatures should have been on the map"
        }

        creatureManager.processMove(hero, map.entrance, Direction.SOUTH)

        val creaturesOnEntrance = creatureManager[map.entrance]
        assert(creaturesOnEntrance.size == 1 && creaturesOnEntrance.contains(monster)) {
            "Monster should have stayed on entrance"
        }

        val creaturesToSouth = creatureManager[map.entrance + Direction.SOUTH]
        assert(creaturesToSouth.size == 1 && creaturesToSouth.contains(hero)) {
            "Hero should have moved to the south"
        }
    }
}