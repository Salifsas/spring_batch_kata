package fr.audensiel.kata.enums;

import fr.audensiel.kata.exceptions.OrientationIncalideException;

public enum Orientation {
    N, E, S, W;

    public Orientation turnLeft() throws OrientationIncalideException {
        return switch (this) {
            case N -> W;
            case W -> S;
            case S -> E;
            case E -> N;
            default -> throw new OrientationIncalideException("Orientation incorrect");
        };
    }

    public Orientation turnRight() throws OrientationIncalideException {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
            default -> throw new OrientationIncalideException("Orientation incorrect");
        };
    }
}
