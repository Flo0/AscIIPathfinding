package com.tagteam.asciipathfinder.flow;

import java.util.Objects;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class Position {

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Position(Position other) {
    this.x = other.x;
    this.y = other.y;
  }

  private int x;
  private int y;

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public void add(Position other) {
    this.x += other.x;
    this.y += other.y;
  }

  public int distanceSquared(Position other) {
    int dx = other.x - this.x;
    int dy = other.y - this.y;
    return dx * dx + dy * dy;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Position)) {
      return false;
    }
    Position otherPos = (Position) other;
    return otherPos.x == this.x && otherPos.y == this.y;
  }

}
