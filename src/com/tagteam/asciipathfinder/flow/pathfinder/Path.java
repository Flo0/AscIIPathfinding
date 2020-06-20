package com.tagteam.asciipathfinder.flow.pathfinder;

import com.tagteam.asciipathfinder.flow.Position;
import java.util.ArrayList;
import java.util.HashSet;

public class Path {

    private final HashSet<Position> path;

    public Path(HashSet<Position> path){
        this.path = path;
    }

    public HashSet<Position> getPath() {
        return path;
    }

    public int getLength(){
        return path.size();
    }
}
