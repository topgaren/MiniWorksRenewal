function appendModel(dtoModel, targetDOM) {

    var fieldInfoList = dtoModel.fieldInfoList;

    var modelInput = document.createElement('div');
    modelInput.innerText = "{ ";

    var modelInnerPadding = document.createElement('div');
    modelInnerPadding.style.paddingLeft = '20px';
    modelInput.append(modelInnerPadding);

    for(var i = 0; i < fieldInfoList.length; i++) {
        appendField(fieldInfoList[i], modelInnerPadding);
    }

    var modelCloseBracket = document.createElement('div');
    modelCloseBracket.innerText = '}';
    modelInput.append(modelCloseBracket);

    targetDOM.append(modelInput);
}


function appendField(field, targetDOM) {
    var fieldInput = document.createElement('div');

    var paramName = document.createElement('span');
    paramName.innerText = '"' + field.parameter + '"';
    paramName.style.fontSize = '14px';
    paramName.style.fontFamily = 'Consolas,monaco,monospace';
    fieldInput.append(paramName);

    var colon = document.createElement('span');
    colon.innerText = ' : ';
    fieldInput.append(colon);

    var textAreaWithQuotes = document.createElement('div')
    textAreaWithQuotes.style.display = 'inline';

    var openQuote = document.createElement('span');
    openQuote.innerText = '"';
    textAreaWithQuotes.append(openQuote);

    var fieldTextArea = document.createElement('textarea');
    fieldTextArea.className = 'requestParameter';
    fieldTextArea.setAttribute('name', field.parameter)
    fieldTextArea.style.padding = '0px';
    fieldTextArea.style.width = '250px';
    fieldTextArea.style.height = '16px';
    fieldTextArea.style.fontSize = '14px';
    fieldTextArea.style.fontFamily = 'Consolas,monaco,monospace';
    fieldTextArea.style.border = 'none';
    fieldTextArea.style.borderBottom = '1px solid #ccc';
    textAreaWithQuotes.append(fieldTextArea);

    var closeQuote = document.createElement('span');
    closeQuote.innerText = '"';
    textAreaWithQuotes.append(closeQuote);

    fieldInput.append(textAreaWithQuotes);

    var questionIcon = document.createElement('img');
    questionIcon.className = 'question-icon';
    questionIcon.id = field.parameter + 'QuesionIcon';
    questionIcon.src = '/img/Q.png';
    questionIcon.style.marginLeft = '2px';
    fieldInput.append(questionIcon);

    var deleteIcon = document.createElement('img');
    deleteIcon.className = 'delete-icon';
    deleteIcon.id = field.parameter + 'DeleteIcon';
    deleteIcon.src = '/img/X.png';
    deleteIcon.style.marginLeft = '2px';
    fieldInput.append(deleteIcon);

    targetDOM.append(fieldInput);
}