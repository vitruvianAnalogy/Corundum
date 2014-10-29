package Corundum.world;

import Corundum.exceptions.CorundumException;

public class Zone {
    /** <b><i>DEV NOTES:</b></i><br>
     * This location is given the lowest x, lowest y, and lowest z coordinates from each of the two locations given to the constructor. */
    private Location low;
    /** <b><i>DEV NOTES:</b></i><br>
     * This location is given the highest x, highest y, and highest z coordinates from each of the two locations given to the constructor. */
    private Location high;

    public Zone(Location location1, Location location2) {
        // if the two corners of the zone are in different worlds, throw an exception
        if (!location1.getWorld().equals(location2.getWorld()))
            throw new ZoneCornersInDifferentWorldsException(location1, location2);

        // put all the lowest-valued coordinates into the "low" location
        low =
                new Location(location1.getX() <= location2.getX() ? location1.getX() : location2.getX(), location1.getY() <= location2.getY() ? location1.getY() : location2
                        .getY(), location1.getZ() <= location2.getZ() ? location1.getZ() : location2.getZ(), location1.getWorld());
        // put all the highest-valued coordinates into the "high" location
        high =
                new Location(location1.getX() >= location2.getX() ? location1.getX() : location2.getX(), location1.getY() >= location2.getY() ? location1.getY() : location2
                        .getY(), location1.getZ() >= location2.getZ() ? location1.getZ() : location2.getZ(), location1.getWorld());
    }

    public class ZoneCornersInDifferentWorldsException extends CorundumException {
        private static final long serialVersionUID = -1202714861919120683L;

        public ZoneCornersInDifferentWorldsException(Location location1, Location location2) {
            super("The corners of a new zone were given in two different worlds!", "zone in two worlds", "Location #1: " + location1.toString(), "Location #2: "
                    + location2.toString());
        }

    }

    public float getVolume() {
        float xLength = (float) (this.high.getX() - this.low.getX());
        float yLength = (float) (this.high.getY() - this.low.getY());
        float zLength = (float) (this.high.getZ() - this.low.getZ());

        return xLength * yLength * zLength;
    }

    // TODO: utilities
}
