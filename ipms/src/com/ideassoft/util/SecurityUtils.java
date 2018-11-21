package com.ideassoft.util;
import java.security.PrivateKey;
import java.security.PublicKey;
public class SecurityUtils {
	
	static String privateKeyStr="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqjqBZtprlFIal6gU"
			+ "2zJzIDvZ/T7GatifPCy12AcqQhRXFh5kJHFizoPE37Id0IywIGYKOKIK783RiBOuQNtfrgX8pDRTiqKXj2M"
			+ "+380A8b+WD7TYcWhfbBe6SAV2nyQdON1nU8rhmTYU1luVeesdoKBj8va0mATvRUxeV6pdCYYD//k"
			+ "29KpuFvACRkllnytlLf2jWaZBdVEiaaVNzLTQyeRS7igwc1QpOZH0MrXMwTG5EcnSk5pd/myBiVKYce"
			+ "V6sQS4HojiasYxX7XNsXq5bbblloQ0U750a13ZNN0O26URItf4BTys+c+UgDmvvGkbBD3j53ClClLgixp"
			+ "gRBPGNAgMBAAECggEAEw7K1ht0ZWKMxR9II95rKmOZoZIrG0l9W0I5ezUqCEoGq8LCE+WJ99BlA8"
			+ "W3QP/pHKKVFwNp0Eyc1ZMNK1kleVhH+DNRAR6tIq+ITlqs89PhBoPMBxD+F2grRz9F0sjAuzmuHQ"
			+ "BeIHhLCTjqOktn2riYSi17moKKx5IMUaEb87u4tqfZQi8Dkhqw+QuI6+g+6sTAlB4zoXXiGFhgwCfYI4xj"
			+ "6x8anmSPXMfcPKL0gjQFqBSUGQECCrHO9BcetQdByM/xlbZiMW5mYMXPzUnO6f2enMvsBdntpos"
			+ "4E7IjooRNMbanJrfOySxVps+byKH4RWXWS4i0CdmiUuRBk1ywIQKBgQDSB0Y7U2VlOm+9HCaZFCD"
			+ "84Wm/9oAZAX5QhVV+dBllQ7B9u/F4RFldWal6O6ZVzr8r3P9sstsonjd8uHBaFJxG2tcGpayumppthcf"
			+ "tVKXQFBJNWFiDoUIpMECS5WC9E08VSz22ict6LsJQiwHSTKhLHYn47aRmKHABHbMVzkvONQKBgQ"
			+ "DP456XHSrGUYLmlWPaE16IKg1urG0O6jz4zvgtNm48O9kkznqOsHwIo8tNDPmCyingsSIhlaVjWsUU"
			+ "nhu2iysgalT9esfaD7sd3vg7jtbAJtPDOTUSSWoj8zqKpjso0qiPwRokHGoVoxvcIwp7Wij1VVnQH9RbA"
			+ "wMvDGWt+M/g+QKBgA2yb8Aatjgd0X+xwEqnZ2dWhAEC4GPBFy3FJEg05hsWiQIKKbtAKdvOpyDTl"
			+ "wttikYgCYBbQbOqbc/yb1FQmsh8KR3D3Dke2AdgfCNcK9wgEvl8U3lyINBy1yM9Vku+YJ/w4jtvTRfQ"
			+ "yNdb2WiPm0Ia0Q6s4NXQuU05VJ6Nb6ApAoGBALZY9Mmd9uAfrU8uaHhnlvqe0EtsiPiVM8gWWw"
			+ "/ld4n0AQLM7ils2k8FxRSftdgoFdWV6ddFmN1uSAJOsQZMShbTzhrM8LHwLHQGGPy8zNec9xB9hH"
			+ "S22pgQCCCHsMqdBOebPJ/CILc76/sK7W3HZrfTFsV8tdri6I8DM8kGvuiZAoGAUlQ9ML4QhrWAUTW"
			+ "qCRHFbQjyZaW2ZYHjnBLByJ1Akywrj7X1Lg2XMRwMcJJXHhQNXDbkbY/ym26woLpUXd/hDfrMG8X"
			+ "bZi1AbPWgk1HQg65rDXTy5Gm/mT4Eco8EqcKFWk80RDuO1ScwTZE1PZTgWGjtMw2r83Luu2S3WHS+R2o=";
	static String publicKeyStr="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqo6gWbaa5RSGpeoFNsycy"
		+ "A72f0+xmrYnzwstdgHKkIUVxYeZCRxYs6DxN+yHdCMsCBmCjiiCu/N0YgTrkDbX64F/KQ0U4qil49jPt/"
		+ "NAPG/lg+02HFoX2wXukgFdp8kHTjdZ1PK4Zk2FNZblXnrHaCgY/L2tJgE70VMXleqXQmGA//5NvSqbh"
		+ "bwAkZJZZ8rZS39o1mmQXVRImmlTcy00MnkUu4oMHNUKTmR9DK1zMExuRHJ0pOaXf5sgYlSmHHl"
		+ "erEEuB6I4mrGMV+1zbF6uW225ZaENFO+dGtd2TTdDtulESLX+AU8rPnPlIA5r7xpGwQ94+dwpQpS4IsaYEQTxjQIDAQAB";

	/**
	* 解密
	* @param cipherText 密文
	* @return 返回解密后的字符串
	* @throws Exception
	*/
	public static String decrypt(String cipherText) {
		// 从文件中得到私钥
		try {
			PrivateKey privateKey = RSAUtils.loadPrivateKey(privateKeyStr);
			byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(cipherText), privateKey);
			String decryptStr = new String(decryptByte);
			return decryptStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* 加密
	* @param plainTest 明文
	* @return 返回加密后的密文
	* @throws Exception
	*/
	public static String encrypt(String plainTest){
		try {
			PublicKey publicKey = RSAUtils.loadPublicKey(publicKeyStr);
			// 加密
			byte[] encryptByte = RSAUtils.encryptData(plainTest.getBytes(), publicKey);
			String afterencrypt = Base64Utils.encode(encryptByte);
			return afterencrypt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}