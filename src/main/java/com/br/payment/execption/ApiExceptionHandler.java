package com.br.payment.execption;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler
    extends
    ResponseEntityExceptionHandler
{

	public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se " +
	    "o problema persistir, entre em contato com o administrador do sistema.";

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable (final HttpMediaTypeNotAcceptableException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request)
	{
		return ResponseEntity.status(status).headers(headers).build();
	}

	@Override
	protected ResponseEntity<Object> handleBindException (final BindException ex, final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request)
	{

		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid (final MethodArgumentNotValidException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request)
	{

		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}

	private ResponseEntity<Object> handleValidationInternal (final Exception ex, final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request, final BindingResult bindingResult)
	{
		final ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		final String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		final List<Problem.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
			final String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

			String name = objectError.getObjectName();

			if (objectError instanceof FieldError)
			{
				name = ((FieldError)objectError).getField();
			}

			return Problem.Object.builder().name(name).userMessage(message).build();
		}).collect(Collectors.toList());

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).objects(problemObjects).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<?> handleNegocio (final BaseException ex, final WebRequest request)
	{

		final HttpStatus status = HttpStatus.BAD_REQUEST;
		final ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		final String detail = ex.getMessage();

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught (final Exception ex, final WebRequest request)
	{
		final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		final ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		final String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

		ex.printStackTrace();

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException (final NoHandlerFoundException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request)
	{

		final ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		final String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch (final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request)
	{

		if (ex instanceof MethodArgumentTypeMismatchException)
		{
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException)ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch (final MethodArgumentTypeMismatchException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request)
	{

		final ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

		final String detail = String.format(
		    "O parâmetro de URL '%s' recebeu o valor '%s', " + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
		    ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable (final HttpMessageNotReadableException ex, final HttpHeaders headers,
	    final HttpStatus status, final WebRequest request)
	{
		final Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException)
		{
			return handleInvalidFormat((InvalidFormatException)rootCause, headers, status, request);
		}
		else if (rootCause instanceof PropertyBindingException)
		{
			return handlePropertyBinding((PropertyBindingException)rootCause, headers, status, request);
		}

		final ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		final String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handlePropertyBinding (final PropertyBindingException ex, final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request)
	{

		final String path = joinPath(ex.getPath());

		final ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		final String detail = String.format("A propriedade '%s' não existe. " + "Corrija ou remova essa propriedade e tente novamente.", path);

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat (final InvalidFormatException ex, final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request)
	{

		final String path = joinPath(ex.getPath());

		final ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		final String detail = String.format(
		    "A propriedade '%s' recebeu o valor '%s', " + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.", path,
		    ex.getValue(), ex.getTargetType().getSimpleName());

		final Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal (final Exception ex, Object body, final HttpHeaders headers, final HttpStatus status,
	    final WebRequest request)
	{

		if (body == null)
		{
			body = Problem.builder().timestamp(OffsetDateTime.now()).title(status.getReasonPhrase()).status(status.value()).userMessage(
			    MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		}
		else if (body instanceof String)
		{
			body = Problem.builder().timestamp(OffsetDateTime.now()).title((String)body).status(status.value()).userMessage(
			    MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder (final HttpStatus status, final ProblemType problemType, final String detail){

		return Problem.builder().timestamp(OffsetDateTime.now()).status(status.value()).type(problemType.getUri()).title(
		    problemType.getTitle()).detail(detail);
	}

	private String joinPath (final List<Reference> references){
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
