package ru.spbau.mit.roguelike.creatures

import org.junit.Before
import org.junit.Test
import ru.spbau.mit.roguelike.creatures.hero.BasicStats
import ru.spbau.mit.roguelike.creatures.hero.Hero
import ru.spbau.mit.roguelike.creatures.hero.HeroStats
import ru.spbau.mit.roguelike.items.Equipment

class HeroTest {
    private lateinit var hero: Hero

    private val sword = Equipment(
            "Excalibur",
            "Holy sword rumored to be belonged to King Arthur",
            Equipment.Slot.SWORD,
            BasicStats(
                    mapOf(
                            BasicStats.Type.AGILITY to 9999,
                            BasicStats.Type.STRENGTH to 9999,
                            BasicStats.Type.DAMAGE to 9999
                    )
            )
    )

    @Before
    fun initHero() {
        hero = TestHero()
    }

    @Test
    fun testInventory() {
        assert(hero.backpack.isEmpty()) { "hero inventory should be initially empty" }
        assert(hero.equipment.isEmpty()) { "hero equipment should be initially empty" }

        hero.takeItem(sword)

        assert(hero.backpack.contains(sword)) { "hero inventory should contain new item" }

        val initialStats = hero.totalStats.copy()

        hero.equipItem(0)

        assert(hero.backpack.isEmpty()) { "equipped item should disappear from backpack" }
        assert(hero.equipment[sword.slot] == sword) {
            "hero equipment should contain equipped item"
        }

        val updatedStats = hero.totalStats.copy()

        for ((statType, value) in sword.stats) {
            assert(initialStats[statType] + value == updatedStats[statType]) {
                "hero stats must be updated on equipped item"
            }
        }

        hero.unequipItem(sword.slot)

        assert(hero.backpack.contains(sword)) {
            "hero inventory should contain unequipped item"
        }

        assert(hero.equipment[sword.slot] == null) {
            "hero equipment should not contain unequipped item"
        }

        val finalStats = hero.totalStats.copy()

        for ((statType, _) in sword.stats) {
            assert(initialStats[statType] == finalStats[statType]) {
                "hero stats must be updated on unequipped item"
            }
        }
    }

    @Test
    fun testHeroExperience() {
        assert(hero.stats.level == 1) {
            "initial hero level should be 1"
        }

        assert(!hero.receiveExperience(HeroStats.NEXT_LEVEL_EXPERIENCE_BOUND / 2)) {
            "hero should have not received a level"
        }
        assert(hero.receiveExperience(HeroStats.NEXT_LEVEL_EXPERIENCE_BOUND)) {
            "hero should have received a level"
        }

        assert(hero.stats.unspentStatPoints == HeroStats.STAT_POINTS_PER_LEVEL) {
            "hero should have received some stat points"
        }

        val upgradedStat = BasicStats.Type.DAMAGE
        val initialStats = hero.totalStats.copy()

        hero.spendStatPoint(upgradedStat)

        val updatedStats = hero.totalStats.copy()

        assert(initialStats[upgradedStat] + 1 == updatedStats[upgradedStat]) {
            "stat point spending should result in stat upgrade"
        }
    }
}