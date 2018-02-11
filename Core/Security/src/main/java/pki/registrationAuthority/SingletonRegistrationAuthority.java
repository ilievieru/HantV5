package pki.registrationAuthority;

public class SingletonRegistrationAuthority {

	private static SingletonRegistrationAuthority instance = new SingletonRegistrationAuthority();

	private SingletonRegistrationAuthority() {
	}

	public static SingletonRegistrationAuthority getInstance() {
		if (instance == null) {
			instance = new SingletonRegistrationAuthority();
		}
		return instance;
	}
	
	
}
