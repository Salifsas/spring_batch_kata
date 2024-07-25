package fr.audensiel.kata.config;

import fr.audensiel.kata.enums.Orientation;
import fr.audensiel.kata.model.Pelouse;
import fr.audensiel.kata.model.Tondeuse;
import fr.audensiel.kata.model.TondeuseCommande;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TondeuseItemProcessorTest {

    private Pelouse pelouse;
    private TondeuseItemProcessor itemProcessor;

    @BeforeEach
    void setUp() {

        itemProcessor = new TondeuseItemProcessor();
    }

    @Test
    void testProcessWithBasicInstructions() throws Exception {
        TondeuseCommande commande = new TondeuseCommande(1, 2, 'N', "GAGAGAGAA", new Pelouse(5, 5));
        Tondeuse tondeuse = itemProcessor.process(commande);
        assertNotNull(tondeuse);
        assertEquals(1, tondeuse.getX());
        assertEquals(3, tondeuse.getY());
        assertEquals(Orientation.N, tondeuse.getOrientation());
    }

    @Test
    void testProcessWithTurnInstructions() throws Exception {
        TondeuseCommande commande = new TondeuseCommande(1, 2, 'N', "G", new Pelouse(5, 5));
        Tondeuse tondeuse = itemProcessor.process(commande);

        assertNotNull(tondeuse);
        assertEquals(1, tondeuse.getX());
        assertEquals(2, tondeuse.getY());
        assertEquals(Orientation.W, tondeuse.getOrientation()); // Should end facing North
    }

    @Test
    void testProcessWithMoveInstructions() throws Exception {
        TondeuseCommande commande = new TondeuseCommande(1, 2, 'N', "A", new Pelouse(5, 5));
        Tondeuse tondeuse = itemProcessor.process(commande);

        assertNotNull(tondeuse);
        assertEquals(1, tondeuse.getX());
        assertEquals(3, tondeuse.getY());
        assertEquals(Orientation.N, tondeuse.getOrientation());
    }

    @Test
    void testProcessWithBoundaries() throws Exception {
        TondeuseCommande commande = new TondeuseCommande(5, 5, 'N', "A", new Pelouse(5, 5));
        Tondeuse tondeuse = itemProcessor.process(commande);
        assertNotNull(tondeuse);
        assertEquals(5, tondeuse.getX()); // Should not move outside boundaries
        assertEquals(5, tondeuse.getY());
        assertEquals(Orientation.N, tondeuse.getOrientation());
    }
}
