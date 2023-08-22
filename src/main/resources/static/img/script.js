    function findByIdDocument() {
        $(document.getElementById("findByIdDocument")).on( "click",function(){
            var id = $('#idFound').val();
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: "http://localhost:8081/web/documents/find",
                    data:{ id: id } ,
                    cache: false,
                    success: function( json ) {
                        var tr=[];
                        tr.push('<tr>');
                        tr.push('<td>' + json.idDocument + '</td>');
                        tr.push('<td>' + json.typeDocument + '</td>');
                        tr.push('<td>' + json.seria + '</td>');
                        tr.push('<td>' + json.numar + '</td>');
                        tr.push('<td>' + json.snils + '</td>');
                        tr.push('<td>' + json.polis + '</td>');
                        tr.push('</tr>');
                        $('tbody:even').empty();
                        $('table').prepend($(tr.join('')));
                    }, error: function ( error ){
                        $('#errorToast').text( error.responseText ).show();
                        $('#liveToastBtn').click();
                    }
                });
        });	
    };

    function findByWordPatient() {
        $(document.getElementById("findByPatient")).on( "click",function(){
            var word = $('#wordParam').val();
                $.ajax({
                    type: "GET",
                    contentType: "application/json; charset=utf-8",
                    url: "http://localhost:8081/web/patients/find/{word}",
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
        });	
    };

    function listDocument() {
        $(document).ready(function() {
        $('table tbody').on('mousedown', 'tr', function(e) {
            $(this).addClass('highlight').siblings().removeClass('highlight');
        });
        $.getJSON('http://localhost:8081/web/documents/list', function(json) {
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

    function AddDocument() {
        $("#testForm").submit( function (event){
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
                    url: "http://localhost:8081/web/documents/add/{docum}",
                    data: JSON.stringify({idDocument: idDocument, typeDocument: typeDocument, seria: seria, numar: numar, snils: snils, polis: polis}),
                    cache: false,
                    success: function( json ) {
                        var tr=[];
                        tr.push('<tr>');
                        tr.push('<td>' + json.idDocument + '</td>');
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



        function listPatient() {
            $(document).ready(function() {
            $('table tbody').on('mousedown', 'tr', function(e) {
                $(this).addClass('highlight').siblings().removeClass('highlight');
            });
            $.getJSON('http://localhost:8081/web/patients/all', function(json) {
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



        function AddPatient() {
            $("#testForm").submit( function (event){
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
                        url: "http://localhost:8081/web/patients/add/{pat}{id-document}?id=" + idDocument,
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
                            tr.push('<td>' + json[i].idPatient + '</td>');
                            tr.push('<td>' + json[i].surname + '</td>');
                            tr.push('<td>' + json[i].name + '</td>');
                            tr.push('<td>' + json[i].fullName + '</td>');
                            tr.push('<td>' + json[i].gender + '</td>');
                            tr.push('<td>' + json[i].phone + '</td>');
                            tr.push('<td>' + json[i].address + '</td>');
                            tr.push('</tr>');
                            
                            location.reload();
                        }, error: function ( error ){
                            $('#errorToast').text( error.responseText ).show();
                            $('#liveToastBtn').click();
                        }
                    });
                });
            };
        