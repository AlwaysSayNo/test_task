package org.grynko.nazar.model;

import java.util.LinkedList;
import java.util.Queue;

public class Floor {

    private final Queue<Passenger> queueUp;
    private final Queue<Passenger> queueDown;
    private final int floorNumber;

    public Floor(int floorNumber) {
        this.queueUp = new LinkedList<>();
        this.queueDown = new LinkedList<>();
        this.floorNumber = floorNumber;
    }

    public Queue<Passenger> getQueueUp() {
        return queueUp;
    }

    public Queue<Passenger> getQueueDown() {
        return queueDown;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void addPassenger(Passenger passenger) {
        Queue<Passenger> queue = getQueueForDirection(passenger.getDirection());
        queue.add(passenger);
    }

    public Passenger peekPassenger(Direction direction) {
        Queue<Passenger> queue = getQueueForDirection(direction);
        return queue.peek();
    }

    public Passenger removePassenger(Direction direction) {
        Queue<Passenger> queue = getQueueForDirection(direction);
        return queue.remove();
    }

    private Queue<Passenger> getQueueForDirection(Direction direction) {
        if(direction.equals(Direction.UP)) return queueUp;
        else return queueDown;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Floor (").append(floorNumber).append("): ");

        sb.append("[UP (size: ").append(queueUp.size()).append(")");
        if(!queueUp.isEmpty()) {
            sb.append(": ");
            for (Passenger passenger : queueUp) {
                sb.append("(id: ").append(passenger.getId())
                        .append(", dirFloor: ").append(passenger.getDirectionalFloor()).append(")");
            }
        }
        sb.append("]");

        sb.append(" [DOWN (size: ").append(queueDown.size()).append(")");
        if(!queueDown.isEmpty()) {
            sb.append(": ");
            for (Passenger passenger : queueDown) {
                sb.append("(id: ").append(passenger.getId())
                        .append(", dirFloor: ").append(passenger.getDirectionalFloor()).append(")");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}

