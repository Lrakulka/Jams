package selfDriving;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelfDrivingRides {

    public static void main(String... args) {


    }

    private static void assignCarsToRiders(List<Car> freeCars, List<Ride> rides, int maxSteps) {
        List<Car> bookedCars = new ArrayList<>();

        for (int step = 0; step < maxSteps; ++step) {
            removeExpiredRide(rides, step);  // delete all rides that expires

            for (Car freeCar : freeCars) {     // loop throws all free cars
                Ride carRide = getRideForCar(freeCar, step, rides);   // get most appropriate ride for free car
                if (Objects.nonNull(carRide)) {
                    bookCar(freeCar, freeCars, bookedCars);            // set ride for the car
                    removeExpiredRide(rides, carRide);
                    assignCar(freeCar, carRide);
                }
            }
            for (Car bookCar : bookedCars) {         // loop throws all booked cars
                bookCar.bookingTime--;            // decrease bookingTime
                if (isCarFree(bookCar)) {           // check if booking time is 0
                    freeCar(bookCar, freeCars, bookedCars);     // put car to free cars list
                }
            }
        }

    }

    private static Ride getRideForCar(Car car, int step, List<Ride> rides) {
        Ride carRide = null;
        int bookingTime = Integer.MAX_VALUE;
        for (Ride ride : rides) {
            int carRichStartRideTime = getDistance(car.position, ride.startPosition);
            int rideTime = getDistance(ride.startPosition, ride.endPosition);
            if (step + carRichStartRideTime + rideTime > ride.endTime) {
                continue;
            }
            int waitTime = ride.startTime - (step + carRichStartRideTime);

            int currBookingTime = step + carRichStartRideTime + ((waitTime > 0) ? waitTime : 0) + rideTime;

            if (bookingTime > currBookingTime) {
                bookingTime = currBookingTime;
                carRide = ride;
            }
        }
        if (Objects.nonNull(carRide)) {
            carRide.actualRideTime = bookingTime;
        }
        return carRide;
    }

    private static int getDistance(Point position1, Point position2) {
        return Math.abs(position1.x - position2.x) + Math.abs(position1.y - position2.y);
    }

    private static void assignCar(Car freeCar, Ride ride) {
        freeCar.handledRides.add(ride.id);
        freeCar.bookingTime = ride.actualRideTime;
    }

    private static void bookCar(Car freeCar, List<Car> freeCars, List<Car> bookedCars) {
        freeCar(freeCar, bookedCars, freeCars);
    }

    private static void freeCar(Car bookCar, List<Car> freeCars, List<Car> bookedCars) {
        bookedCars.remove(bookCar);
        freeCars.add(bookCar);
    }

    private static boolean isCarFree(Car bookCar) {
        return bookCar.bookingTime == 0;
    }

    private static void removeExpiredRide(List<Ride> rides, Ride carRide) {
        rides.remove(carRide);
    }

    private static void removeExpiredRide(List<Ride> rides, int step) {
        rides.removeIf(ride -> ride.endTime >= step);
    }

    private static class Car {
        Point position;
        int bookingTime; // if 0 then car is not booked
        List<Integer> handledRides;

        public Car() {
            position = new Point(0, 0);
            handledRides = new ArrayList<>();
        }
    }

    private static class Ride {
        Point startPosition;
        Point endPosition;
        int startTime;
        int endTime;
        int id;
        int actualRideTime;

        public Ride(int startX, int startY, int endX, int endY, int startTime, int endTime, int id) {
            this.startPosition = new Point(startX, startY);
            this.endPosition = new Point(endX, endY);
            this.startTime = startTime;
            this.endTime = endTime;
            this.id = id;
        }
    }
}
