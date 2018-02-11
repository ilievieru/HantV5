package pki.utility;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extensions;

public class X500NameSpaces {
	private X500NameBuilder x500NameBuilder;

	private String country;
	private String commonName;
	private String locality;
	private String emailAddress;
	private String organizationUnit;
	private String organization;

	public X500NameSpaces() {
		x500NameBuilder = new X500NameBuilder(BCStyle.INSTANCE);
	}
	
	public X500Name build()
	{
		return x500NameBuilder.build();
	}
	public String getCountry() {
		return country;
	}

	public X500NameSpaces setCountry(String country) {
		this.country = country;
		x500NameBuilder.addRDN(BCStyle.COUNTRY_OF_CITIZENSHIP, country);
		return this;
	}

	public String getCommonName() {
		return commonName;
	}

	public X500NameSpaces setCommonName(String commonName) {
		this.commonName = commonName;
		x500NameBuilder.addRDN(BCStyle.CN, commonName);
		return this;
	}

	public String getLocality() {
		return locality;
	}

	public X500NameSpaces setLocality(String locality) {
		this.locality = locality;
		x500NameBuilder.addRDN(BCStyle.L, locality);
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public X500NameSpaces setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		x500NameBuilder.addRDN(BCStyle.EmailAddress, emailAddress);
		return this;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public X500NameSpaces setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
		x500NameBuilder.addRDN(BCStyle.OU, organizationUnit);
		return this;
	}

	public String getOrganization() {
		return organization;
	}

	public X500NameSpaces setOrganization(String organization) {
		this.organization = organization;
		x500NameBuilder.addRDN(BCStyle.O, organization);
		 GeneralNames subjectAltName = new GeneralNames(new GeneralName(GeneralName.dNSName, "*.localhost"));
		 x500NameBuilder.addRDN(BCStyle.SN, subjectAltName);
		return this;
	}

}
