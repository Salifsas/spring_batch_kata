package fr.audensiel.kata.config;

import fr.audensiel.kata.exceptions.PairLineException;
import fr.audensiel.kata.model.TondeuseCommande;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

 class TondeuseItemReaderTest {
    private TondeuseItemReader itemReader;

    @BeforeEach
    public void setUp() throws Exception {
        String testInput = "5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA";
        Resource resource = mock(Resource.class);
        Mockito.when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(testInput.getBytes()));

        itemReader = new TondeuseItemReader(resource);
    }

    @Test
     void testReadSuccess() throws Exception {
        TondeuseCommande first = itemReader.read();
        assertNotNull(first);
        assertEquals(1, first.getX());
        assertEquals(2, first.getY());
        assertEquals('N', first.getOrientation());
        assertEquals("GAGAGAGAA", first.getInstructions());

        TondeuseCommande second = itemReader.read();
        assertNotNull(second);
        assertEquals(3, second.getX());
        assertEquals(3, second.getY());
        assertEquals('E', second.getOrientation());
        assertEquals("AADAADADDA", second.getInstructions());

        // La prochaine lecture devrait retourner null
        assertNull(itemReader.read());
    }

    @Test
     void testReadEmptyFile() throws Exception {
        String testInput = "";
        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(testInput.getBytes()));

        itemReader = new TondeuseItemReader(resource);
        assertNull(itemReader.read());
    }

    @Test
     void testInvalidMaxCoordinates() throws Exception {
        String testInput = "5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA";
        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(testInput.getBytes()));

        itemReader = new TondeuseItemReader(resource);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> itemReader.read());
        assertTrue(exception.getMessage().contains("Invalid max coordinates line"));
    }

    @Test
     void testInvalidTondeuseFormat() throws Exception {
        String testInput = "5 5\n1 2\nGAGAGAGAA\n3 3 E\nAADAADADDA";
        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(testInput.getBytes()));
        itemReader = new TondeuseItemReader(resource);
        Exception exception = assertThrows(PairLineException.class, () -> itemReader.read());
        assertTrue(exception.getMessage().contains("Paire de ligne non trouver"));
    }

    @Test
     void testIncompleteLinePair() throws Exception {
        String testInput = "5 5\n1 2 N\nGAGAGAGAA";
        Resource resource = mock(Resource.class);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(testInput.getBytes()));
        itemReader = new TondeuseItemReader(resource);
        TondeuseCommande first = itemReader.read();
        assertNotNull(first);
    }
}
