/**
 * Ленивый поиск пациентов
 * @param {*} page - страница
 * @param {*} size - размер
 */
function lazyPatients( page, size) {
    $(document).ready(function() {
    $('table tbody').on('mousedown', 'tr', function(e) {
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
    $.getJSON('http://localhost:8082/web/patients/list/{page}{size}?page='+page+'&size='+size, function(json) {
        var tr=[];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].idPatient + '</td>');
            tr.push('<td>' + json[i].surname + '</td>');
            tr.push('<td>' + json[i].name + '</td>');
            tr.push('<td>' + json[i].fullName + '</td>');
            tr.push('<td>' + json[i].gender + '</td>');
            tr.push('<td>' + json[i].phone + '</td>');
            tr.push('<td>' + json[i].address + '</td>');
            tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
    });
};
/**
 * Список всех пациентов
 */
function listPatient() {
    $(document).ready(function() {
    $('table tbody').on('mousedown', 'tr', function(e) {
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
    $.getJSON('http://localhost:8082/web/patients/all', function(json) {
        var tr=[];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].idPatient + '</td>');
            tr.push('<td>' + json[i].surname + '</td>');
            tr.push('<td>' + json[i].name + '</td>');
            tr.push('<td>' + json[i].fullName + '</td>');
            tr.push('<td>' + json[i].gender + '</td>');
            tr.push('<td>' + json[i].phone + '</td>');
            tr.push('<td>' + json[i].address + '</td>');
            tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
    });
};
/**
 * Добавить пациента
 */
function AddPatient() {
    $("#testFormPatient").submit( function (event){
        event.preventDefault();
        var idPatient  = $('#idPatient').val();
        var surname    = $('#surname').val();
        var name       = $('#name').val();
        var fullName   = $('#fullName').val();
        var gender     = $('#gender').val();
        var phone      = $('#phone').val();
        var address    = $('#address').val();
        var idDocument = $('#idDocument').val();
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "http://localhost:8082/web/patients/add/{pat}{id-document}?id=" + idDocument,
                data: JSON.stringify ({idPatient: idPatient,
                                      surname: surname,
                                      name: name,
                                      fullName: fullName,
                                      gender: gender,
                                      phone: phone,
                                      address: address}), 
                cache: false,
                success: function( json ) {
                    var tr=[];
                    tr.push('<tr>');
                    tr.push('<td>' + json.idPatient + '</td>');
                    tr.push('<td>' + json.surname + '</td>');
                    tr.push('<td>' + json.name + '</td>');
                    tr.push('<td>' + json.fullName + '</td>');
                    tr.push('<td>' + json.gender + '</td>');
                    tr.push('<td>' + json.phone + '</td>');
                    tr.push('<td>' + json.address + '</td>');
                    tr.push('</tr>');
                    location.reload();
                }, error: function ( error ){
                    $('#errorToast').text( error.responseText ).show();
                    $('#liveToastBtn').click();
                }
        });
    });
};
/**
 * Поиск пациента по параметрам
 */
function findByWordPatient() {
    $(document.getElementById("findByPatient")).on( "click",function(){
        var word = $('#wordParam').val();
        if(  word.length  == 0 ){
            $('tbody:even').empty();
            lazyPatients(1, 15);
            $('#errorToast').text( 'Значение поля поиск не может быть пустым' ).show();
            $('#liveToastBtn').click();
        }else{
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: "http://localhost:8082/web/patients/find/{word}",
                data:{ word: word } ,
                cache: false,
                success: function( json ) {
                    var tr=[];
                    for (var i = 0; i < json.length; i++) {
                        tr.push('<tr>');
                        tr.push('<td>' + json[i].idPatient + '</td>');
                        tr.push('<td>' + json[i].surname + '</td>');
                        tr.push('<td>' + json[i].name + '</td>');
                        tr.push('<td>' + json[i].fullName + '</td>');
                        tr.push('<td>' + json[i].gender + '</td>');
                        tr.push('<td>' + json[i].phone + '</td>');
                        tr.push('<td>' + json[i].address + '</td>');
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
 * Нумерация страниц
 */
function switchTable(){
    i = 2;
    $(document.getElementById("PreviousPatient")).on( "click",function(){
        if( i < 2 ){
            i = 1;
        }else{
            i--;
        }
        $('tbody:even').empty();
        lazyPatients(i, 15);
    });

    $(document.getElementById("NextPatient")).on( "click",function(){
        if( document.querySelectorAll('#tablePatient tbody tr').length < 15 ){
            i;
        }else{
            i++;
        }
        $('tbody:even').empty();
        lazyPatients(i, 15);
    });

    $(document.getElementById("firstPatient")).on( "click",function(){
        i = 1;
        $('tbody:even').empty();
        lazyPatients(i, 15);
    });

    $(document.getElementById("secondPatient")).on( "click",function(){
        i = 2;
        $('tbody:even').empty();
        lazyPatients(i, 15);
    });

    $(document.getElementById("thirdPatient")).on( "click",function(){
        i = 3;
        $('tbody:even').empty();
        lazyPatients(i, 15);
    });
}