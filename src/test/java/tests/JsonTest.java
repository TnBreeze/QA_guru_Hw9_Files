package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Animals;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonTest {
    private final ClassLoader cl = JsonTest.class.getClassLoader();


    @Test
    void jsonFileParsingTest() throws Exception {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(cl.getResourceAsStream("info.json"))
        )) {
            ObjectMapper mapper = new ObjectMapper();
            Animals animals = mapper.readValue(reader, Animals.class);

            assertThat(animals.getAnimals().get(0).getNumber()).isEqualTo(1);
            assertThat(animals.getAnimals().get(0).getName()).isEqualTo("Mars");
            assertThat(animals.getAnimals().get(0).getAge()).isEqualTo(5);

            assertThat(animals.getAnimals().get(1).getNumber()).isEqualTo(2);
            assertThat(animals.getAnimals().get(1).getName()).isEqualTo("Kassandra");
            assertThat(animals.getAnimals().get(1).getAge()).isEqualTo(13);

            assertThat(animals.getAnimals().get(2).getNumber()).isEqualTo(3);
            assertThat(animals.getAnimals().get(2).getName()).isEqualTo("Biskvit");
            assertThat(animals.getAnimals().get(2).getAge()).isEqualTo(4);
        }
    }
}