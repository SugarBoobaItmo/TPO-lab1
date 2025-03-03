package com.MomsDeveloper.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ArctgTest {
    // the distribution is determined on the site from [-1; 1] 
    @DisplayName("Test bounds")
    @ParameterizedTest()
    @ValueSource(doubles = {-1, 1})
    void testArctgBounds(double x) {
        assertEquals(Math.atan(x), Arctg.arctg(x, 100), 1e-2);
    }

    @DisplayName("Test out of bounds")
    @ParameterizedTest()
    @ValueSource(doubles = {-1.1, 1.1})
    void testArctgOutOfBounds(double x) {
        assertThrows(IllegalArgumentException.class, () -> Arctg.arctg(x, 100));
    }

    @DisplayName("Test dots")
    @ParameterizedTest()
    @CsvFileSource(resources = "/dots.csv", numLinesToSkip = 1, delimiter = ';')
    void testArctgDots(double x, double y){
        assertEquals(y, Arctg.arctg(x, 100), 1e-2);
    }
}
