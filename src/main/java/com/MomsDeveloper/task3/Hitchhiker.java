package com.MomsDeveloper.task3;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Hitchhiker extends Human {
    private boolean isHipel = false;
    private boolean isFrokt = false;
    private boolean hasTowel = false;

    public Hitchhiker(String name, Set<Characteristics> characteristics, boolean hasTowel) {
        super(name, characteristics);
        this.hasTowel = hasTowel;

        if (characteristics.contains(Characteristics.PACKED) || characteristics.contains(Characteristics.SOLID)) {
            this.isFrokt = true;
            if (characteristics.contains(Characteristics.COOL)) {
                this.isHipel = true;
            }
        }
    }

    public boolean zuchit(Human person) {
        if (!this.getKnownPeople().contains(person)) {
            return sexualInterception(person);
        } else
            return true;
    }

    public int hitchhikerScore() {
        int score = 0;
        if (isFrokt)
            score += 3;
        if (isHipel)
            score += 2;
        if (hasTowel)
            score += 5;
        score += characteristics.size();
        return score;
    }

    public String catchPhrase() {
        if (hasTowel)
            return name + " всегда при полотенце. Don't Panic!";
        if (isHipel)
            return name + " – это тот еще хипель!";
        if (isFrokt)
            return name + " – реально солидно упакован.";
        return name + " пока что просто странник.";
    }

    @Override
    public String toString() {
        return super.toString() + (hasTowel ? ", всегда при полотенце" : "");
    }

}
