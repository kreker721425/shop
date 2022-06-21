package com.github.kreker721425.shop.service.impl;

import com.github.kreker721425.shop.db.enums.OperationEnum;
import com.github.kreker721425.shop.db.enums.TableEnum;
import com.github.kreker721425.shop.db.enums.UserStatusEnum;
import com.github.kreker721425.shop.db.tables.pojos.Users;
import com.github.kreker721425.shop.dto.HistoryDto;
import com.github.kreker721425.shop.dto.UserDto;
import com.github.kreker721425.shop.exception.ObjectNotFoundException;
import com.github.kreker721425.shop.mapper.UserMapper;
import com.github.kreker721425.shop.repository.UserRepository;
import com.github.kreker721425.shop.repository.filter.Filter;
import com.github.kreker721425.shop.repository.filter.UserFilter;
import com.github.kreker721425.shop.service.HistoryService;
import com.github.kreker721425.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HistoryService historyService;

    private Users updateUser(Users user) {
        userRepository.update(user);
        return userRepository.findById(user.getId());
    }

    private Users findUserById(Long id) {
        var user = userRepository.findById(id);
        if (user == null) {
            throw new ObjectNotFoundException(String.format("Пользователь не найден. id = %s", id));
        }
        return user;
    }

    @Override
    public UserDto findUserByLogin(String login) {
        var users = userRepository.fetchByLogin(login);
        if (CollectionUtils.isEmpty(users)) {
            throw new ObjectNotFoundException(String.format("Пользователь не найден. login = %s", login));
        }
        return userMapper.map(users.get(0));
    }

    @Override
    public UserDto activateUser(Long id) {
        var user = findUserById(id);
        user.setStatus(UserStatusEnum.ACTIVE);
        return userMapper.map(updateUser(user));
    }

    @Override
    public UserDto deactivateUser(Long id) {
        var user = findUserById(id);
        user.setStatus(UserStatusEnum.DISABLED);
        return userMapper.map(updateUser(user));
    }

    @Override
    public UserDto add(UserDto type) {
        if (Objects.nonNull(type.getId())) {
            return update(type.getId(), type);
        }
        var user = userMapper.mapToEntity(type);
        userRepository.insert(user);

        var userDto = userMapper.map(user);
        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.USER)
                .operation(OperationEnum.ADD)
                .newValue(userDto.toString())
                .build());
        return userDto;
    }

    @Override
    public UserDto get(Long id) {
        var user = userRepository.findById(id);
        if (user == null) {
            throw new ObjectNotFoundException(String.format("Клиент не найден. id = %s", id));
        }
        return userMapper.map(user);
    }

    @Override
    public UserDto update(Long id, UserDto edit) {
        var newUser = userMapper.mapToEntity(edit);
        newUser.setId(id);
        var oldUserDto = get(id);
        var newUserDto = userMapper.map(newUser);
        userRepository.update(newUser);

        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.USER)
                .operation(OperationEnum.PUT)
                .oldValue(oldUserDto.toString())
                .newValue(newUserDto.toString())
                .build());

        return userMapper.map(newUser);
    }

    @Override
    public void delete(Long id) {
        var user = get(id);
        userRepository.deleteById(id);

        historyService.add(HistoryDto.builder()
                .tableName(TableEnum.USER)
                .operation(OperationEnum.DELETE)
                .oldValue(user.toString())
                .build());
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.map(userRepository.findAll());
    }

    @Override
    public List<UserDto> search(UserFilter filter) {
        return userMapper.map(userRepository.search(filter));
    }
}
