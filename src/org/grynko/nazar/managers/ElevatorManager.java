package org.grynko.nazar.managers;

import org.grynko.nazar.model.*;

import java.util.*;

/**
 * The main class for working with the program. It has only one public method - void callElevator().
 */
public class ElevatorManager {

    private final Elevator elevator;
    private final Building building;
    private final PassengerManager passengerManager;

    /**
     * Elevator map for passengers.
     * Shows on which floor each passenger who is currently in the elevator wants to get off.
     */
    private final Map<Integer, List<Passenger>> passengersPerFloor;

    public ElevatorManager(Elevator elevator, Building building, PassengerManager passengerManager) {
        this.elevator = elevator;
        this.building = building;
        this.passengerManager = passengerManager;

        this.passengersPerFloor = new HashMap<>();
        for(int i = 1; i <= building.getMaxFloorNumber(); ++i)
            passengersPerFloor.put(i, new ArrayList<>());
    }

    /**
     * Evict passengers for that floor.
     * @param floorNumber - The floor on which elevator passengers can potentially get off.
     * @return list of  passengers who went out.
     */
    private List<Passenger> evictPassengersForFloor(int floorNumber) {
        List<Passenger> outPassengers = passengersPerFloor.get(floorNumber);
        passengersPerFloor.put(floorNumber, new ArrayList<>());

        Floor floor = building.getFloor(floorNumber);
        for(Passenger passenger : outPassengers) {
            elevator.removePassenger(passenger);
            passenger = passengerManager.assignNewFloor(passenger, building, floor);

            floor.addPassenger(passenger);
        }

        return outPassengers;
    }


    /**
     * Lets passengers into the elevator for that direction, if there are empty seats in the elevator.
     * @param floorNumber - The floor on which elevator passengers can potentially enter.
     * @param oldPassengers - A list of passengers who got off on this floor and got back in queue.
     */
    private void receivePassengersForFloor(int floorNumber, List<Passenger> oldPassengers) {
        Floor floor = building.getFloor(floorNumber);
        Direction direction = elevator.getDirection();
        int availablePlaces = elevator.getAvailablePlaces();

        for(int i = 0; i < availablePlaces; ++i) {
            Passenger passenger = floor.peekPassenger(direction);
            if(passenger == null || oldPassengers.contains(passenger)) break;

            passenger = floor.removePassenger(direction);

            // add in elevator
            elevator.addPassenger(passenger);

            passengersPerFloor.get(passenger.getDirectionalFloor()).add(passenger);

            if(checkDirectionalFloor(passenger))
                elevator.setDirectionalFloor(passenger.getDirectionalFloor());
        }

    }

    /**
     * Elevator method of operation.
     *
     * 1. The elevator lets the passengers out.
     * 2. The elevator checks to see if it needs to change direction.
     * 3. Elevator starts the passengers.
     * 4. The elevator moves to the next floor.
     */
    public void callElevator() {
        int currentFloorNumber = elevator.getCurrentFloorNumber();
        List<Passenger> oldPassengers = evictPassengersForFloor(currentFloorNumber);

        if(checkDirectionForElevator()) setNewDirectionForElevator();

        receivePassengersForFloor(currentFloorNumber, oldPassengers);
        elevator.setCurrentFloorNumber(getNextFloor());
    }

    private Direction getDirectionByDefault() {
        int currentFloorNumber = elevator.getCurrentFloorNumber();
        Floor floor = building.getFloor(currentFloorNumber);

        int upSize = floor.getQueueUp().size();
        int downSize = floor.getQueueDown().size();

        if(upSize == 0 && downSize == 0) return elevator.getDirection();
        else if(upSize > downSize) return Direction.UP;
        else return Direction.DOWN;
    }

    private Direction getDirectionByExtremumFloor() {
        int currentFloorNumber = elevator.getCurrentFloorNumber();

        if(currentFloorNumber == building.getMaxFloorNumber()) return Direction.DOWN;
        else return Direction.UP;
    }

    private boolean checkDirectionalFloor(Passenger passenger) {
        return passenger.getDirection().equals(Direction.UP) ?
                passenger.getDirectionalFloor() > elevator.getDirectionalFloor() :
                passenger.getDirectionalFloor() < elevator.getDirectionalFloor();
    }

    private int getNextFloor() {
        int nextFloor = elevator.getCurrentFloorNumber();

        if(elevator.getDirection().equals(Direction.UP)) nextFloor++;
        else nextFloor--;

        return nextFloor;
    }

    private boolean checkDirectionForElevator() {
        int currentFloorNumber = elevator.getCurrentFloorNumber();
        return elevator.isEmpty() || elevator.getDirectionalFloor() == currentFloorNumber;
    }

    private void setNewDirectionForElevator() {
        int currentFloorNumber = elevator.getCurrentFloorNumber();

        Direction newDirection;

        if(currentFloorNumber == building.getMaxFloorNumber() || currentFloorNumber == building.getMinFloorNumber()) {
            newDirection = getDirectionByExtremumFloor();
        }
        else {
            newDirection = getDirectionByDefault();
        }

        elevator.setDirection(newDirection);
        int nextDirectionalFloor = newDirection == Direction.UP ? currentFloorNumber + 1 : currentFloorNumber - 1;
        elevator.setDirectionalFloor(nextDirectionalFloor);
    }

}