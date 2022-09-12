package org.grynko.nazar.configuration;

import org.grynko.nazar.managers.PassengerManager;
import org.grynko.nazar.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Configuration class. Represents methods for getting the main modules of the program.
 * */
public class ElevatorConfigRandom {

    /**
     * @return an elevator object with a capacity of 5 and a starting floor of 1.
     */
    public static Elevator getElevator() {
        return new Elevator(5, 1);
    }

    /**
     * @param minFloorIncluded - Minimum possible floor
     * @param maxFloorIncluded - Maximum possible floor
     * @param minPassengerIncluded - Minimum possible number of potential passengers on the floor
     * @param maxPassengerIncluded - Maximum possible number of potential passengers on the floor
     * @return filled with floors and passengers of the building
     */
    public static Building getBuilding(int minFloorIncluded, int maxFloorIncluded,
                                       int minPassengerIncluded, int maxPassengerIncluded) {
        if(minFloorIncluded > maxFloorIncluded || minPassengerIncluded > maxPassengerIncluded)
            throw new IllegalArgumentException();


        int floorsNumberRand = getRandomNumber(minFloorIncluded, maxFloorIncluded);
        Building tmpBuilding = new Building(1, floorsNumberRand);

        List<Floor> floorList = new ArrayList<>();
        for(int i = 0; i < floorsNumberRand; ++i) {
            Floor floor = new Floor(i + 1);

            int passengersNumberRand = getRandomNumber(minPassengerIncluded, maxPassengerIncluded);
            for(int j = 0; j < passengersNumberRand - minPassengerIncluded; ++j) {
                Passenger passenger = new Passenger(0, Direction.UP);
                PassengerManager.assignNewFloor(passenger, tmpBuilding, floor);

                floor.addPassenger(passenger);
            }

            floorList.add(floor);
        }

        return new Building(1, floorsNumberRand, floorList);
    }

    /**
     * @return the passenger manager
     */
    public static PassengerManager getPassengerManager() {
        return new PassengerManager();
    }

    private static int getRandomNumber(int minIncluded, int maxIncluded){
        Random random = new Random();
        int randomResult = minIncluded;

        if(minIncluded != maxIncluded)
            randomResult += random.nextInt(maxIncluded - minIncluded);

        return randomResult;
    }

}
