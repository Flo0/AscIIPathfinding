package com.tagteam.asciipathfinder.flow.impl;

import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.AscIITile;
import com.tagteam.asciipathfinder.flow.FieldGenerator;
import com.tagteam.asciipathfinder.flow.TileType;
import com.tagteam.asciipathfinder.utils.SimplexNoise;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class SimplexWorldGenerator implements FieldGenerator {

  private final SimplexNoise noiseGen = new SimplexNoise(200);

  @Override
  public AscIITile generateTile(AscIIField field, int x, int y) {
    double height = noiseGen.noise(x / 15D, y / 15D);
    TileType type = TileType.PASSABLE;
    if (height > 0.4) {
      type = TileType.IMPASSABLE;
    }
    return new AscIITile(field, x, y, type);
  }

}
