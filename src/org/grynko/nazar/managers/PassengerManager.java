package org.grynko.nazar.managers;

import org.grynko.nazar.model.Building;
import org.grynko.nazar.model.Direction;
import org.grynko.nazar.model.Floor;
import org.grynko.nazar.model.Passenger;

import java.util.Random;

public class PassengerManager {

    /**
     * Changes the desired floor for the passenger depending on which floor he/she is on.
     */
    public static Passenger assignNewFloor(Passenger passenger, Building building, Floor currentFloor) {
        Random random = new Random();

        Direction direction;
        int currentFloorNumber = currentFloor.getFloorNumber();

        if(currentFloorNumber == building.getMaxFloorNumber()) {
            direction = Direction.DOWN;
        }
        else if(currentFloorNumber == building.getMinFloorNumber()) {
            direction = Direction.UP;
        }
        else {
            direction = random.nextInt() % 2 == 0 ? Direction.UP : Direction.DOWN;
        }

        int newFloorNumber;
        if(direction.equals(Direction.UP)) {
            int floorDifference = building.getMaxFloorNumber() - currentFloorNumber;
            int shift = random.nextInt(floorDifference) + 1;

            newFloorNumber = currentFloorNumber + shift;
        }
        else {
            int floorDifference = currentFloorNumber - building.getMinFloorNumber();
            int shift = random.nextInt(floorDifference) + 1;

            newFloorNumber = currentFloorNumber - shift;
        }

        passenger.setDirection(direction);
        passenger.setDirectionalFloor(newFloorNumber);

        return passenger;
    }

}
