package com.joshua.craftsman;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <classify href="http://d.android.com/tools/testing">Testing documentation</classify>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public  void generifyCode(){
        Random random=new Random();
        StringBuilder sRand=new StringBuilder();
        for (int i=0;i<6;i++)
        {
            int itmp = random.nextInt(9);
            char ctmp = (char)itmp;
            sRand.append(String.valueOf(ctmp));
        }
        System.out.println(sRand);
    }
}