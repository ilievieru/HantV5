package crypto.utils;

public class SignatureFactory {
	public static SignatureAlgorithm getSignatureAlgorithm(AlgorithmType algorithmType) {

		switch (algorithmType) {
		case RSA:
			return new RSASignature();
		default:
			return null;
		}
	}
}
