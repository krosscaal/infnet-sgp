/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */
 
package br.edu.infnet.krossbyapi.exception;

import java.io.Serial;
import java.util.Locale;

public class BusinessException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public BusinessException(String mensagem) {
        super(mensagem.toUpperCase(Locale.ROOT));
    }

}
