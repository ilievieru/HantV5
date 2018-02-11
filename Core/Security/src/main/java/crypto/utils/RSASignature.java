package crypto.utils;


import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class RSASignature implements SignatureAlgorithm {

	@Override
	public byte[] generateSignature(byte[] data, Key privateKey) {
		Signature sign;
		try {
			sign = Signature.getInstance("SHA1WithRSA");
			sign.initSign((PrivateKey) privateKey);
			sign.update(data);
			return sign.sign();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean verifySignature(byte[] message, byte[] data, Key publicKey) {
		Signature sign;
		try {
			sign = Signature.getInstance("SHA1WithRSA");
			sign.initVerify((PublicKey) publicKey);
			sign.update(message);
			return sign.verify(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}

}
