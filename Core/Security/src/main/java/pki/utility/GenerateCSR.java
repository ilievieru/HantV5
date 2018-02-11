package pki.utility;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.RSAPublicKeySpec;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.crypto.ec.ECElGamalDecryptor;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.util.encoders.Base64;

import crypto.utils.Keys;

public class GenerateCSR {
	private static String SIGNATURE_ALTGORITHM = "SHA1withRSA";
	private static String BEGIN_HEADER = "----BEGIN NEW CERTIFICATE REQUEST----";
	private static String END_HEADER = "----END NEW CERTIFICATE REQUEST----";
	private static String ALGORITHM_TYPE = "RSA";
	
	public static byte[] createCertificateRequest(X500Name x500Name, Keys keys) throws IOException {
		PKCS10CertificationRequest csr = null;

		Security.addProvider(new BouncyCastleProvider());

		PKCS10CertificationRequestBuilder builder = new PKCS10CertificationRequestBuilder(x500Name,
				SubjectPublicKeyInfo.getInstance(keys.getPublicKey().getEncoded()));
		AlgorithmIdentifier sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find(SIGNATURE_ALTGORITHM);
		AlgorithmIdentifier digAlgId = new DefaultDigestAlgorithmIdentifierFinder().find(sigAlgId);

		AsymmetricKeyParameter keyParam;
		try {
			keyParam = PrivateKeyFactory.createKey(keys.getPrivateKey().getEncoded());
			ContentSigner signer = new BcRSAContentSignerBuilder(sigAlgId, digAlgId).build(keyParam);
			csr = builder.build(signer);
		} catch (Exception e) {
			System.out.println("Something is wrong on create certificate request");
		}

		return csr.getEncoded();
	}

	public static void printCertificate(PKCS10CertificationRequest csr) {
		try {
			System.out.println(BEGIN_HEADER);
			System.out.println(new String(Base64.encode(csr.getEncoded())));
			System.out.println(END_HEADER);

		} catch (IOException e) {
			System.out.println("Something is wrong on print certificate");
		}
	}

	public static X500Name createX500Name(String country, String commonName, String locality, String emailAddress,
			String organization) {
		return new X500NameSpaces().setCountry(country).setCommonName(commonName).setLocality(locality)
				.setEmailAddress(emailAddress).setOrganizationUnit(organization).build();

	}

	public static PublicKey getPublicKeyCSR(PKCS10CertificationRequest csr) {
		SubjectPublicKeyInfo pkInfo = csr.getSubjectPublicKeyInfo();
		PublicKey publicKey = null;
		try {
			RSAKeyParameters rsa = (RSAKeyParameters) PublicKeyFactory.createKey(pkInfo);
			RSAPublicKeySpec rsaSpec = new RSAPublicKeySpec(rsa.getModulus(), rsa.getExponent());
			publicKey = KeyFactory.getInstance(ALGORITHM_TYPE).generatePublic(rsaSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return publicKey;
	}

}