package com.tagteam.asciipathfinder.flow.pathfinder;

import com.tagteam.asciipathfinder.flow.AscIIField;
import com.tagteam.asciipathfinder.flow.AscIITile;
import com.tagteam.asciipathfinder.flow.Position;
import com.tagteam.asciipathfinder.flow.TileType;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class HeatPath extends Pathfinder {

  private final int RUNS = 2;
  private final int RANDOM_START_AMOUNT = 3;
  private final double START_HEAT = 1d;
  private final double HEAT_AMOUNT = 1d;
  private final boolean ONLY_BETTER_HEAT = true;

  private HashMap<Position, Double> heatMap = new HashMap<>();
  private Path currentBest;
  private Path shownPath;

  private int tries;
  private boolean isDone = false;

  public HeatPath(AscIIField field) {
    super(field);
    feelTheHeat();
    randomStart();
  }

  public HeatPath(AscIIField field, Position start, Position target) {
    super(field, start, target);
    feelTheHeat();
    randomStart();
  }

  @Override
  public void proceed() {
    tries++;

    drawPath(currentBest);



    Path currentTry = generateHeatedPath();

    System.out.println("Current try: " + currentTry.getLength());

    if (ONLY_BETTER_HEAT) {
      if (currentTry.getLength() < currentBest.getLength()) {
        heatUpPath(currentTry);
        currentBest = currentTry;
      }
    } else {
      heatUpPath(currentTry);
      if (currentTry.getLength() < currentBest.getLength()) {
        currentBest = currentTry;
      }
    }

    if (tries >= RUNS) {
      isDone = true;
    } else if (tries % 1 == 0) {
      resetPath(shownPath);
      shownPath = currentBest;
      drawPath(shownPath);


    }

  }

  public Path generateHeatedPath() {
    HashSet<Position> walked = new HashSet<>();
    walked.clear();

    Position currentPos = start;
    walked.add(currentPos);

    while (currentPos != target) {
      Position possibleNext = generateNext(currentPos, walked);

      if (possibleNext != null) {
        currentPos = generateNext(currentPos, walked);
        walked.add(currentPos);
      } else {
        currentPos = start;
        walked.clear();
      }

      if(target.getX() == currentPos.getX() && target.getY() == currentPos.getY()){
        break;
      }
    }

    System.out.println("Success!");

    return new Path(walked);
  }

  private boolean test(AscIITile tile) {
    return tile.getType() == TileType.PASSABLE;
  }

  public Position generateNext(Position current, HashSet<Position> offLimit) {
    List<AscIITile> surrounding = ascIIField.getTile(current).getSurroundingTiles(this::test);
    List<AscIITile> toRemove = new ArrayList<>();

    if(surrounding.contains(target)){
      return target;
    }

    for (AscIITile tile : surrounding) {

      if (offLimit.contains(tile.getPosition())) {
        toRemove.add(tile);
      }

      if (tile.getSurroundingTiles().size() <= 1) {
        toRemove.add(tile);
      }

    }

    for (AscIITile tile : toRemove) {
      surrounding.remove(tile);
    }

    double bound = 0;

    for (int i = 0; i < surrounding.size(); i++) {
      bound += heatMap.get(surrounding.get(i).getPosition());
    }

    Random random = new Random();
    double randomValue = 0 + (bound - 0) * random.nextDouble();
    double passed = 0;

    for (int i = 0; i < surrounding.size(); i++) {
      if (heatMap.get(surrounding.get(i).getPosition()) + passed >= randomValue) {
        return surrounding.get(i).getPosition();
      }
      passed += heatMap.get(surrounding.get(i).getPosition());
    }

    return null;
  }

  public void randomStart() {
    Path bestPath = generateHeatedPath();



    System.out.println("First path has length of: " + bestPath.getLength());

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < RANDOM_START_AMOUNT; i++) {
      Path randomPath = generateHeatedPath();
      if (randomPath.getLength() < bestPath.getLength()) {
        bestPath = randomPath;
        System.out.println(bestPath.getLength());
        System.out.println(i + "/" + RANDOM_START_AMOUNT);
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

      }
    }

    heatUpPath(bestPath);
    currentBest = bestPath;
    shownPath = bestPath;

    System.out.println("look MOM, I did it!");

  }


  @Override
  public boolean isDone() {
    return isDone;
  }

  @Override
  public void draw(Graphics graphics) {

  }

  public void drawPath(Path path) {
    for (Position pos : path.getPath()) {
      ascIIField.getTile(pos).setType(TileType.TRAMPLED);
    }
  }

  public void resetPath(Path path) {
    for (Position pos : path.getPath()) {
      ascIIField.getTile(pos).setType(TileType.PASSABLE);
    }
  }

  public void feelTheHeat() {

    for (int x = 0; x < ascIIField.getWidht(); x++) {
      for (int y = 0; y < ascIIField.getHeight(); y++) {
        heatMap.put(new Position(x, y), START_HEAT);
      }
    }

  }

  public void heatUp(Position pos, Double amount) {

    this.heatMap.put(pos, this.heatMap.get(pos) + amount);

  }

  public void heatUpPath(Path path) {

    for (Position pos : path.getPath()) {
      heatUp(pos, HEAT_AMOUNT);
    }

  }


}
