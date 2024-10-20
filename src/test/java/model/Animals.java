package model;

import java.util.List;

public class Animals {

    private List<Animals.animals> animals;

    public List<Animals.animals> getAnimals() {
        return animals;
    }

    public static class animals {
        private Integer number;
        private String name;
        private Integer age;

        public Integer getNumber() {
            return number;
        }

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }
    }
}