package org.grynko.nazar.model;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private final List<Floor> floors;
    private final int minFloorNumber;
    private final int maxFloorNumber;

    public Building(int minFloorNumber, int maxFloorNumber) {
        this.minFloorNumber = minFloorNumber;
        this.maxFloorNumber = maxFloorNumber;
        this.floors = new ArrayList<>();

        for(int i = minFloorNumber; i <= maxFloorNumber; ++i) {
            floors.add(new Floor(i));
        }
    }

    public Building(int minFloorNumber, int maxFloorNumber, List<Floor> floors) {
        this.minFloorNumber = minFloorNumber;
        this.maxFloorNumber = maxFloorNumber;
        this.floors = floors;
    }

    public Floor getFloor(int floorNumber) {
        return floors.get(floorNumber - 1);
    }

    public int getMaxFloorNumber() {
        return maxFloorNumber;
    }

    public int getMinFloorNumber() {
        return minFloorNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Building (min: " + minFloorNumber + ", max: " + maxFloorNumber +")\n");
        sb.append("\tfloors").append(floors.size()).append(":\n");

        for(int i = 0; i < floors.size(); ++i) {
            sb.append("\t\t").append(floors.get(i));
            if(i != floors.size() - 1) sb.append("\n");
        }

        return sb.toString();
    }
}
