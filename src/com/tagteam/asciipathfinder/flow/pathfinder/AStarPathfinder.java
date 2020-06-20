package com.tagteam.asciipathfinder.flow.pathfinder;

import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.AscIITile;
import com.tagteam.asciipathfinder.flow.Position;
import com.tagteam.asciipathfinder.flow.TileType;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class AStarPathfinder extends Pathfinder {

  public AStarPathfinder(AscIIField ascIIField) {
    this(ascIIField, new Position(0, 0), new Position(ascIIField.getWidht() - 1, ascIIField.getHeight() - 1));
  }

  public AStarPathfinder(AscIIField ascIIField, Position start, Position target) {
    super(ascIIField, start, target);
    this.lineQueue = new PriorityQueue<>();
    AscIITile first = ascIIField.getTile(start);
    PathLine firstLine = new PathLine(first, this.target, 0);
    this.lineQueue.add(firstLine);
    this.lineHeads = new HashMap<>();
    lineHeads.put(first, firstLine);
  }

  private boolean done = false;
  private final PriorityQueue<PathLine> lineQueue;
  private final Map<AscIITile, PathLine> lineHeads;
  private int count = 0;

  @Override
  public void proceed() {
    count++;
    PathLine line = lineQueue.poll();
    if (line == null) {
      done = true;
      return;
    }

    if (line.isReached()) {
      System.out.println("Took: " + count + " iterations.");
      done = true;
      return;
    }

    for (AscIITile neighbour : line.currentTile.getSurroundingTiles(tile -> tile.getType() == TileType.PASSABLE)) {
      PathLine extended = line;
      if (lineHeads.containsKey(neighbour)) {
        PathLine pathLine = lineHeads.get(neighbour);
        if (pathLine.length < extended.length) {
          extended = pathLine;
        }
      }
      PathLine nextHead = new PathLine(neighbour, this.target, extended.length + 1);
      lineHeads.put(neighbour, nextHead);
      neighbour.setType(TileType.TRAMPLED);
      lineQueue.add(nextHead);
    }

  }

  @Override
  public boolean isDone() {
    return done;
  }

  @Override
  public void draw(Graphics graphics) {

  }

  private static class PathLine implements Comparable<PathLine> {

    private PathLine(AscIITile currentTile, Position target, int length) {
      this.currentTile = currentTile;
      this.length = length;
      this.target = target;
    }

    private final Position target;
    private final int length;
    private final AscIITile currentTile;

    private boolean isReached() {
      Position current = currentTile.getPosition();
      return current.getX() == target.getX() && current.getY() == target.getY();
    }

    @Override
    public int compareTo(PathLine other) {
      return this.currentTile.getPosition().distanceSquared(target) - other.currentTile.getPosition().distanceSquared(target);
    }

  }

}
