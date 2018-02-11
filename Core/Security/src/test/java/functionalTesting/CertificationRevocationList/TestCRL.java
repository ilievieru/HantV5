package pki.CertificationRevocationList;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import crypto.utils.AlgorithmType;
import crypto.utils.GeneratorKey;
import crypto.utils.Keys;
import pki.CertificationRevocationList.CertificationRevocationList;
import pki.certificationAuthority.SingletonCertificateAuthority;
import pki.utility.GenerateCSR;
import pki.utility.X500NameSpaces;

public class TestCRL {

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
	public void testCreationOfNewCRL() throws IOException, InvalidKeyException, CertificateParsingException, NoSuchProviderException, SecurityException, SignatureException, CRLException
	{
		X509Certificate certificate = createACertificate();
		certificateAuthority.processingRequestsRevokeCertificate(certificate.getSerialNumber());
		
		assertTrue(CertificationRevocationList.getCurrentCRLs().get(0).getRevokedCertificate(certificate.getSerialNumber()) != null);
	}
	
	@Test
	public void testAddNewCertificateToCRL() throws IOException, InvalidKeyException, CertificateParsingException, NoSuchProviderException, SecurityException, SignatureException, CRLException
	{
		X509Certificate certificate = createACertificate();
		certificateAuthority.processingRequestsRevokeCertificate(certificate.getSerialNumber());
		assertTrue(CertificationRevocationList.getCurrentCRLs().get(0).getRevokedCertificate(certificate.getSerialNumber()) != null);
	}
	
	public X509Certificate createACertificate() throws IOException
	{
		X500Name x500Name = new X500NameSpaces().setCommonName("IOT TEST").setCountry("Romania")
				.setOrganizationUnit("IoT test").build();

		PKCS10CertificationRequest csr = new PKCS10CertificationRequest(
				GenerateCSR.createCertificateRequest(x500Name, keys));

		return certificateAuthority.processingRequestsGenerateNewCertificate(csr);
	}
}
