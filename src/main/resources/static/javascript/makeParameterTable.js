function makeParameterTable(parameterInfoList, isRequired, tableId) {

    var font = document.createElement('font');
    font.style.size = 2;

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
    theadTr.append(parameterTh);

    var typeTh = document.createElement('th');
    typeTh.innerText = 'Type';
    theadTr.append(typeTh);

    if(isRequired == true) {
        var requiredTh = document.createElement('th');
        requiredTh.innerText = 'Required';
        theadTr.append(requiredTh);
    }

    var descriptionTh = document.createElement('th');
    descriptionTh.innerText = "Description";
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
            var requiredTd = document.createElement('tr');
            requiredTd.innerText = parameterInfoList[i].required;
            tbodyTr.append(requiredTd);
        }

        var descriptionTd = document.createElement('td');
        descriptionTd.innerText = parameterInfoList[i].description;
        tbodyTr.append(descriptionTd);
    }

    return font;
}