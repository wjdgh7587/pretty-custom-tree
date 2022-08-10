package com.my.test.entity.result;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import java.util.Date;

@Getter
@Setter
@Description("ResultMsg VO")
public class ResultMsg<T> {

    private String resultCode;
    private String resultMsg;
    private T payload;
    @JsonFormat(shape = JsonFormat.Shape.STRING
            , pattern = "yyyy-MM-dd 'T' HH:mm:ss"
            , timezone = "Asia/Seoul")
    private Date when;

    public ResultMsg(){
        this.when = new Date();
    }

    public ResultMsg(T payload){
        this.when = new Date();
        this.payload = payload;
    }
}
