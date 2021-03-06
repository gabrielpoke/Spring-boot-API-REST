package br.com.alura.forum.Config.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrosDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrodeFormularioDto> handle(MethodArgumentNotValidException exception){

        List<ErrodeFormularioDto> dto = new ArrayList<>();

        List<FieldError> fielderrors = exception.getBindingResult().getFieldErrors();

        fielderrors.forEach(e ->{

            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());

            ErrodeFormularioDto erro = new ErrodeFormularioDto(e.getField(),mensagem);

            dto.add(erro);
        });
        return dto;
    }
}
