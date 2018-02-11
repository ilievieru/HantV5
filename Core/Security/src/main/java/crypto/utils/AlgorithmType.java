package crypto.utils;

public enum AlgorithmType {
	AES(TypeEncryption.Symetric),
	RSA(TypeEncryption.Asymetric);

	private TypeEncryption typeEncryption;

	AlgorithmType(TypeEncryption typeAlgorithm) {
		this.typeEncryption = typeAlgorithm;
	}

	public TypeEncryption getTypeEncryption() {
		return typeEncryption;
	}
	
	public void setTypeEncryption(TypeEncryption typeAlgorithm) {
		this.typeEncryption = typeAlgorithm;
	}
	
}
