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