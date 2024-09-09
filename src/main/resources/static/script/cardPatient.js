var protocol = window.location.protocol
var hostname = window.location.hostname;
var port = window.location.port;
/**
 * Ленивый поиск карт пациентов
 * @param {*} page - страница
 * @param {*} size - размер
 */
function lazyCard( page, size) {
    $(document).ready(function() {
    $('table tbody').on('mousedown', 'tr', function(e) {
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
    $.getJSON( window.location.protocol + '//'+ hostname + ':' + port +'/web/card-patinets/lazy/{page}{size}?page='+page+'&size='+size, function(json) {
        var tr=[];
        $('tbody').empty();
        var startIndex = (page - 1) * size + 1;
        for (var i = 0; i < json.length; i++) {
            var rowNumber = startIndex + i;
            tr.push('<tr>');
            tr.push('<td>' + rowNumber + '</td>');
            tr.push('<td>' + json[i].diagnosis + '</td>');
            if (json[i].allergy === true) {
                tr.push('<td>' + "Да" + '</td>');
            } else {
                tr.push('<td>' + "Нет" + '</td>');
            }
            tr.push('<td>' + json[i].note + '</td>');
            tr.push('<td>' + json[i].сonclusion + '</td>');
            tr.push('<td>' + json[i].patient.surname + ' ' + json[i].patient.name + ' ' + json[i].patient.fullName + '</td>');
            tr.push('<td>' + json[i].patient.phone + '</td>');
            tr.push('<td>' + json[i].patient.address + '</td>');
            tr.push('<td>' + json[i].patient.document.numar + '</td>');
            tr.push('<td>' + json[i].patient.document.snils + '</td>');
            tr.push('<td>' + json[i].patient.document.polis + '</td>');
            tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
    });
};

/**
 * Добавить карту пациента
 */
function AddCardPatient() {
    $("#FormCard").submit( function (event){
        event.preventDefault();
        var idPatient  = $('#idPatient').val();
        var diagnosis  = $('#diagnosis').val();
        var allergy    = $('#allergy').val();
        var note       = $('#note').val();
        var сonclusion = $('#сonclusion').val();

            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: window.location.protocol + "//"+ hostname  +":" + port +"/web/card-patinets/add?idPatient=" + idPatient,
                data: JSON.stringify ({diagnosis: diagnosis,
                                       allergy: allergy,
                                       note: note,
                                       сonclusion: сonclusion}), 
                cache: false,
                success: function( json ) {
                    $('#exampleModal').modal('hide');
                    $('.modal-backdrop').remove(); 
                    $('#LastCard').click();
                }, error: function ( error ){
                    const response = JSON.parse(error.responseText);
                    $('#errorToast').text( response.message ).show();
                    $('#liveToastBtn').click();
                }
        });
    });
};
/**
 * Поиск карты по параметрам
 */
function findByDocumentCard() {
    $(document.getElementById("findByCardPatient")).on( "click",function(){
        var word = $('#wordParam').val();
        if(  word.length  == 0 ){
            $('#errorToast').text( 'Значение поля поиск не может быть пустым' ).show();
            $('#liveToastBtn').click();
        }else{
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: window.location.protocol +"//"+ hostname +":" + port +"/web/card-patinets/document",
                data:{ word: word } ,
                cache: false,
                success: function( json ) {
                    var tr=[];
                        tr.push('<tr>');
                        tr.push('<td>' + 1 + '</td>');
                        tr.push('<td>' + json.diagnosis + '</td>');
                        tr.push('<td>' + json.allergy + '</td>');
                        tr.push('<td>' + json.note + '</td>');
                        tr.push('<td>' + json.сonclusion + '</td>');
                        tr.push('<td>' + json.patient.surname + ' ' + json.patient.name + ' ' + json.patient.fullName + '</td>');
                        tr.push('<td>' + json.patient.phone + '</td>');
                        tr.push('<td>' + json.patient.address + '</td>');
                        tr.push('<td>' + json.patient.document.numar + '</td>');
                        tr.push('<td>' + json.patient.document.snils + '</td>');
                        tr.push('<td>' + json.patient.document.polis + '</td>');
                        tr.push('</tr>');
                    $('tbody:even').empty();
                    $('table').prepend($(tr.join('')));
                }, error: function ( error ){
                    const response = JSON.parse(error.responseText);
                    $('#errorToast').text( response.message ).show();
                    $('#liveToastBtn').click();
                }
            });
        }
    });	
};

function getCountCard() {
    return new Promise((resolve, reject) => {
        $.getJSON(protocol + '//' + hostname + ':' + port + '/web/card-patinets/count')
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
async function switchTable(){
    let totalCard = await getCountCard();
    i = 2;
    $('#currentPage').text(1);
    $(document.getElementById("PreviousCard")).on( "click",function(){
        if( i < 2 ){
            i = 1;
        }else{
            i--;
        }
        $('#currentPage').text(i);
        $('tbody:even').empty();
        lazyCard(i, 9);
    });

    $(document.getElementById("NextCard")).on( "click",function(){
        if( document.querySelectorAll('#tableCard tbody tr').length < 9 ){
            i;
        }else{
            i++;
        }
        $('#currentPage').text(i);
        $('tbody:even').empty();
        lazyCard(i, 9);
    });

    $(document.getElementById("FirstCard")).on( "click",function(){
        i = 1;
        $('tbody:even').empty();
        $('#currentPage').text(i);
        lazyCard(i, 9);
    });

    $(document.getElementById("SecondCard")).on( "click",function(){
        i = 2;
        $('tbody:even').empty();
        $('#currentPage').text(i);
        lazyCard(i, 9);
    });

    $(document.getElementById("ThirdCard")).on( "click",function(){
        i = 3;
        $('tbody:even').empty();
        $('#currentPage').text(i);
        lazyCard(i, 9);
    });

    $(document.getElementById("LastCard")).on("click", function() {
        $('tbody:even').empty();
        i  = Math.ceil(totalCard / 9);
        $('#currentPage').text(i);
        lazyCard(i, 9);
    });
}