package com.MomsDeveloper.task3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OverallTest {

    @Test
    void testHitchhikerInitialization() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED, Characteristics.COOL), true);
        assertTrue(ford.isHipel());
        assertTrue(ford.isFrokt());
        assertTrue(ford.isHasTowel());
        assertEquals(2, ford.getCharacteristics().size());
    }

    @Test
    void testZuchit() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED), true);
        Human trillian = new Human("Триллиан", Set.of(Characteristics.SEXY));

        assertTrue(ford.zuchit(trillian)); // Триллиан секси — знакомство успешно
        assertTrue(ford.getKnownPeople().contains(trillian));

        Human arthur = new Human("Артур", Set.of(Characteristics.SOLID));
        assertFalse(ford.zuchit(arthur)); // Артур не секси — знакомства нет

    }

    @Test
    void testRandomEncounter() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED), true);
        Hitchhiker arthur = new Hitchhiker("Артур", Set.of(Characteristics.SOLID), false);

        ford.randomEncounter(arthur);
        assertTrue(ford.getKnownPeople().contains(arthur) == arthur.getKnownPeople().contains(ford));
    }

    @Test
    void testHitchhikerScore() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED, Characteristics.COOL), true);
        assertEquals(3 + 2 + 5 + 2, ford.hitchhikerScore()); // 3 (Frokt) + 2 (Hipel) + 5 (Towel) + 2 (характеристики)
    }

    @Test
    void testCatchPhrase() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED, Characteristics.COOL), true);
        assertEquals("Форд всегда при полотенце. Don't Panic!", ford.catchPhrase());

        Hitchhiker arthur = new Hitchhiker("Артур", Set.of(Characteristics.SOLID), false);
        assertEquals("Артур – реально солидно упакован.", arthur.catchPhrase());
    }
}
