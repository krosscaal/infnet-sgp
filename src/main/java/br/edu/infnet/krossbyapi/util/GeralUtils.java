/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.util;

import java.util.regex.Pattern;

public class GeralUtils {
    private static final Pattern SOMENTE_NUMEROS = Pattern.compile("^\\d+$");
    private static final Pattern SOMENTE_LETRAS = Pattern.compile("^[\\p{L}\\s]+$");

    public static boolean contemNumeros(String campo) {
        if (campo == null || campo.trim().isEmpty()) {
            return false;
        }
        return SOMENTE_NUMEROS.matcher(campo).matches();
    }
    public static boolean contemLetras(String campo) {
        if (campo == null || campo.trim().isEmpty()) {
            return false;
        }
        return SOMENTE_LETRAS.matcher(campo).matches();
    }

}
