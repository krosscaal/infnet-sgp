/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.exceptionhandler;

import br.edu.infnet.krossbyapi.exception.BusinessException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestControllerAdvice
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String CAMPOS_INVALIDOS = "Um o mais campos estão inválidos!";

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(final BusinessException ex, final WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrors apiErrors = new ApiErrors(status.value(), LocalDateTime.now().format(dataFormatada), ex.getMessage());
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(final NoSuchElementException ex, final WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrors apiErrors = new ApiErrors(status.value(), LocalDateTime.now().format(dataFormatada), ex.getMessage());
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StringBuilder mensagem = new StringBuilder("<< CONFLITO COM ID DE ALGUMA ENTIDADE ENVOLVIDA NA OPERAÇÃO >> ")
                .append("MENSAGEM DO ERRO -->: ")
                .append(ex.getMessage());
        ApiErrors apiErrors = new ApiErrors(status.value(), LocalDateTime.now().format(dataFormatada), mensagem.toString());
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<Campo> campos = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            campos.add(new Campo(nome, mensagem));
        }
        ApiErrors apiErrors = new ApiErrors(status.value(), LocalDateTime.now().format(dataFormatada), CAMPOS_INVALIDOS, campos);
        return handleExceptionInternal(ex, apiErrors, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleValidationExceptions(ConstraintViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<Campo> campos = convertViolations(ex.getConstraintViolations());
        ApiErrors apiErrors = new ApiErrors(status.value(), LocalDateTime.now().format(dataFormatada), CAMPOS_INVALIDOS, campos);
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    private List<Campo> convertViolations(Set<ConstraintViolation<?>> violations) {
        List<Campo> campos = new ArrayList<>();
        for (ConstraintViolation<?> violation : violations) {
            String nome = violation.getPropertyPath().toString();
            String mensagem = violation.getMessage();
            campos.add(new Campo(nome, mensagem));
        }
        return campos;
    }

}
