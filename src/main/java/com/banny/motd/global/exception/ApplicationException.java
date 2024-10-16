package com.banny.motd.global.exception;

import com.banny.motd.global.dto.response.StstusObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * runtime 시 발생하는 모든 예외에 대한 dto 정의
 */
@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private StstusObject status;
    private ErrorResult data;

    // 메시지가 있는 생성자
    // 메시지를 ErrorResult 객체에 담아 반환
    public ApplicationException(StatusType statusType, String message) {
        this.status = new StstusObject(statusType);
        this.data = new ErrorResult(message);
    }

    // 메시지가 없는 생성자
    // 메시지 없는 ErrorResult 객체 생성
    public ApplicationException(StatusType statusType) {
        this.status = new StstusObject(statusType);
        this.data = new ErrorResult();
    }

}
