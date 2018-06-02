package ru.spbau.mit.roguelike.creatures.hero

import ru.spbau.mit.roguelike.items.Equipment
import ru.spbau.mit.roguelike.items.Item

/**
 * Represents an entity which can manage items in inventory
 * as well as equipped items
 */
internal interface InventoryManager: StatManager {
    /**
     * List of unequipped items
     */
    val backpack: List<Item>

    /**
     * Equipped items (no more than one in a slot)
     */
    val equipment: Map<Equipment.Slot, Equipment>

    /**
     * Takes item to inventory
     * @param item to take
     */
    fun takeItem(item: Item)

    /**
     * Drops item from inventory
     * @param backpackIndex of item to drop
     */
    fun dropItem(backpackIndex: Int): Item

    /**
     * Equips item from inventory
     * @param backpackIndex of item to equip
     */
    fun equipItem(backpackIndex: Int)

    /**
     * Unequips item and places it to inventory
     * @param slot to empty
     */
    fun unequipItem(slot: Equipment.Slot)
}