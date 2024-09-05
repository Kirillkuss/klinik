var protocol = window.location.protocol
var hostname = window.location.hostname;
var port = window.location.port;

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
                url: protocol + "//"+ hostname + ":" + port + "/web/doctors/fio/{word}{page}{size}",
                data:{ word: word, page: 1, size: 100 } ,
                cache: false,
                success: function( json ) {     
                    var tr=[];
                    for (var i = 0; i < json.length; i++) {
                        var rowNumber = 1 + i;
                        tr.push('<tr>');
                        tr.push('<td>' + rowNumber + '</td>');
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
     $.post( protocol + '//'+ hostname + ':' + port +'/web/doctors/lazy?page='+page+'&size='+size, function(json) {
       // $.get('http://localhost:8082/web/doctors/?page='+page+'&size='+size, function(json) {
        var tr=[];
        $('tbody').empty();
        var startIndex = (page - 1) * size + 1;
        for (var i = 0; i < json.length; i++) {
            var rowNumber = startIndex + i;
            tr.push('<tr>');
            tr.push('<td>' + rowNumber + '</td>');
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
    $("#testFormDoctor").submit(function (event) {
        event.preventDefault();
        if (this.checkValidity() === false) {
            return;
        }

        var surname = $('#surname').val();
        var name = $('#name').val();
        var fullName = $('#fullName').val();
        
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: protocol + "//" + hostname + ':' + port + "/web/doctors/add",
            data: JSON.stringify({ surname: surname, name: name, fullName: fullName }),
            cache: false,
            success: function (json) {
                var tr = [];
                tr.push('<tr>');
                tr.push('<td>' + 1111 + '</td>');
                tr.push('<td>' + json.surname + '</td>');
                tr.push('<td>' + json.name + '</td>');
                tr.push('<td>' + json.fullName + '</td>');
                tr.push('</tr>');
                $('table').append($(tr.join('')));
                $('#exampleModal').modal('hide');
                $('.modal-backdrop').remove(); 
                lazyDoctors(1, 15);
            },
            
            error: function (error) {
                $('#errorToast').text(error.responseText).show();
                $('#liveToastBtn').click();
            }
           
        });
    });
}

        function getCountDoctor() {
            return new Promise((resolve, reject) => {
                $.getJSON(protocol + '//' + hostname + ':' + port + '/web/doctors/counts')
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

        async function switchTable() {
            let totalDoctors = await getCountDoctor();
            let i = 2; 
            $(document.getElementById("PreviousDoctor")).on("click", function() {
                if (i < 2) {
                    i = 1;
                } else {
                    i--;
                }
                $('tbody:even').empty();
                lazyDoctors(i, 15);
            });
        
            $(document.getElementById("NextDoctor")).on("click", function() {
                if (document.querySelectorAll('#tableDoctor tbody tr').length < 15) {
                } else {
                    i++;
                }
                $('tbody:even').empty();
                lazyDoctors(i, 15);
            });
        
            $(document.getElementById("firstDoctor")).on("click", function() {
                i = 1;
                $('tbody:even').empty();
                lazyDoctors(i, 15);
            });
        
            $(document.getElementById("secondDoctor")).on("click", function() {
                i = 2;
                $('tbody:even').empty();
                lazyDoctors(i, 15);
            });
        
            $(document.getElementById("thirdDoctor")).on("click", function() {
                i = 3;
                $('tbody:even').empty();
                lazyDoctors(i, 15);
            });
        
            $(document.getElementById("lastDoctor")).on("click", function() {
                $('tbody:even').empty();
                i  = Math.ceil(totalDoctors / 15);
                lazyDoctors(i, 15);
            });
        }
        