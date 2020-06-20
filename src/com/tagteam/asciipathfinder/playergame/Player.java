package com.tagteam.asciipathfinder.playergame;

import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.AscIITile;
import com.tagteam.asciipathfinder.flow.LOCK;
import com.tagteam.asciipathfinder.flow.Position;
import com.tagteam.asciipathfinder.flow.TileType;
import com.tagteam.asciipathfinder.playergame.items.Item;
import com.tagteam.asciipathfinder.playergame.items.ItemType;
import com.tagteam.asciipathfinder.playergame.living.LivingEntity;
import java.util.EnumMap;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class Player implements LivingEntity {

  public Player(String name, String symbol, AscIIField ascIIField) {
    this.name = name;
    this.symbol = symbol;
    this.maxHp = 200;
    this.hp = maxHp;
    this.items = new EnumMap<>(ItemType.class);
    this.ascIIField = ascIIField;
  }

  private final AscIIField ascIIField;
  private final String symbol;
  private final String name;
  private final EnumMap<ItemType, Item> items;
  private int hp;
  private final int maxHp;
  private Position position;

  public void setPosition(Position position, LOCK lock) {
    this.position = position;
  }

  public void move(int dx, int dy, LOCK lock) {
    AscIITile targetTile = ascIIField.getTile(position.getX() + dx, position.getY() + dy);
    if (targetTile != null && targetTile.getType() == TileType.PASSABLE) {
      AscIITile currentTile = ascIIField.getTile(position);
      currentTile.setGameObject(null);
      targetTile.setGameObject(this);
      this.position = new Position(targetTile.getPosition());
    }
  }

  public Position getPosition() {
    return new Position(this.position);
  }

  @Override
  public int getHealth() {
    return hp;
  }

  @Override
  public int getMaxHealth() {
    return maxHp;
  }

  @Override
  public void damage(int amount, LOCK lock) {
    this.hp -= amount;
  }

  @Override
  public Item getItemInSlot(ItemType type) {
    return items.get(type);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }

}
