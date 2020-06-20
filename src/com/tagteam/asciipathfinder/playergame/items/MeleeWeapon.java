package com.tagteam.asciipathfinder.playergame.items;

import com.tagteam.asciipathfinder.flow.LOCK;
import com.tagteam.asciipathfinder.playergame.MessageHandler;
import com.tagteam.asciipathfinder.playergame.Player;
import com.tagteam.asciipathfinder.playergame.living.LivingEntity;
import java.util.concurrent.ThreadLocalRandom;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class MeleeWeapon extends Item {

  public MeleeWeapon(LOCK lock, String name, int dmg, double crit, double block) {
    super(ItemType.MELEE_WEAPON, lock);
    this.name = name;
    this.attackDamage = dmg;
    this.critChance = crit;
    this.blockChance = block;
  }

  private final String name;
  private final int attackDamage;
  private final double blockChance;
  private final double critChance;

  @Override
  public void useOn(ItemTarget itemTarget) {
    if (itemTarget instanceof Player) {
      MessageHandler.send("Du... schlitzt dich mit deiner Waffe und verlierst " + attackDamage + " Leben.");
      ((Player) itemTarget).damage(attackDamage, lock);
      return;
    }

    if (itemTarget instanceof LivingEntity) {
      LivingEntity entity = (LivingEntity) itemTarget;
      MessageHandler.send("Die Nahkampfwaffe " + name + " wurde gegen " + itemTarget.getName() + " eingesetzt.");
      Armor armor = entity.getArmor();

      MeleeWeapon defWeapon = entity.getMeleeWeapon();
      if (defWeapon != null && defWeapon.blockChance > 0) {
        if (ThreadLocalRandom.current().nextDouble() <= blockChance) {
          MessageHandler.send("Der Angriff wurde abgeblockt.");
          return;
        }
      }

      double dmg = attackDamage;
      boolean crit = ThreadLocalRandom.current().nextDouble() <= critChance;
      dmg *= crit ? 2D : 1D;
      if (armor != null && !armor.isBroken()) {
        MessageHandler.send(itemTarget.getName() + " hat eine Rüstung mit " + (armor.getArmorRating() * 100) + "% Verteidigung.");
        dmg *= (1D - armor.getArmorRating());
        armor.reduceDurability(1, lock);
      }

      entity.damage((int) dmg, lock);
      MessageHandler.send(entity.getName() + " erleidet " + (int) dmg + " Schaden. " + (crit ? "[ KRITISCH ] " : " ") + (entity.isDead()
          ? "Und ist somit Tot..." : ""));

    } else {
      MessageHandler.send("Die Waffe " + name + " wird auf " + itemTarget.getName() + " gehauen...");
      MessageHandler.send("Nichts passiert, außer dass die Waffe etwas Haltbarkeit verliert.");
    }

    this.reduceDurability(1, lock);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getSymbol() {
    return "⚔";
  }

}
