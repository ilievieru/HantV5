package pki.certificationAuthority;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import crypto.utils.Keys;

public class CertificateUtils {

	private static long MILI_SECONDS_DAY = 1000L * 60 * 60 * 24;
	private static String ALGORITHM_TYPE = "RSA";
	private static String SIGNATURE_TYPE = "SHA256WithRSA";
	private static String SECURITY_PROVIDER = "BC";
	private static String DNS_NAME = "localhost";
	private static String PATH_ROOT = "D:\\Certificate\\";
	private static String CERTIFICATE_TYPE = "X.509";

	public static X509Certificate generateCertificate(X509Certificate rootCA, X500Name subject, Keys keyPair,
			PublicKey publicKey)
			throws OperatorCreationException, java.security.cert.CertificateException, IOException {
		Date notBefore = new Date(System.currentTimeMillis() - MILI_SECONDS_DAY);
		Date notAfter = new Date(System.currentTimeMillis() + 5 * 365 * MILI_SECONDS_DAY);
		BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());

		X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(rootCA, serial, notBefore,
				notAfter, subject, publicKey);
		certificateBuilder.addExtension(Extension.subjectAlternativeName, false,
				new GeneralNames(new GeneralName(GeneralName.dNSName, DNS_NAME)));
		X509Certificate certificate = signCertificate(certificateBuilder, (PrivateKey) keyPair.getPrivateKey());

		saveNewCreatedCertificate(certificate, getX500Field(BCStyle.CN.getId(), subject));

