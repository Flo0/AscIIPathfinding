package com.tagteam.asciipathfinder.flow.pathfinder;

import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.AscIITile;
import com.tagteam.asciipathfinder.flow.TileType;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class SprayAndPray extends Pathfinder {

  public SprayAndPray(AscIIField ascIIField) {
    super(ascIIField);
    this.random = ThreadLocalRandom.current();
    this.heatMatrix = new double[ascIIField.getWidht()][ascIIField.getHeight()];
  }

  private final double[][] heatMatrix;
  private final ThreadLocalRandom random;
  private SprayPath currentPath = null;
  private boolean done = false;

  private SprayPath generatePath() {
    SprayPath path = new SprayPath(this.ascIIField.getTile(start));
    while (!path.destinationReached()) {
      path.next();
    }
    return path;
  }

  @Override
  public void proceed() {
    if (currentPath != null) {
      currentPath.clearTiles();
    }
    currentPath = generatePath();
    if (currentPath.lastTile.getPosition().equals(target)) {
      done = true;
    }
  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public void draw(Graphics graphics) {

  }

  private class SprayPath {

    private SprayPath(AscIITile startTile) {
      this.tiles = new HashSet<>();
      this.tiles.add(startTile);
      this.lastTile = startTile;
    }

    private boolean reached = false;
    private AscIITile lastTile;
    private final Set<AscIITile> tiles;

    private boolean destinationReached() {
      return reached;
    }

    public double getHeat() {
      int maxDist = start.distanceSquared(target);
      int dist = lastTile.getPosition().distanceSquared(target);
      return (double) dist / maxDist;
    }

    private void next() {
      List<AscIITile> surr = lastTile.getSurroundingTiles(el -> el.getType() == TileType.PASSABLE);
      if (surr.size() == 0) {
        reached = true;
        return;
      }

      AscIITile nextTile = surr.get(random.nextInt(0, surr.size()));
      double currHeat = heatMatrix[nextTile.getPosition().getX()][nextTile.getPosition().getY()];

      for (AscIITile tile : surr) {
        double ht = heatMatrix[tile.getPosition().getX()][tile.getPosition().getY()];
        if (ht < currHeat) {
          nextTile = tile;
        }
      }

      this.lastTile = nextTile;
      this.tiles.add(nextTile);
      nextTile.setType(TileType.TRAMPLED);
      if (nextTile.getPosition().equals(target)) {
        reached = true;
      }
    }

    private void clearTiles() {
      double heat = getHeat();
      for (AscIITile tile : tiles) {
        int x = tile.getPosition().getX();
        int y = tile.getPosition().getY();
        heatMatrix[x][y] += heat;
        tile.setType(TileType.PASSABLE);
        tile.setDisplayString("" + (int) heatMatrix[x][y]);
      }
    }

  }

}