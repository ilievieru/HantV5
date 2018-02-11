package crypto.utils;

public enum TypeSignature {
	SHA_RSA("SHA1withRSA");
	
	private String type;
	
	private TypeSignature(String type) {
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

