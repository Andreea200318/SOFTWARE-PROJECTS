package org.example.bll.valid;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.model.Client;

/**
 * clasa implem un valid pt ob de tip client
 * verifica ca adresa de email sa fie valida
 */

public class Clients implements Validator<org.example.model.Client> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * valid adresa
     * @param client Obiectul de tip T care urmează să fie validat.
     * @throws IllegalArgumentException daca adresa nu e valida
     */

    public void validate(org.example.model.Client client) {
        String email = client.getEmail();

        Pattern pattern = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = pattern.matcher(email);


        if (!matcher.matches()) {
            throw new IllegalArgumentException("Adresa de email a clientului nu este validă.");
        }

    }
}

