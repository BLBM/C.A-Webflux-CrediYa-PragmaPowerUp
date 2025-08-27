package co.com.bancolombia.model.user.gateways;

public interface UserConstants {
     int MIN_BASE_SALARY = 0;
     int MAX_BASE_SALARY = 15_000_000;
     String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
}
