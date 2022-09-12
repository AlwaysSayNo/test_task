package org.grynko.nazar.model;

import java.util.ArrayList;

public class Elevator {

    private final int elevatorCapacity;

    private Direction direction;
    private int currentFloorNumber;
    private final ArrayList<Passenger> passengers;
    private int directionalFloor;

    public Elevator(int elevatorCapacity, int startFloor){
        this.elevatorCapacity = elevatorCapacity;

        this.direction = Direction.UP;
        this.currentFloorNumber = startFloor;
        this.passengers = new ArrayList<>();
        this.directionalFloor = startFloor + 1;
    }

    public boolean isEmpty() {
        return passengers.isEmpty();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getCurrentFloorNumber() {
        return currentFloorNumber;
    }

    public void setCurrentFloorNumber(int currentFloorNumber) {
        this.currentFloorNumber = currentFloorNumber;
    }

    public int getDirectionalFloor() {
        return directionalFloor;
    }

    public void setDirectionalFloor(int directionalFloor) {
        this.directionalFloor = directionalFloor;
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    public void addPassenger(Passenger passenger) {
        if(passengers.size() >= elevatorCapacity) throw new IllegalStateException();
        passengers.add(passenger);
    }

    public int getAvailablePlaces() {
        return elevatorCapacity - passengers.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Elevator (direction: ").append(direction).append(", directionalFloor: ")
                .append(directionalFloor).append(", occupiedPlaces: ").append(passengers.size()).append("): ");

        for(Passenger passenger : passengers) {
            sb.append("(id: ").append(passenger.getId())
                    .append(", dirFloor: ").append(passenger.getDirectionalFloor()).append(")");
        }

        return sb.toString();
    }
}
