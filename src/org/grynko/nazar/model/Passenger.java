package org.grynko.nazar.model;

public class Passenger {

    private static int ID_GENERATOR = 0;

    private final int id;
    private int directionFloor;
    private Direction direction;

    public Passenger(int directionalFloor, Direction direction) {
        this.id = ID_GENERATOR++;
        this.directionFloor = directionalFloor;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public int getDirectionalFloor() {
        return directionFloor;
    }

    public void setDirectionalFloor(int directionFloor) {
        this.directionFloor = directionFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
