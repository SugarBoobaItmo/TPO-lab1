package com.MomsDeveloper.task3;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class OverallTest {

    @Test
    void testHitchhikerInitialization() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED, Characteristics.COOL), true);

        assertAll("Hitchhiker Initialization",
                () -> assertTrue(ford.isHipel(), "Expected Ford to be Hipel"),
                () -> assertTrue(ford.isFrokt(), "Expected Ford to be Frokt"),
                () -> assertTrue(ford.isHasTowel(), "Expected Ford to have a towel"),
                () -> assertEquals(2, ford.getCharacteristics().size(), "Ford should have exactly 2 characteristics"));
    }

    @Test
    void testHitchhikerWithoutCharacteristics() {
        Hitchhiker sam = new Hitchhiker("Сэм", Set.of(), false);

        assertAll("Hitchhiker Initialization with No Characteristics",
                () -> assertFalse(sam.isHipel(), "Sam should NOT be Hipel"),
                () -> assertFalse(sam.isFrokt(), "Sam should NOT be Frokt"),
                () -> assertFalse(sam.isHasTowel(), "Sam should NOT have a towel"),
                () -> assertTrue(sam.getCharacteristics().isEmpty(), "Sam should have no characteristics"));
    }

    @Test
    void testZuchit() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED), true);
        Human trillian = new Human("Триллиан", Set.of(Characteristics.SEXY));

        assertTrue(ford.zuchit(trillian)); // Триллиан секси — знакомство успешно
        assertTrue(ford.getKnownPeople().contains(trillian));

        Human sam = new Human("Сэм", Set.of(Characteristics.SOLID));
        assertFalse(ford.zuchit(sam)); // Сэм не секси — знакомства нет
        assertFalse(ford.getKnownPeople().contains(sam));

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
        int expectedScore = 3 + 2 + 5 + 2; // 3 (Frokt) + 2 (Hipel) + 5 (Towel) + 2 (Characteristics count)

        assertEquals(expectedScore, ford.hitchhikerScore());
    }

    @Test
    void testMeetFunctionality() {
        Human ford = new Human("Форд", Set.of(Characteristics.COOL));
        Human arthur = new Human("Артур", Set.of(Characteristics.SOLID));

        ford.meet(arthur);
        assertTrue(ford.getKnownPeople().contains(arthur), "Ford should know Arthur after meeting.");
    }

    @Test
    void testCatchPhrase() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED, Characteristics.COOL), true);
        assertEquals("Форд всегда при полотенце. Don't Panic!", ford.catchPhrase());

        Hitchhiker arthur = new Hitchhiker("Артур", Set.of(Characteristics.SOLID), false);
        assertEquals("Артур – реально солидно упакован.", arthur.catchPhrase());
    }

    @Test
    void testToStringOutput() {
        Hitchhiker ford = new Hitchhiker("Форд", Set.of(Characteristics.PACKED, Characteristics.COOL), true);
        assertTrue(ford.toString().contains("Форд"), "ToString should include the name.");
        assertTrue(ford.toString().contains("при полотенце"), "ToString should mention the towel if present.");
    }
}
