package com.tagteam.asciipathfinder.playergame.items;

import com.tagteam.asciipathfinder.flow.LOCK;
import com.tagteam.asciipathfinder.playergame.MessageHandler;
import com.tagteam.asciipathfinder.playergame.Player;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class Armor extends Item {

  public Armor(LOCK lock, String name, double armor) {
    super(ItemType.ARMOR, lock);
  }

  private double armorRating;
  private String name;

  public double getArmorRating() {
    return armorRating;
  }

  @Override
  public void useOn(ItemTarget itemTarget) {
    if (itemTarget instanceof Player) {
      if (((Player) itemTarget).getItemInSlot(ItemType.ARMOR) == this) {
        MessageHandler.send("Du streichelst deine eigene Rüstung... nice.");
      }
    } else {
      MessageHandler.send("Du wirfst dich mit deiner Rüstung volle Karacho auf " + itemTarget.getName() + "...");
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getSymbol() {
    return "⛊";
  }

}
