package org.kravemir.svg.labels.rendering;

import org.junit.Test;
import org.kravemir.svg.labels.model.TiledPaper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TilePositionGeneratorTest {

    private TilePositionGenerator generator;

    @Test
    public void test1() {
        generator = new TilePositionGenerator(TiledPaper.builder()
                .setPaperSize(14, 10)
                .setLabelSize(10, 8)
                .setLabelOffset(2, 1)
                .setLabelDelta(0,0)
                .build()
        );

        assertThat(generator.getPaperWidth(), is(14.0));
        assertThat(generator.getPaperHeight(), is(10.0));
        assertThat(collectAllPositions(), is(new double[][] {{2,1}}));
    }

    @Test
    public void test2() {
        generator = new TilePositionGenerator(TiledPaper.builder()
                .setPaperSize(26, 22)
                .setLabelSize(10, 8)
                .setLabelOffset(2, 2)
                .setLabelDelta(2,2)
                .build()
        );

        assertThat(generator.getPaperWidth(), is(26.0));
        assertThat(generator.getPaperHeight(), is(22.0));
        assertThat(collectAllPositions(), is(new double[][] {
                {2,2}, {14,2},
                {2,12}, {14,12},
        }));
    }

    private double[][] collectAllPositions() {
        List<double[]> positions = new ArrayList<>();

        generator.start();
        while (!generator.isFull()) {
            positions.add(new double[] { generator.getX(), generator.getY() });
            generator.nextPosition();
        }

        return positions.toArray(new double[positions.size()][]);
    }
}
