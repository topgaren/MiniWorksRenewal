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
 *  Document에 출력시킬 객체의 DTOInfo의 FieldInfo의 리스트를 생성한다.
 *
 * @param {com.works.document.DTOInfo} modelDTOInfo : Document에 출력시킬 객체의 DTOInfo.
 * @param {String} nestedModelPrefix : Nested Model일 경우 각 필드 이름 앞에 추가할 외부 Model 이름.
 */
function makeFieldInfoListFromModel(modelDTOInfo, nestedModelPrefix) {

    // 결과 리스트
    var resultFieldInfoList = [];

    for(var i = 0; i < modelDTOInfo.fieldInfoList.length; i++) {
        var fieldInfo = modelDTOInfo.fieldInfoList[i];

        // Field가 Nested Model인 경우 필드 이름 앞에 외부 Model의 이름을 추가.
        var fieldName = fieldInfo.parameter;
        if(nestedModelPrefix) {
            fieldName = nestedModelPrefix.concat(fieldInfo.parameter);
        }

        // Filed가 List 타입인 경우 필드 이름 뒤에 '[]'를 추가.
        if(fieldInfo.list) {
            fieldName = fieldName.concat('[]');
        }

        // Field의 이름을 새로 갱신.
        fieldInfo.parameter = fieldName;

        // Field가 Nested Model인 경우 타입명을 'Nested Object'로 변경.
        if(fieldInfo.model) {
            fieldInfo.simpleType = 'Nested Object';
        }

        // 갱신된 FieldInfo 객체를 결과 리스트에 추가.
        resultFieldInfoList.push(fieldInfo);

        // 추가 작업 : Field가 Nested Model인 경우 해당 Model의 FieldInfoList 정보를 가져온다.
        if(fieldInfo.model) {
            var nestedModelDTO = modelDTOInfo.nestedDTOList[fieldInfo.nestedDTOIndex];
            var nestedModelDTOFieldInfoList = makeFieldInfoListFromModel(nestedModelDTO, fieldName.concat('.'));
            resultFieldInfoList = resultFieldInfoList.concat(nestedModelDTOFieldInfoList);
        }
    }

    return resultFieldInfoList;
}