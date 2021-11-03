package cz.muni.pa165.barbershop.restreact.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="Access to recource forbidden")
public class ResourceForbiddenException extends Exception{
}
