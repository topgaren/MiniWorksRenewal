
/**
 * textarea를 생성하여 반환한다.
 * 생성된 textarea는 화면에서 다음과 같이 출력된다.
 * "______________"
 *
 * @param {Boolean} isInline : textarea를 json "key"와 수평으로 배치 여부.
 * @param {Boolean} inList : List 내부에 생성될 textarea인지 여부.
 * @param {String} name : 파라미터의 이름.
 */
function makeTextArea(isInline, inList, name) {

    var div = document.createElement('div');
    if(isInline) {
        div.style.display = 'inline';
    }

    var spanOpenQuote = document.createElement('span');
    var spanCloseQuote = document.createElement('span');
    spanOpenQuote.innerText = '"';
    spanCloseQuote.innerText = '"';

    var textArea = document.createElement('textarea');
    textArea.className = "requestParameter";
    textArea.name = name;
    textArea.style.padding = '0px';
    textArea.style.width = '250px';
    textArea.style.height = '16px';
    textArea.style.fontSize = '14px';
    textArea.style.fontFamily = 'Consolas,monaco,monospace';
    textArea.style.border = 'none';
    textArea.style.borderBottom = '1px solid #ccc';

    div.append(spanOpenQuote, textArea, spanCloseQuote);

    // List 내부에 생성된다면 삭제 아이콘을 추가하여 반환한다.
    if(inList) {
        var divOuter = document.createElement('div');
        var iconDelete = document.createElement('img');
        iconDelete.className = 'icon icon-delete';
        iconDelete.src = '/img/Document/X.png';
        divOuter.append(div, iconDelete);
        iconDelete.setAttribute("onclick", "iconDeleteClickEventHandler()");

        return divOuter;
    }

    return div;
}

/**
 * 필드를 입력받을 수 있는 화면 구성 DOM 객체를 생성한다.
 * 필드의 경우는 다음 4가지 중 하나에 해당한다.
 *   Case 1 : List도 Model도 아닌 일반 필드.
 *   Case 2 : 일반 필드의 List.
 *   Case 3 : 단순 (Nested) Model.
 *   Case 4 : (Nested) Model의 List.
 *
 * @param {com.works.document.FieldInfo} field : 필드 정보를 갖는 객체.
 * @param {java.util.List<com.works.document.DTOInfo>} allDTOInfoList : 모든 DTOInfo 정보를 갖고 있는 리스트.
 */
function makeFieldInput(field, allDTOInfoList) {

    var div = document.createElement('div');
    div.setAttribute("name", field.parameter);

    // "[parameter]" :
    var spanParameter = document.createElement('span');
    spanParameter.innerText = '"' + field.parameter + '"';
    spanParameter.style.fontFamily = 'Consolas,monaco,monospace'
    spanParameter.style.fontSize = '14px';
    var spanColon = document.createElement('span');
    spanColon.innerText = " : ";
    div.append(spanParameter, spanColon);

    // 아이콘 생성
    var iconQuestion = document.createElement('img');
    iconQuestion.className = 'icon icon-question';
    iconQuestion.style.paddingLeft = '4px';
    iconQuestion.src = '/img/Document/Q.png';

    var iconDelete = document.createElement('img');
    iconDelete.className = 'icon icon-delete';
    iconDelete.style.paddingLeft = '4px';
    iconDelete.src = '/img/Document/X.png';
    iconDelete.setAttribute("onclick", "iconDeleteClickEventHandler()");

    if(field.list == false) {
        if(field.model == false) {
            // Case 1: 리스트도 아니고 Model도 아닌 일반 필드
            div.className = 'field-input';
            var textArea = makeTextArea(true, false, field.parameter);
            div.append(textArea, iconQuestion, iconDelete);
        } else {
            // Case 2: 단일 (Nested) Model 추가
            div.className = 'model-input';
            div.append(iconQuestion, iconDelete);
            var nestedModelDTO = getDTOInfoByName(field.type, allDTOInfoList);
            div.append(makeModelInput(nestedModelDTO, allDTOInfoList, false));
        }
    } else {
        div.className = 'list-input';
        if(field.model == false) {
            // Case 3: 단순 필드 리스트
            div.append(iconQuestion, iconDelete);
            div.append(makeListInput());
        } else {
            // Case 4: (Nested) Model의 리스트
            div.append(iconQuestion, iconDelete);
            var nestedModelDTO = getDTOInfoByName(field.type, allDTOInfoList);
            div.append(makeListInput(nestedModelDTO, allDTOInfoList));

        }
    }

    return div;
}

/**
 * Model을 JSON 형태로 입력받을 수 있는 DOM 객체를 생성한다.
 *
 * @param {com.works.document.DTOInfo} model : 모델 정보를 갖는 객체.
 * @param {java.util.List<com.works.document.DTOInfo>} allDTOInfoList : 모든 DTOInfo 정보를 갖고 있는 리스트.
 * @param {Boolean} inList : 모델을 리스트에 추가하는지 여부.
 */
