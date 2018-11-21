/*
 * jQuery File Upload Plugin JS Example
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */

$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
    	acceptFileTypes: /(\.|\/)(docx)$/i,
    	maxNumberOfFiles: 1,
    	messages: {
    	 	maxNumberOfFiles: '文件上传限制!',
    	    acceptFileTypes: '格式错误!'
    	},
    	processfail: function (e, data) {
    	    var currentFile = data.files[data.index];
    	    if (data.files.error && currentFile.error) {
    	        showMsg("上传出错!");
    	    }
    	},
        url: basepath + '/fileupload.do'
    });
    
    // Enable iframe cross-domain access via redirect option:
    /*$('#fileupload').fileupload({
    	send: function(e, data){
    	}
    });*/
    
    $('#fileupload').bind('fileuploaddestroy', function (e, data) {
    	$(data.context).remove();
    });
    $('#fileupload').bind('fileuploadchunkfail', function (e, data){
    	alert("df")
    });
  
    // Load existing files:
    $('#fileupload').addClass('fileupload-processing');
    $.ajax({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: basepath + '/loadExistFile.do',
        dataType: 'json',
        context: $('#fileupload')[0]
    }).always(function () {
        $(this).removeClass('fileupload-processing');
    }).done(function (result) {
        $(this).fileupload('option', 'done')
            .call(this, $.Event('done'), {result: result});
    });
    
    
});
