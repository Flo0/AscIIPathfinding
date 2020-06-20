package com.tagteam.asciipathfinder.flow.pathfinder;

import com.tagteam.asciipathfinder.flow.APConstants;
import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.Position;
import java.awt.Graphics;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public abstract class Pathfinder {

  public Pathfinder(AscIIField ascIIField) {
    this(ascIIField, new Position(0, 0), new Position(APConstants.FIELD_WIDTH - 1, APConstants.FIELD_HEIGHT - 1));
  }

  public Pathfinder(AscIIField ascIIField, Position start, Position target) {
    this.ascIIField = ascIIField;
    this.start = start;
    this.target = target;
  }

  protected final AscIIField ascIIField;
  protected final Position start;
  protected final Position target;

  public abstract void proceed();

  public abstract boolean isDone();

  public abstract void draw(Graphics graphics);

}
