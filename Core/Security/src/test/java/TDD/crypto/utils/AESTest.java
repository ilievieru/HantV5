package test.java.crypto.utils;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import crypto.utils.AlgorithmType;
import crypto.utils.EncryptionAlgorithm;
import crypto.utils.EncryptionFactory;
import crypto.utils.GeneratorKey;
import crypto.utils.Keys;
import crypto.utils.RSAEncryption;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AESTest {
	EncryptionAlgorithm aes;
	Keys key;
	private static final String TEXT = "THIS IS ENCRYPTED WITH AES";
	
	@Test
	public void testThatGenerateTheSymetricKeys() throws FileNotFoundException, IOException
	{
		key = GeneratorKey.generateKey(AlgorithmType.AES);
		
		assertEquals(key.getPrivateKey().getAlgorithm(), "AES");	
	
	}
	
	@Test
	public void testThatCreateTheCorrectAlgorithmEncryptionClass() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		EncryptionFactory factoryEncryptionAlgorithm = new EncryptionFactory();
		aes = factoryEncryptionAlgorithm.getEncryptionAlgorithm(AlgorithmType.RSA);
		
		assertThat(aes, instanceOf(RSAEncryption.class));
	}
	
	@Test
	public void testThatEncryptingBytesResultsInExpectedString() throws IOException {
		key = GeneratorKey.generateKey(AlgorithmType.AES);
		
		EncryptionFactory factoryEncryptionAlgorithm = new EncryptionFactory();
		aes = factoryEncryptionAlgorithm.getEncryptionAlgorithm(AlgorithmType.AES);
		byte[] encryption = aes.encryption(key.getPrivateKey(), TEXT.getBytes());
		byte[] decrytedText = aes.decryption(key.getPrivateKey(), encryption);
		
		assertEquals(new String(decrytedText), TEXT);
	}
}
