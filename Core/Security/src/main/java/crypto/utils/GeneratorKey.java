package crypto.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class GeneratorKey {
	public static Keys generateKey(AlgorithmType algorithmType)
	{
		switch(algorithmType.getTypeEncryption())
		{
			case Symetric:
				return generateSymmetricKey(algorithmType);
			case Asymetric:
				return generateAsymetricKey(algorithmType);
			default:
				return null;
		}
	}
	
		private static Keys generateAsymetricKey(AlgorithmType algorithmType) {
			KeyPair keyPair = null;
			Keys keys = null;
			try 
			{
			    KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithmType.toString(), "BC");
				keyGen.initialize(1024);
				keyPair = keyGen.generateKeyPair();
				keys = new Keys(keyPair.getPublic(), keyPair.getPrivate());
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return keys;
		}
		
		private static Keys generateSymmetricKey(AlgorithmType algorithmType){
			SecretKey key = null;
			Keys keys = null;
			try
			{
				KeyGenerator generator = KeyGenerator.getInstance(algorithmType.toString());
				key = generator.generateKey();
				keys = new Keys(null, key);
			} 
			catch (NoSuchAlgorithmException e) 
			{
				e.printStackTrace();
			}
			
			return keys;
		}
	}

