package pki.certificationAuthority;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import crypto.utils.AlgorithmType;
import crypto.utils.GeneratorKey;
import crypto.utils.Keys;
import pki.utility.GenerateCSR;
import pki.utility.X500NameSpaces;

public class CertificateAuthorityTest {

	private static SingletonCertificateAuthority certificateAuthority;
	private Keys keys;

	@BeforeClass
	public static void runBeforeClass() {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		certificateAuthority = SingletonCertificateAuthority.getInstance();
	}

	@Before
	public void runBeforeMethods() {
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
	}

	@Test
	public void testCreationCSR() throws IOException {
		X500Name x500Name = new X500NameSpaces().setCommonName("IOT TEST").setCountry("Romania")
				.setOrganizationUnit("IoT test").build();

		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(
				GenerateCSR.createCertificateRequest(x500Name, keys));

		assertNotNull(csr);
	}

	@Test
	public void testSignatureOnCSR() throws IOException {
		X500Name x500Name = new X500NameSpaces().setCommonName("IOT TEST").setCountry("Romania")
				.setOrganizationUnit("IoT test").build();

		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(
				GenerateCSR.createCertificateRequest(x500Name, keys));

		assertEquals(keys.getPublicKey(), GenerateCSR.getPublicKeyCSR(csr));
	}

	@Test
	public void testProcessRequestCertificationAuthority() throws IOException {
		X500Name x500Name = new X500NameSpaces().setCommonName("IOT TEST").setCountry("Romania")
				.setOrganizationUnit("IoT test").build();

		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(
				GenerateCSR.createCertificateRequest(x500Name, keys));

		assertNotNull(certificateAuthority.processingRequestsGenerateNewCertificate(csr));
	}

	@Test
	public void testCertificateSignByCA() throws IOException
	{
		X500Name x500Name = new X500NameSpaces().setCommonName("IOT TEST").setCountry("Romania")
				.setOrganizationUnit("IoT test").build();
		
		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(GenerateCSR.createCertificateRequest(x500Name, keys));
		X509Certificate certificate = certificateAuthority.processingRequestsGenerateNewCertificate(csr);
		PublicKey publicKey = CertificateUtils.convertFromPEMToPublicKey("D:\\Certificate\\publicKeyRootCA.pem");
		
		try {
			certificate.verify(publicKey);
		}
		catch (Exception e) {
			fail("Certificate is not signed by CA Root");
		}

	}
	
}
