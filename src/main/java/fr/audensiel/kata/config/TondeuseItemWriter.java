package fr.audensiel.kata.config;

import fr.audensiel.kata.model.Tondeuse;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
public class TondeuseItemWriter implements ItemWriter<Tondeuse> {
    @Override
    public void write(Chunk<? extends Tondeuse> items) throws Exception {
        for (Tondeuse tondeuse : items) {
            System.out.println(tondeuse.getPositionAsString());
        }
    }
}
