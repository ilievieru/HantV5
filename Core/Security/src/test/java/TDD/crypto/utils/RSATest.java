package test.java.crypto.utils;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;

import org.junit.Test;

import crypto.utils.AlgorithmType;
import crypto.utils.EncryptionAlgorithm;
import crypto.utils.EncryptionFactory;
import crypto.utils.GeneratorKey;
import crypto.utils.Keys;
import crypto.utils.RSAEncryption;

public class RSATest {
	EncryptionAlgorithm rsa;
	Keys keys;
	private static final String TEXT = "THIS IS ENCRYPTED WITH RSA";
	
	@Test
	public void testThatGenerateTheAsymetricKeys() throws FileNotFoundException, IOException
	{
		keys = GeneratorKey.generateKey(AlgorithmType.RSA);
		
		assertNotNull(keys);
	}
	
	@Test
	public void testThatCreateTheCorrectAlgorithmEncryptionClass() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		EncryptionFactory factoryEncryptionAlgorithm = new EncryptionFactory();
		rsa = factoryEncryptionAlgorithm.getEncryptionAlgorithm(AlgorithmType.RSA);
		
		assertThat(rsa, instanceOf(RSAEncryption.class));
	}
	
	@Test
	public void testThatEncryptingStringResultsInExpectedBytes() throws IOException {
		keys = this.readKey();
		
		EncryptionFactory factoryEncryptionAlgorithm = new EncryptionFactory();
		rsa = factoryEncryptionAlgorithm.getEncryptionAlgorithm(AlgorithmType.RSA);
		byte[] encryption = rsa.encryption(keys.getPrivateKey(), TEXT.getBytes());
		byte[] testEncryption = this.readEncryptionFromFile();
		
		assertArrayEquals(testEncryption, encryption);
	}
	
	@Test
	public void testThatEncryptingBytesResultsInExpectedString() throws IOException {
		keys = this.readKey();
		
		EncryptionFactory factoryEncryptionAlgorithm = new EncryptionFactory();
		rsa = factoryEncryptionAlgorithm.getEncryptionAlgorithm(AlgorithmType.RSA);
		byte[] encryption = rsa.encryption(keys.getPrivateKey(), TEXT.getBytes());
		byte[] decrytedText = rsa.decryption(keys.getPublicKey(), encryption);
		
		assertEquals(new String(decrytedText), TEXT);
	}
	
	public Keys readKey()
	{
		ObjectInputStream oos;
		Keys keys = null;
		try {
			oos = new ObjectInputStream( 
			        new FileInputStream(new File("RSAKey.ser")));
			keys = (Keys)oos.readObject();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			
		return keys;
	}
	
	public byte[] readEncryptionFromFile()
	{
		try {
			return Files.readAllBytes(new File("RSAEncryption.file").toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
