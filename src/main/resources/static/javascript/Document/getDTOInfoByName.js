
/**
 * DTO 이름을 전달하여 allDTOInfoList에서 일치하는 이름의 DTOInfo를 찾아 반환한다.
 * 일치하는 DTOInfo가 존재하지 않으면 null을 반환한다.
 *
 * @param {String} dtoName : 찾고자하는 DTO 이름.
 * @param {java.util.List<com.works.document.DTOInfo>} allDTOInfoList : 모든 DTOInfo 정보를 갖고 있는 리스트.
 */
function getDTOInfoByName(dtoName, allDTOInfoList) {

    var resultDTOInfo = null;

    for(var i = 0; i < allDTOInfoList.length; i++) {
        if(allDTOInfoList[i].modelName == dtoName) {
            resultDTOInfo = allDTOInfoList[i];
            break;
        }
    }

    return resultDTOInfo;
}