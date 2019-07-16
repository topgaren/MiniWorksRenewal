package com.works.entity;

import com.works.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    private int userId;

    private int domainId;

    private String account;

    private String userName;

    private String userExternalKey;

    private int authentication;

    private String cellPhone;

    private String email;

    private String corporationAddress;

    private Date hireDate;

    public UserResponseDTO toUserDTO() {

        return new UserResponseDTO(domainId, account, userName, userExternalKey, authentication,
                cellPhone, email, corporationAddress, hireDate);
    }

    public void updateUser(User addedUser) {
        // addUser의 필드가 0 또는 null 값인 경우 기존 값을 그대로 사용하도록
        // User 필드 추가/삭제 시 해당 코드도 변경해야하므로 유지 및 보수에 적절하지 않아보임.
        // Reflection을 사용할 수 있는지 고려해볼것.
        userId = addedUser.getUserId() == 0 ? userId : addedUser.getUserId();
        domainId = addedUser.getDomainId() == 0 ? domainId : addedUser.getDomainId();
        account = addedUser.getAccount() == null ? account : addedUser.getAccount();
        userName = addedUser.getUserName() == null ? userName : addedUser.getUserName();
        userExternalKey = addedUser.getUserExternalKey() == null ? userExternalKey : addedUser.getUserExternalKey();
        authentication = addedUser.getAuthentication() == 0 ? authentication : addedUser.getAuthentication();
        cellPhone = addedUser.getCellPhone() == null ? cellPhone : addedUser.getCellPhone();
        email = addedUser.getEmail() == null ? email : addedUser.getEmail();
        corporationAddress = addedUser.getCorporationAddress() == null ? corporationAddress : addedUser.getCorporationAddress();
        hireDate = addedUser.getHireDate() == null ? hireDate : addedUser.getHireDate();
    }
}
