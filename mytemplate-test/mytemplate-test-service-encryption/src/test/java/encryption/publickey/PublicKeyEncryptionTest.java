package encryption.publickey;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;

@SpringBootTest(classes = PublicKeyGeneratorTest.class)
@EnableAutoConfiguration
@Slf4j
class PublicKeyEncryptionTest {

	@Test
	@DisplayName("공개키 암호화 테스트")
	void springEncryptionTextWithKeyGenerator() throws NoSuchAlgorithmException, InvalidKeySpecException {
		// 0. 테스트 target 비밀번호
		String testPassword = "1234";

		/*
		 * [테스트 용 publicKey, privateKey git push 절대 금지]
		 * */
		String publicKeyString = "";
		String privateKeyString = "";


		// 1. 일반적인 키값을 쌍으로 만들어서 구현한 케이스
		Map<String, Object> getKeyPairObject = PublicKeyGeneratorTest.getKeyPair();
		PublicKey publicKey = (PublicKey) getKeyPairObject.get("publicKey");
		PrivateKey privateKey = (PrivateKey) getKeyPairObject.get("privateKey");

		// 2. String 으로 key값들을 받았을 경우 byte로 쪼개서 다시 재구성하는 경우
//		PublicKey publicKey = PublicKeyGeneratorTest.makePublicKey(publicKeyStringcring);

		// 3. publickey로 암호화
		String encrypted = encryptRSA(testPassword, publicKey);
		log.info("[Encrypted Key] : {}", encrypted);

		// 4. 암호화된 값 new BCrytPasswordEncoder를 활용하여 다시 인코딩
		String decryptEncodeKey = new BCryptPasswordEncoder().encode(encrypted);
		log.info("[Decrypted Encoded Key] : {}", decryptEncodeKey);

		// 5. 인코딩된 암호화 값 private key로 복호화
		String decrypted = decryptRSA(encrypted, privateKey);
		log.info("[Decrypted Key] : {}", decrypted);

	}


	/***
	 * [ Decrypting Process Method ]
	 * @param encrypted
	 * @param privateKey
	 * @return
	 */
	@Test
	@DisplayName("복호화 프로세스")
	String decryptRSA(String encrypted, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] bytePlain = cipher.doFinal(byteEncrypted);
			String decrypted = new String(bytePlain, StandardCharsets.UTF_8);
			return decrypted;
		} catch (Exception ex) {
			log.error("[RSA 복호화 실패] = {}", ex.getMessage());
			return null;
		}
	}


	/***
	 * [ Encrypting Process Method ]
	 * @param target
	 * @param publicKey
	 * @return
	 */
	@Test
	@DisplayName("암호화 테스트")
	String encryptRSA(String target, PublicKey publicKey) {
		try{

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] bytePlain = cipher.doFinal(target.getBytes());

			String encrpyted = Base64.getEncoder().encodeToString(bytePlain);
			return encrpyted;
		}catch (Exception ex){
			log.error("[RSA 암호화 실패] = {}", ex.getMessage());
			return null;
		}

	}

}