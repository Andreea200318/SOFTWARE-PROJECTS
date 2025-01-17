package org.example.bll.valid;

import org.example.model.Produs;

import java.io.Serializable;

/**
 * validam cant produs
 *
 */

public class ProdusValid implements Validator<Produs> {
    @Override
    public void validate(Produs produs) {
        if(produs.getCantitate()<=0)
        {
            throw new IllegalArgumentException("Cantitate must be greater than 0");
        }
    }
    /**
     * @param produs asta trebuie sa fie valida
     * @throws IllegalArgumentException daca e mai mica sau egala cu 0
     */
}
