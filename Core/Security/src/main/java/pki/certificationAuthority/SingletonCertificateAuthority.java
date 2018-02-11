package pki.certificationAuthority;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import pki.CertificationRevocationList.CertificationRevocationList;
import pki.utility.GenerateCSR;
import pki.utility.X500NameSpaces;

public class SingletonCertificateAuthority {
	private CAParams paramsCA;

	private static SingletonCertificateAuthority instance = new SingletonCertificateAuthority();

	public static SingletonCertificateAuthority getInstance() {
		if (instance == null) {
			instance = new SingletonCertificateAuthority();
		}
		return instance;
	}

	private SingletonCertificateAuthority() {
		try {
			if ((paramsCA = CertificateUtils.verifiedIfCertificateIssued()) == null) {
				paramsCA = new CAParams();
				X500Name x500Name = new X500NameSpaces().setCommonName(paramsCA.getCn())
						.setOrganization(paramsCA.getO()).setCountry(paramsCA.getC())
						.setOrganizationUnit(paramsCA.getOu()).build();

				paramsCA.setCertificateCA(CertificateUtils.generateSelfSignedCertificate(x500Name, paramsCA.getKeys()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public X509Certificate processingRequestsGenerateNewCertificate(PKCS10CertificationRequest csr) {
		X509Certificate certificate = null;
		try {
			certificate = CertificateUtils.generateCertificate(paramsCA.getCertificateCA(), csr.getSubject(),
					paramsCA.getKeys(), GenerateCSR.getPublicKeyCSR(csr));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return certificate;
	}

	public void processingRequestsRevokeCertificate(BigInteger serial)
			throws InvalidKeyException, NoSuchProviderException, SecurityException, SignatureException,
			CertificateParsingException, CRLException, IOException {

		CertificationRevocationList.revokeCertificate(paramsCA.getCertificateCA(),
				(PrivateKey) paramsCA.getKeys().getPrivateKey(), serial);

	}
}
