package com.lightcc.motd.global.exception;

import com.lightcc.motd.global.response.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 정의된 에러 핸들링
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> applicationExceptionHandler(ApplicationException e) {
        log.error("""
                        ########### Application exception occurred ###########
                        ####### code: {}
                        ####### desc: {}
                        ####### message: {}""",
                e.getResult().getCode(),
                e.getResult().getDesc(),
                e.getData().getMessage());

        return ErrorResponseEntity.toResponseEntity(e.getResult(), e.getData());
    }

    /**
     * 미정의된 에러 핸들링
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> globalExceptionHandler(RuntimeException e) {
        log.error("""
                        ########### Runtime exception occurred ###########
                        ####### error: {}""",
                e.toString());

        return ErrorResponseEntity.toResponseEntity(ResultObject.error(), ErrorResult.error());
    }
}
