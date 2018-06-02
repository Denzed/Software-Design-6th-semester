package ru.spbau.mit.roguelike.creatures

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import ru.spbau.mit.roguelike.map.manhattanDistance
import ru.spbau.mit.roguelike.map.plus
import ru.spbau.mit.roguelike.runner.EmptyMapGenerator
import ru.spbau.mit.roguelike.runner.GameSettings

class MonsterTest {
    private val mapDimensions = 10 to 10

    private val map = EmptyMapGenerator.generateMap(GameSettings(
            mapDimensions,
            GameSettings.Difficulty.EASY
    ))

    private val hero = TestHero()
    private val monster = Monster("test", 1f, 1f)

    @Test
    fun testMonsterMovesTowardsHero() {
        val heroPosition = 1 to 1
        val monsterPosition = mapDimensions

        val monsterMove = runBlocking {
            val action = monster.askAction(
                    monsterPosition,
                    map,
                    mapOf(
                            heroPosition to setOf(hero),
                            monsterPosition to setOf(monster)
                    )
            )
            assert(action is Move) { "monster should move towards hero" }
            return@runBlocking action as Move
        }

        val oldDistance = heroPosition.manhattanDistance(monsterPosition)
        val newDistance = heroPosition.manhattanDistance(monsterPosition + monsterMove.direction)

        assert(newDistance <= oldDistance) { "monster should try to reduce distance to hero" }
    }

    @Test
    fun testMonsterAttacksNearHero() {
        val heroPosition = 1 to 1
        val monsterPosition = 1 to 2

        val monsterAttack = runBlocking {
            val action = monster.askAction(
                    monsterPosition,
                    map,
                    mapOf(
                            heroPosition to setOf(hero),
                            monsterPosition to setOf(monster)
                    )
            )
            assert(action is Attack) { "monster should attack hero if it's near" }
            return@runBlocking action as Attack
        }

        assert(heroPosition == monsterPosition + monsterAttack.direction) {
            "monster's attack should be directed to hero"
        }
    }

    @Test
    fun testMonsterAttacksHeroInSameCell() {
        val heroPosition = 1 to 1

        val monsterAttack = runBlocking {
            val action = monster.askAction(
                    heroPosition,
                    map,
                    mapOf(
                            heroPosition to setOf(hero, monster)
                    )
            )
            assert(action is Attack) { "monster should attack hero if it's near" }
            return@runBlocking action as Attack
        }

        assert(heroPosition == heroPosition + monsterAttack.direction) {
            "monster's attack should be directed to hero"
        }
    }
}