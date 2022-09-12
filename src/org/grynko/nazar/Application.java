package org.grynko.nazar;

import org.grynko.nazar.configuration.ElevatorConfigRandom;
import org.grynko.nazar.managers.ElevatorManager;
import org.grynko.nazar.managers.PassengerManager;
import org.grynko.nazar.model.Building;
import org.grynko.nazar.model.Direction;
import org.grynko.nazar.model.Elevator;

public class Application {

    /**
     * Initial parameters for the building.
     */
    private static final int MIN_FLOOR_INCLUDED = 5;
    private static final int MAX_FLOOR_INCLUDED = 10;
    private static final int MIN_PASSENGERS_ON_FLOOR_INCLUDED = 0;
    private static final int MAX_PASSENGERS_ON_FLOOR_INCLUDED = 10;

    /**
     * Number of elevator direction changes.
     */
    private static final int LIFT_TRIPS_NUMBER = 10;

    public static void main(String[] args) {
        Elevator elevator = ElevatorConfigRandom.getElevator();
        Building building = ElevatorConfigRandom.getBuilding(MIN_FLOOR_INCLUDED, MAX_FLOOR_INCLUDED,
                MIN_PASSENGERS_ON_FLOOR_INCLUDED, MAX_PASSENGERS_ON_FLOOR_INCLUDED);
        PassengerManager passengerManager = ElevatorConfigRandom.getPassengerManager();

        ElevatorManager elevatorManager = new ElevatorManager(elevator, building, passengerManager);

        run(elevator, building, elevatorManager);

    }

    private static void run(Elevator elevator, Building building, ElevatorManager elevatorManager) {

        System.out.println("== Application start ==");
        System.out.println(building);
        System.out.println("\n== == == == == == == ==\n");

        for (int i = 1; i <= LIFT_TRIPS_NUMBER; ++i) {
            Direction oldDirection = elevator.getDirection();

            while (oldDirection.equals(elevator.getDirection())) {
                int currentFloorNumber = elevator.getCurrentFloorNumber();

                // Strings for output
                String elevatorBefore = elevator.toString();
                String floorBefore = building.getFloor(currentFloorNumber).toString();


                // Call function
                elevatorManager.callElevator();

                // Strings for output
                String elevatorAfter = elevator.toString();
                String floorAfter = building.getFloor(currentFloorNumber).toString();


                System.out.println("=> Elevator before <=");
                System.out.println(elevatorBefore);

                System.out.println("=> Floor before <=");
                System.out.println(floorBefore);

                System.out.println("\n");

                System.out.println("=> Elevator after <=");
                System.out.println(elevatorAfter);

                System.out.println("=> Floor after <=");
                System.out.println(floorAfter);

                System.out.println("\n");
                System.out.println("== == == == == == == ==");
                System.out.println("\n");
            }

            System.out.println("<==> <==> <==> <==> <==> <==> <==> <==>\n");
            System.out.println(i + ". Switch direction: " + oldDirection + " => " + elevator.getDirection() + "\n");
            System.out.println("<==> <==> <==> <==> <==> <==> <==> <==>\n");
        }
    }

}
