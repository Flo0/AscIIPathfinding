package com.tagteam.asciipathfinder.flow;

import com.tagteam.asciipathfinder.flow.impl.SimplexWorldGenerator;
import com.tagteam.asciipathfinder.flow.pathfinder.AStarPathfinder;
import com.tagteam.asciipathfinder.flow.pathfinder.GreedyFirst;
import com.tagteam.asciipathfinder.flow.pathfinder.Pathfinder;
import com.tagteam.asciipathfinder.flow.pathfinder.SprayAndPray;
import com.tagteam.asciipathfinder.playergame.Player;
import com.tagteam.asciipathfinder.playergame.TestPlayer;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class AscIIField implements KeyEventDispatcher {

  public AscIIField(int width, int height, Font font) {
    this.matrix = new AscIITile[width][height];
    this.width = width;
    this.height = height;
    this.font = font;
    this.generator = new SimplexWorldGenerator();
    fillMatrix();
    //this.pathfinder = new AStarPathfinder(this);
    this.pathfinder = new GreedyFirst(this);
    this.player = new TestPlayer(this);
    this.lock = new LOCK();
    player.setPosition(new Position(0, 0), lock);
    matrix[0][0].setGameObject(player);
  }

  private final Player player;
  private final FieldGenerator generator;
  protected final Font font;
  private final int width;
  private final int height;
  private final AscIITile[][] matrix;
  private final LOCK lock;
  private final Pathfinder pathfinder;

  public int getWidht() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  private void fillMatrix() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {

        this.matrix[x][y] = generator.generateTile(this, x, y);

      }
    }
  }

  public AscIITile getTile(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("Position must not be null.");
    }
    return getTile(position.getX(), position.getY());
  }

  public boolean isValidTile(int x, int y) {
    return !(x < 0 || x >= width || y < 0 || y >= height);
  }

  public AscIITile getTile(int x, int y) {
    // Check ob x oder y außerhalb der matrix ist
    // Wenn ja return null. (Verhindert OutOfBoundException)
    if (!isValidTile(x, y)) {
      return null;
    }

    return this.matrix[x][y];
  }

  public void draw(Graphics graphics) {
    if (!pathfinder.isDone()) {
      pathfinder.proceed();
    }
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {

        AscIITile tile = matrix[x][y];
        tile.draw(graphics, this.font);

      }
    }

  }


  @Override
  public boolean dispatchKeyEvent(KeyEvent e) {

    if (e.getID() != KeyEvent.KEY_PRESSED) {
      return false;
    }

    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        player.move(0, -1, lock);
        break;
      case KeyEvent.VK_A:
        player.move(-1, 0, lock);
        break;
      case KeyEvent.VK_S:
        player.move(0, 1, lock);
        break;
      case KeyEvent.VK_D:
        player.move(1, 0, lock);
        break;
    }
    return true;
  }

}
