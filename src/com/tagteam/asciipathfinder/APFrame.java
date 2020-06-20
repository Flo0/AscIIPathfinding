package com.tagteam.asciipathfinder;

import javax.swing.JFrame;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of AscIIPathfinding and was created at the 20.06.2020
 *
 * AscIIPathfinding can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class APFrame extends JFrame {

  protected static void run() {
    new APFrame().setVisible(true);
  }

  public APFrame() {
    // JFrame constructor mit Fenstername
    super("AscII Pathfinding");
    // Alles schließen wenn auf X gedrückt
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    APDrawPanel drawPanel = new APDrawPanel();

    this.add(drawPanel);
    this.pack();
    this.setResizable(false);
    // Fenster mittig setzen
    this.setLocationRelativeTo(null);

  }

}
