package co.com.bancolombia.config;

import co.com.bancolombia.model.role.gateways.RoleRepository;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.create_user_case.CreatedUserUseCase;
import co.com.bancolombia.usecase.user_validator.UserValidatorUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public MyUseCase myUseCase() {
            return new MyUseCase();
        }

        @Bean
        public UserRepository userRepository() {
            return Mockito.mock(UserRepository.class);
        }

        @Bean
        public RoleRepository rolRepository() {
            return Mockito.mock(RoleRepository.class);
        }

        @Bean
        public CreatedUserUseCase createdUserUseCase() {
            return Mockito.mock(CreatedUserUseCase.class);
        }

        @Bean
        public UserValidatorUseCase userValidatorUseCase() {
            return Mockito.mock(UserValidatorUseCase.class);
        }

    }


    static class MyUseCase {
        public String execute() {
            return "MyUseCase Test";
        }
    }
}