package br.com.systec.taskflow.publicapi.v1.converter;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.publicapi.v1.dto.UserInputDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserConverter {

    private UserConverter(){}

    public static UserInputDTO toUserInputDTO(User user) {
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setUsername(user.getUsername());
        userInputDTO.setPassword(user.getPassword());
        userInputDTO.setEmail(user.getEmail());

        return userInputDTO;
    }

    public static User toUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setUsername(userInputDTO.getUsername());
        user.setEmail(userInputDTO.getEmail());

        if (userInputDTO.getPassword() != null && !userInputDTO.getPassword().isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(userInputDTO.getPassword()));
        }

        return user;
    }
}
