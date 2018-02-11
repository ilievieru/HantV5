package pki.registrationAuthority;

import java.security.Key;
import java.security.PublicKey;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.pkcs.PKCSException;

public class RegistrationAuthorityUtils {
	
	public static boolean verifyCertificateSignedRequest(org.bouncycastle.pkcs.PKCS10CertificationRequest csr,
			Key publicKey) throws OperatorCreationException, PKCSException {
		ContentVerifierProvider verifier = new JcaContentVerifierProviderBuilder()
				.setProvider(new BouncyCastleProvider()).build((PublicKey) publicKey);
		return csr.isSignatureValid(verifier);
	}
	
	
	private static String getX500Field(String field, X500Name x500Name) {
		RDN[] rdnArray = x500Name.getRDNs(new ASN1ObjectIdentifier(field));
		String retVal = null;
		for (RDN item : rdnArray) {
			retVal = item.getFirst().getValue().toString();
		}

		return retVal;
	}
	
	private static boolean verifiedX500Fields()
	{
		return false;
	}
	
}
