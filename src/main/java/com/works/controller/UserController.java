package com.works.controller;

import com.works.annotation.DescriptionMethod;
import com.works.dto.UserRequestCreateDTO;
import com.works.dto.UserRequestUpdateDTO;
import com.works.dto.UserResponseDTO;
import com.works.entity.UserEntity;
import com.works.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
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
    @GetMapping("/domains/{domainId}/users/{userExternalKey}")
    @DescriptionMethod(apiNameKorVer = "구성원 단건 조회.", description = "단일 구성원의 정보를 조회한다.")
    public UserResponseDTO getUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey) throws Exception {

        return userService.getUserDTO(domainId, userExternalKey);
    }

    /**
     * 구성원 추가.
     *
     * @param domainId : 구성원이 속한 도메인 아이디.
     * @param userExternalKey : 구성원의 외부키.
     * @param userRequestDTO : 추가할 구성원 객체.
     */
    @PostMapping("/domains/{domainId}/users/{userExternalKey}")
    @DescriptionMethod(apiNameKorVer = "구성원 추가.", description = "구성원 정보를 새로 추가한다.")
    public void insertUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey,
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
    @PutMapping("/domains/{domainId}/users/{userExternalKey}")
    @DescriptionMethod(apiNameKorVer = "구성원 수정", description = "구성원 정보를 수정한다. 전달되지 않은 정보는 삭제된다.")
    public void updateAllUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey,
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
    @PatchMapping("/domains/{domainId}/users/{userExternalKey}")
    @DescriptionMethod(apiNameKorVer = "구성원 부분 수정", description = "구성원 정보를 수정한다. 전달되지 않은 정보는 수정하지 않는다.")
    public void updatePartUserDTO(@PathVariable int domainId, @PathVariable String userExternalKey,
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
    @DeleteMapping("/domains/{domainId}/users/{userExternalKey}")
    @DescriptionMethod(apiNameKorVer = "구성원 삭제", description = "구성원 정보를 삭제한다.")
    public void deleteUser(@PathVariable int domainId, @PathVariable String userExternalKey) throws Exception {

        userService.deleteUser(domainId, userExternalKey);
    }
}
