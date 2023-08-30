package com.file.api.fileapi.exeption;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.file.api.fileapi.model.MessageError;
import com.file.api.fileapi.model.WebResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public WebResponse<MessageError> handleMaxSizeResponse(MaxUploadSizeExceededException exc){
        return WebResponse.<MessageError>builder().error("Error : File to large").build();
    }
}
