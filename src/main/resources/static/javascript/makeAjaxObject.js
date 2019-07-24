function getAjaxObject(requestURL, httpMethod, requestData) {

    var ajaxObject = {
        url: requestURL,
        type: httpMethod,
        dataType: 'json'
    };

    if(httpMethod == 'GET') {
        ajaxObject['success'] = function(data) {
            $('#responseBodyTextArea').val(JSON.stringify(data, null, 6));
        }
    }

    if(httpMethod == 'POST' || httpMethod == 'PUT' || httpMethod == 'PATCH' || httpMethod == 'DELETE') {
        ajaxObject['data'] = JSON.stringify(requestData);
        ajaxObject['contentType'] = 'application/json';
        ajaxObject['success'] = function(data, textStatus, jqXHR) {
            $('#responseBodyTextArea').val(jqXHR.status);
        }
    }

    ajaxObject['error'] = function(data) {
        $("#responseBodyTextArea").val(JSON.stringify(data.responseJSON, null, 6));
    }

    return ajaxObject;
}