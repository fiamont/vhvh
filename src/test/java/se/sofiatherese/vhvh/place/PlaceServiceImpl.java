package se.sofiatherese.vhvh.place;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PlaceServiceImpl {

    @Test
    public void testAdd() {
        Assertions.assertEquals(42, Integer.sum(19, 23));
    }
}
