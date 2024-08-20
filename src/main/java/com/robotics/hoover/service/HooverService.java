package com.robotics.hoover.service;

import com.robotics.hoover.model.Coordinate;
import com.robotics.hoover.model.HooverRequest;
import com.robotics.hoover.model.HooverResponse;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
@Service
public class HooverService {
    public HooverResponse clean(HooverRequest hooverRequest){
        int[] requestedCoordinates = hooverRequest.getCoords();
        int[] roomSize = hooverRequest.getRoomSize();
        int[][] patches = hooverRequest.getPatches();
        String instructions = hooverRequest.getInstructions();
        //null check
        if(instructions==null || roomSize==null || patches==null || requestedCoordinates==null){
            throw new NullPointerException("Input params null");
        }
        if(roomSize[0]<=0 ){
            throw new IllegalArgumentException("Room size should be more than 0");
        }
        Coordinate currentCoordinate = new Coordinate(requestedCoordinates[0], requestedCoordinates[1]);

        Set<Coordinate> traceSet = new HashSet<>();
        String[] instructionsList = instructions.split("");
        for (String s : instructionsList) {
            currentCoordinate = switch (s) {
                case "N" -> new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() + 1);
                case "S" -> new Coordinate(currentCoordinate.getX(), currentCoordinate.getY() - 1);
                case "E" -> new Coordinate(currentCoordinate.getX() + 1, currentCoordinate.getY());
                case "W" -> new Coordinate(currentCoordinate.getX() - 1, currentCoordinate.getY());
                default -> currentCoordinate;
            };
            //hitting the wall
            if (currentCoordinate.getX() >= roomSize[0]) {
                throw new IllegalArgumentException("robot has hit the right wall");
            } else if (currentCoordinate.getY() >= roomSize[1]) {
                throw new IllegalArgumentException("robot has hit the top wall");
            } else if (currentCoordinate.getX() < 0) {
                throw new IllegalArgumentException("robot has hit the left wall");
            } else if (currentCoordinate.getY() < 0) {
                throw new IllegalArgumentException("robot has hit the bottom wall");
            }
            traceSet.add(currentCoordinate);
        }
        int count = 0;
        for (int[] patch:patches) {
            if (traceSet.contains(new Coordinate(patch[0], patch[1]))) {
                count++;
            }
        }
        return new HooverResponse(currentCoordinate,count);
    }
}
