package crypto.utils;

import java.io.Serializable;
import java.security.Key;

public class Keys implements Serializable{
	private static final long serialVersionUID = 1L;
	private Key publicKey;
	private Key privateKey;
	
	public Keys(Key publicKey, Key privateKey)
	{
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public Key getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(Key publicKey) {
		this.publicKey = publicKey;
	}
	public Key getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(Key privateKey) {
		this.privateKey = privateKey;
	}
}
