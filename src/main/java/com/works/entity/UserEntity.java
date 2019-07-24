package com.works.entity;

import com.works.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

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

    public void updateUser(UserEntity addedUserEntity) {
        // addUser의 필드가 0 또는 null 값인 경우 기존 값을 그대로 사용하도록
        // User 필드 추가/삭제 시 해당 코드도 변경해야하므로 유지 및 보수에 적절하지 않아보임.
        // Reflection을 사용할 수 있는지 고려해볼것.
        userId = StringUtils.isEmpty(addedUserEntity.getUserId()) ? userId : addedUserEntity.getUserId();
        domainId = addedUserEntity.getDomainId() == 0 ? domainId : addedUserEntity.getDomainId();
        account = StringUtils.isEmpty(addedUserEntity.getAccount()) ? account : addedUserEntity.getAccount();
        userName = StringUtils.isEmpty(addedUserEntity.getUserName()) ? userName : addedUserEntity.getUserName();
        userExternalKey = StringUtils.isEmpty(addedUserEntity.getUserExternalKey()) ? userExternalKey : addedUserEntity.getUserExternalKey();
        authentication = addedUserEntity.getAuthentication() == 0 ? authentication : addedUserEntity.getAuthentication();
        cellPhone = StringUtils.isEmpty(addedUserEntity.getCellPhone()) ? cellPhone : addedUserEntity.getCellPhone();
        email = StringUtils.isEmpty(addedUserEntity.getEmail()) ? email : addedUserEntity.getEmail();
        corporationAddress = StringUtils.isEmpty(addedUserEntity.getCorporationAddress()) ? corporationAddress : addedUserEntity.getCorporationAddress();
        hireDate = addedUserEntity.getHireDate() == null ? hireDate : addedUserEntity.getHireDate();
    }
}
