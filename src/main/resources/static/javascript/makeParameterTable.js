function makeParameterTable(parameterInfoList) {

    documentOutput = "";
    documentOutput += '<font size="2">';
    documentOutput += '    <table class="table table-striped table-bordered">';
    documentOutput += '        <thead>';
    documentOutput += '        <tr align="center">';
    documentOutput += '            <th>Parameter</th>';
    documentOutput += '            <th>Type</th>';
    documentOutput += '            <th>Required</th>';
    documentOutput += '            <th>Description</th>';
    documentOutput += '        </tr>';
    documentOutput += '        </thead>';
    documentOutput += '        <tbody>';
    for(var i = 0; i < parameterInfoList.length; i++) {
        documentOutput += '        <tr>';
        documentOutput += '            <td>' + parameterInfoList[i].parameter + '</td>';
        documentOutput += '            <td>' + parameterInfoList[i].simpleType + '</td>';
        documentOutput += '            <td>' + parameterInfoList[i].required + '</td>';
        documentOutput += '            <td>' + parameterInfoList[i].description + '</td>';
        documentOutput += '        </tr>';
    }
    documentOutput += '        </tbody>';
    documentOutput += '    </table">';
    documentOutput += '</font>';

    return documentOutput;
}