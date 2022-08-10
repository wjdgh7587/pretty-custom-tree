package encryption.publickey;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PublicKeyGeneratorTest {

    /***
     * [ PublicKey, PrivateKey 생성 - Pair - ]
     * @Descryption : 쌍으로 publicKey, privateKey 생성
     * @return
     */
    @Test
    @DisplayName("쌍으로 연결되는 publicKey 암호화 key 생성")
    public static Map<String, Object> getKeyPair(){
        Map<String, Object> keyMap = new HashMap<>();

        try{
            // RSA 방식으로 키를 생성
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

            // keySize를 지정
            generator.initialize(2048);

            // KeyPair 값 지정
            KeyPair keyPair = generator.genKeyPair();

            // public Key 생성
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            keyMap.put("publicKey", publicKey);
            keyMap.put("privateKey", privateKey);

            log.info("[Public Key] : {}", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            log.info("[Private Key] : {}", Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        }catch (Exception e){
            log.error("[Generating Public Key Error] : {}, {}", e.getMessage(), e.getCause());
        }

        return keyMap;
    }

    /***
     * [ public key 객체 생성 ]
     * @param publicKeyString -> String Type의 publicKey
     * @return PublicKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @DisplayName("publicKey 값이 String 일 경우 publicKey Object 변환")
    public static PublicKey makePublicKey(String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] publicKeyByte = Base64.getDecoder().decode(publicKeyString.getBytes());
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyByte);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        return publicKey;
    }

    /***
     * [ private key 객체 생성 ]
     * @param privateKeyString - String Type의 privateKey
     * @return PrivateKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    @Test
    @DisplayName("privateKey 값이 String 일 경우 privateKey Object 변환")
    public static PrivateKey makePrivateKey(String privateKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] privateKeyByte = Base64.getDecoder().decode(privateKeyString.getBytes());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return privateKey;
    }
}
