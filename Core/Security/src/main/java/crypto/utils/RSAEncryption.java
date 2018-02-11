package crypto.utils;

import java.security.Key;

import javax.crypto.Cipher;

public class RSAEncryption implements EncryptionAlgorithm {
	
	@Override
	public byte[] encryption(Key publicKey, byte[] dataToBeEncrypted) {
		 byte[] encryptedData = null;
		 try {
		   Cipher cipher = Cipher.getInstance("RSA");
		   cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		   encryptedData = cipher.doFinal(dataToBeEncrypted);
		 } catch (Exception e) {
		   e.printStackTrace();
		 }
		 return encryptedData;
	}

	@Override
	public byte[] decryption(Key privateKey, byte[] dataEncrypted) {
		 byte[] decryptedData = null;
		 try {
		   Cipher cipher = Cipher.getInstance("RSA");
		   cipher.init(Cipher.DECRYPT_MODE, privateKey);
		   decryptedData = cipher.doFinal(dataEncrypted);
		 } 
		 catch (Exception ex) 
		 {
		   ex.printStackTrace();
		 }

		 return decryptedData;
	}

}
