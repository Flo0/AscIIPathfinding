package com.tagteam.asciipathfinder.playergame.items;

import com.tagteam.asciipathfinder.flow.LOCK;
import com.tagteam.asciipathfinder.playergame.GameObject;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public abstract class Item implements GameObject {

  public Item(ItemType itemType, LOCK lock) {
    this.itemType = itemType;
    this.lock = lock;
  }

  protected final LOCK lock;
  private final ItemType itemType;
  private int durability;

  public void reduceDurability(int amount, LOCK lock) {
    this.durability -= amount;
  }

  public boolean isBroken() {
    return durability <= 0;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public int getDurabilityLeft() {
    return durability;
  }

  public abstract void useOn(ItemTarget itemTarget);

}