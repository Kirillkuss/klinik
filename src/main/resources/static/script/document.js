var protocol = window.location.protocol
var hostname = window.location.hostname;
var port = window.location.port;

/**
 * Поиск документов по параметрам
 */
function findByWordDocument() {
    $(document.getElementById("findByWordDocument")).on( "click",function(){
        var word = $('#wordFound').val();
        if(  word.length  == 0 ){
            $('tbody:even').empty();
            lazyDocument(1, 15);
            $('#errorToast').text( 'Значение поля поиск не может быть пустым' ).show();
            $('#liveToastBtn').click();
        }else{
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: protocol + "//"+ hostname + ':' + port + "/web/documents/find/{word}",
                data:{ word: word } ,
                cache: false,
                success: function( json ) {
                    var tr=[];
                    for (var i = 0; i < json.length; i++) {
                        var rowNumber = 1 + i;
                        tr.push('<tr>');
                        tr.push('<td>' + rowNumber + '</td>');
                        tr.push('<td>' + json[i].typeDocument + '</td>');
                        tr.push('<td>' + json[i].seria + '</td>');
                        tr.push('<td>' + json[i].numar + '</td>');
                        tr.push('<td>' + json[i].snils + '</td>');
                        tr.push('<td>' + json[i].polis + '</td>');
                        tr.push('</tr>');
                    }
                    $('tbody:even').empty();
                    $('table').prepend($(tr.join('')));
                }, error: function ( error ){
                    $('#errorToast').text( error.responseText ).show();
                    $('#liveToastBtn').click();
                }
            });
        }

    });	
};
/**
 * Список всех страниц
 */
function listDocument() {
    $(document).ready(function() {
    $('table tbody').on('mousedown', 'tr', function(e) {
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
    $.getJSON( protocol+ '//'+ hostname + port + '/web/documents/list', function(json) {
        var tr=[];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].idDocument + '</td>');
            tr.push('<td>' + json[i].typeDocument + '</td>');
            tr.push('<td>' + json[i].seria + '</td>');
            tr.push('<td>' + json[i].numar + '</td>');
            tr.push('<td>' + json[i].snils + '</td>');
            tr.push('<td>' + json[i].polis + '</td>');
            tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
    });
};
/**
 * Добавить документ
 */
function AddDocument() {
    $("#testFormDocument").submit( function (event){
        event.preventDefault();
        var idDocument =  $('#idDocument').val();
        var typeDocument = $('#typeDocument').val();
        var seria = $('#seria').val();
        var numar = $('#numar').val();
        var snils = $('#snils').val();
        var polis = $('#polis').val();
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "http://" + hostname + ':' + port + "/web/documents/add",
                data: JSON.stringify({typeDocument: typeDocument, seria: seria, numar: numar, snils: snils, polis: polis}),
                cache: false,
                success: function( json ) {
                    var tr=[];
                    tr.push('<tr>');
                    //tr.push('<td>' + json.idDocument + '</td>');
                    tr.push('<td>' + json.typeDocument + '</td>');
                    tr.push('<td>' + json.seria + '</td>');
                    tr.push('<td>' + json.numar + '</td>');
                    tr.push('<td>' + json.snils + '</td>');
                    tr.push('<td>' + json.polis + '</td>');
                    tr.push('</tr>');
                    $('table').append($(tr.join('')));

                    location.reload();
                }, error: function ( error ){
                    $('#errorToast').text( error.responseText ).show();
                    $('#liveToastBtn').click();
                }
            });
        });
    };
    /**
     * Ленивая загрука
     * @param {*} page - страница 
     * @param {*} size - размер
     */
    function lazyDocument( page, size) {
        $(document).ready(function() {
        $('table tbody').on('mousedown', 'tr', function(e) {
            $(this).addClass('highlight').siblings().removeClass('highlight');
        });
        $.getJSON(protocol + '//'+ hostname + ':' + port + '/web/documents/list/{page}{size}?page='+page+'&size='+size, function(json) {
            var tr=[];
            $('tbody').empty();
            var startIndex = (page - 1) * size + 1;
            for (var i = 0; i < json.length; i++) {
                var rowNumber = startIndex + i;
                tr.push('<tr>');
                tr.push('<td>' + rowNumber + '</td>');
                tr.push('<td>' + json[i].typeDocument + '</td>');
                tr.push('<td>' + json[i].seria + '</td>');
                tr.push('<td>' + json[i].numar + '</td>');
                tr.push('<td>' + json[i].snils + '</td>');
                tr.push('<td>' + json[i].polis + '</td>');
                tr.push('</tr>');
                }
                $('table').append($(tr.join('')));
            });
        });
    };

    function getCountDocuments() {
        return new Promise((resolve, reject) => {
            $.getJSON(protocol + '//' + hostname + ':' + port + '/web/documents/count')
                .done(function(json) {
                    var count = json;
                    resolve(count); 
                })
                .fail(function(error) {
                    console.error("Ошибка при получении данных:", error);
                    reject(error); 
                });
        });
    }

    /**
     * Нумерация страниц
     */
    async function switchTable() {
        let totalDocuments = await getCountDocuments();
        i = 2;
        $('#currentPage').text(1);
        $(document.getElementById("Previous")).on( "click",function(){
            if( i < 2 ){
                i = 1;
            }else{
                i--;
            }
            $('#currentPage').text(i);
            $('tbody:even').empty();
            lazyDocument(i, 15);
        });

        $(document.getElementById("Next")).on( "click",function(){
            if( document.querySelectorAll('#tableDocument tbody tr').length < 15 ){
                i;
            }else{
                i++;
            }
            $('#currentPage').text(i);
            $('tbody:even').empty();
            lazyDocument(i, 15);
        });

        $(document.getElementById("first")).on( "click",function(){
            i = 1;
            $('#currentPage').text(i);
            $('tbody:even').empty();
            lazyDocument(i, 15);
        });

        $(document.getElementById("second")).on( "click",function(){
            i = 2;
            $('#currentPage').text(i);
            $('tbody:even').empty();
            lazyDocument(i, 15);
        });

        $(document.getElementById("third")).on( "click",function(){
            i = 3;
            $('#currentPage').text(i);
            $('tbody:even').empty();
            lazyDocument(i, 15);
        });
                
        $(document.getElementById("last")).on("click", function() {
            $('tbody:even').empty();
            i  = Math.ceil(totalDocuments / 15);
            $('#currentPage').text(i);
            lazyDocument(i, 15);
        });
    }


    

    function listEM() {
        $(document).ready(function() {
        $('table tbody').on('mousedown', 'tr', function(e) {
            $(this).addClass('highlight').siblings().removeClass('highlight');
        });
        $.getJSON('http://127.0.0.1:8080/rest/api/entitymanager', function(json) {
            console.log(json.data );
            var tr=[];
            for (var i = 0; i < json.data.length; i++) {
                tr.push('<tr>');
                tr.push('<td>' + json.data[i].idUser + '</td>');
                tr.push('<td>' + json.data[i].name + '</td>');
                tr.push('<td>' + json.data[i].login + '</td>');
                tr.push('<td>' + json.data[i].phone + '</td>');
                tr.push('<td>' + json.data[i].wallet + '</td>');
                tr.push('</tr>');
                }
                $('table').append($(tr.join('')));
            });
        });
    };