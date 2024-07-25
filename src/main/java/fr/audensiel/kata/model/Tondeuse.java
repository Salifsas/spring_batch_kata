package fr.audensiel.kata.model;

import fr.audensiel.kata.enums.Orientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tondeuse {
    private int x;
    private int y;
    private Orientation orientation;

    public void setPosition(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public String getPositionAsString() {
        return x + " " + y + " " + orientation;
    }
}
