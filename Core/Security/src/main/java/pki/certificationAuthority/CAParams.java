package pki.certificationAuthority;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import crypto.utils.AlgorithmType;
import crypto.utils.GeneratorKey;
import crypto.utils.Keys;

public class CAParams {
	private Keys keys;
	private static final String CN = "rootCA";
	private static final String OU = "ROOT Certification Authority";
	private static final String O = "E-Health Technologies";
	private static final String C = "RO";
	
	private X509Certificate certificateCA;
	
	public CAParams()
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
	}
	
	public CAParams(PrivateKey privateKey, PublicKey publicKey)
	{
		keys = new Keys(publicKey, privateKey);
	}
	
	public Keys getKeys() {
		return keys;
	}
	public void setKeys(Keys keys) {
		this.keys = keys;
	}
	public String getCn() {
		return CN;
	}
	public String getO() {
		return O;
	}
	public X509Certificate getCertificateCA() {
		return certificateCA;
	}
	public void setCertificateCA(X509Certificate certificateCA) {
		this.certificateCA = certificateCA;
	}
	public String getOu() {
		return OU;
	}
	public String getC() {
		return C;
	}
}
