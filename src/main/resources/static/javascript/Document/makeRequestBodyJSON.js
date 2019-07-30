
/**
 * Request Body Trying Tool의 입력 내용을 JSON으로 변환시킨다.
 *
 * @param {DOM} targetDOM : 입력의 시작이 되는 model-input 클래스 DOM
 */
function makeRequestBodyJSON(targetDOM) {

    var resultJSON = null;

    var dataType = targetDOM.getAttribute('class');
    if(dataType == 'model-input') {

        resultJSON = {};

        var fieldDOMs = targetDOM.childNodes[1].childNodes;
        for(var i = 0; i < fieldDOMs.length - 1; i++) {
            var fieldDOM = fieldDOMs[i];
            var fieldName = fieldDOM.getAttribute('name');
            resultJSON[fieldName] = makeRequestBodyJSON(fieldDOM);
        }

    } else if(dataType == 'list-input') {

        resultJSON = [];

        var elementDOMs = targetDOM.childNodes[4].childNodes[1].childNodes;
        for(var i = 0; i < elementDOMs.length - 1; i++) {
            var elementDOM = elementDOMs[i];
            resultJSON.push(makeRequestBodyJSON(elementDOM));
        }

    } else if(dataType == 'field-input') {

        var textArea = targetDOM.childNodes[2].childNodes[1];
        resultJSON = textArea.value;

    } else {

        return null;
    }

    return resultJSON;
}