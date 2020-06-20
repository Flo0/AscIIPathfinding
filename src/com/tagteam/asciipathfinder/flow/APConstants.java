package com.tagteam.asciipathfinder.flow;

import java.awt.Font;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class APConstants {

  public static final int FONT_SIZE = 18;
  public static final int TILE_SIZE = 24;
  public static final int FIELD_WIDTH = 40;
  public static final int FIELD_HEIGHT = 40;
  public static final double TARGET_FPS = 30;
  public static final String FONT_TYPE = "Noto";
  public static final int FONT_MUTATION = Font.PLAIN;

  public static int getScreenWidth() {
    return TILE_SIZE * FIELD_WIDTH;
  }

  public static int getScreenHeight() {
    return TILE_SIZE * FIELD_HEIGHT;
  }

}
