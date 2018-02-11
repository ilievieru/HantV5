package pki.validationAuthority;

import java.util.ArrayList;

import pki.utility.DigitalCertificate;

public class SingletonValidationAuthority {
	ArrayList<DigitalCertificate> listDigitalCertificate;
	ArrayList<DigitalCertificate> listRemovedDigitalCertificate;
	private static SingletonValidationAuthority instance = new SingletonValidationAuthority();

	private SingletonValidationAuthority() {
	}

	public static SingletonValidationAuthority getInstance() {
		if (instance == null) {
			instance = new SingletonValidationAuthority();
		}
		return instance;
	}
	
	public void verifiedDigitalCertificate(DigitalCertificate certificate)
	{
		
	}
	
	public void verifiedExperationDigitalCertificate()
	{
		
	}
	
	private void saveDigitalCertificate(DigitalCertificate certificate)
	{
		
	}
	
}
