package org.example.polinom;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinom {
    private Map<Integer,Double> termeni;
    public Polinom()
    {
        termeni=new HashMap<>();
    }
    public Polinom(Map<Integer, Double> termeni) {
        this.termeni = termeni;
    }

    public Map<Integer, Double> getTermeni() {
        return termeni;
    }

    public void setTermeni(Map<Integer, Double> termeni) {
        this.termeni = termeni;
    }

    public static HashMap<Integer, Double> transformStringToPolinomHashMap(String strPolinom) {
        HashMap<Integer, Double> polinom = new HashMap<>();
        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(strPolinom);

        while (matcher.find()) {
            String termen = matcher.group().replace(" ", "");
            double coeficient=1;
            int exponent=0;



            if (termen.equals("x")) {
                coeficient = 1; // Setăm coeficientul implicit pentru "x" ca fiind 1
                exponent = 1;   // Setăm exponentul implicit pentru "x" ca fiind 1
            } else if (termen.contains("x")) {
                String[] parts = termen.split("x\\^?");
                if (parts[0].equals("-")) {
                    coeficient = -1;
                } else if (!parts[0].isEmpty() && !parts[0].equals("+")) {
                    coeficient = Double.parseDouble(parts[0]);
                }

                if (parts.length > 1) {
                    exponent = Integer.parseInt(parts[1]);
                } else {
                    exponent = 1;
                }
            } else if (!termen.isEmpty()) {
                // Dacă termenul nu are "x" și nu e gol, atunci e un coef const
                coeficient = Double.parseDouble(termen);
                exponent = 0; // Setăm exponentul  pentru un coef const ca 0
            }

            polinom.put(exponent, coeficient);
        }

        return polinom;
    }





    public String convertFromHashMap(HashMap<Integer, Double> polinom) {
        StringBuilder rez = new StringBuilder();


        for (int exponent : polinom.keySet()) {
            double coeficient = polinom.get(exponent);

            if (coeficient != 0) {//semnul + sau - doar pt termenii care nu is 0
                rez.append(coeficient >= 0 ? " + " : " - ");

                // Adăugam coef și exponentul la rez
                if (coeficient != 1 && coeficient != -1 || exponent == 0) {
                    rez.append(Math.abs(coeficient));
                }

                if (exponent != 0) {
                    rez.append("x");

                    if (exponent != 1) {
                        rez.append("^").append(exponent);
                    }
                }
            }
        }

        // Dacă polinomul este gol, ret 0
        if (rez.length() == 0) {
            return "0";
        }

        return rez.toString();
    }


}
