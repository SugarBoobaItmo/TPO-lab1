package com.MomsDeveloper.task3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import lombok.Data;

@Data
public class Human {
    protected String name;
    protected Set<Characteristics> characteristics;
    protected Set<Human> knownPeople;

    public Human(String name, Set<Characteristics> characteristics) {
        this.name = name;
        this.characteristics = characteristics;
        this.knownPeople = new HashSet<>();
    }

    public void meet(Human person) {
        knownPeople.add(person);
    }

    public boolean sexualInterception(Human person) {
        if (person.getCharacteristics().contains(Characteristics.SEXY)) {
            knownPeople.add(person);
            return true;
        }
        return false;
    }

    public boolean randomEncounter(Human other) {
        Random random = new Random();
        if (random.nextBoolean()) {
            meet(other);
            other.meet(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        Set<String> characteristicsDesc = new HashSet<>();
        for (Characteristics i : characteristics)
            characteristicsDesc.add(i.getDescription());

        return name + " (характеристики: " + characteristicsDesc + ")";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Human human = (Human) o;
        return Objects.equals(name, human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
