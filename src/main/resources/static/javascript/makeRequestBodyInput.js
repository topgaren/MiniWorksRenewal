function appendModel(dtoModel, targetDOM) {

    var fieldInfoList = dtoModel.fieldInfoList;

    var modelInput = document.createElement('div');
    modelInput.innerText = "{ ";

    var modelInnerPadding = document.createElement('div');
    modelInnerPadding.style.paddingLeft = '40px';
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
    fieldInput.append(paramName);

    var colon = document.createElement('span');
    colon.innerText = ' : ';
    fieldInput.append(colon);

    var textAreaWithQuotes = document.createElement('div')
    textAreaWithQuotes.style.display = 'inline';

    var openQuote = document.createElement('span');
    openQuote.innerText = '"';

    var fieldTextArea = document.createElement('textarea');
    fieldTextArea.className = 'requestParameter';
    fieldTextArea.setAttribute('name', field.parameter)
    fieldTextArea.style.padding = '0px';
    fieldTextArea.style.width = '250px';
    fieldTextArea.style.height = '16px';
    fieldTextArea.style.fontSize = '14px';
    fieldTextArea.style.border = 'none';
    fieldTextArea.style.borderBottom = '1px solid #ccc';

    var closeQuote = document.createElement('span');
    closeQuote.innerText = '"';

    textAreaWithQuotes.append(openQuote);
    textAreaWithQuotes.append(fieldTextArea);
    textAreaWithQuotes.append(closeQuote);

    fieldInput.append(textAreaWithQuotes);

    targetDOM.append(fieldInput);
}