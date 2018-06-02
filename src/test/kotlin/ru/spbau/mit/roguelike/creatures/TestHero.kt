package ru.spbau.mit.roguelike.creatures

import ru.spbau.mit.roguelike.creatures.hero.Hero
import ru.spbau.mit.roguelike.items.Item
import ru.spbau.mit.roguelike.map.GameMap
import ru.spbau.mit.roguelike.map.Position

/**
 * Hero for testing. Does nothing
 */
internal class TestHero: Hero("test") {
    override fun exchangeItems(items: MutableList<Item>) {}

    override suspend fun askAction(
            position: Position,
            visibleMap: GameMap,
            visibleCreatures: Map<Position, Set<Creature>>
    ): CreatureAction = PassTurn
}