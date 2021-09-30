package com.anhnc2.ehealicords.service.user;

import com.anhnc2.ehealicords.data.common.User;
import com.anhnc2.ehealicords.data.common.PresignResult;
import com.anhnc2.ehealicords.data.entity.UserRelativeEntity;
import com.anhnc2.ehealicords.data.response.BalanceResult;

import com.anhnc2.ehealicords.data.response.LiteUser;
import com.anhnc2.ehealicords.data.response.LiteUserRelative;
import com.anhnc2.ehealicords.data.response.PaginationResponse;
import java.util.List;

public interface UserService {
    void createRegisterCode(String phoneNumber);

    void createLoginCode(String phoneNumber);

    String register(String phoneNumber, String verifiedCode);

    String login(String phoneNumber, String verifyCode);

    User getCurrentPatientWithLocation();

    List<UserRelativeEntity> getAllRelatives();

    Long createNewRelative(UserRelativeEntity userRelativeEntity);

    void updateRelative(UserRelativeEntity userRelativeEntity);

    void updateInfo(long userId, User user);

    PresignResult getAvatarUpdateUrl(String fileName);

    void updateAvatar(String key);

    void deleteRelative(Long id);

    User getCurrentPatient();

    BalanceResult getBalanceOfUser(long userId);

    UserRelativeEntity getRelative(long relativeId);

    PaginationResponse<List<LiteUser>> getAllUsers(int page, int pageSize);

    User getUserDetailById(long userId);

    List<LiteUserRelative> getUserRelatives(long userId);

    PaginationResponse<List<LiteUserRelative>> getAllRelativeUsers(int page, int pageSize);
}
