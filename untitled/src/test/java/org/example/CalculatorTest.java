package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    public void addTest(){
        Calculator c = new Calculator();
        int result = c.addTwoNum(5,4);
        assertEquals(9,result);
        assertNotEquals(10, result);
    }

    @Test
    public void addTest2(){
        Calculator c = new Calculator();
        assertEquals(10,c.addTwoNum(5,5));
    }

}
