package com.example.Velosity20.user.domain;

import com.example.Velosity20.user.db.UserEntity;
import com.example.Velosity20.user.db.UserMapper;
import com.example.Velosity20.user.db.UserRepository;
import com.example.Velosity20.user.dto.UserFilterDto;
import com.example.Velosity20.user.dto.UserRequestDto;
import com.example.Velosity20.user.dto.UserResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.mapper = userMapper;
    }

    public List<UserResponseDto> findAll(UserFilterDto filterDto) {

        int pageSize = filterDto.pageSize() != null ? filterDto.pageSize() : 100;
        int pageNumber = filterDto.pageNumber() != null ? filterDto.pageNumber() : 0;

        Pageable pageable = Pageable
                .ofSize(pageSize)
                .withPage(pageNumber);

        return userRepository.findAll(pageable).stream()
                .map(user -> mapper.toResponse(user))
                .toList();
    }

    public UserResponseDto findById(Long id) {
        return mapper.toResponse(userRepository.findById(id).orElseThrow(() -> new NoSuchElementException()));
    }

    public UserResponseDto createUser(UserRequestDto user) {
        UserEntity userToCreate = mapper.toEntity(user);
        return mapper.toResponse(userRepository.save(userToCreate));
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }

        userRepository.deleteById(id);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        if (userRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Element with id = " + id + " not found");
        }

        UserEntity userToUpdate = mapper.toEntity(user);
        userToUpdate.setId(id);
        return mapper.toResponse(userRepository.save(userToUpdate));
    }
}
