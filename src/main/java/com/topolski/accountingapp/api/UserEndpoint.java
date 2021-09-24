package com.topolski.accountingapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.topolski.accountingapp.dto.UserDTO;
import com.topolski.accountingapp.exception.CompanyNotFoundException;
import com.topolski.accountingapp.exception.UserNotFoundException;
import com.topolski.accountingapp.model.User;
import com.topolski.accountingapp.repository.UserRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@EnableSwagger2
@RequestMapping("/users")
public class UserEndpoint {
    private final UserRepository userRepository;
    private final ModelMapper mapper = configureMapper();

    @Autowired
    public UserEndpoint(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private ModelMapper configureMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {

        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity
                .ok()
                .body(users
                        .stream()
                        .map(value -> mapper.map(value, UserDTO.class))
                        .collect(Collectors.toList()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable final Long id) {
        try {
            User user = Optional
                    .ofNullable(userRepository
                            .findById(id)
                            .orElseThrow(UserNotFoundException::new))
                    .orElseThrow();
            UserDTO userDTO = mapper.map(user, UserDTO.class);
            return ResponseEntity.ok(userDTO);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody final UserDTO newUser) {
        User user = mapper.map(newUser, User.class);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userRepository.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> alterUser(@PathVariable final Long id,
                                          @RequestBody final UserDTO alterUser) {
        User user = mapper.map(alterUser, User.class);
        Optional.ofNullable(userRepository.findById(id)
                .map(newUser -> {
                    user.setAddress(newUser.getAddress());
                    user.setCompany(newUser.getCompany());
                    user.setContact(newUser.getContact());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    return ResponseEntity.ok(user);
                })
                .orElseThrow(UserNotFoundException::new));

        return ResponseEntity.ok(user);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<User> updateUser(@PathVariable final long id,
                                           @RequestBody final JsonPatch patch) {
        try {
            User user = Optional
                    .ofNullable(userRepository
                            .findById(id)
                            .orElseThrow(UserNotFoundException::new))
                    .orElseThrow();
            user = applyPatchToUser(patch, user);
            userRepository.save(user);

            return ResponseEntity.ok(user);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        } catch (CompanyNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable final long id) {
        try {
            User user = Optional
                    .ofNullable(userRepository
                            .findById(id)
                            .orElseThrow(UserNotFoundException::new))
                    .orElseThrow();
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        } catch (CompanyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private User applyPatchToUser(JsonPatch patch, User targetUser)
            throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch
                .apply(objectMapper
                        .convertValue(targetUser, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }

}
