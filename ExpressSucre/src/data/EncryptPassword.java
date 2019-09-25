package data;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MessageDigest messageDigest;
	private byte[] digest;
	private String password;
	private String encriptPassword;

	public EncryptPassword() {
	}

	public String getEncriptPassword(String password) {
		this.password = password;
		StringBuilder builder = new StringBuilder();
		try {
			this.messageDigest = MessageDigest.getInstance("MD5");
			this.messageDigest.update(this.password.getBytes());
			this.digest = this.messageDigest.digest();
			for (int i = 0; i < digest.length; i++) {
				if (Integer.toHexString(0xff & digest[i]).length() == 1) {
					builder.append("0");
				}
				builder.append(Integer.toHexString(0xff & digest[i]));
			}
			this.encriptPassword = builder.toString();
			builder = null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encriptPassword;
	}
}
