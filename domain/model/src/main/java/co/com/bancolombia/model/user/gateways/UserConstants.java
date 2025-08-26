package co.com.bancolombia.model.user.gateways;

public interface UserConstants {
     static final int MIN_BASE_SALARY = 0;
     static final int MAX_BASE_SALARY = 15_000_000;
     static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
}
