package org.example.bll.valid;

import org.example.model.Orders;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import java.io.IOException;

/**
 * validator pt oders
 */

public class ValidOrder implements Validator<Orders> {
    /**
     * valideaza cant unei comenzi
     * @param order Obiectul de tip T care urmează să fie validat.
     * @throws IllegalArgumentException daca cant este mai mica sau egala cu 0
     */
    public void validate(Orders order)  {
        if(order.getCantitate()<1){
            throw new IllegalArgumentException("Cantitate must be greater than 0");
        }
    }


}
