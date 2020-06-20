package com.tagteam.asciipathfinder.flow;

import java.awt.Color;

public enum TileType {

  IMPASSABLE(new Color(80, 80, 80)),
  PASSABLE( new Color(34,139,34)),
  TRAMPLED(Color.RED);


  private Color color;

  public Color getColor() {
    return color;
  }

  private TileType(Color color) {
    this.color = color;
  }

}
