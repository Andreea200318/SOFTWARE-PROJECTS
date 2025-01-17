package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.polinom.Polinom;
import org.example.polinom.Operatii;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    public void testTransformStringToPolinomHashMap() {
        String input = "2x^3 - 3x^2 + 5x - 7";
        HashMap<Integer, Double> asteptat = new HashMap<>();
        asteptat.put(3, 2.0);
        asteptat.put(2, -3.0);
        asteptat.put(1, 5.0);
        asteptat.put(0, -7.0);

        HashMap<Integer, Double> result = Polinom.transformStringToPolinomHashMap(input);

        assertEquals(asteptat, result);
    }

    public void testSuma() {
        HashMap<Integer, Double> polinom1 = new HashMap<>();
        polinom1.put(3, 2.0);
        polinom1.put(1, 5.0);

        HashMap<Integer, Double> polinom2 = new HashMap<>();
        polinom2.put(2, -3.0);
        polinom2.put(1, 3.0);
        polinom2.put(0, -7.0);

        Map<Integer, Double> asteptat = new HashMap<>();
        asteptat.put(3, 2.0);
        asteptat.put(2, -3.0);
        asteptat.put(1, 8.0);
        asteptat.put(0, -7.0);


        Map<Integer, Double> result = new Operatii().suma(polinom1, polinom2);

        assertEquals(asteptat, result);
    }
    public void testDiferenta() {
        HashMap<Integer, Double> polinom1 = new HashMap<>();
        polinom1.put(3, 2.0);
        polinom1.put(1, 5.0);

        HashMap<Integer, Double> polinom2 = new HashMap<>();
        polinom2.put(2, -3.0);
        polinom2.put(1, 3.0);
        polinom2.put(0, -7.0);

        Map<Integer, Double> asteptat = new HashMap<>();
        asteptat.put(3, 2.0);
        asteptat.put(2, 3.0);
        asteptat.put(1, 2.0);
        asteptat.put(0, 7.0);

        Map<Integer, Double> result = new Operatii().diferenta(polinom1, polinom2);

        assertEquals(asteptat, result);
    }
    public void testInmultire() {
        HashMap<Integer, Double> polinom1 = new HashMap<>();
        polinom1.put(1, 5.0);

        HashMap<Integer, Double> polinom2 = new HashMap<>();
        polinom2.put(1, 3.0);
        polinom2.put(0, -7.0);

        Map<Integer, Double> asteptat = new HashMap<>();
        asteptat.put(2, 15.0);
        asteptat.put(1, -35.0);


        Map<Integer, Double> result = new Operatii().inmultire(polinom1, polinom2);

        assertEquals(asteptat, result);
    }
    public void testDerivata() {
        HashMap<Integer, Double> polinom1 = new HashMap<>();
        polinom1.put(3, 2.0);
        polinom1.put(1, 5.0);


        Map<Integer, Double> asteptat = new HashMap<>();
        asteptat.put(2, 6.0);
        asteptat.put(0, 5.0);


        Map<Integer, Double> result = new Operatii().derivata(polinom1);

        assertEquals(asteptat, result);
    }

    public void testInt() {
        HashMap<Integer, Double> polinom1 = new HashMap<>();
        polinom1.put(3, 2.0); // 2x^3
        polinom1.put(2, -3.0); // -3x^2
        polinom1.put(1, 5.0);  // 5x
        polinom1.put(0, -7.0); // -7

        HashMap<Integer, Double> asteptat= new HashMap<>();
        //  2x^3 e (2/4)x^4 = (1/2)x^4
        asteptat.put(4, 0.5);
        //  -3x^2 e (-3/3)x^3 = -x^3
        asteptat.put(3, -1.0);
        //  5x e (5/2)x^2 = 2.5x^2
        asteptat.put(2, 2.5);
        //  -7 e (-7x)
        asteptat.put(1, -7.0);


        Map<Integer, Double> result = new Operatii().integrala(polinom1);

        assertEquals(asteptat, result);
    }
}