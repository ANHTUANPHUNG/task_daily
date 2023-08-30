package com.example.taskdaily.service.user;

import com.example.taskdaily.domain.Enums.EGender;
import com.example.taskdaily.domain.Profile;
import com.example.taskdaily.repositories.ProfileRepository;
import com.example.taskdaily.repositories.UserRepository;
import com.example.taskdaily.service.user.request.UserEditRequest;
import com.example.taskdaily.service.user.request.UserSaveRequest;
import com.example.taskdaily.service.user.response.UserListResponse;
import com.example.taskdaily.util.AppMessage;
import com.example.taskdaily.util.AppUtil;
import com.example.taskdaily.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    public void createUser(UserSaveRequest userSaveRequest){
        var user= AppUtil.mapper.map(userSaveRequest, User.class);
        var profile= AppUtil.mapper.map(userSaveRequest, Profile.class);
//        profile= profileRepository.save(profile);
        user.setProfile(profile);
        userRepository.save(user);
    }
    public List<UserListResponse> findAll(){
        return userRepository.findAll().stream().map(user -> {
            var result = new UserListResponse();
            result.setId(user.getId());
            result.setUsername(user.getUsername());
            result.setEmail(user.getEmail());
            result.setDob(user.getProfile().getDob());
            result.setGender(user.getProfile().getGender());
            result.setFullName(user.getProfile().getFullName());
            return result;
        }).toList();
    }
    public User findById(Long id){
        //de tai su dung
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format(AppMessage.ID_NOT_FOUND, "User", id)));
    }
    public void delete(Long id){
        User user = findById(id);
        userRepository.delete(user);
    }
    public void edit(UserEditRequest request, Long id) throws Exception{
        var userInDb = findById(id);
        if(!Objects.equals(userInDb.getPassword(), request.getOldPassword())){
            throw new Exception("Incorrect Password");
        }
        userInDb.setPassword(request.getPassword());
        userInDb.getProfile().setDob(LocalDate.parse(request.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        userInDb.getProfile().setGender(EGender.valueOf(request.getGender()));
        userInDb.getProfile().setFullName(request.getFullName());
        request.setId(id.toString());
        userRepository.save(userInDb);
    }

    public UserEditRequest showEditById(Long id){
        User user = findById(id);
        return userToUserEditRequest(user);
    }

    private UserEditRequest userToUserEditRequest(User user){
        var result = new UserEditRequest();
        result.setDob(String.valueOf(user.getProfile().getDob()));
        result.setGender(String.valueOf(user.getProfile().getGender()));
        result.setFullName(user.getProfile().getFullName());
        result.setId(String.valueOf(user.getId()));
        return result;
    }
}
