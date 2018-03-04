package selfDriving;

import util.InputData;
import util.OutputData;
import util.ProblemDataIO;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SelfDrivingRides {

    static class SelfDrivingInputData implements InputData {
        int rows;
        int columns;
        List<Car> cars;
        List<Ride> rides;
        int bonusNumber;
        int steps;


        @Override
        public void setParams(String[] params) {
            rows = Integer.valueOf(params[0]);
            columns = Integer.valueOf(params[1]);
            cars = new ArrayList<>();
            for (int i = Integer.valueOf(params[2]); i > 0; --i) {
                cars.add(new Car());
            }
            rides = new ArrayList<>();
            bonusNumber = Integer.valueOf(params[4]);
            steps = Integer.valueOf(params[5]);
        }

        private int rideId;
        @Override
        public void fillData(String dataLine) {
            String[] params = dataLine.split(" ");
            Ride ride = new Ride(Integer.valueOf(params[0]), Integer.valueOf(params[1]), Integer.valueOf(params[2]),
                    Integer.valueOf(params[3]), Integer.valueOf(params[4]), Integer.valueOf(params[5]), rideId);
            rideId++;
            rides.add(ride);
        }
    }

    static class SelfDrivingOutputData implements OutputData {

        @Override
        public String getOutput(Object data) {
            List<Car> cars = (List<Car>) data;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cars.size(); ++i) {
                builder.append(cars.get(i).toString()).append(System.lineSeparator());
            }
            return builder.toString();
        }
    }

    public static void main(String... args) {
        ProblemDataIO dataIO = new ProblemDataIO(new SelfDrivingInputData(), new SelfDrivingOutputData(),
                "selfDriving", "e_high_bonus.in");
        SelfDrivingInputData inputData = (SelfDrivingInputData) dataIO.readData();
        List<Car> cars = assignCarsToRiders(inputData.cars, inputData.rides, inputData.steps, inputData.bonusNumber);
        dataIO.writeData(cars);
    }

    private static List<Car> assignCarsToRiders(List<Car> freeCars, List<Ride> rides, int maxSteps, int bonus) {
        List<Car> bookedCars = new ArrayList<>();

        for (int step = 0; step < maxSteps; ++step) {
            removeExpiredRide(rides, step);  // delete all rides that expires

            Iterator<Car> carIterator = freeCars.iterator();
            while (carIterator.hasNext()) {     // loop throws all free cars
                Car freeCar = carIterator.next();
                Ride carRide = getRideForCar(freeCar, step, rides, bonus);   // get most appropriate ride for free car
                if (Objects.nonNull(carRide)) {
                    bookCar(freeCar, carIterator, bookedCars);            // set ride for the car
                    removeExpiredRide(rides, carRide);
                    assignCar(freeCar, carRide);
                }
            }
            Iterator<Car> bookCarIterator = bookedCars.iterator();
            while (bookCarIterator.hasNext()) {         // loop throws all booked cars
                Car bookCar = bookCarIterator.next();
                bookCar.bookingTime--;            // decrease bookingTime
                if (isCarFree(bookCar)) {           // check if booking time is 0
                    freeCar(bookCar, freeCars, bookCarIterator);     // put car to free cars list
                }
            }
        }
        bookedCars.addAll(freeCars);
        return bookedCars;
    }

    private static Ride getRideForCar(Car car, int step, List<Ride> rides, int bonus) {
        Ride carRide = null;
        int triger = Integer.MIN_VALUE;
        int rideBookingTime = 0;
        for (Ride ride : rides) {
            int carRichStartRideTime = getDistance(car.position, ride.startPosition);
            int rideTime = getDistance(ride.startPosition, ride.endPosition);
            if (step + carRichStartRideTime + rideTime > ride.endTime) {
                continue;
            }
            int waitTime = ride.startTime - (step + carRichStartRideTime);
            int price = rideTime + (waitTime >= 0 ? bonus : waitTime);
            int bookingTime = carRichStartRideTime + rideTime + (waitTime >= 0 ? waitTime : 0);

            int currTriger = price / bookingTime;

            if (triger < currTriger) {
                triger = currTriger;
                rideBookingTime = bookingTime;
                carRide = ride;
            }
        }
        if (Objects.nonNull(carRide)) {
            carRide.actualRideTime = rideBookingTime;
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

    private static void bookCar(Car freeCar, Iterator<Car> freeCars, List<Car> bookedCars) {
        freeCar(freeCar, bookedCars, freeCars);
    }

    private static void freeCar(Car bookCar, List<Car> freeCars, Iterator<Car> bookedCars) {
        bookedCars.remove();
        freeCars.add(bookCar);
    }

    private static boolean isCarFree(Car bookCar) {
        return bookCar.bookingTime <= 0;
    }

    private static void removeExpiredRide(List<Ride> rides, Ride carRide) {
        rides.remove(carRide);
    }

    private static void removeExpiredRide(List<Ride> rides, int step) {
        rides.removeIf(ride -> ride.endTime <= step);
    }

    private static class Car {
        Point position;
        int bookingTime; // if 0 then car is not booked
        List<Integer> handledRides;

        public Car() {
            position = new Point(0, 0);
            handledRides = new ArrayList<>();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(handledRides.size());
            handledRides.forEach(c -> builder.append(" ").append(c));
            return builder.toString();
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
