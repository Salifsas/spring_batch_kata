package fr.audensiel.kata.config;

import fr.audensiel.kata.enums.Orientation;
import fr.audensiel.kata.model.Tondeuse;
import fr.audensiel.kata.model.TondeuseCommande;
import org.springframework.batch.item.ItemProcessor;

public class TondeuseItemProcessor implements ItemProcessor<TondeuseCommande, Tondeuse> {


    @Override
    public Tondeuse process(TondeuseCommande commande) throws Exception {
        String[] parts = commande.getInitialPosition().split(" ");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Orientation orientation = Orientation.valueOf(parts[2]);

        Tondeuse tondeuse = new Tondeuse(x, y, orientation);

        for (char instruction : commande.getInstructions().toCharArray()) {
            switch (instruction) {
                case 'G' ->
                        tondeuse.setPosition(tondeuse.getX(), tondeuse.getY(), tondeuse.getOrientation().turnLeft());
                case 'D' ->
                        tondeuse.setPosition(tondeuse.getX(), tondeuse.getY(), tondeuse.getOrientation().turnRight());
                case 'A' -> {
                    int nextX = tondeuse.getX();
                    int nextY = tondeuse.getY();
                    switch (tondeuse.getOrientation()) {
                        case N:
                            nextY++;
                            break;
                        case E:
                            nextX++;
                            break;
                        case S:
                            nextY--;
                            break;
                        case W:
                            nextX--;
                            break;
                    }
                    if (commande.getPelouse().isInside(nextX, nextY)) {
                        tondeuse.setPosition(nextX, nextY, tondeuse.getOrientation());
                    }
                }
            }
        }

        return tondeuse;
    }
}
