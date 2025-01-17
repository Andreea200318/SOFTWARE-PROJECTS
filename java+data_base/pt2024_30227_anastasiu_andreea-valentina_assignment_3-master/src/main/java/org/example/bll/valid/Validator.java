package org.example.bll.valid;

/**
 * Această interfață definește un validator generic.
 * Un validator este folosit pentru a valida un obiect de tip T.
 *
 *
 * @param <T> Tipul obiectului care urmează să fie validat.
 */
public interface Validator<T> {

    /**
     * Validează obiectul dat.
     *
     * @param t Obiectul de tip T care urmează să fie validat.
     */
    void validate(T t);
}
