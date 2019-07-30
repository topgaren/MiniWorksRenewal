/**
 * FieldInfo 객체의 리스트를 입력받아 Document에 출력할 테이블을 생성한다.
 *
 * @param {java.util.List<com.works.document.FieldInfo>} parameterInfoList : 테이블로 만들 FieldInfo 객체의 리스트.
 * @param {Boolean} isRequired : Path Parameter 또는 Request Body 인지.
 * @param {String} tableId : 만들어질 테이블의 Id.
 */
function makeParameterTable(parameterInfoList, isRequired, tableId) {

    var font = document.createElement('font');
    font.setAttribute('size', '2');

    var table = document.createElement('table');
    table.style.marginTop = '0px';
    table.className = 'field-table';
    table.id = tableId;
    font.append(table);

    var thead = document.createElement('thead');
    table.append(thead);

    var theadTr = document.createElement('tr');
    thead.append(theadTr);

    var parameterTh = document.createElement('th');
    parameterTh.innerText = 'Parameter';
    parameterTh.style.width = isRequired ? "16%" : "20%";
    theadTr.append(parameterTh);

    var typeTh = document.createElement('th');
    typeTh.innerText = 'Type';
    typeTh.style.width = isRequired ? "16%" : "20%";
    theadTr.append(typeTh);

    if(isRequired == true) {
        var requiredTh = document.createElement('th');
        requiredTh.innerText = 'Required';
        requiredTh.style.width = "16%";
        theadTr.append(requiredTh);
    }

    var descriptionTh = document.createElement('th');
    descriptionTh.innerText = "Description";
    descriptionTh.style.width = isRequired ? "52%" : "60%";
    theadTr.append(descriptionTh);

    tbody = document.createElement('tbody');
    table.append(tbody);

    for(var i = 0; i < parameterInfoList.length; i++) {
        var tbodyTr = document.createElement('tr');
        tbody.append(tbodyTr);

        var parameterTd = document.createElement('td');
        parameterTd.innerText = parameterInfoList[i].parameter;
        tbodyTr.append(parameterTd);

        var typeTd = document.createElement('td');
        typeTd.innerText = parameterInfoList[i].simpleType;
        tbodyTr.append(typeTd);

        if(isRequired == true) {
            var requiredTd = document.createElement('td');
            requiredTd.innerText = parameterInfoList[i].required;
            tbodyTr.append(requiredTd);
        }

        var descriptionTd = document.createElement('td');
        descriptionTd.innerText = parameterInfoList[i].description;
        tbodyTr.append(descriptionTd);
    }

    return font;
}

/**
 * 인자로 전달한 DTOInfo를 비롯하여 Nested 형태의 DTOInfo를 리스트 형태로 반환한다.
 *
 * @param {com.works.document.DTOInfo} startDTOInfo : Nested DTOInfo 리스트의 최상위 DTOInfo.
 * @param {java.util.List<com.works.document.DTOInfo>} allDTOInfoList : 모든 DTOInfo 정보를 갖고 있는 리스트.
 */
function makeDTOInfoList(startDTOInfo, allDTOInfoList) {

    var resultDTOInfoList = [];
    resultDTOInfoList.push(startDTOInfo);

    for(var i = 0; i < startDTOInfo.fieldInfoList.length; i++) {
        var fieldInfo = startDTOInfo.fieldInfoList[i];
        if(fieldInfo.model) {
            var nestedDTOInfo = getDTOInfoByName(fieldInfo.type, allDTOInfoList);
            var nestedDTOInfoList = makeDTOInfoList(nestedDTOInfo, allDTOInfoList);
            resultDTOInfoList = resultDTOInfoList.concat(nestedDTOInfoList);
        }
    }

    return resultDTOInfoList;
}

/**
 * 리스트에 존재하는 모든 DTOInfo 정보를 이용하여 Document에 Table을 생성한다.
 *
 * @param {java.util.List<com.works.document.DTOInfo>} allDTOInfoList : 모든 DTOInfo 정보를 갖고 있는 리스트.
 * @param {Boolean} isRequired : Path Parameter 또는 Request Body 인지.
 */
function makeTableByDTOInfoList(dtoInfoList, isRequired) {

    var div = document.createElement('div');

    for(var i = 0; i < dtoInfoList.length; i++) {
        var dtoInfo = dtoInfoList[i];
        var dtoInfoDiv = document.createElement('div');
        dtoInfoDiv.style.marginTop = '20px';
        var tableName = document.createElement('div');
        tableName.innerText = dtoInfo.simpleModelName;
        tableName.style.fontSize = '14px';
        tableName.style.fontWeight = 'bold';

        var table = makeParameterTable(dtoInfo.fieldInfoList, isRequired, tableName);
        dtoInfoDiv.append(tableName, table);

        div.append(dtoInfoDiv);
    }

    return div;
}

