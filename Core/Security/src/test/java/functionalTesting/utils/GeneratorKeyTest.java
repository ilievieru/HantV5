package crypto.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.junit.Before;
import org.junit.Test;

public class GeneratorKeyTest {
	Keys keys;
	
	@Before
	public void beforeTests()
	{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	@Test
	public void testThatGenerateTheAsymetricKeys() throws FileNotFoundException, IOException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		assertNotNull(keys);	
	}
	
	@Test
	public void testThatGenerateThePublicKey() throws FileNotFoundException, IOException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		assertNotNull(keys.getPublicKey());
	} 
	
	@Test
	public void testThatGenerateTheCorrectTypePublicKey() throws FileNotFoundException, IOException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		assertEquals(keys.getPublicKey().getAlgorithm(), "RSA");
	}
	
	@Test
	public void testThatGenerateThePrivateKey() throws FileNotFoundException, IOException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		assertNotNull(keys.getPrivateKey());
	}
	
	@Test
	public void testThatGenerateTheCorrectTypePrivateKey() throws FileNotFoundException, IOException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		assertEquals(keys.getPrivateKey().getAlgorithm(), "RSA");
	}
	
	@Test
	public void testThatPrivateKeyIsCreatedFromPublicKey() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		KeyFactory kf = KeyFactory.getInstance("RSA");
		RSAPrivateKeySpec priv = kf.getKeySpec(keys.getPrivateKey(), RSAPrivateKeySpec.class);

		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(priv.getModulus(), BigInteger.valueOf(65537));

		PublicKey publicKey = kf.generatePublic(keySpec);
	
		assertEquals((PublicKey)keys.getPublicKey(), publicKey);
	}
	
}
