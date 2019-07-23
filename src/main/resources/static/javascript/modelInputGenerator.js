function appendModel(model, containerDiv, nestedStage = 0) {
    var fList = model.fieldInfoList;
    var tabSize = 20;

    /***
    * HTML Structure
    * <div class="model-outer" style="margin-left=20px;">
    *   <div class = "model-open-bracket">{</div>
    *   <div class = "model-inner" style="margin-left=40px;">
    *       <div class = "field">
    *           <span>"domainId": </span>
    *           <span>"</span><span><textarea></textarea></span><span>"</span>
    *       </div>
    *       <div class = "field">
    *           <span>"domainId": </span>
    *           <span>"</span><span><textarea></textarea></span><span>"</span>
    *       </div>
    *       ...
    *   </div>
    * </div>
    ***/


    var modelOuterDiv = document.createElement('div');
    modelOuterDiv.className = 'model-outer';
    modelOuterDiv.style.marginLeft = (nestedStage * tabSize) + 'px';

    var modelOpenBracketDiv = document.createElement('div');
    modelOpenBracketDiv.className = 'model-open-bracket';
    modelOpenBracketDiv.textContent = "{";
    modelOuterDiv.append(modelOpenBracketDiv);

    var modelInnerDiv = document.createElement('div');
    modelInnerDiv.className = 'model-inner';
    modelInnerDiv.style.marginLeft = ((nestedStage + 1) * tabSize) + 'px';
    modelOuterDiv.append(modelInnerDiv);

    for(var i = 0; i < fList.length; i++) {
        var fieldDiv = document.createElement('div');
        fieldDiv.className = 'field-div' + i;

        var fieldNameSpan = document.createElement('span');
        fieldNameSpan.textContent = '"' + fList[i].parameter + '"' + ': ';

        var quoteOpenSpan = document.createElement('span');
        quoteOpenSpan.textContent = '"';
        var quoteCloseSpan = document.createElement('span');
        quoteCloseSpan.textContent = '"';

        var fieldInputSpan = document.createElement('span');
        var fieldInput = document.createElement('textarea');
        fieldInput.style.padding = 0;
        fieldInput.style.height = '16px';
        fieldInput.style.fontSize = '14px';
        fieldInput.style.border = 'none';
        fieldInputSpan.append(quoteOpenSpan);
        fieldInputSpan.append(fieldInput);
        fieldInputSpan.append(quoteCloseSpan);

        fieldDiv.append(fieldNameSpan);
        fieldDiv.append(fieldInputSpan);
        modelInnerDiv.append(fieldDiv);
    }

    var modelCloseBracketDiv = document.createElement('div');
    modelCloseBracketDiv.className = 'model-close-bracket';
    modelCloseBracketDiv.textContent = "}";
    modelOuterDiv.append(modelCloseBracketDiv);

    containerDiv.append(modelOuterDiv);
}