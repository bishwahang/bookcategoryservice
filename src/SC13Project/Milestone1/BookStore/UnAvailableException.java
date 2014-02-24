package SC13Project.Milestone1.BookStore;

//Please do not change the name of the package or this class
public class UnAvailableException extends Exception {
	public UnAvailableException() {
		System.out.println("No argument");
	}

	public UnAvailableException(String message) {
		System.out.println(message);
		System.out.println(initCause(new Throwable("define exception")));
	}

	public UnAvailableException(Throwable cause) {
		System.out.println(cause.getCause());
	}

	public UnAvailableException(String message, Throwable cause) {
		System.out.println(message);
		System.out.println(cause.getCause());

		}
}
