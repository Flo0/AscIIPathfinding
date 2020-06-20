package com.tagteam.asciipathfinder;

import com.tagteam.asciipathfinder.flow.APConstants;
import com.tagteam.asciipathfinder.flow.AscIIField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class APDrawPanel extends JPanel {

  public APDrawPanel() {
    this.setPreferredSize(new Dimension(APConstants.getScreenWidth(), APConstants.getScreenHeight()));
    Font font = new Font(APConstants.FONT_TYPE, APConstants.FONT_MUTATION, APConstants.FONT_SIZE);
    this.ascIIField = new AscIIField(APConstants.FIELD_WIDTH, APConstants.FIELD_HEIGHT, font);
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ascIIField);
    final long fpsTimer = (long) (1E6 / APConstants.TARGET_FPS);
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::repaint, 0L, fpsTimer, TimeUnit.MICROSECONDS);
  }

  private final AscIIField ascIIField;

  @Override
  public void paint(Graphics graphics) {
    ascIIField.draw(graphics);
  }

}
