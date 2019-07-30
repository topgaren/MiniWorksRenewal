function makeAjaxObject(requestURL, httpMethod, requestData) {

    var ajaxObject = {
        url: requestURL,
        type: httpMethod,
    };

    if(httpMethod == 'GET') {
        ajaxObject['success'] = function(data) {
            $('#responseBodyTextArea').val(JSON.stringify(data, null, 3));
        }
    }

    if(httpMethod == 'POST' || httpMethod == 'PUT' || httpMethod == 'PATCH' || httpMethod == 'DELETE') {
        ajaxObject['data'] = JSON.stringify(requestData);
        ajaxObject['contentType'] = 'application/json';
        ajaxObject['success'] = function(data, textStatus, jqXHR) {
            var responseObject = jqXHR;
            delete(responseObject.readyState);
            delete(responseObject.responseText);
            $('#responseBodyTextArea').val(JSON.stringify(responseObject, null, 3));
        }
    }

    ajaxObject['error'] = function(data) {
        $("#responseBodyTextArea").val(JSON.stringify(data.responseJSON, null, 3));
    }

    return ajaxObject;
}