package com.tagteam.asciipathfinder.playergame.living;

import com.tagteam.asciipathfinder.flow.LOCK;
import com.tagteam.asciipathfinder.playergame.items.Armor;
import com.tagteam.asciipathfinder.playergame.items.Item;
import com.tagteam.asciipathfinder.playergame.items.ItemTarget;
import com.tagteam.asciipathfinder.playergame.items.ItemType;
import com.tagteam.asciipathfinder.playergame.items.MeleeWeapon;
import com.tagteam.asciipathfinder.playergame.items.RangedWeapon;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface LivingEntity extends ItemTarget {

  int getHealth();

  int getMaxHealth();

  void damage(int amount, LOCK lock);

  Item getItemInSlot(ItemType type);

  default boolean isDead() {
    return getHealth() <= 0;
  }

  default void takeWeapon(LivingEntity taker) {
    if (!isDead()) {

    }
  }

  default MeleeWeapon getMeleeWeapon() {
    Item item = getItemInSlot(ItemType.MELEE_WEAPON);
    MeleeWeapon weapon = null;
    if (item != null) {
      weapon = (MeleeWeapon) item;
    }
    return weapon;
  }

  default RangedWeapon getRangedWeapon() {
    Item item = getItemInSlot(ItemType.RANGED_WEAPON);
    RangedWeapon weapon = null;
    if (item != null) {
      weapon = (RangedWeapon) item;
    }
    return weapon;
  }

  default Armor getArmor() {
    Item item = getItemInSlot(ItemType.ARMOR);
    Armor armor = null;
    if (item != null) {
      armor = (Armor) item;
    }
    return armor;
  }

}
