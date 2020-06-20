package com.tagteam.asciipathfinder.flow;

import com.tagteam.asciipathfinder.playergame.GameObject;
import com.tagteam.asciipathfinder.utils.GraphicUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class AscIITile {

  public AscIITile(AscIIField ascIIField, int xPos, int yPos, TileType type) {
    this.ascIIField = ascIIField;
    this.position = new Position(xPos, yPos);
    this.type = type;

    int dxy = APConstants.TILE_SIZE;
    this.tileSquare = new Rectangle(xPos * dxy + 1, yPos * dxy + 1, dxy - 1, dxy - 1);
  }

  private final AscIIField ascIIField;
  private final Position position;
  private final Rectangle tileSquare;
  private TileType type;
  private String displayString = "";
  private GameObject gameObject;

  public void setGameObject(GameObject object) {
    this.gameObject = object;
  }

  public void setDisplayString(String value) {
    this.displayString = value;
  }

  public void setType(TileType type) {
    this.type = type;
  }

  public Position getPosition() {
    return position;
  }

  public TileType getType() {
    return type;
  }

  public List<AscIITile> getSurroundingTiles(Predicate<AscIITile> filter) {
    List<AscIITile> filterList = new ArrayList<>();
    for (AscIITile tile : getSurroundingTiles()) {
      if (filter.test(tile)) {
        filterList.add(tile);
      }
    }
    return filterList;
  }

  public List<AscIITile> getSurroundingTiles() {
    List<AscIITile> surrList = new ArrayList<>();

    AscIITile left = ascIIField.getTile(position.getX() - 1, position.getY());
    AscIITile right = ascIIField.getTile(position.getX() + 1, position.getY());
    AscIITile top = ascIIField.getTile(position.getX(), position.getY() + 1);
    AscIITile bot = ascIIField.getTile(position.getX(), position.getY() - 1);

    if (left != null) {
      surrList.add(left);
    }
    if (right != null) {
      surrList.add(right);
    }
    if (top != null) {
      surrList.add(top);
    }
    if (bot != null) {
      surrList.add(bot);
    }

    return surrList;
  }

  public void draw(Graphics graphics, Font font) {
    graphics.setColor(type.getColor());
    //graphics.drawRect(tileSquare.x, tileSquare.y, tileSquare.width, tileSquare.height);
    graphics.fillRect(tileSquare.x, tileSquare.y, tileSquare.width, tileSquare.height);
    graphics.setColor(Color.WHITE);
    //GraphicUtils.drawCenteredString(graphics, displayString, tileSquare, font);
    String drawString = gameObject == null ? displayString : gameObject.getSymbol();
    GraphicUtils.drawCenteredString(graphics, drawString, tileSquare, font);
  }

  @Override
  public int hashCode() {
    return this.position.hashCode();
  }

}