function makeModelInput(model, allDTOInfoList, inList) {

    var fieldList = model.fieldInfoList;

    // JSON의 시작을 나타내는 Open Bracket.
    var divOuter = document.createElement('div');
    divOuter.className = 'model-input';
    divOuter.innerText = '{ ';

    // 객체 내 필드의 들여쓰기(20px)를 위한 div 태그.
    var divInner = document.createElement('div');
    divInner.style.paddingLeft = '20px';
    divOuter.append(divInner);

    // 모델 내 모든 필드를 조회하며 required가 true인 필드를 대상으로만 입력이 가능한 태그를 생성.
    for(var i = 0; i < fieldList.length; i++) {
        var fieldInfo = fieldList[i];
        if(fieldInfo.required == true) {
            divInner.append(makeFieldInput(fieldList[i], allDTOInfoList));
        }
    }

    // JSON의 끝을 나타내는 Close Bracket.
    var modelCloseBracket = document.createElement('div');
    modelCloseBracket.innerText = '}';
    divOuter.append(modelCloseBracket);

    if(inList) {
        var iconDelete = document.createElement('img');
        iconDelete.src = '/img/Document/X.png';
        iconDelete.setAttribute("onclick", "iconDeleteClickEventHandler()");
        divOuter.append(iconDelete);
    }

    var select = makeSelectDropbox(model, allDTOInfoList);
    divInner.append(select);

    return divOuter;
}

/**
 * 삭제 버튼 클릭 이벤트 핸들러
 */
function iconDeleteClickEventHandler() {
    // 삭제 대상 DOM과 삭제 대상 필드명을 가져온다.
    var deleteTarget = event.target.parentElement;
    var deletedFieldName = deleteTarget.getAttribute('name');

    // 삭제 필드명으로 새로운 옵션 DOM을 생성한다.
    var option = document.createElement('option');
    option.value = deletedFieldName;
    option.innerText = deletedFieldName;

    // select DOM에 옵션 DOM을 추가시킨다.
    var select = deleteTarget.parentElement.lastElementChild;
    select.append(option);

    // DOM 삭제 수행.
    deleteTarget.remove();

    // Select Dropbox가 지워진 상태라면 다시 생성한다.
    if(select.style.display == 'none') {
        select.style.display = 'block';
    }
}

/**
 * List를 JSON 형태로 입력받을 수 있는 DOM 객체를 생성한다.
 *
 * @param {com.works.document.DTOInfo} model : Model의 List를 생성할 경우 해당 model을 전달.
 * @param {java.util.List<com.works.document.DTOInfo>} allDTOInfoList : 모든 DTOInfo 정보를 갖고 있는 리스트.
 */
function makeListInput(model, allDTOInfoList) {

    // JSON 리스트의 시작을 나타내는 Open Bracket.
    var divOuter = document.createElement('div');
    divOuter.innerText = '[ ';

    // 객체 내 필드의 들여쓰기(20px)를 위한 div 태그.
    var divInner = document.createElement('div');
    divInner.style.paddingLeft = '20px';
    divOuter.append(divInner);

    if(model) {
        // 파라미터 model을 전달할 경우에만 Model Input 생성
        var modelInput = makeModelInput(model, allDTOInfoList, true);
        divInner.append(modelInput);
    } else {
        // 파라미터 model을 전달하지 않으면 일반 textarea 생성
        var textArea = makeTextArea(false, true); // Nonamed
        divInner.append(textArea);
    }

    // Plus 버튼 생성
    var iconPlus = document.createElement('img');
    iconPlus.className = 'icon icon-plus';
    iconPlus.src = '/img/Document/Plus.png';
    divInner.append(iconPlus);

    if(model) {
        iconPlus.onclick = function(event) {
            var modelInput = makeModelInput(model, allDTOInfoList, true);
            event.target.before(modelInput);
        }
    } else {
        iconPlus.onclick = function(event) {
            var textArea = makeTextArea(false, true);
            event.target.before(textArea);
        }
    }

    // JSON 리스트의 끝을 나타내는 Close Bracket.
    var listCloseBracket = document.createElement('div');
    listCloseBracket.innerText = ']';
    divOuter.append(listCloseBracket);

    return divOuter;
}


function makeSelectDropbox(model, allDTOInfoList) {

    var select = document.createElement('select');
    select.className = 'select ' + model.simpleModelName;

    // Select Dropbox의 제목을 담당할 Option
    var addProperty = document.createElement('option');
    addProperty.innerText = '-- add property --';
    addProperty.selected = 'selected';
    addProperty.disabled = 'disabled';
    select.append(addProperty);

    // 모델 내의 필드를 조회하며 required가 false인 필드만 Option에 추가시킨다.
    for(var i = 0; i < model.fieldInfoList.length; i++) {
        var fieldInfo = model.fieldInfoList[i];
        if(fieldInfo.required == false) {
            var option = document.createElement('option');
            option.value = fieldInfo.parameter;
            option.innerText = fieldInfo.parameter;
            select.append(option);
        }
    }

    // Select Dropbox 내부의 Option을 클릭할 때의 이벤트 핸들러.
    select.onchange = function() {
        // 선택한 Option의 인덱스를 가져온다. 인덱스 0은 제목 옵션을 의미.
        var optionIndex = this.selectedIndex;

        // 제목 옵션을 선택한 경우가 아니라면
        if(optionIndex > 0) {
            // 해당 옵션의 파라미터 이름을 저장한 뒤 옵션 삭제.
            var targetOption = this.options[optionIndex];
            var fieldName = targetOption.value;
            targetOption.remove();

            // 제목 옵션으로 포커싱.
            this.options[0].selected = 'selected';

            // 제목 옵션 외에 남아있지 않다면 Select Dropbox를 지운다.
            if(select.childElementCount == 1) {
                select.style.display = 'none';
            }

            for(var i = 0; i < model.fieldInfoList.length; i++) {
                fieldInfo = model.fieldInfoList[i];
                if(fieldName == fieldInfo.parameter) {
                    var fieldInput = makeFieldInput(fieldInfo, allDTOInfoList);
                    this.before(fieldInput);
                    break;
                }
            }
        }

    }

    return select;
}