package com.tagteam.asciipathfinder.flow.pathfinder;

import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.AscIITile;
import com.tagteam.asciipathfinder.flow.TileType;
import java.awt.Graphics;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 21.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class GreedyFirst extends Pathfinder {

  public GreedyFirst(AscIIField ascIIField) {
    super(ascIIField);
    this.currentTile = ascIIField.getTile(this.start);
  }

  private boolean done = false;
  private AscIITile currentTile;

  @Override
  public void proceed() {
    int shortest = start.distanceSquared(target);
    AscIITile next = null;
    for (AscIITile tile : this.currentTile.getSurroundingTiles(el -> el.getType() == TileType.PASSABLE)) {
      int dist = tile.getPosition().distanceSquared(this.target);
      if (dist < shortest) {
        shortest = dist;
        next = tile;
      }
    }
    if (next == null) {
      return;
    } else if (next.getPosition().equals(this.target)) {
      done = true;
    }
    currentTile = next;
    currentTile.setType(TileType.TRAMPLED);
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public void draw(Graphics graphics) {

  }

}