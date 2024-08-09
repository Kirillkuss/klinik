function findByWordDoctor() {
    $(document.getElementById("findByWordDoctor")).on( "click",function(){
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
                url: "http://localhost:8082/web/doctors/fio/{word}{page}{size}",
                data:{ word: word, page: 1, size: 100 } ,
                cache: false,
                success: function( json ) {
                    var tr=[];
                    for (var i = 0; i < json.length; i++) {
                        tr.push('<tr>');
                        tr.push('<td>' + json[i].idDoctor + '</td>');
                        tr.push('<td>' + json[i].surname + '</td>');
                        tr.push('<td>' + json[i].name + '</td>');
                        tr.push('<td>' + json[i].fullName + '</td>');
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
     * Ленивая загрука
     * @param {*} page - страница 
     * @param {*} size - размер
     */
function lazyDoctors( page, size) {
    $(document).ready(function() {
    $('table tbody').on('mousedown', 'tr', function(e) {
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
     //$.post('https://localhost:8082/web/doctors/lazy?page='+page+'&size='+size, function(json) {
        $.get('http://localhost:8082/web/doctors/?page='+page+'&size='+size, function(json) {
        var tr=[];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].idDoctor + '</td>');
            tr.push('<td>' + json[i].surname + '</td>');
            tr.push('<td>' + json[i].name + '</td>');
            tr.push('<td>' + json[i].fullName + '</td>');
            tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
    });
    };
/**
 * Добавить документ
 */
function AddDoctor() {
    $("#testFormDoctor").submit( function (event){
        event.preventDefault();
        var idDoctor =  $('#idDoctor').val();
        var surname = $('#surname').val();
        var name = $('#name').val();
        var fullName = $('#fullName').val();
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "http://localhost:8082/web/doctors/add",
                data: JSON.stringify({idDoctor: idDoctor, surname: surname, name: name, fullName: fullName}),
                cache: false,
                success: function( json ) {
                    var tr=[];
                    tr.push('<tr>');
                    tr.push('<td>' + json.idDoctor + '</td>');
                    tr.push('<td>' + json.surname + '</td>');
                    tr.push('<td>' + json.name + '</td>');
                    tr.push('<td>' + json.fullName + '</td>');
                    tr.push('</tr>');
                    tr.push('</tr>');
                    $('table').append($(tr.join('')));
                    var modal = document.getElementById('testFormDoctor');
                    modal.style.display = 'none';
                   //    location.reload();
                }, error: function ( error ){
                    $('#errorToast').text( error.responseText ).show();
                    $('#liveToastBtn').click();
                }
            });
        });
    };

    function switchTable(){
        i = 2;
        $(document.getElementById("PreviousDoctor")).on( "click",function(){
            if( i < 2 ){
                i = 1;
            }else{
                i--;
            }
            $('tbody:even').empty();
            lazyDoctors(i, 15);
        });
    
        $(document.getElementById("NextDoctor")).on( "click",function(){
            if( document.querySelectorAll('#tableDoctor tbody tr').length < 15 ){
                i;
            }else{
                i++;
            }
            $('tbody:even').empty();
            lazyDoctors(i, 15);
        });
    
        $(document.getElementById("firstDoctor")).on( "click",function(){
            i = 1;
            $('tbody:even').empty();
            lazyDoctors(i, 15);
        });
    
        $(document.getElementById("secondDoctor")).on( "click",function(){
            i = 2;
            $('tbody:even').empty();
            lazyDoctors(i, 15);
        });
    
        $(document.getElementById("thirdDoctor")).on( "click",function(){
            i = 3;
            $('tbody:even').empty();
            lazyDoctors (i, 15);
        });
    }