package crypto.utils;

import java.security.Key;

public interface EncryptionAlgorithm {
	public byte[] encryption(Key publicKey, byte[] data);

	public byte[] decryption(Key privateKey, byte[] data);
}
