package com.MomsDeveloper.task3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import lombok.Data;

@Data
public class Human {
    protected String name;
    protected Set<Characteristics> characteristics;
    protected Set<Human> knownPeople = new HashSet<>();

    public Human(String name, Set<Characteristics> characteristics){
        this.name = name;
        this.characteristics = characteristics;
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

    public void randomEncounter(Human other) {
        Random random = new Random();
        if (random.nextBoolean()) {
            meet(other);
            other.meet(this);
            System.out.println(this.name + " случайно встретил " + other.name + " и теперь они знакомы.");
        } else {
            System.out.println(this.name + " и " + other.name + " разминулись.");
        }
    }

    @Override
    public String toString() {
        Set<String> characteristicsDesc = new HashSet<>();
        for (Characteristics i : characteristics)
            characteristicsDesc.add(i.getDescription());
        
        return name + " (характеристики: " + characteristicsDesc + ")";

    }
}
