package fr.audensiel.kata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TondeuseCommande {
    private int x;
    private int y;
    private char orientation;
    private String instructions;
    private Pelouse pelouse;
    public String getInitialPosition() {
        return x + " " + y + " " + orientation;
    }

}
