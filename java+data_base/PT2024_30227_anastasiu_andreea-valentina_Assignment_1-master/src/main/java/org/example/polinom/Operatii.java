package org.example.polinom;

import java.util.HashMap;

public class Operatii {
    public static HashMap<Integer,Double>  suma(HashMap<Integer, Double> polinom1, HashMap<Integer, Double> polinom2) {
        HashMap<Integer, Double> rezultat = new HashMap<>();

        for (int exponent : polinom1.keySet()) {
            double coeficient1 = polinom1.get(exponent);
            double coeficient2 = polinom2.containsKey(exponent) ? polinom2.get(exponent) : 0.0;
            rezultat.put(exponent, coeficient1 + coeficient2);
        }

        for (int exponent : polinom2.keySet()) {
            if (!rezultat.containsKey(exponent)) {
                rezultat.put(exponent, polinom2.get(exponent));
            }
        }

        return rezultat;
    }
    public static HashMap<Integer,Double> diferenta(HashMap<Integer, Double> polinom1, HashMap<Integer, Double> polinom2)
    {
        HashMap<Integer,Double> rezultat=new HashMap<>();
        for(int exponent:polinom1.keySet()){
            if(polinom2.containsKey(exponent))
            {
                rezultat.put(exponent,polinom1.get(exponent)-polinom2.get(exponent));

            }
            else
                rezultat.put(exponent,polinom1.get(exponent));
        }
        for(int exponent:polinom2.keySet())
        {
            if(!rezultat.containsKey(exponent))
                rezultat.put(exponent,-polinom2.get(exponent));
        }
        return rezultat;
    }
    public static HashMap<Integer,Double> inmultire(HashMap<Integer, Double> polinom1, HashMap<Integer, Double> polinom2){
        HashMap<Integer,Double> rezultat=new HashMap<>();
        for(int exponent1:polinom1.keySet()){
            for(int exponent2:polinom2.keySet()){
                int newExponent=exponent1+exponent2;
                double newCoeficient=polinom1.get(exponent1)*polinom2.get(exponent2);

                if(rezultat.containsKey(newExponent))
                {
                    rezultat.put(newExponent,rezultat.get(newExponent)+newCoeficient);
                }
                else
                    rezultat.put(newExponent,newCoeficient);
            }
        }
        return rezultat;
    }

    public static HashMap<Integer,Double> derivata(HashMap<Integer, Double> polinom1)
    {
        HashMap<Integer,Double> rez=new HashMap<>();
        for(int exponent:polinom1.keySet())
        {
            if(exponent!=0)
            {
                double coef=polinom1.get(exponent);
                rez.put(exponent-1,exponent*coef);
            }
        }
        return rez;
    }
    public static HashMap<Integer,Double> integrala(HashMap<Integer, Double> polinom1)
    {
        HashMap<Integer,Double> rez=new HashMap<>();
        for(int exponent:polinom1.keySet())
        {

            double coef=polinom1.get(exponent);
            rez.put(exponent+1,coef/(exponent+1));

        }
        return rez;
    }

}