		return certificate;

	}

	private static void saveNewCreatedCertificate(X509Certificate certificate, String name)
			throws CertificateEncodingException, IOException {
		saveToFile(certificate, PATH_ROOT + name + "Certificate.cer");
		saveToFile(convertToPEMFormat(certificate), PATH_ROOT + name + "Certificate.pem");
	}

	public static X509Certificate generateSelfSignedCertificate(X500Name x500Name, Keys keyPair)
			throws OperatorCreationException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
			SignatureException, java.security.cert.CertificateException, IOException {

		Date notBefore = new Date(System.currentTimeMillis() - MILI_SECONDS_DAY);
		Date notAfter = new Date(System.currentTimeMillis() + 5 * 365 * MILI_SECONDS_DAY);
		BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());

		X500Name issuer = x500Name;
		X500Name subject = x500Name;

		X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(issuer, serial, notBefore,
				notAfter, subject, (PublicKey) keyPair.getPublicKey());
		certificateBuilder.addExtension(Extension.subjectAlternativeName, false,
				new GeneralNames(new GeneralName(GeneralName.dNSName, "localhost")));
		X509Certificate certificate = signCertificate(certificateBuilder, (PrivateKey) keyPair.getPrivateKey());

		saveRootCertificateAndKeys(certificate, keyPair);

		return certificate;
	}

	private static void saveRootCertificateAndKeys(X509Certificate certificate, Keys keyPair)
			throws CertificateEncodingException, IOException {
		saveToFile(certificate, PATH_ROOT + "rootCA.cer");
		saveToFile(convertToPEMFormat(certificate), PATH_ROOT + "rootCA.pem");
		saveToFile(convertToPEMFormat((PrivateKey) keyPair.getPrivateKey()), PATH_ROOT + "privateKeyRootCA.pem");
		saveToFile(convertToPEMFormat((PublicKey) keyPair.getPublicKey()), PATH_ROOT + "publicKeyRootCA.pem");

	}

	private static X509Certificate signCertificate(X509v3CertificateBuilder certificateBuilder, PrivateKey privateKey)
			throws OperatorCreationException, java.security.cert.CertificateException {
		ContentSigner signatureGenerator = new JcaContentSignerBuilder(SIGNATURE_TYPE)
				.setProvider(new BouncyCastleProvider()).build(privateKey);

		return new JcaX509CertificateConverter().setProvider(SECURITY_PROVIDER)
				.getCertificate(certificateBuilder.build(signatureGenerator));
	}

	private static String getX500Field(String field, X500Name x500Name) {
		RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(field));
		String retVal = null;
		for (RDN item : rdnArray) {
			retVal = item.getFirst().getValue().toString();
		}

		return retVal;
	}

	private static void saveToFile(X509Certificate certificate, String filePath)
			throws CertificateEncodingException, IOException {

		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
			fileOutputStream.write(certificate.getEncoded());
			fileOutputStream.flush();
		} catch (Exception e) {
			System.out.println("Something went wrong with save certificate to file");
		}
	}

	public static void saveToFile(String data, String filePath) throws IOException {

		try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
			fileOutputStream.write(data.getBytes());
			fileOutputStream.flush();
		} catch (Exception e) {
			System.out.println("Something went wrong with save data to file");
		}
	}

	public static String convertToPEMFormat(final X509Certificate certificate) {
		PemWriter pemWriter = null;
		try {
			StringWriter stringWriter = new StringWriter();
			pemWriter = new PemWriter(stringWriter);
			pemWriter.writeObject(new PemObject("CERTIFICATE", certificate.getEncoded()));
			pemWriter.flush();
			pemWriter.close();
			return stringWriter.toString();

		} catch (IOException | CertificateEncodingException e) {
			throw new RuntimeException("Cannot format certificate to PEM format", e);
		} finally {
			try {
				pemWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String convertToPEMFormat(final PrivateKey key) {
		PemWriter pemWriter = null;
		try {
			StringWriter stringWriter = new StringWriter();
			pemWriter = new PemWriter(stringWriter);
			pemWriter.writeObject(new PemObject("RSA PRIVATE KEY", key.getEncoded()));
			pemWriter.flush();
			return stringWriter.toString();

		} catch (IOException e) {
			throw new RuntimeException("Cannot format Private Key to PEM format", e);
		} finally {
			try {
				pemWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String convertToPEMFormat(final PublicKey key) {
		PemWriter pemWriter = null;
		try {
			StringWriter stringWriter = new StringWriter();
			pemWriter = new PemWriter(stringWriter);
			pemWriter.writeObject(new PemObject("RSA PRIVATE KEY", key.getEncoded()));
			pemWriter.flush();
			return stringWriter.toString();

		} catch (IOException e) {
			throw new RuntimeException("Cannot format Public Key to PEM format", e);
		} finally {
			try {
				pemWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static PrivateKey convertFromPEMToPrivateKey(String path) {

		try (PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(path)))) {
			KeyFactory factory = KeyFactory.getInstance(ALGORITHM_TYPE, SECURITY_PROVIDER);
			byte[] content = pemReader.readPemObject().getContent();
			PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
			return factory.generatePrivate(privKeySpec);

		} catch (Exception e) {
			throw new RuntimeException("Cannot format Pem to Private Key", e);
		}
	}

	public static PublicKey convertFromPEMToPublicKey(String path) {
		PemReader pemReader = null;
		try {
			KeyFactory factory = KeyFactory.getInstance(ALGORITHM_TYPE, SECURITY_PROVIDER);
			pemReader = new PemReader(new InputStreamReader(new FileInputStream(path)));
			byte[] content = pemReader.readPemObject().getContent();
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
			return factory.generatePublic(pubKeySpec);

		} catch (Exception e) {
			throw new RuntimeException("Cannot format Pem to Public Key", e);
		} finally {
			try {
				pemReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static X509Certificate convertFromPEMToX509Cert(String path) {
		try {
			CertificateFactory fact = CertificateFactory.getInstance(CERTIFICATE_TYPE);
			return (X509Certificate) fact.generateCertificate(new FileInputStream(path));
		} catch (Exception e) {
			throw new RuntimeException("Cannot format Pem to Certificate", e);
		}
	}

	public static CAParams verifiedIfCertificateIssued() {
		File certificateFile = new File(PATH_ROOT+ "rootCA.pem");
		File publicKeyFile = new File(PATH_ROOT + "publicKeyRootCA.pem");
		File privateKeyFile = new File(PATH_ROOT + "privateKeyRootCA.pem");
		PublicKey publicKey;
		PrivateKey privateKey;
		X509Certificate certificate;
		CAParams paramsCA;

		if (!publicKeyFile.exists() || !privateKeyFile.exists() || !certificateFile.exists())
			return null;

		publicKey = CertificateUtils.convertFromPEMToPublicKey(publicKeyFile.getPath());
		privateKey = CertificateUtils.convertFromPEMToPrivateKey(privateKeyFile.getPath());
		paramsCA = new CAParams(privateKey, publicKey);
		certificate = CertificateUtils.convertFromPEMToX509Cert(certificateFile.getPath());
		
		if(verifyCertificate(certificate, publicKey))
			paramsCA.setCertificateCA(certificate);
		else 
			return null;

		return paramsCA;
	}

	private static boolean verifyCertificate(X509Certificate certificate, PublicKey publicKey)
	{
		try
		{
			certificate.verify(publicKey);
		} 
		catch (Exception e) 
		{
			return false;
		}
		return true;
	}

}
