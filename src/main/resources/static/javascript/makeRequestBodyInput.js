
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
        iconDelete.src = '/img/X.png';
        divOuter.append(div, iconDelete);
        iconDelete.onclick = function() {
            var deleteTarget = event.target.parentElement;
            deleteTarget.remove();
        }

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
    iconQuestion.src = '/img/Q.png';

    var iconDelete = document.createElement('img');
    iconDelete.className = 'icon icon-delete';
    iconDelete.style.paddingLeft = '4px';
    iconDelete.src = '/img/X.png';
    iconDelete.onclick = function() {
        var deleteTarget = event.target.parentElement;
        deleteTarget.remove();
    }

    iconDelete.setAttribute("onclick", "deleteIconClickEvent()");

    if(field.list == false) {
        if(field.model == false) {
            // Case 1: 리스트도 아니고 Model도 아닌 일반 필드
            var textArea = makeTextArea(true, false, field.parameter);
            div.append(textArea, iconQuestion, iconDelete);
        } else {
            // Case 2: 단일 (Nested) Model 추가
            div.append(iconQuestion, iconDelete);

            var nestedModelDTO = getDTOInfoByName(field.type, allDTOInfoList);
            div.append(makeModelInput(nestedModelDTO, allDTOInfoList, false));
        }
    } else {
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
    divOuter.innerText = '{ ';

    // 객체 내 필드의 들여쓰기(20px)를 위한 div 태그.
    var divInner = document.createElement('div');
    divInner.style.paddingLeft = '20px';
    divOuter.append(divInner);

    // 모델 내 모든 필드를 조회하며 입력이 가능한 태그를 추가.
    for(var i = 0; i < fieldList.length; i++) {
        divInner.append(makeFieldInput(fieldList[i], allDTOInfoList));
    }

    // JSON의 끝을 나타내는 Close Bracket.
    var modelCloseBracket = document.createElement('div');
    modelCloseBracket.innerText = '}';
    divOuter.append(modelCloseBracket);

    if(inList) {
        var iconDelete = document.createElement('img');
        iconDelete.src = '/img/X.png';
        divOuter.append(iconDelete);

        iconDelete.onclick = function() {
            var deleteTarget = event.target.parentElement;
            deleteTarget.remove();
        }
    }

    return divOuter;
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
    iconPlus.src = '/img/Plus.png';
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