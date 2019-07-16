package com.works.entity;

import com.works.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

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

    public void updateUser(UserEntity addedUserEntity) {
        // addUser의 필드가 0 또는 null 값인 경우 기존 값을 그대로 사용하도록
        // User 필드 추가/삭제 시 해당 코드도 변경해야하므로 유지 및 보수에 적절하지 않아보임.
        // Reflection을 사용할 수 있는지 고려해볼것.
        userId = addedUserEntity.getUserId() == 0 ? userId : addedUserEntity.getUserId();
        domainId = addedUserEntity.getDomainId() == 0 ? domainId : addedUserEntity.getDomainId();
        account = addedUserEntity.getAccount() == null ? account : addedUserEntity.getAccount();
        userName = addedUserEntity.getUserName() == null ? userName : addedUserEntity.getUserName();
        userExternalKey = addedUserEntity.getUserExternalKey() == null ? userExternalKey : addedUserEntity.getUserExternalKey();
        authentication = addedUserEntity.getAuthentication() == 0 ? authentication : addedUserEntity.getAuthentication();
        cellPhone = addedUserEntity.getCellPhone() == null ? cellPhone : addedUserEntity.getCellPhone();
        email = addedUserEntity.getEmail() == null ? email : addedUserEntity.getEmail();
        corporationAddress = addedUserEntity.getCorporationAddress() == null ? corporationAddress : addedUserEntity.getCorporationAddress();
        hireDate = addedUserEntity.getHireDate() == null ? hireDate : addedUserEntity.getHireDate();
    }
}
