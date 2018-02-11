package crypto.utils;

public class EncryptionFactory {
	public EncryptionAlgorithm getEncryptionAlgorithm(AlgorithmType algorithmType) {
		
		switch(algorithmType)
		{
			case RSA:
				return new RSAEncryption();
			case AES:
				return new AESEncryption();
			default:
				return null;
		}
	}
}
