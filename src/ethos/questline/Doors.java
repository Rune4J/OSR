package ethos.questline;

import java.util.HashMap;
import java.util.Map;

public class Doors {
    // Map to store door states (open or closed) based on door ID
    private Map<Integer, Boolean> doorStates;

    public Doors() {
        doorStates = new HashMap<>();
        initializeDoors();
    }

    // Method to initialize doors with their default states
    private void initializeDoors() {
        // Existing door initializations
        doorStates.put(123, false); // Example door ID, starts closed
        doorStates.put(456, false); // Another door ID, starts closed
        
        // Add door 11470 with initial state closed
        doorStates.put(11470, false);
    }

    // Method to open a door by ID
    public void openDoor(int doorId) {
        if (doorStates.containsKey(doorId) && !doorStates.get(doorId)) {
            // Logic to handle the visual change or game state update for opening the door
            System.out.println("Opening door with ID: " + doorId);
            doorStates.put(doorId, true); // Set door state to open
        } else {
            System.out.println("Door " + doorId + " is already open or does not exist.");
        }
    }

    // Method to close a door by ID
    public void closeDoor(int doorId) {
        if (doorStates.containsKey(doorId) && doorStates.get(doorId)) {
            // Logic to handle the visual change or game state update for closing the door
            System.out.println("Closing door with ID: " + doorId);
            doorStates.put(doorId, false); // Set door state to closed
        } else {
            System.out.println("Door " + doorId + " is already closed or does not exist.");
        }
    }

    // Method to check the state of a door (open or closed)
    public boolean isDoorOpen(int doorId) {
        return doorStates.getOrDefault(doorId, false);
    }

    // Method to handle door interaction based on ID and action
    public void handleDoorInteraction(int doorId, boolean open) {
        if (open) {
            openDoor(doorId);
        } else {
            closeDoor(doorId);
        }
    }

    // Method to add a new door
    public void addDoor(int doorId, boolean initialState) {
        if (!doorStates.containsKey(doorId)) {
            doorStates.put(doorId, initialState);
            System.out.println("Door with ID " + doorId + " added with initial state: " + (initialState ? "open" : "closed"));
        } else {
            System.out.println("Door with ID " + doorId + " already exists.");
        }
    }

    // Method to remove a door
    public void removeDoor(int doorId) {
        if (doorStates.containsKey(doorId)) {
            doorStates.remove(doorId);
            System.out.println("Door with ID " + doorId + " removed.");
        } else {
            System.out.println("Door with ID " + doorId + " does not exist.");
        }
    }

    // Additional method to automatically open door 11470 during initialization
    public void openDefaultDoors() {
        openDoor(11470); // Opens door 11470 by default
    }
}