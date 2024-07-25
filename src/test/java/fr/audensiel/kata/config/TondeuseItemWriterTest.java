package fr.audensiel.kata.config;

import fr.audensiel.kata.enums.Orientation;
import fr.audensiel.kata.model.Tondeuse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.Chunk;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

class TondeuseItemWriterTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private TondeuseItemWriter itemWriter;

    @BeforeEach
     void setUp() {
        System.setOut(new PrintStream(outputStream));
        itemWriter = new TondeuseItemWriter();
    }

    @AfterEach
     void tearDown() {
        System.setOut(originalOut);
    }
    @Test
    void testWrite() throws Exception {
        Tondeuse tondeuse1 = new Tondeuse(1, 2, Orientation.N);
        Tondeuse tondeuse2 = new Tondeuse(3, 4, Orientation.E);
        Chunk<Tondeuse> chunk = new Chunk<>(Arrays.asList(tondeuse1, tondeuse2));
        itemWriter.write(chunk);
        String output = outputStream.toString().trim();
        String expectedOutput = "1 2 N\n3 4 E";
        assertEquals(expectedOutput, output);
    }
}
