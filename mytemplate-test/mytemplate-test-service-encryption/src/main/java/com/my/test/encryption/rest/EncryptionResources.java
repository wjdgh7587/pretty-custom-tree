package com.my.test.encryption.rest;

import com.my.test.entity.result.ResultMsg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncryptionResources {

    @PostMapping("/mytemplate-test-encryption/publickey/generate")
    public ResponseEntity<ResultMsg> generatePublicKeyRSA(){





        return null;
    }
}
