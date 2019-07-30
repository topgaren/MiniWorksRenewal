package com.works.controller;

import com.works.annotation.DescriptionAPI;
import com.works.annotation.DescriptionAPIGroup;
import com.works.annotation.DescriptionPathParam;
import com.works.dto.UserRequestCreateDTO;
import com.works.dto.UserRequestUpdateDTO;
import com.works.dto.UserResponseDTO;
import com.works.entity.UserEntity;
import com.works.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@DescriptionAPIGroup(apiGroupName = "User", apiGroupCode = 1)
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 구성원 단건 조회.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원의 외부키.
     * @return : 조회한 UserResponseDTO 객체.
     */
    @RequestMapping(value = "/organization/domains/{domainId}/users/{userExternalKey}", method = RequestMethod.GET)
    @DescriptionAPI(apiNameKorVer = "구성원 단건 조회", description = "단일 구성원의 정보를 조회한다.", apiCode = 1001)
    public UserResponseDTO getUserDTO(
            @PathVariable(name = "domainId") @DescriptionPathParam("도메인 아이디") int domainId,
            @PathVariable(name = "userExternalKey") @DescriptionPathParam("구성원 외부키") String userExternalKey) throws Exception {

        return userService.getUserDTO(domainId, userExternalKey);
    }

    /**
     * 구성원 추가.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원의 외부키.
     * @param userRequestDTO : 추가할 구성원 객체.
     */
    @RequestMapping(value = "/organization/domains/{domainId}/users/{userExternalKey}", method = RequestMethod.POST)
    @DescriptionAPI(apiNameKorVer = "구성원 추가", description = "구성원 정보를 새로 추가한다.", apiCode = 1002)
    public void insertUserDTO(
            @PathVariable(name = "domainId") @DescriptionPathParam("도메인 아이디") int domainId,
            @PathVariable(name = "userExternalKey") @DescriptionPathParam("구성원 외부키") String userExternalKey,
            @RequestBody UserRequestCreateDTO userRequestDTO) throws Exception {

        UserEntity userRequestEntity = userRequestDTO.toUserEntity();
        userRequestEntity.setDomainId(domainId);
        userRequestEntity.setUserExternalKey(userExternalKey);

        userService.insertUserDTO(userRequestEntity);
    }

    /**
     * 구성원 정보 전체 수정.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원의 외부키.
     * @param userRequestDTO : 구성원 수정 정보를 갖고 있는 객체.
    */
    @RequestMapping(value = "/organization/domains/{domainId}/users/{userExternalKey}", method = RequestMethod.PUT)
    @DescriptionAPI(apiNameKorVer = "구성원 수정", description = "구성원 정보를 수정한다. 전달되지 않은 정보는 삭제된다.", apiCode = 1003)
    public void updateAllUserDTO(
            @PathVariable(name = "domainId") @DescriptionPathParam("도메인 아이디") int domainId,
            @PathVariable(name = "userExternalKey") @DescriptionPathParam("구성원 외부키") String userExternalKey,
            @RequestBody UserRequestUpdateDTO userRequestDTO) throws Exception {

        UserEntity userRequestEntity = userRequestDTO.toUserEntity();
        userRequestEntity.setDomainId(domainId);
        userRequestEntity.setUserExternalKey(userExternalKey);

        userService.updateAllUserDTO(userRequestEntity);

    }

    /**
     * 구성원 정보 부분 수정.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원의 외부키.
     * @param userRequestDTO : 구성원 수정 정보를 갖고 있는 객체.
     */
    @RequestMapping(value = "/organization/domains/{domainId}/users/{userExternalKey}", method = RequestMethod.PATCH)
    @DescriptionAPI(apiNameKorVer = "구성원 부분 수정", description = "구성원 정보를 수정한다. 전달되지 않은 정보는 수정하지 않는다.", apiCode = 1004)
    public void updatePartUserDTO(
            @PathVariable(name = "domainId") @DescriptionPathParam("도메인 아이디") int domainId,
            @PathVariable(name = "userExternalKey") @DescriptionPathParam("구성원 외부키") String userExternalKey,
            @RequestBody UserRequestUpdateDTO userRequestDTO) throws Exception {

        UserEntity userRequestEntity = userRequestDTO.toUserEntity();
        userRequestEntity.setDomainId(domainId);
        userRequestEntity.setUserExternalKey(userExternalKey);

        userService.updatePartUserDTO(userRequestEntity);
    }

    /**
     * 구성원 정보 삭제.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원의 외부키.
     */
    @RequestMapping(value = "/organization/domains/{domainId}/users/{userExternalKey}", method = RequestMethod.DELETE)
    @DescriptionAPI(apiNameKorVer = "구성원 삭제", description = "구성원 정보를 삭제한다.", apiCode = 1005)
    public void deleteUser(
            @PathVariable(name = "domainId") @DescriptionPathParam("도메인 아이디") int domainId,
            @PathVariable(name = "userExternalKey") @DescriptionPathParam("구성원 외부키") String userExternalKey) throws Exception {

        userService.deleteUser(domainId, userExternalKey);
    }
}
