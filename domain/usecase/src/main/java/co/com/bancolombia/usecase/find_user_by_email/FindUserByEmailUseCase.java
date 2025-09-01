package co.com.bancolombia.usecase.find_user_by_email;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindUserByEmailUseCase {

    private final UserRepository userRepository;

    public Mono<User> execute(String email){
        return userRepository.findByEmail(email);
    }

}
