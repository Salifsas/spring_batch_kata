package fr.audensiel.kata.config;
import fr.audensiel.kata.exceptions.PairLineException;
import fr.audensiel.kata.model.Pelouse;
import fr.audensiel.kata.model.TondeuseCommande;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TondeuseItemReader implements ItemReader<TondeuseCommande> {
    private BufferedReader reader;
    private boolean isMaxCoordinatesRead = false;
    private Pelouse pelouse;
    public TondeuseItemReader(Resource resource) throws IOException {
        Assert.notNull(resource, "Resource must not be null");
        this.reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
    }
    @Override
    public TondeuseCommande read() throws Exception {
        if (!isMaxCoordinatesRead) {
            String maxCoordsLine = reader.readLine();
            if (maxCoordsLine == null) {
                return null;
            }
            String[] maxCoords = maxCoordsLine.split(" ");
            if (maxCoords.length != 2) {
                throw new IllegalArgumentException("Invalid max coordinates line: " + maxCoordsLine);
            }
            String[] dimensions = maxCoordsLine.split(" ");
            int maxX = Integer.parseInt(dimensions[0]);
            int maxY = Integer.parseInt(dimensions[1]);
            pelouse = new Pelouse(maxX, maxY);
            isMaxCoordinatesRead = true;
        }

        String line1 = reader.readLine();
        if (line1 == null) {
            return null;
        }
        String line2 = reader.readLine();
        if (line2 == null) {
            throw new PairLineException("Paire de ligne non trouver pour : " + line1);
        }
        String[] positionTokens = line1.split(" ");
        if (positionTokens.length != 3) {
            throw new PairLineException("Paire de ligne non trouver pour : " + line1);
        }
        int x = Integer.parseInt(positionTokens[0]);
        int y = Integer.parseInt(positionTokens[1]);
        char orientation = positionTokens[2].charAt(0);
        return new TondeuseCommande(x, y, orientation, line2,pelouse);
    }
}
