package crypto.utils;

import java.security.Key;

public interface SignatureAlgorithm {
	public byte[] generateSignature(byte[] data, Key privateKey);
	public boolean verifySignature(byte[] message, byte[] data, Key privateKey);
	
}
